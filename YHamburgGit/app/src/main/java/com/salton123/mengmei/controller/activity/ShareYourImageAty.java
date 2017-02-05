package com.salton123.mengmei.controller.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.salton123.base.ActivityFrameIOS;
import com.salton123.base.AdapterBase;
import com.salton123.base.ViewHolder;
import com.salton123.mengmei.model.bean.User;
import com.salton123.common.util.ImageLoaderUtils;
import com.salton123.mengmei.R;
import com.salton123.mengmei.model.engine.BmobDataEngine;
import com.salton123.mengmei.model.bmob.ImageBean;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2016/2/24 11:54
 * Time: 11:54
 */
public class ShareYourImageAty extends ActivityFrameIOS {

    private GridView gv_pictures;
    private GridViewAdapter mAdapter;
    private static final int REQUEST_IMAGE = 2;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppendMainBody(R.layout.aty_share_your_image);
        SetTopTitle("表情分享");
        SetTopAdditionImageListener(R.drawable.ic_cloud_upload_white_24dp, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAdapter.GetList().isEmpty()) {
                    ShowToast("请添加照片");
                    return;
                }
                //尚未登录
                if (User.getCurrentUser(User.class) == null) {
                    _userId = "";
                    ShowTipDialog();
                } else {
                    _userId = User.getCurrentUser(User.class).getObjectId();
                    UploadImageModel();
                }
            }
        });
    }

    public void InitView() {
        gv_pictures = (GridView) findViewById(R.id.gv_pictures);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

    }

    private String[] ListToArray(List<String> lists) {
        String arr[] = new String[lists.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = lists.get(i);
        }
        return arr;
    }

    String _userId;

    public void InitListener() {
        mAdapter = new GridViewAdapter(this);
        gv_pictures.setAdapter(mAdapter);

    }

    private void ShowTipDialog() {
        AlertDialog.Builder _builder = new AlertDialog.Builder(ShareYourImageAty.this);
        _builder.setTitle("温馨提示：");
        _builder.setMessage("如果你未登录帐号，所上传表情将不会记录在你的个人名下，但是能够共享给其他人使用.是否现在登录？");
        _builder.setPositiveButton("登录", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OpenActivity(LoginActivity.class);
            }
        });
        _builder.setNegativeButton("继续", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UploadImageModel();

            }
        });
        _builder.setCancelable(false);
        _builder.show();
    }


    private void UploadImageModel() {
        mProgressDialog.show();
        BmobDataEngine.UploadMultiImage(ListToArray(mSelectPath), new UploadBatchListener() {
                    @Override
                    public void onSuccess(List<BmobFile> list, List<String> list1) {
                        for (int i = 0; i < list.size(); i++) {
                            String fileUrl = list.get(i).getUrl();
                            String suffix = list.get(i).getFilename().substring(list.get(i).getFilename().lastIndexOf(".") + 1, list.get(i).getFilename().length());
                            Log.e("filename", fileUrl);
                            ImageBean _ImageBean = new ImageBean();
                            _ImageBean.setPicUrl(fileUrl);
                            _ImageBean.setProvider(_userId);
                            _ImageBean.setSuffix(suffix);
                            if (User.getCurrentUser(User.class) != null) {
                                BmobRelation _realtion = new BmobRelation();
                                _realtion.add(User.getCurrentUser(User.class));
                                _ImageBean.setLinks(_realtion);
                            }
                            _ImageBean.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e == null) {
                                        Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_LONG).show();
                                        mProgressDialog.dismiss();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "保存失败" + s, Toast.LENGTH_LONG).show();
//                            Log.e("save", s);
                                        mProgressDialog.dismiss();
                                    }
                                }
                            });

                        }
                    }

                    @Override
                    public void onProgress(int i, int i1, int i2, int i3) {

                    }

                    @Override
                    public void onError(int i, String s) {
                        Log.e("onFailure", s);
                        Toast.makeText(getApplicationContext(), "上传失败", Toast.LENGTH_LONG).show();
                        mProgressDialog.dismiss();
                    }
                }
        );
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProgressDialog = null;
    }

    @Override
    public void InitData() {

    }

    /**
     * @return 布局解析器
     */
    protected LayoutInflater GetLayouInflater() {
        LayoutInflater _LayoutInflater = LayoutInflater.from(this);
        return _LayoutInflater;
    }

    class GridViewAdapter extends AdapterBase {

        public GridViewAdapter(Context pContext) {
            super(pContext);
        }

        @Override
        public int getCount() {
            return super.getCount() + 1;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = GetLayouInflater().inflate(R.layout.adapter_item_pic_info, null);
            }
            ImageView iv_thumbnail = ViewHolder.get(convertView, R.id.iv_thumbnail);
//            if (GetList().size() != 0) {
//
//            }
            if (position == getCount() - 1) {
                iv_thumbnail.setImageResource(R.drawable.icon_addpic_unfocused);
                iv_thumbnail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(ShareYourImageAty.this, MultiImageSelectorActivity.class);
                        // 是否显示拍摄图片
                        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
                        // 最大可选择图片数量
                        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 20);
                        // 选择模式
                        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
                        startActivityForResult(intent, REQUEST_IMAGE);
                    }
                });
            } else {
                String url = (String) GetList().get(position);
                ImageLoaderUtils.display(iv_thumbnail, url);
                iv_thumbnail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GetList().remove(position);
                        notifyDataSetChanged();
                    }
                });
            }
            return convertView;
        }
    }

    private ArrayList<String> mSelectPath;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                mAdapter.AddToList(mSelectPath);
            }
        }
    }

}
