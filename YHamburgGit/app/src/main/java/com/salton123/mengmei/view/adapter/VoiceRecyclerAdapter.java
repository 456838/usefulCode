package com.salton123.mengmei.view.adapter;

import android.support.v7.widget.RecyclerView;

import com.salton123.mengmei.R;
import com.salton123.mengmei.model.bmob.VoiceEntity;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2016/9/5 22:57
 * Time: 22:57
 * Description:
 */
public class VoiceRecyclerAdapter extends BGARecyclerViewAdapter<VoiceEntity> {
    public VoiceRecyclerAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.adapter_item_voice);
    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper viewHolderHelper) {
        viewHolderHelper.setItemChildClickListener(R.id.iv_item_play_voice);
        super.setItemChildListener(viewHolderHelper);
    }

    @Override
    protected void fillData(BGAViewHolderHelper viewHolderHelper, int position, VoiceEntity model) {
        viewHolderHelper.setText(R.id.tv_describe, model.getDescribe());

    }
}
