package com.salton123.mengmei.controller.activity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.salton123.base.ActivityFrameIOS;
import com.salton123.common.util.ResourceUtils;
import com.salton123.mengmei.R;


/**
 * Created by salton on 16/4/29.
 */
public class AboutUsAty extends ActivityFrameIOS {
    private WebView mWebAboutUs ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppendMainBody(R.layout.aty_about_us);
        SetTopTitle("关于我们");
//        Toast.makeText(getApplicationContext(),"关于我们",Toast.LENGTH_LONG).show();
    }

    @Override
    public void InitView() {
        mWebAboutUs= (WebView) findViewById(R.id.webView);
        String str = ResourceUtils.geFileFromAssets(GetContext(), "guanyu.htm");
        mWebAboutUs.loadDataWithBaseURL("", str, "text/html", "UTF-8", "");
        mWebAboutUs.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }
        });
    }

    @Override
    public void InitListener() {

    }

    @Override
    public void InitData() {

    }
}
