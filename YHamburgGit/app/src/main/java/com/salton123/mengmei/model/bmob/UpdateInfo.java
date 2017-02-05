package com.salton123.mengmei.model.bmob;

import cn.bmob.v3.BmobObject;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2016/8/22 21:51
 * Time: 21:51
 * Description:
 */
public class UpdateInfo extends BmobObject {
    private int versionCode;
    private String downloadUrl;
    private int updateType;      //1:完全更新 2:patch更新 3.bug更新

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    private String updateContent ;

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public int getUpdateType() {
        return updateType;
    }

    public void setUpdateType(int updateType) {
        this.updateType = updateType;
    }

    @Override
    public String toString() {
        return "UpdateInfo{" +
                "updateType=" + updateType +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", versionCode=" + versionCode +
                '}';
    }
}
