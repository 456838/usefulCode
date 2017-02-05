package com.salton123.mengmei.model.bean;

import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2016/4/22 18:43
 * Time: 18:43
 * Description:
 */
public class User extends BmobUser {

    private int age ;   //年龄
    private String consigneeId ;
    //    private String headPic ;
    private int type ;  //0普通用户，1艺术家，默认0
    private String realName ;
    private String artistId;    //如果是艺术家的话，则拥有艺术家id
    private String birthday ;       //生日
    private String phoneNum;    //电话号码
    private String hobby;       //爱好


    private String nickname;    //昵称
    private String avatar;  //头像
    private int yc_UID;
    private String sex ;
    private String signature;

    private boolean bLiveAnimator;  // 渐隐动画


    private int id;
    private String province;
    private String city;
    private String district;
    private String addrStr;
    private double latitude;
    private double longitude;
    private List<String> favoriteList;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddrStr() {
        return addrStr;
    }

    public void setAddrStr(String addrStr) {
        this.addrStr = addrStr;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<String> getFavoriteList() {
        return favoriteList;
    }

    public void setFavoriteList(List<String> favoriteList) {
        this.favoriteList = favoriteList;
    }

    public boolean isbLiveAnimator() {
        return bLiveAnimator;
    }

    public void setbLiveAnimator(boolean bLiveAnimator) {
        this.bLiveAnimator = bLiveAnimator;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getYc_UID() {
        return yc_UID;
    }

    public void setYc_UID(int yc_UID) {
        this.yc_UID = yc_UID;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    /**
     * //显示数据拼音的首字母
     */
    private String sortLetters;
    public String getSortLetters() {
        return sortLetters;
    }
    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public String getConsigneeId() {
        return consigneeId;
    }

    public void setConsigneeId(String consigneeId) {
        this.consigneeId = consigneeId;
    }


}
