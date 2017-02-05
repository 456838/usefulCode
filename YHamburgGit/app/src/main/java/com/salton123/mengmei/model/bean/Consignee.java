package com.salton123.mengmei.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/2/13.
 */
public class Consignee extends BmobObject {
    private boolean isdefault ;
    private String receiverName;
    private String phoneNum;
    private String address ;
    private String ownerId;

    @Override
    public String toString() {
        return "Consignee{" +
                "isdefault=" + isdefault +
                ", receiverName='" + receiverName + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", address='" + address + '\'' +
                '}';
    }


    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public boolean isdefault() {
        return isdefault;
    }

    public void setIsdefault(boolean isdefault) {
        this.isdefault = isdefault;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
