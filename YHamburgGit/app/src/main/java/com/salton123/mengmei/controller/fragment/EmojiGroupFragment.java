package com.salton123.mengmei.controller.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.facebook.binaryresource.BinaryResource;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.request.ImageRequest;
import com.salton123.base.BaseFragment;
import com.salton123.common.net.HttpResponseHandler;
import com.salton123.common.util.LogUtils;
import com.salton123.common.util.PreferencesUtils;
import com.salton123.mengmei.R;
import com.salton123.mengmei.controller.activity.MainActivity;
import com.salton123.mengmei.model.engine.BmobDataEngine;
import com.salton123.mengmei.view.adapter.EmojiGroupAdapter;
import com.salton123.mengmei.model.bean.EmojiGroup;
import com.salton123.mengmei.model.engine.ShareEngine;

import java.io.File;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2016/3/22 22:36
 * Time: 22:36
 * Description:
 */
public class EmojiGroupFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate, EmojiGroupAdapter.GirdViewItemClickListener {

    private RecyclerView rv_recyclerview_data;
    private BGARefreshLayout rl_recyclerview_refresh;
    private EmojiGroupAdapter mAdapter;
    private MainActivity _MainActivity;
    private boolean defaultChannel;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_emoji_market);
        _MainActivity = (MainActivity) getActivity();
        rl_recyclerview_refresh = (BGARefreshLayout) getViewById(R.id.rl_recyclerview_refresh);
        rv_recyclerview_data = (RecyclerView) getViewById(R.id.rv_recyclerview_data);
        rl_recyclerview_refresh.setDelegate(this);
//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        LinearLayoutManager layoutManager = new LinearLayoutManager(GetContext(), LinearLayoutManager.VERTICAL, false);
        rv_recyclerview_data.setLayoutManager(layoutManager);
        mAdapter = new EmojiGroupAdapter(rv_recyclerview_data);
        rv_recyclerview_data.setAdapter(mAdapter);
        mAdapter.setGirdViewItemClickListener(this);
        rl_recyclerview_refresh.setRefreshViewHolder(new BGANormalRefreshViewHolder(GetContext(), true));
        defaultChannel = PreferencesUtils.getBoolean(getActivity(), "channel");     //true表示QQ,false表示微信
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        GetData();
    }

    @Override
    protected void onUserVisible() {
        defaultChannel = PreferencesUtils.getBoolean(getActivity(), "channel");     //true表示QQ,false表示微信
    }

    private int skip = 0;
    private int limit = 30;

    private void GetData() {
//        List<EmojiGroup> emojiList = new ArrayList<>();
//        for(int i = 0 ; i <10;i++){
//            EmojiGroup _emoji = new EmojiGroup();
//            _emoji.setEmojiPic("http://newfile.codenow.cn:8080/7ac54f47de8c4dba981bc5db51aa427f.jpg?t=2&a=ab02eff0aa4cf8d3a356151a6951c1a4;http://newfile.codenow.cn:8080/50fec38b99da4aecab87f606e7bf7e89.jpg?t=2&a=ab02eff0aa4cf8d3a356151a6951c1a4;http://newfile.codenow.cn:8080/a076ff6dfc6d40ea9fae7d493a76b43b.jpg?t=2&a=ab02eff0aa4cf8d3a356151a6951c1a4;http://newfile.codenow.cn:8080/26a11fce95e144a99a24a084a1b9f6ca.jpg?t=2&a=ab02eff0aa4cf8d3a356151a6951c1a4;http://newfile.codenow.cn:8080/dc65116836234b4d96c0529c6934155f.jpg?t=2&a=ab02eff0aa4cf8d3a356151a6951c1a4;");
//            _emoji.setOwnerId("nidsada");
//            _emoji.setKeyWord("金馆长");
//            emojiList.add(_emoji);
//        }
//        mAdapter.addMoreDatas(emojiList);
        BmobDataEngine.FetchEmojiGroupByPage(GetContext(), skip, limit, new HttpResponseHandler<List<EmojiGroup>>() {
            @Override
            public void onSuccess(List<EmojiGroup> list) {
                if (list.size() < limit) {
//                    Toast.makeText(getApplicationContext(), "数据加载完毕", Toast.LENGTH_LONG).show();
                    rl_recyclerview_refresh.endLoadingMore();
                }
                //  showToast("加载成功...");
                rl_recyclerview_refresh.endRefreshing();
                mAdapter.addMoreDatas(list);
                skip = mAdapter.getDatas().size();
            }

            @Override
            public void onFailure(String content) {
                rl_recyclerview_refresh.endRefreshing();
            }
        });
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        skip = 0;
        mAdapter.clear();
        GetData();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        GetData();
        return true;
    }

    private String getLocalPath(String p_Url) {
       try{
           ImageRequest imageRequest = ImageRequest.fromUri(p_Url);
           CacheKey cacheKey = DefaultCacheKeyFactory.getInstance()
                   .getEncodedCacheKey(imageRequest,null);
           BinaryResource resource = ImagePipelineFactory.getInstance()
                   .getMainDiskStorageCache().getResource(cacheKey);
           File file = ((FileBinaryResource) resource).getFile();
           return file.getAbsolutePath();
       }catch(Exception e){
           e.printStackTrace();
           return "";
       }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        ShowToast("点击了这里：" + position);
        String item = (String) parent.getItemAtPosition(position);
        String suffix = item.substring(item.lastIndexOf(".") + 1, item.length());
        LogUtils.e("item"+item);
        LogUtils.e("suffix:"+suffix);
        if (defaultChannel == true) {      //发送到微信
            ShareEngine.ShareImageToQQ(getActivity(), getLocalPath(item));
        } else {
            if (!TextUtils.isEmpty(suffix) && suffix.equals("gif")) {
                ShareEngine.SendEmojiToWX(getActivity(), getLocalPath(item));
            } else {
                ShareEngine.ShareImageToWeiXin(getActivity(), getLocalPath(item));
            }
        }
    }
}
