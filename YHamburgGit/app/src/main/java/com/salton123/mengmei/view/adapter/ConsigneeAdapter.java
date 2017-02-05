package com.salton123.mengmei.view.adapter;

import android.support.v7.widget.RecyclerView;

import com.salton123.mengmei.R;
import com.salton123.mengmei.model.bean.Consignee;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by Administrator on 2016/2/13.
 */
public class ConsigneeAdapter extends BGARecyclerViewAdapter<Consignee> {
    public ConsigneeAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.adapter_item_consignee);
    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper viewHolderHelper) {
        viewHolderHelper.setItemChildClickListener(R.id.lay_base);
    }

    @Override
    protected void fillData(BGAViewHolderHelper bgaViewHolderHelper, int i, Consignee qiangGouProductItem) {
        Consignee _item = getDatas().get(i);
        //   ImageLoaderUtils.display(iv_thumbnail, _item.getImage_url().split(";")[0]);
        //    BootstrapProgressBar _ProgressBar = bgaViewHolderHelper.getView(R.id.pb_bought);
//        _ProgressBar.setProgress(_item.getTotalPeople());
        //   int current =((_item.getTotalPeople()*100 - _item.getJoinPeople()*100)/_item.getTotalPeople());
        //   _ProgressBar.setProgress(current);
        bgaViewHolderHelper
                .setText(R.id.tv_name, _item.getReceiverName())
                .setText(R.id.tv_phoneNum, _item.getPhoneNum() + "")
                .setText(R.id.tv_address, _item.getAddress() + "")
        ;
        if(!_item.isdefault()){
            bgaViewHolderHelper
                    .setText(R.id.tv_default,"");
        }
    }
}
