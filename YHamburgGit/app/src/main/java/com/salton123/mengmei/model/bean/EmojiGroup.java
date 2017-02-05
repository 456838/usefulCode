package com.salton123.mengmei.model.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2016/3/23 9:37
 * Time: 9:37
 * Description:
 */
public class EmojiGroup extends BmobObject {
    private User owner ;
    private String picUrl;
//    private String localPath;
    private BmobRelation links;     //喜欢这张图片的人有谁
    private String keyWord ;        //关键字

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }


    public BmobRelation getLinks() {
        return links;
    }

    public void setLinks(BmobRelation links) {
        this.links = links;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    @Override
    public String toString() {
        return "EmojiGroup{" +
                "owner=" + owner +
                ", picUrl='" + picUrl + '\'' +
                ", links=" + links +
                ", keyWord='" + keyWord + '\'' +
                '}';
    }
}