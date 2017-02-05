package com.salton123.mengmei.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.salton123.base.AdapterBase;
import com.salton123.base.ViewHolder;
import com.salton123.mengmei.R;


/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2016/2/27 17:00
 * Time: 17:00
 * Description:
 */
public class TestAdapter extends AdapterBase<String> {
    public TestAdapter(Context pContext) {
        super(pContext);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = GetLayoutInflater().inflate(R.layout.adapter_test_item, null);
        }
        TextView tv = ViewHolder.get(convertView, R.id.tv_title);
        tv.setText(GetList().get(position));
        return convertView;
    }

}
