package com.salton123.mengmei.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.salton123.base.ActivityFrameIOS;
import com.salton123.mengmei.model.bean.User;
import com.salton123.common.net.HttpResponseHandler;
import com.salton123.mengmei.R;
import com.salton123.mengmei.model.engine.BmobDataEngine;
import com.salton123.mengmei.view.adapter.ConsigneeAdapter;
import com.salton123.mengmei.model.bean.Consignee;
import com.salton123.common.widget.Divider;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by Administrator on 2016/2/6.
 */
public class ConsigneeAty extends ActivityFrameIOS implements BGARefreshLayout.BGARefreshLayoutDelegate, BGAOnRVItemClickListener, BGAOnItemChildClickListener {

    private BGARefreshLayout mRefreshLayout;
    private ConsigneeAdapter mConsigneeAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppendMainBody(R.layout.aty_consignee);
        SetTopAdditionImageListener(R.drawable.ic_add, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenActivityForResult(EditConsigneeAty.class, 100);
            }
        });

    }

    @Override
    public void InitView() {
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.refreshLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    @Override
    public void InitListener() {
        mRefreshLayout.setDelegate(this);
        mConsigneeAdapter = new ConsigneeAdapter(mRecyclerView);
        mConsigneeAdapter.setOnRVItemClickListener(this);
        mConsigneeAdapter.setOnItemChildClickListener(this);
       // mRefreshLayout.setCustomHeaderView(DataEngine.getCustomHeaderView(GetContext()), true);
    }


    @Override
    public void InitData() {
        mRefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(GetContext(), true));
        mRecyclerView.addItemDecoration(new Divider(GetContext()));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(GetContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mConsigneeAdapter);
        GetData();
    }

    private int mLimit = 5;
    private int total = 0;

    private void GetData() {
        BmobDataEngine.FetchConsigneeByPage(User.getCurrentUser().getObjectId(), total, mLimit, new HttpResponseHandler() {
            @Override
            public void onSuccess(Object content) {
                List<Consignee> list = (List<Consignee>) content;
                mConsigneeAdapter.addMoreDatas(list);
                total = mConsigneeAdapter.getDatas().size();
                mRefreshLayout.endRefreshing();
//                showToast("加载成功...");
            }

            @Override
            public void onFailure(String content) {
                mRefreshLayout.endRefreshing();
                //showToast("加载失败...");
            }
        });
    }

    @Override
    public void onItemChildClick(ViewGroup viewGroup, View view, int i) {
        if (view.getId() == R.id.lay_base) {
            Bundle _bundle = new Bundle();
            _bundle.putSerializable("consignee", mConsigneeAdapter.getItem(i));
            OpenActivityForResult(EditConsigneeAty.class, 101, _bundle);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            total = 0;
            mConsigneeAdapter.clear();
            GetData();
        }
    }

    @Override
    public void onRVItemClick(ViewGroup viewGroup, View view, int i) {
//        Bundle _bundle = new Bundle();
//        _bundle.putSerializable("consignee", mSwipeViewAdapter.getItem(i));
//        OpenActivityForResult(EditConsigneeAty.class, 101, _bundle);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout bgaRefreshLayout) {
        total = 0;
        mConsigneeAdapter.clear();
        GetData();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout bgaRefreshLayout) {
        bgaRefreshLayout.endLoadingMore();
        return false;
    }
}
