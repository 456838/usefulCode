package com.salton123.mengmei.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2016/1/6 17:24
 * Time: 17:24
 * Description:
 */
public class Cost extends BmobObject {
    private String type;  //类型
    private double money;   //消费金额
    private String time;    //时间，保存到哪一天
    private String owner;   //消费产生者
    private String thing;      //消费内容
    private String memo;   //备注

    public enum Type {
        健康美食, 衣服饰品, 居家物业, 行车交通, 金融银行, 学习教育, 休闲娱乐, 交流通讯, 医疗保健, 人情往来, 母婴用品
    }


    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getThing() {
        return thing;
    }

    public void setThing(String thing) {
        this.thing = thing;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
