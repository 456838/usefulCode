package com.salton123.mengmei.controller.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.salton123.base.BaseFragment;
import com.salton123.mengmei.model.bean.User;
import com.salton123.common.util.LogUtils;
import com.salton123.common.widget.CircleImageView;
import com.salton123.mengmei.R;
import com.salton123.mengmei.controller.activity.AboutUsAty;
import com.salton123.mengmei.controller.activity.FuWuXieYiAty;
import com.salton123.mengmei.controller.activity.HolderActivity;
import com.salton123.mengmei.controller.activity.MyEmojiActivity;
import com.salton123.mengmei.controller.activity.PersonalDataAty;
import com.salton123.mengmei.controller.activity.XinShouZhiNanAty;
import com.salton123.mengmei.controller.activity.ShareEmojiGroupAty;

import org.xutils.x;

import java.io.File;

import cn.bmob.v3.BmobUser;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2015/12/25 11:10
 * Time: 11:10
 * Description:
 */
public class PersonCenterFragment extends BaseFragment implements View.OnClickListener {
    private CircleImageView civ_head_portrait;
    private TextView tv_signature;
    private TextView tv_nickname;
    private TextView tv_id;
    BootstrapButton btn_logout;
    public static final int FRAGMENT_REQUEST_LOGIN_CODE = 0x101;
    User mUser ;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_personal_center);
        civ_head_portrait = (CircleImageView) getViewById(R.id.iv_thumbnail);
        tv_signature = (TextView) getViewById(R.id.tv_signature);
        tv_nickname = (TextView) getViewById(R.id.tv_nickname);
        tv_id = (TextView) getViewById(R.id.tv_user_id);
        btn_logout = (BootstrapButton) getViewById(R.id.btn_logout);
        mUser =BmobUser.getCurrentUser(User.class);
    }

    @Override
    protected void setListener() {
        getViewById(R.id.rel_provider_setting).setOnClickListener(this);
        getViewById(R.id.rel_address).setOnClickListener(this);
        getViewById(R.id.rel_feedback).setOnClickListener(this);
        getViewById(R.id.rl_user_profile_header).setOnClickListener(this);
        getViewById(R.id.btn_logout).setOnClickListener(this);
        getViewById(R.id.rel_aboutus).setOnClickListener(this);
        getViewById(R.id.rel_connect).setOnClickListener(this);
        getViewById(R.id.rel_guide).setOnClickListener(this);
        getViewById(R.id.rel_protocol).setOnClickListener(this);
        getViewById(R.id.tv_xianzhi).setOnClickListener(this);
        getViewById(R.id.tv_jianzhi).setOnClickListener(this);
        getViewById(R.id.tv_liuxue).setOnClickListener(this);
        getViewById(R.id.tv_yiliao).setOnClickListener(this);

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
//        CustomApplication.getInstance().setPersonalInfo(_user);
        if (mUser != null) {
            x.image().bind(civ_head_portrait, mUser.getAvatar());
//            FrescoImageLoader.displayThumbnailImage(civ_head_portrait, _user.getAvatar());
            if (!TextUtils.isEmpty(mUser.getNickname())) {
                LogUtils.i(mUser.getNickname());
                if (mUser.getNickname().equals("null")) {
                    tv_nickname.setText("请设置您的昵称");
                } else
                    tv_nickname.setText(mUser.getNickname());
            } else {
                tv_nickname.setText("请设置您的昵称");
            }

            if (!TextUtils.isEmpty(mUser.getUsername())) {
                LogUtils.i(mUser.getUsername());
                if (mUser.getUsername().equals("null")) {
                    tv_id.setText("请登录");
                } else
                    tv_id.setText(mUser.getUsername());
            } else {
                tv_id.setText("请登录");
            }

            if (!TextUtils.isEmpty(mUser.getSignature())) {
                LogUtils.i(mUser.getSignature());
                if (mUser.getSignature().equals("null")) {
                    tv_signature.setText("请设置您的个性签名");
                } else
                    tv_signature.setText(mUser.getSignature());
            } else {
                tv_signature.setText("请设置您的个性签名");
            }
            btn_logout.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(getActivity(), "请先登录获取更优质的体验哦！", Toast.LENGTH_LONG).show();
            btn_logout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onUserVisible() {
        processLogic(null);
    }
    private void ShareYourImages() {
        startActivity(new Intent(getActivity(), ShareEmojiGroupAty.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rel_provider_setting:  //我的简介
//                OpenActivity(HolderActivity.class);
                ShareYourImages();
                break;
            case R.id.rl_user_profile_header:
                Bundle bundle = new Bundle();
                bundle.putSerializable("user",mUser);
                OpenActivityForResult(PersonalDataAty.class, FRAGMENT_REQUEST_LOGIN_CODE,bundle);
                break;
            case R.id.rel_address:
                OpenActivity(MyEmojiActivity.class);
                break;
            case R.id.rel_aboutus:
                OpenActivity(AboutUsAty.class);
                break;
            case R.id.rel_guide:
                OpenActivity(XinShouZhiNanAty.class);
                break;
            case R.id.rel_connect:
                OpenActivity(HolderActivity.class);
                break;
            case R.id.rel_protocol:
                OpenActivity(FuWuXieYiAty.class);
                break;
            case R.id.tv_xianzhi:
                showToast("功能研发中...");
                break;
            case R.id.tv_jianzhi:
                showToast("功能研发中...");
                break;
            case R.id.tv_liuxue:
                showToast("功能研发中...");
                break;
            case R.id.tv_yiliao:
                showToast("功能研发中...");
                break;
            case R.id.btn_logout:
                User.getCurrentUser().logOut();
                mUser =null ;
                civ_head_portrait.setImageResource(R.drawable.pic_loading);
                tv_nickname.setText("请设置您的昵称");
                tv_id.setText("请登录");
                tv_signature.setText("请设置您的个性签名");
                break;
            case R.id.rel_feedback:
                try {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.setType("*/*");
                    intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(getActivity().getPackageResourcePath())));
                    startActivity(intent);
                } catch (Exception e) {
                    ShowToast("暂无相应分享服务");
                }
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FRAGMENT_REQUEST_LOGIN_CODE) {
            User _user = BmobUser.getCurrentUser(User.class);
            mUser = _user ;
//        CustomApplication.getInstance().setPersonalInfo(_user);
            if (_user != null) {
                x.image().bind(civ_head_portrait, _user.getAvatar());
//            FrescoImageLoader.displayThumbnailImage(civ_head_portrait, _user.getAvatar());
                if (!TextUtils.isEmpty(_user.getNickname())) {
                    LogUtils.i(_user.getNickname());
                    if (_user.getNickname().equals("null")) {
                        tv_nickname.setText("请设置您的昵称");
                    } else
                        tv_nickname.setText(_user.getNickname());
                } else {
                    tv_nickname.setText("请设置您的昵称");
                }

                if (!TextUtils.isEmpty(_user.getUsername())) {
                    LogUtils.i(_user.getUsername());
                    if (_user.getUsername().equals("null")) {
                        tv_id.setText("请登录");
                    } else
                        tv_id.setText(_user.getUsername());
                } else {
                    tv_id.setText("请登录");
                }

                if (!TextUtils.isEmpty(_user.getSignature())) {
                    LogUtils.i(_user.getSignature());
                    if (_user.getSignature().equals("null")) {
                        tv_signature.setText("请设置您的个性签名");
                    } else
                        tv_signature.setText(_user.getSignature());
                } else {
                    tv_signature.setText("请设置您的个性签名");
                }
                btn_logout.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(getActivity(), "请先登录获取更优质的体验哦！", Toast.LENGTH_LONG).show();
                btn_logout.setVisibility(View.GONE);
            }
        }
    }
}
