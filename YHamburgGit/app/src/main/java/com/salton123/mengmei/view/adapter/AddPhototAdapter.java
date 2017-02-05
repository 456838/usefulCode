package com.salton123.mengmei.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.salton123.common.util.ImageLoaderUtils;
import com.salton123.mengmei.R;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2016/1/7 11:31
 * Time: 11:31
 * Description:
 */
public class AddPhototAdapter extends BGARecyclerViewAdapter<String> {
    public AddPhototAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.adapter_item_add_cost_type);
    }

    @Override
    protected void fillData(BGAViewHolderHelper viewHolderHelper, int position, String model) {
        ImageView iv_add = viewHolderHelper.getView(R.id.text1);
        ImageLoaderUtils.display(iv_add, model);
    }
}
