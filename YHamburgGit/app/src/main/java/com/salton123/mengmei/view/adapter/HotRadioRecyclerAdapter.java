package com.salton123.mengmei.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.salton123.mengmei.model.bean.YcProgram;
import com.salton123.mengmei.R;

import org.xutils.x;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2016/9/5 22:57
 * Time: 22:57
 * Description:
 */
public class HotRadioRecyclerAdapter extends BGARecyclerViewAdapter<YcProgram> {
    public HotRadioRecyclerAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.adapter_item_hot_radio);
    }

//    @Override
//    protected void setItemChildListener(BGAViewHolderHelper viewHolderHelper) {
//        viewHolderHelper.setItemChildClickListener(R.id.iv_item_play_voice);
//        super.setItemChildListener(viewHolderHelper);
//    }

    @Override
    protected void fillData(BGAViewHolderHelper viewHolderHelper, int position, YcProgram model) {
        ImageView imageView = viewHolderHelper.getView(R.id.sdv_thumbnail);
        x.image().bind(imageView,model.getThumbnailUrl());
        int total = (int) model.getTotalPeople();
        viewHolderHelper
                .setText(R.id.tv_title_info, model.getTitleInfo())
                .setText(R.id.tv_program_owner, model.getOwnerName())
                .setText(R.id.tv_reply_count, "当前人数:" + total);
    }

}
