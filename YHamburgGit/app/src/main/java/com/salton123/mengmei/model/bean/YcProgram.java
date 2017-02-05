package com.salton123.mengmei.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2016/4/22 23:06
 * Time: 23:06
 * Description:尝试使用了一下builder
 */
public class YcProgram extends BmobObject {
    private String thumbnailUrl;
    private String titleInfo;
    private double totalPeople; //目前人数
    private String ownerObjectId;      //节目作者id
    private String ownerName;      //作者名称
    private int status;    //节目状态 1:正常 2：被封
    private int yc_SID;    //频道id

    public int getYc_SID() {
        return yc_SID;
    }

    public YcProgram setYc_SID(int yc_SID) {
        this.yc_SID = yc_SID;
        return YcProgram.this;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public YcProgram setOwnerName(String ownerName) {
        this.ownerName = ownerName;
        return YcProgram.this;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public YcProgram setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
        return YcProgram.this;
    }

    public String getTitleInfo() {
        return titleInfo;
    }

    public YcProgram setTitleInfo(String titleInfo) {
        this.titleInfo = titleInfo;
        return YcProgram.this;
    }

    public double getTotalPeople() {
        return totalPeople;
    }

    public YcProgram setTotalPeople(double totalPeople) {
        this.totalPeople = totalPeople;
        return YcProgram.this;
    }

    public String getOwnerObjectId() {
        return ownerObjectId;
    }

    public YcProgram setOwnerObjectId(String ownerObjectId) {
        this.ownerObjectId = ownerObjectId;
        return YcProgram.this;
    }

    public int getStatus() {
        return status;
    }

    public YcProgram setStatus(int status) {
        this.status = status;
        return YcProgram.this;
    }
}
