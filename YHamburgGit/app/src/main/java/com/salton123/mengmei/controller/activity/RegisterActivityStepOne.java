package com.salton123.mengmei.controller.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.salton123.base.ActivityFrameIOS;
import com.salton123.common.util.CheckUtils;
import com.salton123.common.util.DigestUtils;
import com.salton123.common.util.LogUtils;
import com.salton123.common.util.ValidateCodeGenerator;
import com.salton123.mengmei.R;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2015/12/22 15:56
 * Time: 15:56
 * Description:
 */
public class RegisterActivityStepOne extends ActivityFrameIOS {

    VerificationCodeCountTimer timer;
    private EditText inputPhone2, inputCode, input_password;
    private BootstrapButton btn_next, btnGetCode;
    private TextView tologin,tv_policy;
    private ImageView iv_verificationCode;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppendMainBody(R.layout.activity_register_step_one);
        SetTopTitle("注册");
        SetTopBackHint("返回");
    }

    @Override
    public void InitView() {
        inputPhone2 = (EditText) findViewById(R.id.inputPhone2);
        inputCode = (EditText) findViewById(R.id.inputCode);
        input_password = (EditText) findViewById(R.id.input_password);
        tologin = (TextView) findViewById(R.id.tologin);
        tv_policy = (TextView) findViewById(R.id.tv_policy);
        btn_next = (BootstrapButton) findViewById(R.id.btn_next);
        btnGetCode = (BootstrapButton) findViewById(R.id.btnGetCode);
        iv_verificationCode = (ImageView) findViewById(R.id.iv_verificationCode);
    }

    @Override
    public void InitListener() {
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String verificationCode = inputCode.getText().toString().trim();
                if (TextUtils.isEmpty(verificationCode)) {
                    ShowToast("请获取验证码！");
                    return;
                }
                String phoneNum = inputPhone2.getText().toString().trim();
                if (!isPhoneNumValid(phoneNum)) {
                    ShowToast("非法手机号码");
                    return;
                }
                String _edit_pwd = input_password.getText().toString().trim();
                if (TextUtils.isEmpty(_edit_pwd)) {
                    ShowToast(getString(R.string.label_input_pwd));
                    return;
                }
                if (!verificationCode.equals(ValidateCodeGenerator.getCode())) {
                    ShowToast("验证码错误,请重试");
                    return;
                }
                // Intent _Intent = new Intent(RegisterActivityStepOne.this, RegisterActivityStepTwo.class);
//                _Intent.putExtra("phoneNum", phoneNum);
//                _Intent.putExtra("password", DigestUtils.md5(_edit_pwd));
                Bundle _bundle = new Bundle();
                _bundle.putString("phoneNum", phoneNum);
                _bundle.putString("password", DigestUtils.md5(_edit_pwd));
                OpenActivity(RegisterActivityStepTwo.class, _bundle);
            }
        });
        tologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   OpenActivity(LoginActivity.class);
                RegisterActivityStepOne.this.finish();
            }
        });
        btnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNum = inputPhone2.getText().toString().trim();
                if (!isPhoneNumValid(phoneNum)) {
                    ShowToast("非法手机号码");
                    return;
                }
                iv_verificationCode.setImageBitmap(ValidateCodeGenerator.createBitmap(200, 100));
                LogUtils.i(ValidateCodeGenerator.getCode());
                iv_verificationCode.setVisibility(View.VISIBLE);
            }
        });
    }


    private boolean isPhoneNumValid(String phoneNum) {
        //TODO: Replace this with your own logic
        return CheckUtils.checkLengthEq(phoneNum, 11) && phoneNum.charAt(0) == '1';
    }

    @Override
    public void InitData() {
        String xieyi = "<font color=" + "\"" + "#AAAAAA" + "\">" + "点击上面的"
                + "\"" + "下一步" + "\"" + "按钮,即表示你同意" + "</font>" + "<u>"
                + "<font color=" + "\"" + "#576B95" + "\">" + "《软件许可及服务协议》"
                + "</font>" + "</u>";
        tv_policy.setText(Html.fromHtml(xieyi));
    }


    class VerificationCodeCountTimer extends CountDownTimer {

        public VerificationCodeCountTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            btnGetCode.setText((millisUntilFinished / 1000) + "秒后重发");
        }

        @Override
        public void onFinish() {
            btnGetCode.setText("重新发送验证码");
            btnGetCode.setEnabled(true);
        }
    }
}
