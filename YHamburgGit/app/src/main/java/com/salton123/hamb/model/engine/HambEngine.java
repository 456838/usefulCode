package com.salton123.hamb.model.engine;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.salton123.common.net.HttpResponseHandler;
import com.salton123.common.util.LogUtils;
import com.salton123.hamb.model.bean.CriteriaSearchRet;
import com.salton123.hamb.model.bean.PostCriteriaSearch;
import com.salton123.mengmei.model.config.OkHttpManager;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/2/4 23:23
 * Time: 23:23
 * Description:
 */
public class HambEngine {

    private static final String CRITERIA_SEARCH_URL = "http://kk.yy.com/hamburg/index.php/Home/Api/criteriaSearch";

    public static void criteriaSearch(PostCriteriaSearch p_PostCriteriaSearch, final HttpResponseHandler<List<CriteriaSearchRet>> p_HttpResponseHandler) {
        String json = new Gson().toJson(p_PostCriteriaSearch, PostCriteriaSearch.class);
        LogUtils.d(json);
        OkHttpManager.getOkHttpManager().asyncPost(CRITERIA_SEARCH_URL, json, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                p_HttpResponseHandler.onFailure(e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String result = response.body().string() ;
                    Logger.e(result);
                    try {
                        List<CriteriaSearchRet> data = new Gson().fromJson(result, new TypeToken<List<CriteriaSearchRet>>() {
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
