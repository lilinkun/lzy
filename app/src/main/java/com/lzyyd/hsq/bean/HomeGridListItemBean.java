package com.lzyyd.hsq.bean;

/**
 * Create by liguo on 2020/9/7
 * Describe:
 */
public class HomeGridListItemBean {
    private String img;
    private String name;
    private String url;
    private int type;//0：跳到确实省钱 1：自营  2：积分 3：vip

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
