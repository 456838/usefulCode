package com.salton123.hamb.model.bean;

import java.util.List;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/2/4 23:36
 * Time: 23:36
 * Description:实用建造者模式构建数据
 */
public class PostCriteriaSearch {


    private List<String> maleRoleCount;
    private List<String> femaleRoleCount;
    private List<String> author;
    private List<String> title;
    private List<String> category;
    private List<String> peroid;
    private List<String> longtype;
    private List<String> plot;

    /**
     * action : count
     * limit : 10
     * skip : 10
     */

    private String action;
    private int limit;
    private int skip;
    private List<String> keyword;


    public static String parseMaleRoleCount(String tag) {
        if (tag.equals("0男")) {
            return "0";
        } else if (tag.equals("1男")) {
            return "1";
        } else if (tag.equals("2男")) {
            return "2";
        } else if (tag.equals("3男")) {
            return "3";
        } else if (tag.equals("4男")) {
            return "4";
        } else if (tag.equals("5男+")) {
            return "5";
        } else {
            return "0";
        }
    }

    public static String parseFemaleRoleCount(String tag) {
        if (tag.equals("0女")) {
            return "0";
        } else if (tag.equals("1女")) {
            return "1";
        } else if (tag.equals("2女")) {
            return "2";
        } else if (tag.equals("3女")) {
            return "3";
        } else if (tag.equals("4女")) {
            return "4";
        } else if (tag.equals("5女+")) {
            return "5";
        } else {
            return "0";
        }
    }

    public List<String> getMaleRoleCount() {
        return maleRoleCount;
    }

    public void setMaleRoleCount(List<String> maleRoleCount) {
        if (maleRoleCount.size() != 2 || maleRoleCount.get(1).equals("男")) {        //符合要求的数据
            this.maleRoleCount = null;
        } else {
            this.maleRoleCount = maleRoleCount;
        }
    }

    public List<String> getFemaleRoleCount() {
        return femaleRoleCount;
    }

    public void setFemaleRoleCount(List<String> femaleRoleCount) {
        if (femaleRoleCount.size() != 2 || femaleRoleCount.get(1).equals("女")) {        //符合要求的数据
            this.femaleRoleCount = null;
        } else {
            this.femaleRoleCount = femaleRoleCount;
        }
    }

    public List<String> getAuthor() {
        return author;
    }

    public void setAuthor(List<String> author) {
        this.author = author;

    }

    public List<String> getTitle() {
        return title;
    }

    public void setTitle(List<String> title) {
        this.title = title;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        if (category.size() != 2 || category.get(1).equals("剧本类型")) {        //符合要求的数据
            this.category = null;
        } else {
            this.category = category;
        }

    }

    public List<String> getPeroid() {
        return peroid;
    }

    public void setPeroid(List<String> peroid) {
        if (peroid.size() != 2 || peroid.get(1).equals("时代背景")) {        //符合要求的数据
            this.peroid = null;
        } else {
            this.peroid = peroid;
        }
    }

    public List<String> getLongtype() {
        return longtype;
    }

    public static String parseLongType(String tag) {
        if (tag.equals("短篇")) {
            return "1";
        } else if (tag.equals("中篇")) {
            return "2";
        } else {
            return "3";
        }
    }

    public void setLongtype(List<String> longtype) {

        if (longtype.size() != 2 || longtype.get(1).equals("篇幅")) {        //符合要求的数据

            this.longtype = null;
        } else {
            this.longtype = longtype;
        }
    }

    public List<String> getPlot() {
        return plot;
    }

    public void setPlot(List<String> plot) {
        this.plot = plot;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public List<String> getKeyword() {
        return keyword;
    }

    public void setKeyword(List<String> keyword) {
        this.keyword = keyword;
    }
}
