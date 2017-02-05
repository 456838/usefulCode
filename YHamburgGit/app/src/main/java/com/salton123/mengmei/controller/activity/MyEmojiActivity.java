package com.salton123.mengmei.controller.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.binaryresource.BinaryResource;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.request.ImageRequest;
import com.orhanobut.logger.Logger;
import com.salton123.common.net.HttpResponseHandler;
import com.salton123.common.util.PreferencesUtils;
import com.salton123.mengmei.R;
import com.salton123.mengmei.model.bean.User;
import com.salton123.mengmei.model.bmob.ImageBean;
import com.salton123.mengmei.model.engine.BmobDataEngine;
import com.salton123.mengmei.model.engine.ShareEngine;
import com.salton123.mengmei.view.adapter.MyEmojiAdapter;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildLongClickListener;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

import static com.salton123.mengmei.R.id.rl_recyclerview_refresh;
import static com.salton123.mengmei.R.id.rv_recyclerview_data;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2016/10/17 18:38
 * Time: 18:38
 * Description:
 */
public class MyEmojiActivity extends AppCompatActivity implements BGARefreshLayout.BGARefreshLayoutDelegate, BGAOnItemChildClickListener, BGAOnItemChildLongClickListener {

    @BindView(rv_recyclerview_data)
    RecyclerView rvRecyclerviewData;
    @BindView(rl_recyclerview_refresh)
    BGARefreshLayout rlRecyclerviewRefresh;
    private MyEmojiAdapter mAdapter;
    private User mUser;
    private boolean isViewLoaded = false;
    private boolean defaultChannel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_emoji_market);
        ButterKnife.bind(this);
        defaultChannel = PreferencesUtils.getBoolean(this, "channel");     //true表示QQ,false表示微信
        mUser = User.getCurrentUser(User.class);
        rlRecyclerviewRefresh.setDelegate(this);
//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        rlRecyclerviewRefresh.setRefreshViewHolder(new BGANormalRefreshViewHolder(MyEmojiActivity.this, true));
//        rv_recyclerview_data.addItemDecoration(new Divider(GetContext()));
        LinearLayoutManager layoutManager = new LinearLayoutManager(MyEmojiActivity.this, LinearLayoutManager.VERTICAL, false);
        rvRecyclerviewData.setLayoutManager(layoutManager);
        mAdapter = new MyEmojiAdapter(rvRecyclerviewData);
        rvRecyclerviewData.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(this);
        mAdapter.setOnItemChildLongClickListener(this);

