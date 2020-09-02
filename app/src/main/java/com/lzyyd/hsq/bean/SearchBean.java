package com.lzyyd.hsq.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Create by liguo on 2020/9/2
 * Describe:
 */
@Entity
public class SearchBean {
    private String username;
    private String searchname;

    @Generated(hash = 153234401)
    public SearchBean(String username, String searchname) {
        this.username = username;
        this.searchname = searchname;
    }

    public SearchBean() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSearchname() {
        return searchname;
    }

    public void setSearchname(String searchname) {
        this.searchname = searchname;
    }
}
