package com.lzyyd.hsq.bean;

/**
 * Create by liguo on 2020/7/21
 * Describe:
 */
public class GoodsDetailBean<T> {
    private GoodsBean goodsItem;
    private T goodsAttrItem;

    public GoodsBean getGoodsItem() {
        return goodsItem;
    }

    public void setGoodsItem(GoodsBean goodsItem) {
        this.goodsItem = goodsItem;
    }

    public T getGoodsAttrItem() {
        return goodsAttrItem;
    }

    public void setGoodsAttrItem(T goodsAttrItem) {
        this.goodsAttrItem = goodsAttrItem;
    }
}
