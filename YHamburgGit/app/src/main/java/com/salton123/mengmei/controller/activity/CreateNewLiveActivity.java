package com.salton123.mengmei.controller.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.orhanobut.logger.Logger;
import com.salton123.base.ActivityFrameIOS;
import com.salton123.common.util.ToastUtils;
import com.salton123.mengmei.model.bean.User;
import com.salton123.mengmei.model.bean.YcProgram;
import com.salton123.mengmei.R;
import com.tencent.qcloud.suixinbo.views.customviews.CustomSwitch;

import org.xutils.x;

import java.io.File;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultSubscriber;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;

import static com.salton123.mengmei.R.id.tv_pic_tip;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2016/9/25 12:51
 * Time: 12:51
 * Description:
 */
public class CreateNewLiveActivity extends ActivityFrameIOS {

    private String mPicUrl;
    private ProgressDialog mProgressDialog;
    private static final int REQUSET_LOGIN = 0x102;
    @BindView(R.id.et_title)
    BootstrapEditText mEtTitle;
    @BindView(R.id.btn_start_liveplay)
    BootstrapButton mBtnStartLiveplay;
    @BindView(tv_pic_tip)
    TextView mTvPicTip;
    User mUser;
    YcProgram mProgram;
    @BindView(R.id.cover)
    ImageView mCover;
    @BindView(R.id.img_lbs)
    ImageView mImgLbs;
    @BindView(R.id.address)
    TextView mAddress;
    @BindView(R.id.btn_lbs)
    CustomSwitch mBtnLbs;
    @BindView(R.id.push_stream)
    TextView mPushStream;
    @BindView(R.id.record_btn)
    CustomSwitch mRecordBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppendMainBody(R.layout.aty_create_new_live);
        SetTopTitle("我要开播");
        ButterKnife.bind(this);

    }

    @Override
    public void InitView() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(getString(R.string.label_uploading));
        mProgressDialog.setCancelable(true);
    }

    @Override
    public void InitListener() {

    }

    @Override
    public void InitData() {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUSET_LOGIN) {
//            Bundle bundle = new Bundle();
//            bundle.putInt("uid", User.getCurrentUser(User.class).getYc_UID());
//            bundle.putInt("sid", 10086);
//            Intent intent = new Intent(CreateNewLiveActivity.this, MyNewLiveActivity.class);
//            intent.putExtras(bundle);
//            startActivity(intent);
            Random random = new Random();
            int x = random.nextInt(899999);
            int number = x + 100000;
            mProgram = new YcProgram();
            if (data != null) {
                mUser = (User) data.getSerializableExtra("user");
                if (mUser == null) {
                    startActivityForResult(new Intent(CreateNewLiveActivity.this, LoginActivity.class), REQUSET_LOGIN);
                } else {
                    mProgram.setOwnerName(mUser.getNickname());
                    mProgram.setOwnerObjectId(mUser.getObjectId());
                    mProgram.setTitleInfo("测试频道" + number);
                    mProgram.setYc_SID(number);
                    mProgram.setThumbnailUrl("https://img3.doubanio.com/view/music_topic/o/public/2134-3571.jpg");
                    mProgram.setTotalPeople(9000);
                    mProgram.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("program", mProgram);
//                            bundle.putInt("uid", User.getCurrentUser(User.class).getYc_UID());
//                            bundle.putInt("sid", 10086);
//                                        Intent intent = new Intent(CreateNewLiveActivity.this, MyNewLiveActivity.class);
//                                        intent.putExtras(bundle);
//                                        startActivity(intent);
                                        ToastUtils.show(getApplicationContext(),"未实现");
                                    }
                                });
                            }
                        }
                    });
                }

            }
        }

    }


    @OnClick({R.id.cover, R.id.btn_start_liveplay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cover:
                RxGalleryFinal
                        .with(CreateNewLiveActivity.this)
                        .image()
                        .radio()
                        .crop()
                        .cropropCompressionQuality(50)
                        .cropMaxResultSize(800,600)
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
                                            x.image().bind(mCover,mPicUrl);
                                            mTvPicTip.setText("上传完毕");
                                        } else {
                                            Toast.makeText(getApplicationContext(), "文件上传失败:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                        mProgressDialog.dismiss();
                                    }

                                    @Override
                                    public void onProgress(Integer value) {
                                        // 返回的上传进度（百分比）
                                        mTvPicTip.setText("上传进度:" + value + "%");
                                    }
                                });

                            }
                        })
                        .openGallery();
                break;
            case R.id.btn_start_liveplay:
                Logger.e("onClick");
                Random random = new Random();
                int x = random.nextInt(899999);
                int number = x + 100000;
                mUser = User.getCurrentUser(User.class);
                if (mUser == null) {
                    startActivityForResult(new Intent(CreateNewLiveActivity.this, LoginActivity.class), REQUSET_LOGIN);
                } else {
                    if (TextUtils.isEmpty(mEtTitle.getText().toString().trim())) {
                        Toast.makeText(getApplicationContext(), "请输入标题", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if (TextUtils.isEmpty(mPicUrl)) {
                        Toast.makeText(getApplicationContext(), "请选择图片并等待上传完成", Toast.LENGTH_SHORT).show();
                        return;
                    }else {
                        mProgram = new YcProgram();
                        mProgram.setOwnerName(mUser.getNickname());
                        mProgram.setOwnerObjectId(mUser.getObjectId());
                        mProgram.setTitleInfo(mEtTitle.getText().toString().trim());
                        mProgram.setYc_SID(number);
                        mProgram.setThumbnailUrl(mPicUrl);
                        mProgram.setTotalPeople(8888);
                        mProgram.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Bundle bundle = new Bundle();
//                                            bundle.putSerializable("program", mProgram);
////                            bundle.putInt("uid", User.getCurrentUser(User.class).getYc_UID());
////                            bundle.putInt("sid", 10086);
//                                            Intent intent = new Intent(CreateNewLiveActivity.this, MyNewLiveActivity.class);
//                                            intent.putExtras(bundle);
//                                            startActivity(intent);
                                            ToastUtils.show(getApplicationContext(),"未实现");
                                        }
                                    });
                                }
                            }
                        });
                    }

                }
                break;
        }
    }
}
