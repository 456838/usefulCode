package com.salton123.mengmei.model.bmob;

import com.salton123.mengmei.model.bean.User;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2016/2/22 10:56
 * Time: 10:56
 * Description:
 */
public class ImageBean extends BmobObject {
    private String picUrl;
    private String localPath;
    private String provider;        //图片的提供者
    private String suffix;      //图片后缀
    private BmobRelation links;     //喜欢这张图片的人有谁
    private int shareCount;         //分享次数
    private User owner;
    private String keyWord;        //关键字

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public BmobRelation getLinks() {
        return links;
    }

    public void setLinks(BmobRelation links) {
        this.links = links;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }


    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
