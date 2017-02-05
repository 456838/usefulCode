package com.salton123.mengmei.controller.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.salton123.base.BaseFragment;
import com.salton123.common.net.HttpResponseHandler;
import com.salton123.mengmei.R;
import com.salton123.mengmei.model.config.OkHttpManager;
import com.salton123.mengmei.model.config.URLProviderUtil;
import com.salton123.mengmei.model.config.ViewUtil;
import com.salton123.mengmei.model.yinyuetai.AreaBean;
import com.salton123.mengmei.view.adapter.BaseFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2016/8/26 19:01
 * Time: 19:01
 * Description:
 */
public class HotMVFragment extends BaseFragment {

    ViewPager mViewPager;
    TabLayout mTabLayout;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 103:
                    List<AreaBean> content = (List<AreaBean>) msg.obj;
                    initViewPager(content);
                    break;
                case 104:
                    String errorInfo = (String) msg.obj;
                    Toast.makeText(getContext(), "" + errorInfo, Toast.LENGTH_SHORT).show();
                    Logger.e(errorInfo);
                    break;
            }
        }
    };

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fm_hot_mv);
        mViewPager = getViewById(R.id.viewpager);
        mTabLayout = getViewById(R.id.tabs);

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        processLogic();
    }

    @Override
    protected void onUserVisible() {

    }

    protected <T extends View> T f(View view, int resId) {
        return (T) view.findViewById(resId);
    }

    public void initViewPager(List<AreaBean> newsChannels) {
        List<Fragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        if (newsChannels != null) {
            //除了固定的其他频道被选中，添加
            for (AreaBean news : newsChannels) {
                final MVViewPagerItemFragment fragment = MVViewPagerItemFragment.getInstance(news.getCode());
                fragments.add(fragment);
                titles.add(news.getName());
            }
        }

        if (mViewPager.getAdapter() == null) {
            //初始化ViewPager
            BaseFragmentAdapter adapter = new BaseFragmentAdapter(getActivity().getSupportFragmentManager(),
                    fragments, titles);
            mViewPager.setAdapter(adapter);
        } else {
            final BaseFragmentAdapter adapter = (BaseFragmentAdapter) mViewPager.getAdapter();
            adapter.updateFragments(fragments, titles);
        }
        mViewPager.setCurrentItem(0, false);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setScrollPosition(0, 0, true);
        ViewUtil.dynamicSetTabLayoutMode(mTabLayout);
    }


    protected void processLogic() {
        OkHttpManager.getOkHttpManager().AsyncGetMvArea(URLProviderUtil.getMVareaUrl(), new HttpResponseHandler<List<AreaBean>>() {
            @Override
            public void onSuccess(List<AreaBean> content) {
//                    initViewPager(content);
                Message msg = Message.obtain();
                msg.what = 103;
                msg.obj = content;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onFailure(String content) {
                Message msg = Message.obtain();
                msg.what = 104;
                msg.obj = content;
                mHandler.sendMessage(msg);
            }
        });


//        OkHttpManager.getOkHttpManager().asyncGet(URLProviderUtil.getMVareaUrl(), new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                //创建一个JsonParser
//                JsonParser parser = new JsonParser();
//                //通过JsonParser对象可以把json格式的字符串解析成一个JsonElement对象
//                JsonElement el = null;
//                final ArrayList<AreaBean> areaBeanArrayList;
//                try {
//                    el = parser.parse(response.body().string());
//                    //把JsonElement对象转换成JsonArray
//                    JsonArray jsonArray = null;
//                    if (el.isJsonArray()) {
//                        jsonArray = el.getAsJsonArray();
//                    }
//                    areaBeanArrayList = new ArrayList<>();
//                    Iterator it = jsonArray.iterator();
//                    while (it.hasNext()) {
//                        JsonElement e = (JsonElement) it.next();
//                        //JsonElement转换为JavaBean对象
//                        AreaBean field = new Gson().fromJson(e, AreaBean.class);
//                        areaBeanArrayList.add(field);
//                    }
//                    getActivity().runOnUiThread(new TimerTask() {
//                        @Override
//                        public void run() {
//                            initViewPager(areaBeanArrayList);
//                        }
//                    });
//                } catch (JsonSyntaxException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }


//    public void showSnake(String message) {
//        Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
//        Logger.e(message);
//    }
}
