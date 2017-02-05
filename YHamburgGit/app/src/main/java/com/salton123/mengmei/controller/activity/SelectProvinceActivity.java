package com.salton123.mengmei.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.salton123.base.ActivityFrameIOS;
import com.salton123.mengmei.R;
import com.salton123.mengmei.model.Province;
import com.salton123.mengmei.view.adapter.ProvinceAdapter;

import java.util.ArrayList;


/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2015-10-27
 * Time: 17:50
 * Description:
 */
public class SelectProvinceActivity extends ActivityFrameIOS {

    ArrayList<Province> provinceList;
    ListView city_list;
    ProvinceAdapter mProvinceAdapter;
    String[] provinceArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppendMainBody(R.layout.activity_province);
        SetTopBackHint(getString(R.string.label_edit_info));
        SetTopTitle(getString(R.string.label_select_area));
        String pcode = getIntent().getStringExtra("pcode");
        provinceArr = getResources().getStringArray(R.array.province_array);
        provinceList = new ArrayList<>();
        for (int i = 0; i < provinceArr.length; i++) {
            provinceList.add(new Province(i, provinceArr[i]));
        }
        // provinceList = ProvinceHelper.getInstance(this).queryProvince();
        city_list = (ListView) findViewById(R.id.province_list);
        mProvinceAdapter = new ProvinceAdapter(SelectProvinceActivity.this);
        mProvinceAdapter.AddAll(provinceList);
        city_list.setAdapter(mProvinceAdapter);
        city_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
//                String cityId = provinceList.get(position).getId();
//                Intent intent = new Intent(ProvinceActivity.this, DistrictActivity.class);
//                intent.putExtra("cityCode", cityId);
//                startActivity(intent);
                //EventBus.getDefault().post(new EventMsg(100, provinceList.get(position).getName()));
                Intent _Intent = new Intent();
                _Intent.putExtra("province", provinceList.get(position).getName());
                setResult(RESULT_OK, _Intent);
                SelectProvinceActivity.this.finish();
            }
        });
    }

    @Override
    public void InitView() {

    }

    @Override
    public void InitListener() {

    }

    @Override
    public void InitData() {

    }
}
