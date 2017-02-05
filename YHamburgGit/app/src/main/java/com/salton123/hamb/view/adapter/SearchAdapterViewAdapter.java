package com.salton123.hamb.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.salton123.common.util.ImageLoaderUtils;
import com.salton123.hamb.model.bean.CriteriaSearchRet;
import com.salton123.mengmei.R;

import cn.bingoogolapple.androidcommon.adapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/2/5 15:14
 * Time: 15:14
 * Description:
 */
public class SearchAdapterViewAdapter extends BGAAdapterViewAdapter<CriteriaSearchRet> {

    public SearchAdapterViewAdapter(Context context) {
        super(context, R.layout.item_search);
    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper viewHolderHelper) {
        viewHolderHelper.setItemChildClickListener(R.id.iv_cover);
    }

    @Override
    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, CriteriaSearchRet model) {
        try {
            if(model!=null){
                viewHolderHelper.setText(R.id.tv_author, "编剧："+model.getAuthor()+"")
                        .setText(R.id.tv_male, model.getMaleRoleCount()+"")
                        .setText(R.id.tv_female, model.getFemaleRoleCount()+"")
                        .setText(R.id.tv_title, model.getTitle()+"")
                        .setText(R.id.tv_category,"类型："+ model.getCategory()+"")
                        .setText(R.id.tv_wordcount,"字数："+ model.getLength()+"")
                        .setText(R.id.tv_digest,"描述："+ model.getDigest()+"");
                ImageView iv_cover = viewHolderHelper.getView(R.id.iv_cover);
                ImageLoaderUtils.display(iv_cover, model.getCover()+"");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}