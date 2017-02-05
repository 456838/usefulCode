package com.salton123.hamb.controller.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.salton123.base.BaseFragment;
import com.salton123.common.net.HttpResponseHandler;
import com.salton123.common.widget.AutoClearEditView;
import com.salton123.hamb.model.bean.CriteriaSearchRet;
import com.salton123.hamb.model.bean.PostCriteriaSearch;
import com.salton123.hamb.model.engine.HambEngine;
import com.salton123.hamb.view.adapter.SearchAdapterViewAdapter;
import com.salton123.mengmei.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildLongClickListener;
import cn.bingoogolapple.refreshlayout.BGAMoocStyleRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/2/4 17:36
 * Time: 17:36
 * Description:
 */
public class SearchFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, BGAOnItemChildClickListener, BGAOnItemChildLongClickListener {


    @BindView(R.id.search_input)
    AutoClearEditView searchInput;
    @BindView(R.id.search_back_img)
    LinearLayout searchBackImg;
    @BindView(R.id.search_img)
    LinearLayout searchImg;
    @BindView(R.id.sp_category)
    Spinner spCategory;
    @BindView(R.id.sp_period)
    Spinner spPeriod;
    @BindView(R.id.sp_lenght)
    Spinner spLenght;
    @BindView(R.id.sp_man)
    Spinner spMan;
    @BindView(R.id.sp_woman)
    Spinner spWoman;
    @BindView(R.id.lv_listview_data)
    ListView mDataLv;
    @BindView(R.id.rl_listview_refresh)
    BGARefreshLayout mRefreshLayout;
    public static final int LOADING_DURATION = 2000;
    PostCriteriaSearch mPostCriteriaSearch;
    private SearchAdapterViewAdapter mAdapter;
    int skip = 0;       //翻页跳过的条数

    public static SearchFragment getInstance() {
        return new SearchFragment();
    }

