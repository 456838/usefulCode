package com.salton123.mengmei.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.salton123.base.ActivityFrameIOS;
import com.salton123.mengmei.model.bean.User;
import com.salton123.common.net.HttpResponseHandler;
import com.salton123.common.util.CheckUtils;
import com.salton123.common.util.DigestUtils;
import com.salton123.mengmei.R;
import com.salton123.mengmei.model.engine.BmobDataEngine;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends ActivityFrameIOS {


    private EditText loginName, loginPassword;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppendMainBody(R.layout.activity_login);
        SetTopTitle("登录");
    }

    public void InitView() {
        loginName = (EditText) findViewById(R.id.loginName);
        loginPassword = (EditText) findViewById(R.id.loginPassword);
    }

    @Override
    public void InitListener() {
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginLogic();
            }
        });
        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenActivity(RegisterActivityStepOne.class);
            }
        });
        findViewById(R.id.btn_retrieve_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenActivity(ResetPasswordActivity.class);
            }
        });
    }

    @Override
    public void InitData() {

    }

    public void LoginLogic() {
        String phoneNum = loginName.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();
        if (!isPhoneNumValid(phoneNum)) {
            ShowToast("请填写正确的手机号码");
            return;
        }
        if (!isPasswordValid(password)) {
            ShowToast("密码长度应该大于6位以保证安全");
            return;
        }
        Login(phoneNum, DigestUtils.md5(password));
    }

    private boolean isPhoneNumValid(String phoneNum) {
        //TODO: Replace this with your own logic
        return CheckUtils.checkLengthEq(phoneNum, 11) && phoneNum.charAt(0) == '1';
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    public void Login(String p_PhoneNum, String password) {
        BmobDataEngine.Login(p_PhoneNum, password, new HttpResponseHandler<User>() {
            @Override
            public void onSuccess(User content) {
//                LogUtils.i("result:" + content.toString());
//                OpenActivity(MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user",content);
                Intent resultIntent = new Intent();
                resultIntent.putExtras(bundle);
                setResult(RESULT_OK,resultIntent);
                LoginActivity.this.finish();
            }

            @Override
            public void onFailure(String content) {
                Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();
            }
        });
    }

}

