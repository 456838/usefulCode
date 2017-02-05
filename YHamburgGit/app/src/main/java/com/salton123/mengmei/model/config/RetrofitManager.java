package com.salton123.mengmei.model.config;

import com.salton123.mengmei.model.api.BmobString;


import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by succlz123 on 15/8/14.
 */
public class RetrofitManager {
    private static Retrofit.Builder sInstance;

    private static Retrofit.Builder getInstance() {
        if (sInstance == null) {
            synchronized (RetrofitManager.class) {
                if (sInstance == null) {
                    sInstance = new Retrofit.Builder();
                }
            }
        }
        return sInstance;
    }

    private static Retrofit getRetrofit(String url) {
        Retrofit retrofit = RetrofitManager.getInstance()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(url)
                .build();
        return retrofit;
    }

    public static Retrofit getBmob() {
        return RetrofitManager.getRetrofit(BmobString.BASE_URL);
    }
}
