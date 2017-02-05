package com.salton123.mengmei.controller.activity;

import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.salton123.base.GlobalParams;
import com.salton123.mengmei.R;
import com.salton123.mengmei.controller.fragment.EmojiGroupFragment;
import com.salton123.mengmei.controller.fragment.HotLiveRadioFragment;
import com.salton123.mengmei.controller.fragment.HotMVFragment;
import com.salton123.mengmei.controller.fragment.PersonCenterFragment;
import com.salton123.mengmei.controller.fragment.VoiceFragment;
import com.salton123.mengmei.model.api.BmobApi;
import com.salton123.mengmei.model.bmob.UpdateInfo;
import com.salton123.mengmei.model.bmob.UpdateInfoResult;
import com.salton123.mengmei.view.GroupImageView.util.DisplayUtil;
import com.salton123.mengmei.view.tablayout.QiHuTabLayout;
import com.salton123.mengmei.view.tablayout.interfaces.OnTabSelectedListener;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import me.shenfan.updateapp.UpdateService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
//    String URL = "http://mobile.d.appchina.com/McDonald/e/630716/0/0/0/1471872947805/package_8.1471872947805";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalParams.screenWidth = DisplayUtil.getWindowWidth(this);
        GlobalParams.screenHeight = DisplayUtil.getWindowHeight(this);
        setContentView(R.layout.aty_main);
        Call<UpdateInfoResult> updaInfo = BmobApi.getUpdate().getUpdateInfo();
        updaInfo.enqueue(new Callback<UpdateInfoResult>() {
            @Override
            public void onResponse(Call<UpdateInfoResult> call, Response<UpdateInfoResult> response) {
                try {
                    UpdateInfo info = response.body().getResults().get(0);
//                Logger.e("info2:"+response.code()+":"+info.toString());
                    if (info != null) {
//                    Response{protocol=http/1.1, code=401, message=Unauthorized, url=https://api.bmob.cn/1/classes/updateInfo}
                        if (info.getVersionCode() > getVersionCode(MainActivity.this)) {
                            updateDialog(info.getUpdateContent(),info.getDownloadUrl());
                        }
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<UpdateInfoResult> call, Throwable t) {

            }
        });
        initView();
    }

    private QiHuTabLayout qhtl1;

    private void initView() {
        qhtl1 = (QiHuTabLayout) findViewById(R.id.qhtl);
        qhtl1.setSelectedListener(onTabSelectedListener);
        if (mHotMVFragment == null) {
            mHotMVFragment = new HotMVFragment();
        }
        if (!mHotMVFragment.isVisible()) {
            setFragment(mHotMVFragment);
        }

    }

    private HotMVFragment mHotMVFragment;
    private VoiceFragment mVoiceFragment;
    private EmojiGroupFragment mEmojiGroupFragment;
    private HotLiveRadioFragment mHotLiveRadioFragment;
    private PersonCenterFragment mPersonCenterFragment;

    OnTabSelectedListener onTabSelectedListener = new OnTabSelectedListener() {
        @Override
        public void onSelected(QiHuTabLayout qiHuTabLayout, int position) {
            switch (position) {
                case 0:     //热门
                    if (mHotMVFragment == null) {
                        mHotMVFragment = new HotMVFragment();
                    }
                    if (!mHotMVFragment.isVisible()) {
                        setFragment(mHotMVFragment);
                    }
                    break;
                case 1:
                    if (mVoiceFragment == null) {
                        mVoiceFragment = new VoiceFragment();
                    }
                    if (!mVoiceFragment.isVisible()) {
                        setFragment(mVoiceFragment);
                    }
                    break;

                case 2:
                    if (mHotLiveRadioFragment == null) {
                        mHotLiveRadioFragment = new HotLiveRadioFragment();
                    }
                    if (!mHotLiveRadioFragment.isVisible()) {
                        setFragment(mHotLiveRadioFragment);
                    }
                    break;
                case 3:
                    if (mEmojiGroupFragment == null) {
                        mEmojiGroupFragment = new EmojiGroupFragment();
                    }
                    if (!mEmojiGroupFragment.isVisible()) {
                        setFragment(mEmojiGroupFragment);
                    }
                    break;
                case 4:
                    if (mPersonCenterFragment == null) {
                        mPersonCenterFragment = new PersonCenterFragment();
                    }
                    if (!mPersonCenterFragment.isVisible()) {
                        setFragment(mPersonCenterFragment);
                    }
                    break;
            }
//            Toast.makeText(getApplicationContext(), "當前位置：" + position, Toast.LENGTH_SHORT).show();
//            Logger.e("qiHuTabLayout:" + position);
        }
    };

    private void setFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        //提交修改
        transaction.commit();
    }


    public void updateDialog(final String updateInfo, final String downloadUrl) {
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(this);
        dialogBuilder
                .withTitle("发现更新")                                  //.withTitle(null)  no title
                .withTitleColor("#FFFFFFFF")                                  //def
                .withDividerColor("#4CAF50")                              //def
                .withMessage(updateInfo)                     //.withMessage(null)  no Msg
                .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                .withDialogColor("#4CAF50")                               //def  | withDialogColor(int resid)
                .withIcon(getResources().getDrawable(R.mipmap.ic_launcher))
                .withDuration(1700)                                          //def
                .withEffect(Effectstype.Slidetop)                                         //def Effectstype.Slidetop
                .withButton1Text("确定")                                      //def gone
                .withButton2Text("取消")                                  //def gone
                .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
//                .setCustomView(R.layout.activity_main,MainActivity.this)         //.setCustomView(View or ResId,context)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UpdateService.Builder.create(downloadUrl)
                                .setStoreDir("update/flag")
                                .setDownloadSuccessNotificationFlag(Notification.DEFAULT_ALL)
                                .setDownloadErrorNotificationFlag(Notification.DEFAULT_ALL)
                                .build(MainActivity.this);
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                    }
                })
                .show();
    }

    public static int getVersionCode(Context context)//获取版本号(内部识别号)
    {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.
                setTitle("Hi").
                setMessage("确定退出app吗").
                setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        System.exit(0);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
//        super.onBackPressed();
    }
}
