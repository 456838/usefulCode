package com.salton123.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.salton123.mengmei.R;


/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2015-08-17
 * Time: 14:19
 */
public abstract class ActivityFrameIOS extends ActivityBase {

    public TextView tv_title_back, tv_title, tv_title_additional;
    public ImageView iv_more;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置窗体扩展特性
        _InitVariable();
        setContentView(R.layout.activity_frame_ios);
        _InitView();
    }
    private void _InitVariable() {
        // 修改状态栏颜色，4.4+生效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus();
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.colorPrimary);//通知栏所需颜色
    }

    @TargetApi(19)
    protected void setTranslucentStatus() {
        Window window = getWindow();
        // Translucent status bar
        window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // Translucent navigation bar
        window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    private void _InitView() {
        tv_title_back = (TextView) this.findViewById(R.id.tv_title_back);
        tv_title = (TextView) this.findViewById(R.id.tv_title);
        tv_title_additional = (TextView) this.findViewById(R.id.tv_title_additional);
        iv_more = (ImageView) findViewById(R.id.iv_more);
        OnBackListener _OnBackListener = new OnBackListener();
        tv_title_back.setOnClickListener(_OnBackListener);
    }

    /**
     * 显示标题
     *
     * @param p_Title
     */
    public void SetTopTitle(String p_Title) {
        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText(p_Title);
    }

    /**
     * 隐藏标题
     */
    public void HideTopTitle() {
        tv_title.setVisibility(View.GONE);
    }

    /**
     * 显示上级标题提示
     *
     * @param p_Hint
     */
    public void SetTopBackHint(String p_Hint) {
        tv_title_back.setVisibility(View.VISIBLE);
        tv_title_back.setText(p_Hint);
    }

    public void SetTopBackHint(String p_Hint,View.OnClickListener p_listener) {
        tv_title_back.setVisibility(View.VISIBLE);
        tv_title_back.setText(p_Hint);
        tv_title_back.setOnClickListener(p_listener);
    }

    /**
     *
     */
    public void HideTopBackHint() {
        tv_title_back.setVisibility(View.GONE);
    }

    public void SetTopAdditionalImage(@DrawableRes int drawableRes) {
        iv_more.setVisibility(View.VISIBLE);
        iv_more.setImageResource(drawableRes);
    }

    /**
     * 隐藏额外信息提示
     */
    public void HideTopAdditionalImage() {
        iv_more.setVisibility(View.GONE);
    }

    public void SetTopAdditionImageListener(@DrawableRes int drawableRes, View.OnClickListener p_Listener) {
        iv_more.setVisibility(View.VISIBLE);
        iv_more.setImageResource(drawableRes);
        iv_more.setOnClickListener(p_Listener);
    }

    /**
     * 显示额外信息提示
     *
     * @param p_Hint
     */
    public void SetTopAdditionalHint(CharSequence p_Hint) {
        tv_title_additional.setVisibility(View.VISIBLE);
        tv_title_additional.setText(p_Hint);
    }

    /**
     * 隐藏额外信息提示
     */
    public void HideTopAdditionalHint() {
        tv_title_additional.setVisibility(View.GONE);
    }

    public void SetTopAdditionalListener(CharSequence p_Hint, View.OnClickListener p_Listener) {
        tv_title_additional.setVisibility(View.VISIBLE);
        tv_title_additional.setText(p_Hint);
        tv_title_additional.setOnClickListener(p_Listener);
    }

    /**
     * 添加Layout到程序Body中
     *
     * @param pResID 要添加的Layout资源ID
     */
    protected void AppendMainBody(int pResID) {
        LinearLayout _MainBody = (LinearLayout) findViewById(GetMainBodyLayoutID());
        View _View = LayoutInflater.from(this).inflate(pResID, null);
        RelativeLayout.LayoutParams _LayoutParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
        _MainBody.addView(_View, _LayoutParams);
        //_View.setPadding(15,15,15,15);
        InitView();
        InitListener();
        InitData();
    }

    /**
     * 添加Layout到程序Body中
     *
     * @param pView 要添加的View
     */
    protected void AppendMainBody(View pView) {
        LinearLayout _MainBody = (LinearLayout) findViewById(GetMainBodyLayoutID());
        RelativeLayout.LayoutParams _LayoutParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
        _MainBody.addView(pView, _LayoutParams);
        //_View.setPadding(15,15,15,15);
        InitView();
        InitListener();
        InitData();
    }

    private int GetMainBodyLayoutID() {
        return R.id.layMainBody;
    }

    private class OnBackListener implements View.OnClickListener {
        public void onClick(View view) {
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            finish();
        }
    }

    /**
     * 隐藏软键盘
     * hideSoftInputView
     *
     * @param
     * @return void
     * @throws
     * @Title: hideSoftInputView
     * @Description: TODO
     */
    public void hideSoftInputView() {
        InputMethodManager manager = ((InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE));
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    /**
     * 初始化控件
     */
    public abstract void InitView();

    /**
     * 初始化监听器
     */
    public abstract void InitListener();

    /**
     * 初始化数据
     */
    public abstract void InitData();
}
