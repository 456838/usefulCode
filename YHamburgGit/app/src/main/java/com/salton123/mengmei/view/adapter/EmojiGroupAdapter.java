package com.salton123.mengmei.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.salton123.base.AdapterBase;
import com.salton123.base.ViewHolder;
import com.salton123.mengmei.model.bean.User;
import com.salton123.common.widget.ExpandGridView;
import com.salton123.mengmei.R;
import com.salton123.mengmei.model.bean.EmojiGroup;
import com.salton123.common.util.FrescoImageLoader;

import java.util.Arrays;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2016/2/22 10:49
 * Time: 10:49
 * Description:
 */
public class EmojiGroupAdapter extends BGARecyclerViewAdapter<EmojiGroup> {
    int mImageSize;

    public EmojiGroupAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.adapter_item_home_emoji);
        mImageSize = mContext.getResources().getDimensionPixelOffset(R.dimen.emoji_size);
    }
//
//    @Override
//    public void setItemChildListener(BGAViewHolderHelper viewHolderHelper) {
//        viewHolderHelper.setItemChildClickListener(R.id.iv_thumbnail);
//        viewHolderHelper.setItemChildLongClickListener(R.id.iv_thumbnail);
//    }

    @Override
    protected void fillData(BGAViewHolderHelper viewHolderHelper, int position, final EmojiGroup model) {
        ExpandGridView _EmojiRv =viewHolderHelper.getView(R.id.egv_emojis);
        loadData(_EmojiRv,Arrays.asList(model.getPicUrl().split(";")));
        User owner = model.getOwner();
        if(owner!=null){//昵称存在
            if(!TextUtils.isEmpty(owner.getNickname())){
                viewHolderHelper.setText(R.id.tv_user_id,"来自"+owner.getNickname()+"的分享");
            }
            if(!TextUtils.isEmpty(owner.getAvatar())){
                SimpleDraweeView sdv = viewHolderHelper.getView(R.id.sdv_user_header);
                FrescoImageLoader.displayThumbnailImage(sdv,owner.getAvatar());
            }
        }
        viewHolderHelper.setText(R.id.tv_create_time, model.getCreatedAt());
    }

    private void loadData(ExpandGridView emojiRv, List<String> emojiPic) {
        MyGridViewAdapter adapter = new MyGridViewAdapter(mContext);
        emojiRv.setAdapter(adapter);
        adapter.AddAll(emojiPic);
        emojiRv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(mContext,"点击了"+position,Toast.LENGTH_SHORT).show();
                if (mGirdViewItemClickListener != null) {

                    mGirdViewItemClickListener.onItemClick(parent, view, position, id);
                }
            }
        });
    }

    class MyGridViewAdapter extends AdapterBase<String> {

        public MyGridViewAdapter(Context pContext) {
            super(pContext);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView ==null){
                convertView =GetLayoutInflater().inflate(R.layout.adapter_item_emoji_group,null);
            }
            SimpleDraweeView iv_thumbnail = ViewHolder.get(convertView,R.id.sdv_thumbnail);
            FrescoImageLoader.display(iv_thumbnail, getItem(position));
            return convertView;
        }
    }


    public void setGirdViewItemClickListener(GirdViewItemClickListener p_Listener){
        mGirdViewItemClickListener =p_Listener;
    }

    private GirdViewItemClickListener mGirdViewItemClickListener ;
    public interface GirdViewItemClickListener{
        void onItemClick(AdapterView<?> parent, View view, int position, long id);
    }


}
