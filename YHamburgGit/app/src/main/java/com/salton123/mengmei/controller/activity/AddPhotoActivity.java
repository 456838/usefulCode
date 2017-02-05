package com.salton123.mengmei.controller.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.salton123.base.ActivityFrameIOS;
import com.salton123.base.GlobalParams;
import com.salton123.common.util.FileUtils;
import com.salton123.mengmei.R;
import com.salton123.mengmei.view.adapter.AddPhototAdapter;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2016/1/12 14:22
 * Time: 14:22
 * Description:
 */
public class AddPhotoActivity extends ActivityFrameIOS implements BGAOnRVItemClickListener {
    private ImageView iv_add;
    private int canLoadImg;
    private static final int REASON_SIZE_INCOMFORTABLE = 1;      //尺寸不合格
    private static final int REASON_TYPE_INCOMFORTABLE = 2;      //类型不合格
    private static final int REASON_OK = 3;      //类型不合格
    String mImgPath;   //本地图片地址
    private ProgressDialog mProgressDialog;
    private RecyclerView rv_type;
    private AddPhototAdapter mAddCostTypeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppendMainBody(R.layout.activity_add_photo);
        //SetTopAdditionalHint(getString(R.string.label_submit));
        SetTopTitle("添加照片");
        InitView();
    }

    /**
     * 初始化控件
     */
    public void InitView() {

        rv_type = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(GetContext(), LinearLayoutManager.HORIZONTAL, true);
        rv_type.setLayoutManager(layoutManager);
        mAddCostTypeAdapter = new AddPhototAdapter(rv_type);
        rv_type.setAdapter(mAddCostTypeAdapter);
        iv_add = (ImageView) findViewById(R.id.iv_add);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(getString(R.string.label_uploading));
        mProgressDialog.setCancelable(false);
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 打开图库，选取照片
                Intent _Intent = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(_Intent, GlobalParams.FLAG_REQUEST_CODE_200);
            }
        });
    }

    @Override
    public void InitListener() {

    }

    @Override
    public void InitData() {
        mAddCostTypeAdapter.addItem(mAddCostTypeAdapter.getDatas().size(),"https://p1.ssl.qhimg.com/t0151320b1d0fc50be8.png");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent _IntentData) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, _IntentData);
        if (requestCode == GlobalParams.FLAG_REQUEST_CODE_200 & resultCode == RESULT_OK) {
            Uri _PicUri = _IntentData.getData();
            String _PicUriStr = _IntentData.getDataString();
            Cursor _Cursor = getContentResolver().query(_PicUri, null, null,
                    null, null);
            if (_Cursor != null) {
                if (_Cursor.moveToFirst()) { //
                    // while(_Cursor.moveToNext()){
                    int _Index = _Cursor
                            .getColumnIndex(MediaStore.Images.Media.DATA);
                    mImgPath = _Cursor.getString(_Index);
                    long _ImgSize = FileUtils.getFileSize(mImgPath);
                    if (_ImgSize > 10000000 || _ImgSize < 100) {
                        canLoadImg = REASON_SIZE_INCOMFORTABLE;
                        ShowToast("size error");
                        return;
                    }
                    if (mImgPath.toLowerCase().endsWith(".png") || mImgPath.toLowerCase().endsWith("jpg") || mImgPath.toLowerCase().endsWith("jpeg")) {
//                        ImageLoaderUtils.display(iv_add, mImgPath);
                        mAddCostTypeAdapter.addItem(mAddCostTypeAdapter.getDatas().size(), mImgPath);
                    } else {
                        canLoadImg = REASON_TYPE_INCOMFORTABLE;
                        ShowToast("label_file_format_err");
                        return;
                    }
                }
            }
        } else {
            ShowToast("label_select_err");
        }
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        if(position==mAddCostTypeAdapter.getDatas().size()-1){      //最后一个
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 打开图库，选取照片
                    Intent _Intent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(_Intent, GlobalParams.FLAG_REQUEST_CODE_200);
                }
            });
        }
    }
}