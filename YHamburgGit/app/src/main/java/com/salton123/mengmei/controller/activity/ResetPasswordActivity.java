package com.salton123.mengmei.controller.activity;

import android.os.Bundle;

import com.salton123.base.ActivityFrameIOS;
import com.salton123.mengmei.R;


/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2015/12/22 16:39
 * Time: 16:39
 * Description:
 */
public class ResetPasswordActivity extends ActivityFrameIOS {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppendMainBody(R.layout.activity_reset_password);
        SetTopTitle("找回密码");
    }

    @Override
    public void InitView() {

    }

    @Override
    public void InitListener() {

    }

    @Override
    public void InitData() {

    }
}
