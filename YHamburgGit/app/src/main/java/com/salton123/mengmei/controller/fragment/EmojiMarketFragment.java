package com.salton123.mengmei.controller.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.binaryresource.BinaryResource;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.request.ImageRequest;
import com.salton123.base.BaseFragment;
import com.salton123.common.net.HttpResponseHandler;
import com.salton123.common.util.PreferencesUtils;
import com.salton123.mengmei.R;
import com.salton123.mengmei.controller.activity.MainActivity;
import com.salton123.mengmei.model.bmob.ImageBean;
import com.salton123.mengmei.view.adapter.AddCostTypeAdapter;
import com.salton123.mengmei.model.engine.BmobDataEngine;
import com.salton123.mengmei.controller.activity.ShareYourImageAty;
import com.salton123.mengmei.model.engine.ShareEngine;

import java.io.File;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildLongClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemLongClickListener;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2016/2/25 15:43
 * Time: 15:43
 * Description:
 */
public class EmojiMarketFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate, BGAOnRVItemClickListener, BGAOnRVItemLongClickListener, BGAOnItemChildClickListener, BGAOnItemChildLongClickListener {

    private RecyclerView rv_recyclerview_data;
    private BGARefreshLayout rl_recyclerview_refresh;
    private AddCostTypeAdapter.MyRecyclerViewAdapter mAdapter;
    private MainActivity _MainActivity;
    private boolean defaultChannel;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_emoji_market);
        _MainActivity = (MainActivity) getActivity();
        rl_recyclerview_refresh = (BGARefreshLayout) getViewById(R.id.rl_recyclerview_refresh);
        rv_recyclerview_data = (RecyclerView) getViewById(R.id.rv_recyclerview_data);
        rl_recyclerview_refresh.setDelegate(this);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        rv_recyclerview_data.setLayoutManager(layoutManager);
        mAdapter = new AddCostTypeAdapter.MyRecyclerViewAdapter(rv_recyclerview_data);
        rv_recyclerview_data.setAdapter(mAdapter);
        mAdapter.setOnRVItemClickListener(this);
//        mAdapter.setOnItemChildClickListener(this);
        mAdapter.setOnRVItemLongClickListener(this);
        rl_recyclerview_refresh.setRefreshViewHolder(new BGANormalRefreshViewHolder(GetContext(), true));
        defaultChannel = PreferencesUtils.getBoolean(getActivity(), "channel");     //true表示QQ,false表示微信
