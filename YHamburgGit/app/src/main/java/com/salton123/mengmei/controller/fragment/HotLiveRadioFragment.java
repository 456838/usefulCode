package com.salton123.mengmei.controller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mingle.widget.LoadingView;
import com.salton123.base.BaseFragment;
import com.salton123.common.net.HttpResponseHandler;
import com.salton123.common.util.ToastUtils;
import com.salton123.hamb.controller.activity.ScenarioSearchAty;
import com.salton123.mengmei.R;
import com.salton123.mengmei.controller.activity.CreateNewLiveActivity;
import com.salton123.mengmei.model.bean.User;
import com.salton123.mengmei.model.bean.YcProgram;
import com.salton123.mengmei.model.engine.BmobDataEngine;
import com.salton123.mengmei.view.adapter.HotRadioRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static com.salton123.mengmei.R.id.swipeRefreshLayout;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2016/9/24 15:32
 * Time: 15:32
 * Description:
 */
public class HotLiveRadioFragment extends BaseFragment implements BGAOnRVItemClickListener {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.loadingView)
    LoadingView mLoadingView;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.fab_add)
    FloatingActionButton mFabAdd;
    @BindView(R.id.ll_status)
    FrameLayout mLlStatus;
    HotRadioRecyclerAdapter mAdapter;
    @BindView(R.id.tv_title_back)
    TextView tvTitleBack;
    @BindView(R.id.tv_title_search)
    TextView tvTitleSearch;


    public static HotLiveRadioFragment getInstance(String areaCode) {
        HotLiveRadioFragment mHotLiveRadioFragment = new HotLiveRadioFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("areaCode", areaCode);
//        mvViewPagerItemFragment.setArguments(bundle);
        return mHotLiveRadioFragment;
    }

    //    private ArrayList<YcProgram> videosList = new ArrayList<>();
    protected boolean refresh;
    protected int lastVisibleItem;
    protected boolean hasMore = true;
    int skip;
    int limit = 5;


    public void getData(int offset, int size) {
        showLoading();
        BmobDataEngine.getYcProgrameByPage(skip, limit, new FindListener<YcProgram>() {
            @Override
            public void done(List<YcProgram> list, BmobException e) {
                Message msg = Message.obtain();
                msg.obj = list;
                msg.what = 101;
                mHandler.sendMessage(msg);
            }
        });
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 101:
                    ArrayList<YcProgram> videoBeanArrayList = (ArrayList<YcProgram>) msg.obj;
                    setData(videoBeanArrayList);
                    break;
            }
        }
    };

    public void setData(List<YcProgram> videoList) {
        dismissLoading();
        if (refresh) {
            refresh = false;
            skip = 0;
            mAdapter.clear();
        }
        if (videoList == null || videoList.size() == 0) {
            hasMore = false;
        } else {
            hasMore = true;
            int pos = mAdapter.getDatas().size() - 1;
            mAdapter.addMoreDatas(videoList);
            mAdapter.notifyItemRangeChanged(pos, videoList.size());
            skip += videoList.size();
        }
    }

    public void showLoading() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    public void dismissLoading() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);

            }
        }, 1000);
    }

    public void showSnake(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fm_hot_live_radio);
        ButterKnife.bind(this, mContentView);
        mAdapter = new HotRadioRecyclerAdapter(mRecyclerView);
        mAdapter.setOnRVItemClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                Logger.e("onScrollStateChanged");
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                }
                if (newState == RecyclerView.SCROLL_STATE_IDLE && (lastVisibleItem + 1 == mAdapter.getItemCount()) && hasMore) {
                    getData(skip + 1, limit);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                Logger.e("onScrolled");
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();

            }
        });
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, getResources()
                        .getDisplayMetrics()));
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh = true;
                mAdapter.clear();
                skip = 0;
                getData(skip, limit);
//                Logger.e("onRefresh");
//                dismissLoading();
                //加载数据
            }
        });
        getData(skip, limit);
    }


    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void onUserVisible() {

    }

    @OnClick(R.id.fab_add)
    public void onClick() {
        //先查询一下是否有自己在的频道

        mLoadingView.setVisibility(View.VISIBLE);
        if (User.getCurrentUser(User.class) != null) {
            BmobDataEngine.queryMyYcProgram(User.getCurrentUser(User.class).getObjectId(), new HttpResponseHandler<YcProgram>() {

                @Override
                public void onSuccess(YcProgram content) {
                    mLoadingView.setVisibility(View.INVISIBLE);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("program", content);
//                    Intent intent = new Intent(getActivity(), MyNewLiveActivity.class);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
                    ToastUtils.show(getActivity(), "未实现");
                }

                @Override
                public void onFailure(String content) {
                    mLoadingView.setVisibility(View.INVISIBLE);
                    startActivity(new Intent(getActivity(), CreateNewLiveActivity.class));
                }
            });
        } else {
            mLoadingView.setVisibility(View.INVISIBLE);
            startActivity(new Intent(getActivity(), CreateNewLiveActivity.class));
        }
    }

    YcProgram mProgram;

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        mProgram = mAdapter.getItem(position);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("program", mProgram);
////                            bundle.putInt("uid", User.getCurrentUser(User.class).getYc_UID());
////                            bundle.putInt("sid", 10086);
//        Intent intent = new Intent(getActivity(), LivingRoomActivity.class);
//        intent.putExtras(bundle);
//        startActivity(intent);
//        startActivity(new Intent(getActivity(), LivingRoomActivity.class));
        ToastUtils.show(getActivity(), "未实现");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.tv_title_back, R.id.tv_title_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_title_back:
                break;
            case R.id.tv_title_search:
                OpenActivity(ScenarioSearchAty.class);
                break;
        }
    }
}
