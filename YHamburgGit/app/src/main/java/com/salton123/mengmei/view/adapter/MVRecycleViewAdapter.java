package com.salton123.mengmei.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.salton123.mengmei.R;
import com.salton123.mengmei.model.yinyuetai.VideoBean;

import org.xutils.x;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Mr.Yangxiufeng
 * DATE 2016/5/11
 * YinYueTai
 */
public class MVRecycleViewAdapter extends RecyclerView.Adapter<MVRecycleViewAdapter.ViewHolder> {

    private ArrayList<VideoBean> videoList = new ArrayList<>();

    public MVRecycleViewAdapter(ArrayList<VideoBean> videoList) {
        this.videoList = videoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mv_recycleview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final VideoBean videoBean = videoList.get(position);
//        x.image().bind(holder.videoplayer.coverImageView, videoBean.getThumbnailPic());
//        holder.videoplayer.titleTextView.setText(videoBean.getTitle());
        boolean setUp = false;
        if (TextUtils.isEmpty(videoBean.getShdUrl())) {
            if (TextUtils.isEmpty(videoBean.getUhdUrl())) {
                if (TextUtils.isEmpty(videoBean.getHdUrl())) {
                    setUp = holder.videoplayer.setUp(videoBean.getUrl()
                            , JCVideoPlayer.SCREEN_LAYOUT_LIST, videoBean.getTitle());
                } else {
                    setUp = holder.videoplayer.setUp(videoBean.getHdUrl()
                            , JCVideoPlayer.SCREEN_LAYOUT_LIST, videoBean.getTitle());
                }
            } else {
                setUp = holder.videoplayer.setUp(videoBean.getUhdUrl()
                        , JCVideoPlayer.SCREEN_LAYOUT_LIST, videoBean.getTitle());
            }
        } else {
            setUp = holder.videoplayer.setUp(videoBean.getShdUrl()
                    , JCVideoPlayer.SCREEN_LAYOUT_LIST, videoBean.getTitle());
        }
        if (setUp) {
            x.image().bind(holder.videoplayer.thumbImageView, videoBean.getAlbumImg());
        }
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.videoplayer)
        JCVideoPlayerStandard videoplayer;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
