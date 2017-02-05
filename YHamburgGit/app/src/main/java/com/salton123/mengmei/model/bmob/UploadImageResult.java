package com.salton123.mengmei.model.bmob;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2016/10/22 16:57
 * Time: 16:57
 * Description:
 */
public class UploadImageResult {


    /**
     * filename : myPicture.jpg
     * url : http://bmob-cdn-24.b0.upaiyun.com/2016/04/14/9306f2e74090d668801eac8814b3f56f.jpg
     * cdn : upyun
     */

    private String filename;
    private String url;
    private String cdn;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCdn() {
        return cdn;
    }

    public void setCdn(String cdn) {
        this.cdn = cdn;
    }
}
