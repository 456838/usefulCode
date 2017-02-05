package com.salton123.mengmei.model.bean;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2015-10-27
 * Time: 16:38
 * Description:
 */
public class Province {

    /**
     * （省份/城市/地区）名字
     */
    private String name;
    /**
     * （省份/城市/地区）id
     */
    private int id;

    public Province() {
    }

    public Province(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
