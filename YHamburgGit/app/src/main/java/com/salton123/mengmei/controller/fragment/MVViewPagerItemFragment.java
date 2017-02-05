package com.salton123.mengmei.controller.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.orhanobut.logger.Logger;
import com.salton123.mengmei.R;
import com.salton123.base.BaseFragment;
import com.salton123.mengmei.model.config.OkHttpManager;
import com.salton123.mengmei.model.config.URLProviderUtil;
import com.salton123.mengmei.model.yinyuetai.MVListBean;
import com.salton123.mengmei.model.yinyuetai.VideoBean;
import com.salton123.mengmei.view.adapter.MVRecycleViewAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Mr.Yangxiufeng
 * DATE 2016/5/11
 * YinYueTai
 */
public class MVViewPagerItemFragment extends BaseFragment {

    //    @BindView(R.id.mv_RecyclerView)
    RecyclerView mvRecyclerView;
    //    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    MVRecycleViewAdapter recycleViewAdapter;
    private ArrayList<VideoBean> videosList = new ArrayList<>();
    private String areaCode;

    protected boolean refresh;
    protected int lastVisibleItem;
    protected boolean hasMore = true;
    protected static final int SIZE = 20;
    protected int mOffset = 0;

    public static MVViewPagerItemFragment getInstance(String areaCode) {
        MVViewPagerItemFragment mvViewPagerItemFragment = new MVViewPagerItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("areaCode", areaCode);
        mvViewPagerItemFragment.setArguments(bundle);
        return mvViewPagerItemFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        areaCode = bundle.getString("areaCode");
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.mv_viewpager_item_fragment);
        mvRecyclerView = getViewById(R.id.mv_RecyclerView);
        swipeRefreshLayout = getViewById(R.id.swipeRefreshLayout);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        initView();
    }

    @Override
    protected void onUserVisible() {

    }


    private void initView() {
        recycleViewAdapter = new MVRecycleViewAdapter(videosList);
        mvRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mvRecyclerView.setAdapter(recycleViewAdapter);
        mvRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                Logger.e("onScrollStateChanged");
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                }
                if (newState == RecyclerView.SCROLL_STATE_IDLE && (lastVisibleItem + 1 == recycleViewAdapter.getItemCount()) && hasMore) {
                    getData(mOffset + 1, SIZE, areaCode);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                Logger.e("onScrolled");
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();

            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, getResources()
                        .getDisplayMetrics()));
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh = true;
                videosList.clear();
                mOffset=0 ;
                getData(mOffset, SIZE, areaCode);
                Logger.e("onRefresh");
//                dismissLoading();
                //加载数据
            }
        });
        getData(mOffset, SIZE, areaCode);
    }

    public void getData(int offset, int size, String areaCode) {
        showLoading();
        OkHttpManager.getOkHttpManager().asyncGet(URLProviderUtil.getMVListUrl(areaCode, offset, size), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                showSnake(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null) {
                    try {
                        MVListBean mvListBean = new Gson().fromJson(response.body().string(), MVListBean.class);
                        Message msg = Message.obtain();
                        msg.obj = mvListBean.getVideos();
                        msg.what = 101;
                        mHandler.sendMessage(msg);

                        //setData(mvListBean.getVideos());
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        showSnake(e.getLocalizedMessage());
                    }
                } else {
                    showSnake("");
                }
            }
        });
    }


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 101:
                    ArrayList<VideoBean> videoBeanArrayList = (ArrayList<VideoBean>) msg.obj;
                    setData(videoBeanArrayList);
                    break;
            }
        }
    };

    public void setData(List<VideoBean> videoList) {
        dismissLoading();
        if (refresh) {
            refresh = false;
            mOffset = 0;
            videosList.clear();
        }
        if (videoList == null || videoList.size() == 0) {
            hasMore = false;
        } else {
            hasMore = true;
            int pos = videosList.size() - 1;
            videosList.addAll(videoList);
            recycleViewAdapter.notifyItemRangeChanged(pos, videoList.size());
            mOffset += videoList.size();
        }
    }

    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    public void dismissLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    public void showSnake(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}