    public Boolean onBackPress() {
        return false;
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fm_search);
        ButterKnife.bind(this, mContentView);
    }

    @Override
    protected void setListener() {
        mRefreshLayout.setDelegate(this);
        // 设置正在加载更多时不显示加载更多控件
//        mRefreshLayout.setIsShowLoadingMoreView(false);

        mDataLv.setOnItemClickListener(this);
        mDataLv.setOnItemLongClickListener(this);
        mDataLv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                Log.i(TAG, "滚动状态变化");
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                Log.i(TAG, "正在滚动");
            }
        });

        mAdapter = new SearchAdapterViewAdapter(getActivity());
        mAdapter.setOnItemChildClickListener(this);
        mAdapter.setOnItemChildLongClickListener(this);
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                if (pos == 0) {
                    return;
                }
                String value = (String) parent.getItemAtPosition(pos);
                List<String> categoryList = new ArrayList<String>();
                categoryList.add("=");
                categoryList.add(value);
                mPostCriteriaSearch.setCategory(categoryList);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        spPeriod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {       //时代背景
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                if (pos == 0) {
                    return;
                }
                String value = (String) parent.getItemAtPosition(pos);
                List<String> categoryList = new ArrayList<String>();
                categoryList.add("=");
                categoryList.add(value);
                mPostCriteriaSearch.setPeroid(categoryList);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        spLenght.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {       //篇幅
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                if (pos == 0) {
                    return;
                }
                String value = (String) parent.getItemAtPosition(pos);
                List<String> categoryList = new ArrayList<String>();
                categoryList.add("=");
                categoryList.add(PostCriteriaSearch.parseLongType(value));
                mPostCriteriaSearch.setLongtype(categoryList);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        spMan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {      //男
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                if (pos == 0) {
                    return;
                } else if (pos == 6) {
                    String value = (String) parent.getItemAtPosition(pos);
                    List<String> categoryList = new ArrayList<String>();
                    categoryList.add(">");
                    categoryList.add(PostCriteriaSearch.parseMaleRoleCount(value));
                    mPostCriteriaSearch.setMaleRoleCount(categoryList);
                } else {
                    String value = (String) parent.getItemAtPosition(pos);
                    List<String> categoryList = new ArrayList<String>();
                    categoryList.add("=");
                    categoryList.add(PostCriteriaSearch.parseMaleRoleCount(value));
                    mPostCriteriaSearch.setMaleRoleCount(categoryList);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        spWoman.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {        //女
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                if (pos == 0) {
                    return;
                } else if (pos == 6) {
                    String value = (String) parent.getItemAtPosition(pos);
                    List<String> categoryList = new ArrayList<String>();
                    categoryList.add(">");
                    categoryList.add(PostCriteriaSearch.parseFemaleRoleCount(value));
                    mPostCriteriaSearch.setFemaleRoleCount(categoryList);
                } else {
                    String value = (String) parent.getItemAtPosition(pos);
                    List<String> categoryList = new ArrayList<String>();
                    categoryList.add("=");
                    categoryList.add(PostCriteriaSearch.parseFemaleRoleCount(value));
                    mPostCriteriaSearch.setFemaleRoleCount(categoryList);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mPostCriteriaSearch = new PostCriteriaSearch();
        BGAMoocStyleRefreshViewHolder moocStyleRefreshViewHolder = new BGAMoocStyleRefreshViewHolder(getActivity(), true);
        moocStyleRefreshViewHolder.setUltimateColor(R.color.custom_imoocstyle);
        moocStyleRefreshViewHolder.setOriginalImage(R.drawable.custom_mooc_icon);
//        moocStyleRefreshViewHolder.setLoadMoreBackgroundColorRes(R.color.custom_imoocstyle);
        moocStyleRefreshViewHolder.setSpringDistanceScale(0.2f);
//        moocStyleRefreshViewHolder.setRefreshViewBackgroundColorRes(R.color.custom_imoocstyle);
        mRefreshLayout.setRefreshViewHolder(moocStyleRefreshViewHolder);
//        mRefreshLayout.setCustomHeaderView(DataEngine.getCustomHeaderView(getActivity()), true);
        mDataLv.setAdapter(mAdapter);
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        skip = 0;
        mPostCriteriaSearch.setSkip(skip);
        mAdapter.clear();
        showLoadingDialog();
        HambEngine.criteriaSearch(mPostCriteriaSearch, new HttpResponseHandler<List<CriteriaSearchRet>>() {
            @Override
            public void onSuccess(final List<CriteriaSearchRet> content) {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.endRefreshing();
                        dismissLoadingDialog();
                        mAdapter.addNewDatas(content);
                    }
                });
            }

            @Override
            public void onFailure(String content) {
                Message msg = Message.obtain();
                msg.what = 104;
                msg.obj = content;
                mHandler.sendMessage(msg);
                dismissLoadingDialog();
                mRefreshLayout.endRefreshing();
            }
        });
    }


    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        skip += 10;
        mPostCriteriaSearch.setSkip(skip);
        showLoadingDialog();
        HambEngine.criteriaSearch(mPostCriteriaSearch, new HttpResponseHandler<List<CriteriaSearchRet>>() {
            @Override
            public void onSuccess(final List<CriteriaSearchRet> content) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.endLoadingMore();
                        mAdapter.addMoreDatas(content);
                        dismissLoadingDialog();
                    }
                });
            }

            @Override
            public void onFailure(String content) {
                Message msg = Message.obtain();
                msg.what = 104;
                msg.obj = content;
                mHandler.sendMessage(msg);
                dismissLoadingDialog();
                mRefreshLayout.endLoadingMore();
            }
        });
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showToast("点击了条目 " + mAdapter.getItem(position).getTitle());
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        showToast("长按了" + mAdapter.getItem(position).getTitle());
        return true;
    }

    @Override
    public void onItemChildClick(ViewGroup parent, View childView, int position) {
        if (childView.getId() == R.id.iv_cover) {
            mAdapter.removeItem(position);
        }
    }

    @Override
    public boolean onItemChildLongClick(ViewGroup parent, View childView, int position) {
        if (childView.getId() == R.id.iv_cover) {
            showToast("长按了删除 " + mAdapter.getItem(position).getTitle());
            return true;
        }
        return false;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }


    @OnClick({R.id.search_back_img, R.id.search_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_back_img:
                break;
            case R.id.search_img:
                String keyword = searchInput.getText().toString().trim();
                if(!TextUtils.isEmpty(keyword)){
                    List<String> categoryList = new ArrayList<String>();
                    categoryList.add("like");
                    categoryList.add(keyword);
                    mPostCriteriaSearch.setKeyword(categoryList);
                }
                skip = 0;
                mPostCriteriaSearch.setSkip(skip);
                mAdapter.clear();
                showLoadingDialog();
                HambEngine.criteriaSearch(mPostCriteriaSearch, new HttpResponseHandler<List<CriteriaSearchRet>>() {
                    @Override
                    public void onSuccess(final List<CriteriaSearchRet> content) {

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mRefreshLayout.endRefreshing();
                                Logger.e("size:" + content.size());
                                mAdapter.addNewDatas(content);
                                dismissLoadingDialog();
                            }
                        });
                    }

                    @Override
                    public void onFailure(String content) {
                        Message msg = Message.obtain();
                        msg.what = 104;
                        msg.obj = content;
                        mHandler.sendMessage(msg);
                        dismissLoadingDialog();
                    }
                });
                break;
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 104:
                    String errorInfo = (String) msg.obj;
                    Toast.makeText(getContext(), "" + errorInfo, Toast.LENGTH_SHORT).show();
                    Logger.e(errorInfo);
                    break;
            }
        }
    };

}