//        rl_recyclerview_refresh.setRefreshViewHolder(new BGANormalRefreshViewHolder(MyEmojiActivity.this, true));
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
////                UploadImages();
//                ShareYourImages();
//            }
//        });
        isViewLoaded = true;
        GetData();
    }

    private int skip = 0;
    private int limit = 30;

    private void GetData() {
//        List<ImageBean> imageModels = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            ImageBean model = new ImageBean();
//            model.setPicUrl("http://e.hiphotos.baidu.com/image/h%3D200/sign=a0901680a3c27d1eba263cc42bd4adaf/b21bb051f819861842d54ba04ded2e738bd4e600.jpg");
//            imageModels.add(model);
//        }
//        mAdapter.addMoreDatas(imageModels);
        String userId;
        if (mUser == null) {
            userId = "";
        } else {
            userId = mUser.getObjectId();
        }
        BmobDataEngine.FetchMyEmojiByPage(userId, skip, limit, new HttpResponseHandler<List<ImageBean>>() {
            @Override
            public void onSuccess(List<ImageBean> list) {
                if (list.size() < limit) {
//                    Toast.makeText(getApplicationContext(), "数据加载完毕", Toast.LENGTH_LONG).show();
                    rlRecyclerviewRefresh.endLoadingMore();
                }
                //  showToast("加载成功...");
                rlRecyclerviewRefresh.endRefreshing();
                mAdapter.addMoreDatas(list);
                skip = mAdapter.getDatas().size();
            }

            @Override
            public void onFailure(String content) {
                Logger.e(content);
                rlRecyclerviewRefresh.endRefreshing();
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
        ImageRequest imageRequest = ImageRequest.fromUri(p_Url);
        CacheKey cacheKey = DefaultCacheKeyFactory.getInstance()
                .getEncodedCacheKey(imageRequest, null);
        BinaryResource resource = ImagePipelineFactory.getInstance()
                .getMainDiskStorageCache().getResource(cacheKey);
        File file = ((FileBinaryResource) resource).getFile();
//        Toast.makeText(MyEmojiActivity.this, file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        return file.getAbsolutePath();
    }


    public boolean isAvilible(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        for (int i = 0; i < pinfo.size(); i++) {
            if (((PackageInfo) pinfo.get(i)).packageName.equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }

    @Override
    public void onItemChildClick(ViewGroup parent, View childView, final int position) {
        if (childView.getId() == R.id.tv_del) {
//            Toast.makeText(MyEmojiActivity.this, "点击了删除按钮", Toast.LENGTH_SHORT).show();
            ImageBean selctedImageBean = mAdapter.getItem(position);
            BmobDataEngine.DelteMyEmoji(selctedImageBean, new HttpResponseHandler() {
                @Override
                public void onSuccess(Object content) {
                    mAdapter.removeItem(position);
                    GetData();
                }

                @Override
                public void onFailure(String content) {

                }
            });

        } else if (childView.getId() == R.id.iv_thumbnail) {
            ImageBean _model = mAdapter.getItem(position);
            if (defaultChannel == true) {      //发送到微信
                ShareEngine.ShareImageToQQ(MyEmojiActivity.this, getLocalPath(_model.getPicUrl()));
            } else {
                if (_model != null) {
                    if (!TextUtils.isEmpty(_model.getSuffix()) && _model.getSuffix().equals("gif")) {
                        ShareEngine.SendEmojiToWX(MyEmojiActivity.this, getLocalPath(_model.getPicUrl()));
                    } else {
                        ShareEngine.ShareImageToWeiXin(MyEmojiActivity.this, getLocalPath(_model.getPicUrl()));
                    }
                } else {
                    ShowToast("网络错误，稍后重试！");
                }
            }

        }
    }

//    @Override
//    protected void onUserVisible() {
//        if (isViewLoaded) {
//            switchLoginStatus();
//            defaultChannel = PreferencesUtils.getBoolean(MyEmojiActivity.this, "channel");     //true表示QQ,false表示微信
//        }
//    }

    private void switchLoginStatus() {
        mUser = User.getCurrentUser(User.class);
        if (mUser != null) {
            GetData();
        }
    }

    @Override
    public boolean onItemChildLongClick(ViewGroup parent, View childView, int position) {
        ImageBean p_ImageBean = mAdapter.getItem(position);
        addToCollectionDialog(p_ImageBean);
        return true;
    }

    private void addToCollectionDialog(final ImageBean p_ImageBean) {
        AlertDialog.Builder _builder = new AlertDialog.Builder(MyEmojiActivity.this);
//        _builder.setTitle("提示");
//        _builder.setMessage("");
        _builder.setPositiveButton("收藏", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                BmobDataEngine.AddToCollection(p_ImageBean, new HttpResponseHandler<String>() {
                    @Override
                    public void onSuccess(String content) {
                        ShowToast(content);
                    }

                    @Override
                    public void onFailure(String content) {
                        ShowToast(content);
                    }
                });
            }
        });
        String showTip = defaultChannel == true ? "微信" : "QQ";      //取反
        _builder.setNegativeButton("发送到" + showTip, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (defaultChannel == false) {      //发送到微信
//                    LogUtils.e(getLocalPath(p_ImageBean.getPicUrl()));
//                    System.out.println(p_ImageBean.getPicUrl());
//                    System.out.println(getLocalPath(p_ImageBean.getPicUrl()));
                    ShareEngine.ShareImageToQQ(MyEmojiActivity.this, getLocalPath(p_ImageBean.getPicUrl()));
                } else {
                    if (p_ImageBean != null) {
                        if (!TextUtils.isEmpty(p_ImageBean.getSuffix()) && p_ImageBean.getSuffix().equals("gif")) {
                            ShareEngine.SendEmojiToWX(MyEmojiActivity.this, getLocalPath(p_ImageBean.getPicUrl()));
                        } else {
                            ShareEngine.ShareImageToWeiXin(MyEmojiActivity.this, getLocalPath(p_ImageBean.getPicUrl()));
                        }
                    } else {
                        ShowToast("网络错误，稍后重试！");
                    }
                }
            }
        });
        _builder.show();
    }

    private void ShowToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }


}
