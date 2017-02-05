package com.salton123.mengmei.controller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mingle.widget.LoadingView;
import com.orhanobut.logger.Logger;
import com.salton123.mengmei.R;
import com.salton123.base.BaseFragment;
import com.salton123.mengmei.controller.activity.NewVoiceActivity;
import com.salton123.mengmei.model.api.BmobApi;
import com.salton123.mengmei.model.bmob.VoiceEntityResult;
import com.salton123.mengmei.view.adapter.VoiceRecyclerAdapter;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2016/9/5 22:25
 * Time: 22:25
 * Description:
 */
public class VoiceFragment extends BaseFragment implements BGAOnItemChildClickListener{


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.circle_refresh_layout)
    SwipeRefreshLayout circleRefreshLayout;
    @BindView(R.id.loadingView)
    LoadingView loadingView;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.fab_add)
    FloatingActionButton fabAdd;
    @BindView(R.id.ll_status)
    FrameLayout llStatus;

    VoiceRecyclerAdapter mVoiceRecyclerAdapter;
    protected int lastVisibleItem;
    protected boolean hasMore = true;
    IjkMediaPlayer ijkMediaPlayer;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fm_voice);
        ButterKnife.bind(this, mContentView);
        mVoiceRecyclerAdapter = new VoiceRecyclerAdapter(recyclerView);
        mVoiceRecyclerAdapter.setOnItemChildClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mVoiceRecyclerAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                Logger.e("onScrollStateChanged");
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                }
                if (newState == RecyclerView.SCROLL_STATE_IDLE && (lastVisibleItem + 1 == mVoiceRecyclerAdapter.getItemCount()) && hasMore) {
                    //
                    ijkMediaPlayer.pause();
                    ijkMediaPlayer.reset();
                    showToast("加载更多数据");
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                Logger.e("onScrolled");
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();

            }
        });
    }

    @Override
    protected void setListener() {
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NewVoiceActivity.class));
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        Call<VoiceEntityResult> voiceCall = BmobApi.getVoice().getVoiceList();
        voiceCall.enqueue(new Callback<VoiceEntityResult>() {
            @Override
            public void onResponse(Call<VoiceEntityResult> call, Response<VoiceEntityResult> response) {
                mVoiceRecyclerAdapter.addNewDatas(response.body().getResults());
                loadingView.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<VoiceEntityResult> call, Throwable throwable) {
                throwable.printStackTrace();
                Logger.e("onFailure processLogic");
                loadingView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onItemChildClick(ViewGroup parent, View childView, int position) {
        Logger.e("播放音乐按钮");
        if (childView.getId() == R.id.iv_item_play_voice) {

            //播放音乐
            ijkMediaPlayer = new IjkMediaPlayer();
//            ijkMediaPlayer.reset();
            try {
                Logger.e(mVoiceRecyclerAdapter.getItem(position).getVoiceUrl());
                ijkMediaPlayer.setDataSource("/sdcard/player.mp3");
//                ijkMediaPlayer.prepareAsync();
                ijkMediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
