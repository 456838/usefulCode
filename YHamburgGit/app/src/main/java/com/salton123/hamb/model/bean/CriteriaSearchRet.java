package com.salton123.hamb.model.bean;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/2/4 23:54
 * Time: 23:54
 * Description:
 */
public class CriteriaSearchRet {

    /**
     * id : 27615
     * title : 80经典电视剧《声音盛宴》
     * category : 剧情歌
     * author : 深山老怪
     * cover : http://yycloudvod1285246627.bs2dl.yy.com/dmViMzczOTk3NzRmODY5NTFkYzhiYTcwYzYzZWJmNjljMTU5NTYxNDU4OQ
     * digest : 该剧本根据80/90年代的经典电视剧合集创作而成，希望能给大家带来童年的回忆。
     * createdat : 2017-02-04 21:04:43
     * updatedat : 2017-02-04 22:01:46
     * length : 3223
     * maleRoleCount : 24
     * femaleRoleCount : 11
     */

    private String id;
    private String title;
    private String category;
    private String author;
    private String cover;
    private String digest;
    private String createdat;
    private String updatedat;
    private int length;
    private int maleRoleCount;
    private int femaleRoleCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getCreatedat() {
        return createdat;
    }

    public void setCreatedat(String createdat) {
        this.createdat = createdat;
    }

    public String getUpdatedat() {
        return updatedat;
    }

    public void setUpdatedat(String updatedat) {
        this.updatedat = updatedat;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getMaleRoleCount() {
        return maleRoleCount;
    }

    public void setMaleRoleCount(int maleRoleCount) {
        this.maleRoleCount = maleRoleCount;
    }

    public int getFemaleRoleCount() {
        return femaleRoleCount;
    }

    public void setFemaleRoleCount(int femaleRoleCount) {
        this.femaleRoleCount = femaleRoleCount;
    }
}
