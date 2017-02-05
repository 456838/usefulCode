package com.salton123.mengmei.model.config;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.salton123.common.net.HttpResponseHandler;
import com.salton123.mengmei.model.yinyuetai.AreaBean;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Mr.Yangxiufeng
 * DATE 2016/5/10
 * YinYueTai
 */
public class OkHttpManager {
    private static OkHttpManager okHttpManager;
    private static OkHttpClient okHttpClient;
    private Handler handler;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static OkHttpManager getOkHttpManager() {
        if (okHttpManager == null) {
            synchronized (OkHttpManager.class) {
                okHttpManager = new OkHttpManager();
            }
        }
        return okHttpManager;
    }

    private OkHttpManager() {
        okHttpClient = new OkHttpClient();
        okHttpClient.newBuilder().connectTimeout(10, TimeUnit.SECONDS);
        okHttpClient.newBuilder().readTimeout(10, TimeUnit.SECONDS);
        okHttpClient.newBuilder().writeTimeout(10, TimeUnit.SECONDS);
        handler = new Handler(Looper.getMainLooper());
    }

    public void asyncGet(String url, final okhttp3.Callback callBack) {
//        Logger.e(url);
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(callBack);
    }

    public void asyncPost(String url ,String json,final okhttp3.Callback callBack){
        RequestBody body = RequestBody.create(JSON,json);
        Request request = new Request.Builder().url(url).post(body).build();
        okHttpClient.newCall(request).enqueue(callBack);
    }


    public void AsyncGetMvArea(String url, final HttpResponseHandler<List<AreaBean>> p_HttpResponseHandler) {
        asyncGet(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                p_HttpResponseHandler.onFailure(e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        List<AreaBean> data = new Gson().fromJson(response.body().string(), new TypeToken<List<AreaBean>>() {
                        }.getType());
                        p_HttpResponseHandler.onSuccess(data);
                    } catch (Exception e) {
                        e.printStackTrace();
                        p_HttpResponseHandler.onFailure(e.getMessage());
                    }
                } else p_HttpResponseHandler.onFailure(response.toString());
            }
        });
    }
}
