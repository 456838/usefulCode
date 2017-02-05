package com.salton123.mengmei.controller.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.salton123.base.ActivityFrameIOS;
import com.salton123.mengmei.model.bean.User;
import com.salton123.common.net.HttpResponseHandler;
import com.salton123.mengmei.R;
import com.salton123.mengmei.model.engine.BmobDataEngine;
import com.salton123.mengmei.model.bean.Consignee;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2016/2/13 14:12
 * Time: 14:12
 * Description:
 */
public class EditConsigneeAty extends ActivityFrameIOS {

    private EditText et_name, et_phoneNum, et_address;
    private CheckBox cb_default;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppendMainBody(R.layout.aty_edit_consignee);

    }

    @Override
    public void InitView() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_phoneNum = (EditText) findViewById(R.id.et_phoneNum);
        et_address = (EditText) findViewById(R.id.et_address);
        cb_default = (CheckBox) findViewById(R.id.cb_default);
        SetTopAdditionImageListener(R.drawable.ic_delete, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (_Consignee != null) {
                    _Consignee.delete(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e ==null){
                                setResult(RESULT_OK);
                                EditConsigneeAty.this.finish();
                            }else {
                                ShowToast("删除失败：" + e.getMessage());
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void InitListener() {
        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (_Consignee != null) {
                    _Consignee.setAddress(et_address.getText().toString().trim());
                    _Consignee.setPhoneNum(et_phoneNum.getText().toString().trim());
                    _Consignee.setReceiverName(et_name.getText().toString().trim());
                    _Consignee.setIsdefault(cb_default.isChecked());
                    //将其他是默认的设置为非默认
                    BmobDataEngine.SetConsigneeListNotDefault(User.getCurrentUser().getObjectId(), new HttpResponseHandler<String>() {
                        @Override
                        public void onSuccess(String content) {
                            _Consignee.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e ==null){
                                        ShowToast("修改成功");
                                        setResult(RESULT_OK);
                                        EditConsigneeAty.this.finish();
                                    }else{
                                        ShowToast("修改失败:" + e.getMessage());
                                    }
                                }
                            });
                        }

                        @Override
                        public void onFailure(String content) {

                        }
                    });

                } else {
                    Consignee consignee = new Consignee();
                    consignee.setAddress(et_address.getText().toString().trim());
                    consignee.setPhoneNum(et_phoneNum.getText().toString().trim());
                    consignee.setReceiverName(et_name.getText().toString().trim());
                    consignee.setIsdefault(cb_default.isChecked());
                    consignee.setOwnerId(User.getCurrentUser().getObjectId());
                    BmobDataEngine.SetConsigneeListNotDefault(User.getCurrentUser().getObjectId(), new HttpResponseHandler<String>() {
                        @Override
                        public void onSuccess(String content) {
//                            ShowToast("修改成功");
                        }

                        @Override
                        public void onFailure(String content) {
//                            ShowToast("修改失败:" + content);
                        }
                    });
                    consignee.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e ==null){
                                ShowToast("保存成功");
                                setResult(RESULT_OK);
                                EditConsigneeAty.this.finish();
                            }else {
                                ShowToast("保存失败：" + e.getMessage());
                            }
                        }
                    });
                }
            }
        });
    }



    Consignee _Consignee;

    @Override
    public void InitData() {
        try {
            _Consignee = (Consignee) getIntent().getExtras().get("consignee");
            if (_Consignee != null) {
                et_name.setText(_Consignee.getReceiverName());
                et_phoneNum.setText(_Consignee.getPhoneNum());
                et_address.setText(_Consignee.getAddress());
                HideTopAdditionalImage();
            }
        } catch (Exception e) {
        }

    }
}
