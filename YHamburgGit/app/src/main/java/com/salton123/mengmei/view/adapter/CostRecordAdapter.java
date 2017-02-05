package com.salton123.mengmei.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.salton123.mengmei.R;
import com.salton123.mengmei.model.bean.Cost;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2016/1/6 17:47
 * Time: 17:47
 * Description:
 */
public class CostRecordAdapter extends BGARecyclerViewAdapter<Cost> {
    public CostRecordAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.adapter_item_cost);
    }

    @Override
    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, Cost model) {
        viewHolderHelper
                .setText(R.id.tv_time, model.getTime())
                .setText(R.id.tv_type, model.getType())
                .setText(R.id.tv_money, model.getMoney() + "")
                .setText(R.id.tv_memo, model.getMemo())
                .setText(R.id.tv_thing, model.getThing());
        ImageView iv = viewHolderHelper.getView(R.id.iv_thumbnail);
        LinearLayout ll = viewHolderHelper.getView(R.id.lay_parent);
        setTypeIcon(model.getType(), iv);
    }

    private void setTypeIcon(String type, ImageView iv_thumbnail) {
        //  健康美食, 衣服饰品, 居家物业, 行车交通, 金融银行, 学习教育, 休闲娱乐, 交流通讯, 医疗保健, 人情往来, 母婴用品
        if (Cost.Type.健康美食.name().equals(type)) {
            iv_thumbnail.setImageResource(R.drawable.jiankangmeishi);
        }
        if (Cost.Type.衣服饰品.name().equals(type)) {
            iv_thumbnail.setImageResource(R.drawable.yifushipin);
        }
        if (Cost.Type.居家物业.name().equals(type)) {
            iv_thumbnail.setImageResource(R.drawable.jiajuwuye);
        }
        if (Cost.Type.行车交通.name().equals(type)) {
            iv_thumbnail.setImageResource(R.drawable.xingchejiaotong);
        }
        if (Cost.Type.金融银行.name().equals(type)) {
            iv_thumbnail.setImageResource(R.drawable.jinrongyinhang);
        }
        if (Cost.Type.学习教育.name().equals(type)) {
            iv_thumbnail.setImageResource(R.drawable.xuexijiaoyu);
        }
        if (Cost.Type.休闲娱乐.name().equals(type)) {
            iv_thumbnail.setImageResource(R.drawable.xiuxianyule);
        }
        if (Cost.Type.交流通讯.name().equals(type)) {
            iv_thumbnail.setImageResource(R.drawable.jiaoliutongxun);
        }
        if (Cost.Type.医疗保健.name().equals(type)) {
            iv_thumbnail.setImageResource(R.drawable.yiliaobaojian);
        }
        if (Cost.Type.人情往来.name().equals(type)) {
            iv_thumbnail.setImageResource(R.drawable.renqingwanglai);
        }
        if (Cost.Type.母婴用品.name().equals(type)) {
            iv_thumbnail.setImageResource(R.drawable.muyinyongpin);
        }
    }

}