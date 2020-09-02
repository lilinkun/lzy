package com.lzyyd.hsq.bean;

import java.io.Serializable;

/**
 * Create by liguo on 2020/8/15
 * Describe:
 */
public class StoreCartBean<T> implements Serializable {

    private int StoreId;
    private String StoreName;
    private String StoreLogo;
    private T listGoods;

    public int getStoreId() {
        return StoreId;
    }

    public void setStoreId(int storeId) {
        StoreId = storeId;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public String getStoreLogo() {
        return StoreLogo;
    }

    public void setStoreLogo(String storeLogo) {
        StoreLogo = storeLogo;
    }

    public T getListGoods() {
        return listGoods;
    }

    public void setListGoods(T listGoods) {
        this.listGoods = listGoods;
    }
}
