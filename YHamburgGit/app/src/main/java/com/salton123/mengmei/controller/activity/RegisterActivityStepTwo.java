package com.salton123.mengmei.controller.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.orhanobut.logger.Logger;
import com.salton123.base.ActivityFrameIOS;
import com.salton123.base.GlobalParams;
import com.salton123.mengmei.model.bean.User;
import com.salton123.common.util.LogUtils;
import com.salton123.common.util.PhotoUtil;
import com.salton123.common.widget.CircleImageView;
import com.salton123.mengmei.R;
import com.salton123.mengmei.model.engine.BmobDataEngine;

import org.xutils.x;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultSubscriber;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2015/12/22 15:56
 * Time: 15:56
 * Description:
 */
public class RegisterActivityStepTwo extends ActivityFrameIOS {


    private String mPhoneNum;
    private String mPicUrl;
    private String mProvince;
    private String mPassword;

    private CircleImageView image_head;
    private ProgressDialog mProgressDialog;
    private TextView tv_province;
    private EditText input_name, input_jobtitle;
    private BootstrapButton register_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppendMainBody(R.layout.activity_register_step_two);
        SetTopBackHint("返回");
        SetTopTitle("完善资料");
        mPhoneNum = getIntent().getStringExtra("phoneNum");
        mPassword = getIntent().getStringExtra("password");
        LogUtils.i(mPhoneNum + ":" + mPassword);
    }

    @Override
    public void InitView() {
        image_head = (CircleImageView) findViewById(R.id.image_head);
        tv_province = (TextView) findViewById(R.id.tv_province);
        input_name = (EditText) findViewById(R.id.input_name);
        input_jobtitle = (EditText) findViewById(R.id.input_jobtitle);
        register_btn = (BootstrapButton) findViewById(R.id.register_btn);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(getString(R.string.label_uploading));
        mProgressDialog.setCancelable(true);
    }

    public String filePath = "";

    private void showAvatarPop() {
        new AlertDialog.Builder(this).setTitle("设置头像").setSingleChoiceItems(getResources().getStringArray(R.array.head_choose), -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String sexStr = null;
                switch (which) {
                    case 0:
                        File dir = new File(GlobalParams.DIRECTORY_AVATAR);
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        // 原图
                        File file = new File(dir, new SimpleDateFormat("yyMMddHHmmss")
                                .format(new Date()));
                        filePath = file.getAbsolutePath();// 获取相片的保存路径
                        Uri imageUri = Uri.fromFile(file);
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intent,
                                GlobalParams.REQUESTCODE_UPLOAD_CAMERA);
                        break;
                    case 1:
                        Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                        intent1.setDataAndType(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent1,
                                GlobalParams.REQUESTCODE_UPLOAD_LOCAL);
                        break;
                }
                dialog.dismiss();
            }
        }).create().show();

    }

    boolean isFromCamera = false;// 区分拍照旋转
    int degree = 0;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GlobalParams.REQUESTCODE_UPLOAD_CAMERA:// 拍照修改头像
                if (resultCode == RESULT_OK) {
                    if (!Environment.getExternalStorageState().equals(
                            Environment.MEDIA_MOUNTED)) {
                        ShowToast(getString(R.string.label_sdcard_error));
                        return;
                    }
                    isFromCamera = true;
                    File file = new File(filePath);
                    degree = PhotoUtil.readPictureDegree(file.getAbsolutePath());
//                    Log.i("life", "拍照后的角度：" + degree);
                    startImageAction(Uri.fromFile(file), 200, 200,
                            GlobalParams.REQUESTCODE_UPLOAD_CROP, true);
                }
                break;
            case GlobalParams.REQUESTCODE_UPLOAD_LOCAL:// 本地修改头像
                Uri uri = null;
                if (data == null) {
                    return;
                }
                if (resultCode == RESULT_OK) {
                    if (!Environment.getExternalStorageState().equals(
                            Environment.MEDIA_MOUNTED)) {
                        ShowToast(getString(R.string.label_sdcard_error));
                        return;
                    }
                    isFromCamera = false;
                    uri = data.getData();
                    startImageAction(uri, 200, 200,
                            GlobalParams.REQUESTCODE_UPLOAD_CROP, true);
                } else {
                    ShowToast(getString(R.string.label_internal_error_2));

                }

                break;
            case GlobalParams.REQUESTCODE_UPLOAD_CROP:// 裁剪头像返回
                if (data == null) {
                    // Toast.makeText(this, "取消选择", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    saveCropAvator(data);
                }
                // 初始化文件路径
                filePath = "";
                // 上传头像
                uploadAvatar();
                break;
            case 101://选择省份
                if (resultCode == RESULT_OK) {
                    String province = data.getExtras().getString("province");
                    tv_province.setText(province + "");
                    mProvince = province;
                    LogUtils.i(province);
                }
                break;
            default:
                break;
        }
    }


    String imagePath;

    /**
     * 保存裁剪的头像
     *
     * @param data
     */
    private void saveCropAvator(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap bitmap = extras.getParcelable("data");
            Log.i("life", "avatar - bitmap = " + bitmap);
            if (bitmap != null) {
                bitmap = PhotoUtil.toRoundCorner(bitmap, 10);
                if (isFromCamera && degree != 0) {
                    bitmap = PhotoUtil.rotaingImageView(degree, bitmap);
                }
                image_head.setImageBitmap(bitmap);
                // 保存图片
                String filename = new SimpleDateFormat("yyMMddHHmmss")
                        .format(new Date()) + ".jpg";
                imagePath = GlobalParams.DIRECTORY_AVATAR + filename;
                PhotoUtil.saveBitmap(GlobalParams.DIRECTORY_AVATAR, filename,
                        bitmap, true);
                // 上传头像
                if (bitmap != null && bitmap.isRecycled()) {
                    bitmap.recycle();
                }
            }
        }
    }

    /**
     * @return void
     * @throws
     * @Title: startImageAction
     */
    private void startImageAction(Uri uri, int outputX, int outputY,
                                  int requestCode, boolean isCrop) {
        Intent intent = null;
        if (isCrop) {
            intent = new Intent("com.android.camera.action.CROP");
        } else {
            intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        }
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, requestCode);
    }

    private void uploadAvatar() {
//    Logger.e(imagePath);
//        System.out.println(imagePath);
        final BmobFile bmobFile = new BmobFile(new File(imagePath));
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    //bmobFile.getFileUrl()--返回的上传文件的完整地址
                    if (TextUtils.isEmpty((String) bmobFile.getFileUrl())) {
                        Toast.makeText(GetContext(), getString(R.string.label_file_upload_failed), Toast.LENGTH_LONG).show();
                        return;
                    }
                    //把头像更新一下
                    mPicUrl = (String) bmobFile.getFileUrl();
                    Logger.e(mPicUrl);
                    x.image().bind(image_head, mPicUrl);
//                   runOnUiThread(new Runnable() {
//                       @Override
//                       public void run() {
//                           User _user = BmobUser.getCurrentUser(User.class);
//                           _user.setAvatar(mPicUrl);
//                           _user.update(new UpdateListener() {
//                               @Override
//                               public void done(BmobException e) {
//                                   if (e == null) {
//                                       x.image().bind(image_head, mPicUrl);
//                                   } else {
//                                       ShowToast("文件上传失败");
//                                   }
//                               }
//                           });
//                       }
//                   });

                } else {
                    ShowToast("文件上传失败了");
                }
                mProgressDialog.dismiss();
            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
            }
        });

    }

    @Override
    public void InitListener() {
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mPhoneNum)) {
                    ShowToast("手机号有误，请重试");
                    return;
                }
                if (TextUtils.isEmpty(mPassword)) {
                    ShowToast("密码有误，请重试");
                    return;
                }
                if (TextUtils.isEmpty(mPicUrl)) {
                    ShowToast("请设置头像");
                    return;
                }
                if (TextUtils.isEmpty(mProvince)) {
                    ShowToast("请选择省份");
                    return;
                }
                final String nickname = input_name.getText().toString().trim();
                if (TextUtils.isEmpty(nickname)) {
                    ShowToast("请填写您的真实姓名");
                    return;
                }
                final String signature = input_jobtitle.getText().toString().trim();
                if (TextUtils.isEmpty(signature)) {
                    ShowToast("请填写您的职称");
                    return;
                }

                BmobDataEngine.RegisterWithInfo(mPhoneNum, mPassword, signature, mPicUrl, mProvince, nickname, new SaveListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (e == null) {
                            ShowToast("注册成功");
                            finish();
                            OpenActivity(LoginActivity.class);
                        } else {
                            ShowToast("注册失败:" + e.getErrorCode());
                        }
                    }
                });
            }

        });

        tv_province.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenActivityForResult(SelectProvinceActivity.class, 101);
            }
        });
        image_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showAvatarPop();
                RxGalleryFinal
                        .with(RegisterActivityStepTwo.this)
                        .image()
                        .radio()
                        .crop()
                        .cropropCompressionQuality(50)
                        .cropMaxResultSize(200,200)
                        .imageLoader(ImageLoaderType.GLIDE)
                        .subscribe(new RxBusResultSubscriber<ImageRadioResultEvent>() {
                            @Override
                            protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
                                System.out.println("location2：" + imageRadioResultEvent.getResult().getCropPath());
                                Toast.makeText(getBaseContext(), imageRadioResultEvent.getResult().getCropPath(), Toast.LENGTH_SHORT).show();
                                final BmobFile bmobFile = new BmobFile(new File(imageRadioResultEvent.getResult().getCropPath()));
                                bmobFile.uploadblock(new UploadFileListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            if (TextUtils.isEmpty((String) bmobFile.getFileUrl())) {
                                                Toast.makeText(GetContext(), getString(R.string.label_file_upload_failed), Toast.LENGTH_LONG).show();
                                                return;
                                            }
                                            mPicUrl = (String) bmobFile.getFileUrl();
                                            x.image().bind(image_head, mPicUrl);
                                        } else {
                                            Toast.makeText(getApplicationContext(), "文件上传失败:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                        mProgressDialog.dismiss();
                                    }

                                    @Override
                                    public void onProgress(Integer value) {
                                        // 返回的上传进度（百分比）
                                    }
                                });

                            }
                        })
                        .openGallery();
            }
        });

    }

    @Override
    public void InitData() {

    }
}
