package com.salton123.mengmei.controller.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.salton123.base.BaseFragment;
import com.salton123.common.widget.PeriscopeLayout;
import com.salton123.mengmei.R;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2016/8/5 13:06
 * Time: 13:06
 * Description:
 */
public class BusinessFragment extends BaseFragment {

    @BindView(R.id.periscope)
    PeriscopeLayout mPeriscope;
    //心形气泡
    private Handler mHanlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    mPeriscope.addHeart();
                    break;
                case 2:
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fm_business);
        ButterKnife.bind(this, mContentView);
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

    public void addHeart() {
        Observable.interval(0, 100, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Long aLong) {
                        mPeriscope.addHeart();
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
