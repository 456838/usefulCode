package com.salton123.mengmei.controller.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.salton123.mengmei.R;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2016/8/26 19:19
 * Time: 19:19
 * Description:
 */
public class TestFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fm_test,null);
    }
}
