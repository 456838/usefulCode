package com.salton123.mengmei.view.adapter;

import android.support.v7.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.salton123.mengmei.R;
import com.salton123.mengmei.model.bmob.ImageBean;
import com.salton123.common.util.FrescoImageLoader;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2016/2/22 10:49
 * Time: 10:49
 * Description:
 */
public class MyEmojiAdapter extends BGARecyclerViewAdapter<ImageBean> {

//    private String colorArr[] = new String[]{
//            "#FFCDD2","#f8bbd0","#e1bee7",
//            "#d1c4e9","#c5cae9","#bddefb",
//            "#b3e5fc","#b2ebf2","#b2dfdb",
//            "#c8e6c9","#dcedc8","#f0f4c3",
//            "#ffccbc","#cfd8dc","#d7cccb"
//    };

    private String colorArr[] = new String[]{
            "#D32F2F","#C2185B","#7B1FA2",
            "#512DA8","#303F9F","#1976D2",
            "#0288D1","#0097A7","#00796B",
            "#388E3C","#689F38","#689F38",
            "#FBC02D","#FFA000","#455A64"
    };
    int mImageSize;
    public MyEmojiAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.adapter_item_my_emoji);
        mImageSize = mContext.getResources().getDimensionPixelOffset(R.dimen.folder_cover_size);
    }


    @Override
    protected void setItemChildListener(BGAViewHolderHelper viewHolderHelper) {
        viewHolderHelper.setItemChildClickListener(R.id.tv_del);
        viewHolderHelper.setItemChildClickListener(R.id.iv_thumbnail);
        viewHolderHelper.setItemChildLongClickListener(R.id.iv_thumbnail);
    }


    @Override
    protected void fillData(BGAViewHolderHelper viewHolderHelper, int position, final ImageBean model) {
//        BGASwipeItemLayout bgaSwipeItemLayout = viewHolderHelper.getView(R.id.sil_item_swipe_root);
//        if (position == 1) {
//            bgaSwipeItemLayout.findViewById(R.id.lay_del).setVisibility(View.GONE);
//        }
//        ImageView iv_thumbnail = viewHolderHelper.getView(R.id.iv_thumbnail);
//        ImageLoader.display(iv_thumbnail, model.getPicUrl(), new HttpResponseHandler<String>() {
//            @Override
//            public void onSuccess(String content) {
//                model.setLocalPath(content);
//            }
//
//            @Override
//            public void onFailure(String content) {
//            }
//        });
        SimpleDraweeView iv_thumbnail = viewHolderHelper.getView(R.id.iv_thumbnail);
        FrescoImageLoader.displayThumbnailImage(iv_thumbnail, model.getPicUrl(), mImageSize, mImageSize);
        viewHolderHelper.setText(R.id.tv_add_time,model.getCreatedAt()+"").setText(R.id.tv_share_count, "分享次数:" + model.getLinks().getObjects().size());
//        viewHolderHelper.getView(R.id.lay_background).setBackgroundColor(Color.parseColor(colorArr[position%15]));

    }
}
