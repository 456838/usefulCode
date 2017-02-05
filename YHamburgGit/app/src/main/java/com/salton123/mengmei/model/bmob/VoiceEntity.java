package com.salton123.mengmei.model.bmob;

import com.salton123.mengmei.model.bean.User;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2016/9/5 22:45
 * Time: 22:45
 * Description:
 */
public class VoiceEntity extends BmobObject {

    private int id;
    private int versionCode;
    private User userEntity;
    private String userId;
    private String website;
    private String title;
    private String describe;
    private String from;
    private String image_cover;
    private String voiceUrl ;
    private long createTime;
    private long updateTime;
    private List<String> tags;
    private List<String> favoriteUserIds;
    private List<String> imageList;

    public List<String> getFavoriteUserIds() {
        return favoriteUserIds;
    }

    public void setFavoriteUserIds(List<String> favoriteUserIds) {
        this.favoriteUserIds = favoriteUserIds;
    }

    public String getVoiceUrl() {
        return voiceUrl;
    }

    public void setVoiceUrl(String voiceUrl) {
        this.voiceUrl = voiceUrl;
    }

    public String getImage_cover() {
        return image_cover;
    }

    public void setImage_cover(String image_cover) {
        this.image_cover = image_cover;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public User getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(User userEntity) {
        this.userEntity = userEntity;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

}