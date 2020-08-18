package com.lzyyd.hsq.bean;

/**
 * Create by liguo on 2020/8/17
 * Describe:
 */
public class CartListBean<T> {
    private String StoreId;
    private String StoreName;
    private String StoreLogo;
    private String CreateTime;
    private T listGoods;

    public String getStoreId() {
        return StoreId;
    }

    public void setStoreId(String storeId) {
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

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public T getListGoods() {
        return listGoods;
    }

    public void setListGoods(T listGoods) {
        this.listGoods = listGoods;
    }
}
