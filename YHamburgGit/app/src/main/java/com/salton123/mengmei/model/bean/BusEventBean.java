package com.salton123.mengmei.model.bean;

import java.io.Serializable;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2016/3/3 23:25
 * Time: 23:25
 * Description:
 */
public class BusEventBean  implements Serializable {
    private String type ;   //类型
    private String content ;    //内容


    public BusEventBean(String type, String content) {
        this.type = type;
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "BusEventBean{" +
                "type='" + type + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