//        rl_recyclerview_refresh.setRefreshViewHolder(new BGANormalRefreshViewHolder(GetContext(), true));
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
    }

    private void ShareYourImages() {
        startActivity(new Intent(getActivity(), ShareYourImageAty.class));
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
//        List<ImageBean> imageModels = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            ImageBean model = new ImageBean();
//            model.setPicUrl("http://e.hiphotos.baidu.com/image/h%3D200/sign=a0901680a3c27d1eba263cc42bd4adaf/b21bb051f819861842d54ba04ded2e738bd4e600.jpg");
//            imageModels.add(model);
//        }

//        mAdapter.addMoreDatas(imageModels);
        BmobDataEngine.FetchImageModelByPage(skip, limit, new HttpResponseHandler<List<ImageBean>>() {
            @Override
            public void onSuccess(List<ImageBean> list) {
                if (list!=null&&list.size() < limit) {
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
        return false;
    }

//    @Override
//    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
//        Toast.makeText(getApplicationContext(),"点击了"+position,Toast.LENGTH_LONG).show();
//        Intent intent = new Intent("android.intent.action.SEND");
//        intent.setType("text/plain");
//        intent.putExtra(Intent.EXTRA_SUBJECT, "微信表情");
//        intent.putExtra(Intent.EXTRA_TEXT, mAdapter.getItem(position).getPicUrl());
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        //intent.setComponent(new ComponentName("com.tencent.mm.ui.tools.ShareImgUI", "com.tencent.mm"));
//        startActivity(intent);

//        shareMsg("微信表情", "可使用微信表情","/sdcard/temp.jpg", "com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
//        ImageView _SimpleDraweeView = (ImageView) parent.getChildAt(position).findViewById(R.id.iv_thumbnail);
//        if(_SimpleDraweeView.getDrawingCache()!=null) {
//            ShareImage(_SimpleDraweeView.getDrawingCache());
//        }
//        Toast.makeText(getApplicationContext(), mAdapter.getItem(position).getLocalPath(), Toast.LENGTH_LONG).show();
//        ShareImageToWeiXin(getLocalPath(mAdapter.getItem(position).getPicUrl()));
//        ImageBean _model = mAdapter.getItem(position);
//        _MainActivity.SendEmojiToWX(_model.getPicUrl());
//        if(_model!=null&&_model.getSuffix()!=null){
//            if(_model.getSuffix().equals("gif")){
//
//            }
//            else{
//                ShareImageToWeiXin(getLocalPath(_model.getPicUrl()));
//            }
//        }
//    }

    private String getLocalPath(String p_Url) {
        ImageRequest imageRequest = ImageRequest.fromUri(p_Url);
        CacheKey cacheKey = DefaultCacheKeyFactory.getInstance()
                .getEncodedCacheKey(imageRequest,null);
        BinaryResource resource = ImagePipelineFactory.getInstance()
                .getMainDiskStorageCache().getResource(cacheKey);
        File file = ((FileBinaryResource) resource).getFile();
//        Toast.makeText(getActivity(), file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        return file.getAbsolutePath();
    }


//    private void ShareImage(Bitmap p_Bitmap) {
//        Intent localIntent = new Intent("android.intent.action.SEND");
//        localIntent.setType("image/*");
////        Uri uri = Uri.parse("file:///sdcard/temp.jpg");
//        localIntent.putExtra("android.intent.extra.STREAM", p_Bitmap);
//        localIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(Intent.createChooser(localIntent, "分享"));
//    }


//    private void ShareImageToWeiXin2(String p_LocalImagePath) {
//        if (!isAvilible(getActivity(), "com.tencent.mm")) {
//            Toast.makeText(GetContext(), "请先安装微信", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        Intent localIntent = new Intent("android.intent.action.SEND");
//        localIntent.setType("image/*");
////        Uri uri = Uri.parse("file:///sdcard/temp.jpg");
//        Uri uri = Uri.parse("file://" + p_LocalImagePath);
//        localIntent.putExtra("android.intent.extra.STREAM", uri);
//        localIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        localIntent.setComponent(new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI"));
//        startActivity(Intent.createChooser(localIntent, "分享"));
//    }
//
//    private void shareMsg(String msgTitle, String msgText,
//                          String imgPath, String packageName, String activityName) {
//        if (!packageName.isEmpty() && !isAvilible(GetContext(), packageName)) {
//            Toast.makeText(GetContext(), "请先安装微信", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        Intent intent = new Intent("android.intent.action.SEND");
//        if ((imgPath == null) || (imgPath.equals(""))) {
//            intent.setType("text/plain");
//        } else {
//            File f = new File(imgPath);
//            if ((f != null) && (f.exists()) && (f.isFile())) {
//                intent.setType("image/png");
//                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
//            }
//        }
//        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
//        intent.putExtra(Intent.EXTRA_TEXT, msgText);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        if (!packageName.isEmpty()) {
//            intent.setComponent(new ComponentName(packageName, activityName));
//            startActivity(intent);
//        } else {
//            startActivity(Intent.createChooser(intent, msgTitle));
//        }
//    }


    @Override
    public void onItemChildClick(ViewGroup parent, View childView, int position) {

    }

    @Override
    public boolean onItemChildLongClick(ViewGroup parent, View childView, int position) {
        ShowToast("onItemChildLongClick");
        if (childView.getId() == R.id.iv_thumbnail) {
//            Toast.makeText(getActivity(), "长按图片", Toast.LENGTH_SHORT).show();
            ImageBean p_ImageBean = mAdapter.getItem(position);
            addToCollectionDialog(p_ImageBean);
        }
        return false;
    }

    private void addToCollectionDialog(final ImageBean p_ImageBean) {
        AlertDialog.Builder _builder = new AlertDialog.Builder(_MainActivity);
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
        String showTip = defaultChannel == true ?"微信":"QQ";      //取反
        _builder.setNegativeButton("发送到" + showTip, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (defaultChannel == false) {      //发送到微信
//                    LogUtils.e(getLocalPath(p_ImageBean.getPicUrl()));
//                    System.out.println(p_ImageBean.getPicUrl());
//                    System.out.println(getLocalPath(p_ImageBean.getPicUrl()));
                    ShareEngine.ShareImageToQQ(getActivity(), getLocalPath(p_ImageBean.getPicUrl()));
                } else {
                    if (p_ImageBean != null) {
                        if (!TextUtils.isEmpty(p_ImageBean.getSuffix()) && p_ImageBean.getSuffix().equals("gif")) {
                            ShareEngine.SendEmojiToWX(getActivity(), getLocalPath(p_ImageBean.getPicUrl()));
                        } else {
                            ShareEngine.ShareImageToWeiXin(getActivity(), getLocalPath(p_ImageBean.getPicUrl()));
                        }
                    } else {
                        ShowToast("网络错误，稍后重试！");
                    }
                }
            }
        });
        _builder.show();
    }

    @Override
    public boolean onRVItemLongClick(ViewGroup parent, View itemView, int position) {
//            ShowToast("onRVItemLongClick");
        ImageBean p_ImageBean = mAdapter.getItem(position);
        addToCollectionDialog(p_ImageBean);
        return true;
    }


    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
//        ShowToast("onRVItemClick");
//        SendEmojiToWX(Environment.getExternalStorageDirectory().getAbsolutePath() + "/987654321");
        ImageBean _model = mAdapter.getItem(position);
        if (defaultChannel == true) {      //发送到微信
            ShareEngine.ShareImageToQQ(getActivity(), getLocalPath(_model.getPicUrl()));
        } else {
            if (_model != null) {
                if (!TextUtils.isEmpty(_model.getSuffix()) && _model.getSuffix().equals("gif")) {
                    ShareEngine.SendEmojiToWX(getActivity(), getLocalPath(_model.getPicUrl()));
                } else {
                    ShareEngine.ShareImageToWeiXin(getActivity(), getLocalPath(_model.getPicUrl()));
                }
            } else {
                ShowToast("网络错误，稍后重试！");
            }
        }

    }


//
//    private byte[] InputStreamToByte(InputStream is) throws IOException {
//        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
//        int ch;
//        while ((ch = is.read()) != -1) {
//            bytestream.write(ch);
//        }
//        byte imgdata[] = bytestream.toByteArray();
//        bytestream.close();
//        return imgdata;
//    }
}
