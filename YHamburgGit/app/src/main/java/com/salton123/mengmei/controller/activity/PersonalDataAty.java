package com.salton123.mengmei.controller.activity;

import android.app.AlertDialog;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.salton123.base.ActivityFrameIOS;
import com.salton123.base.GlobalParams;
import com.salton123.mengmei.model.bean.User;
import com.salton123.common.util.LogUtils;
import com.salton123.common.util.PhotoUtil;
import com.salton123.mengmei.R;

import org.xutils.x;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultSubscriber;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;


/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2016/2/2 18:05
 * Time: 18:05
 * Description:
 */
public class PersonalDataAty extends ActivityFrameIOS {

    private TextView tv_signature, tv_area, tv_contact, tv_birthday, tv_sex, tv_nickname;
    private ImageView iv_headIcon;
    public static final int LOGIN_REQUEST_CODE = 0x100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppendMainBody(R.layout.aty_personal_data);
        SetTopTitle("个人资料");
    }


    @Override
    public void InitView() {
        tv_signature = (TextView) findViewById(R.id.tv_signature);
        tv_area = (TextView) findViewById(R.id.tv_area);
        tv_contact = (TextView) findViewById(R.id.tv_contact);
        tv_birthday = (TextView) findViewById(R.id.tv_birthday);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        tv_nickname = (TextView) findViewById(R.id.tv_nickname);
        iv_headIcon = (ImageView) findViewById(R.id.iv_headIcon);

    }

    @Override
    public void InitListener() {
//        findViewById(R.id.tv_address).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                OpenActivity(ConsigneeAty.class);
//            }
//        });
        iv_headIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showAvatarPop();
                RxGalleryFinal
                        .with(PersonalDataAty.this)
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
                                            x.image().bind(iv_headIcon, mPicUrl);
                                        } else {
                                            Toast.makeText(getApplicationContext(), "文件上传失败:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
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

        tv_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSex();
            }
        });

        tv_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateArea();
            }
        });

        tv_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBirthday();
            }
        });

        tv_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateContact();
            }
        });

        tv_signature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSignature();
            }
        });

        tv_nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNick();
            }
        });
        tv_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    private void updateSignature() {
        final AlertDialog.Builder _builder = new AlertDialog.Builder(PersonalDataAty.this);
        final View _view = GetLayouInflater().inflate(R.layout.dialog_input, null);
        _builder.setTitle("个性签名").setView(_view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText et_input = (EditText) _view.findViewById(R.id.et_input);
                final String input = et_input.getText().toString().trim();
                User _user = BmobUser.getCurrentUser(User.class);
                _user.setSignature(input);
                _user.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            tv_signature.setText(input);
                        } else {
                            ShowToast("更新个性签名失败！" + e.getMessage());
                        }
                    }
                });
                dialog.dismiss();
            }
        }).setNegativeButton("取消", null);
        _builder.show();

    }

    private void updateContact() {
        final AlertDialog.Builder _builder = new AlertDialog.Builder(PersonalDataAty.this);
        final View _view = GetLayouInflater().inflate(R.layout.dialog_input, null);
        _builder.setTitle("联系号码").setView(_view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText et_input = (EditText) _view.findViewById(R.id.et_input);
                final String input = et_input.getText().toString().trim();
                User _user = BmobUser.getCurrentUser(User.class);
                _user.setPhoneNum(input);
                _user.update(_user.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            tv_contact.setText(input);
                        } else {
                            e.printStackTrace();
                            ShowToast("更新联系号码失败！" + e.getMessage());
                        }
                    }
                });

                dialog.dismiss();
            }
        }).setNegativeButton("取消", null);
        _builder.show();

    }


    private void updateBirthday() {
        final AlertDialog.Builder _builder = new AlertDialog.Builder(PersonalDataAty.this);
        final View _view = GetLayouInflater().inflate(R.layout.dialog_input, null);
        _builder.setTitle("生日").setView(_view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText et_input = (EditText) _view.findViewById(R.id.et_input);
                final String input = et_input.getText().toString().trim();
                User _user = BmobUser.getCurrentUser(User.class);
                _user.setBirthday(input);
                _user.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            tv_birthday.setText(input);
                        } else {
                            ShowToast("更新生日失败！" + e.getMessage());
                        }
                    }
                });
                dialog.dismiss();
            }
        }).setNegativeButton("取消", null);
        _builder.show();

    }


    private void updateNick() {
        final AlertDialog.Builder _builder = new AlertDialog.Builder(PersonalDataAty.this);
        final View _view = GetLayouInflater().inflate(R.layout.dialog_input, null);
        _builder.setTitle("昵称").setView(_view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText et_input = (EditText) _view.findViewById(R.id.et_input);
                final String input = et_input.getText().toString().trim();
                User _user = BmobUser.getCurrentUser(User.class);
                _user.setNickname(input);
                _user.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            tv_nickname.setText(input);
                        } else {
                            ShowToast("更新昵称失败！" + e.getMessage());
                        }
                    }
                });
                dialog.dismiss();
            }
        }).setNegativeButton("取消", null);
        _builder.show();

    }


    private void updateArea() {
        final AlertDialog.Builder _builder = new AlertDialog.Builder(PersonalDataAty.this);
        final View _view = GetLayouInflater().inflate(R.layout.dialog_input, null);
        _builder.setTitle("区域").setView(_view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText et_input = (EditText) _view.findViewById(R.id.et_input);
                final String input = et_input.getText().toString().trim();
                User _user = BmobUser.getCurrentUser(User.class);
                _user.setDistrict(input);
                _user.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            tv_area.setText(input);
                        } else {
                            e.printStackTrace();
                            ShowToast("更新区域失败！" + e.getMessage());
                        }
                    }
                });
                dialog.dismiss();
            }
        }).setNegativeButton("取消", null);
        _builder.show();

    }

    @Override
    public void InitData() {
//        User _user = BmobUser.getCurrentUser(User.class);
        User _user  = (User) getIntent().getSerializableExtra("user");
        if (_user != null) {
            tv_signature.setText(_user.getSignature());
            x.image().bind(iv_headIcon, _user.getAvatar());
            tv_area.setText(_user.getDistrict());
            tv_contact.setText(_user.getPhoneNum());
            tv_sex.setText(_user.getSex());
            tv_birthday.setText(_user.getBirthday());
            tv_nickname.setText(_user.getNickname());
//            BmobDataEngine.GetUser(_user.getObjectId(), new QueryListener<User>() {
//                @Override
//                public void done(User _User, BmobException e) {
//                    if (e == null) {
//                        tv_signature.setText(_User.getSignature());
//                        x.image().bind(iv_headIcon, _User.getAvatar());
//                        tv_area.setText(_User.getArea());
//                        tv_contact.setText(_User.getPhoneNum());
//                        tv_sex.setText(_User.getSex());
//                        tv_birthday.setText(_User.getBirthday());
//                        tv_nickname.setText(_User.getNickname());
//                    }
//                }
//            });
        } else {
            Toast.makeText(getApplicationContext(), "尚未登录，请登录后操作！", Toast.LENGTH_LONG).show();
//            PersonalDataAty.this.finish();
            OpenActivityForResult(LoginActivity.class,LOGIN_REQUEST_CODE);
        }
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
            case LOGIN_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    User _User = (User) data.getSerializableExtra("user");
                    if (_User != null) {
                        tv_signature.setText(_User.getSignature());
                        x.image().bind(iv_headIcon, _User.getAvatar());
                        tv_area.setText(_User.getDistrict());
                        tv_contact.setText(_User.getPhoneNum());
                        tv_sex.setText(_User.getSex());
                        tv_birthday.setText(_User.getBirthday());
                        tv_nickname.setText(_User.getNickname());
                    }
                }else{
                    finish();
                }
                break;
            default:
                break;
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
//                iv_headIcon.setImageBitmap(bitmap);
                // 保存图片
                String filename = new SimpleDateFormat("yyMMddHHmmss")
                        .format(new Date()) + System.currentTimeMillis() + ".jpg";
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

    private String mPicUrl;

    /**
     * 上传头像
     */
    private void uploadAvatar() {
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
                    LogUtils.e(mPicUrl);
                    User _user = BmobUser.getCurrentUser(User.class);
                    _user.setAvatar(mPicUrl);
                    _user.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                x.image().bind(iv_headIcon, mPicUrl);
                            } else {
                                ShowToast("头像更新失败");
                            }
                        }
                    });

                } else {
                    ShowToast("头像更新失败了");
                }

            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
            }
        });
    }


    private void updateSex() {
        new AlertDialog.Builder(PersonalDataAty.this).setTitle("性别").setSingleChoiceItems(getResources().getStringArray(R.array.sex), -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                String sexStr = null;
                User _user = BmobUser.getCurrentUser(User.class);
                switch (which) {
                    case 0:
                        sexStr = "男";
                        _user.setSex(sexStr);
                        break;
                    case 1:
                        sexStr = "女";
                        _user.setSex(sexStr);
                        break;
                }
                final String finalSexStr = sexStr;
                _user.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            tv_sex.setText(finalSexStr);
                        } else {
                            ShowToast("更新失败" + e.getMessage());
                        }
                    }
                });
                dialog.dismiss();
            }
        }).create().show();


    }


}
