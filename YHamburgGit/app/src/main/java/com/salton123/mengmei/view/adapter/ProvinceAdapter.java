package com.salton123.mengmei.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.salton123.base.AdapterBase;
import com.salton123.mengmei.R;
import com.salton123.mengmei.model.Province;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2015-10-27
 * Time: 17:53
 * Description:
 */
public class ProvinceAdapter extends AdapterBase {
    public ProvinceAdapter(Context pContext) {
        super(pContext);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(GetContext()).inflate(
                    R.layout.adapter_item_province_list, null);
            holder.province = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Province _province = (Province) GetList().get(position);
        holder.province.setText(_province.getName());
        return convertView;
    }
    private class ViewHolder {
        private TextView province;
    }
}
