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
 * Date: 2016/1/7 11:31
 * Time: 11:31
 * Description:
 */
public class AddCostTypeAdapter extends BGARecyclerViewAdapter<String> {
    public AddCostTypeAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.adapter_item_add_cost_type);
    }

    @Override
    protected void fillData(BGAViewHolderHelper viewHolderHelper, int position, String model) {
        viewHolderHelper.setText(R.id.text1, model);

    }

    /**
     * User: 巫金生(newSalton@outlook.com)
     * Date: 2016/2/22 10:49
     * Time: 10:49
     * Description:
     */
    public static class MyRecyclerViewAdapter extends BGARecyclerViewAdapter<ImageBean> {
        int mImageSize;
        public MyRecyclerViewAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.adapter_item_drawee_images);
            mImageSize = mContext.getResources().getDimensionPixelOffset(R.dimen.folder_cover_size);
        }
    //
    //    @Override
    //    public void setItemChildListener(BGAViewHolderHelper viewHolderHelper) {
    //        viewHolderHelper.setItemChildClickListener(R.id.iv_thumbnail);
    //        viewHolderHelper.setItemChildLongClickListener(R.id.iv_thumbnail);
    //    }

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
            FrescoImageLoader.displayThumbnailImage(iv_thumbnail, model.getPicUrl(),mImageSize,mImageSize);
        }
    }
}
