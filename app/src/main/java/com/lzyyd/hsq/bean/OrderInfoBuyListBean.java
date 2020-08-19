package com.lzyyd.hsq.bean;

import android.graphics.Typeface;

import com.lzyyd.hsq.base.ProApplication;

import java.util.ArrayList;

/**
 * Create by liguo on 2020/8/19
 * Describe:
 */
public class OrderInfoBuyListBean {
    private int Integral;
    private int ReturnIntegral;
    private String Discount;
    private double GoodsAmount;
    private double OrderAmount;
    private double ShippingFree;
    private int StoreId;
    private String StoreName;
    private String StoreLogo;
    private String Weight;
    private ArrayList<OrderGoodsBuyListBean> OrderGoodsBuyList;

    public int getIntegral() {
        return Integral;
    }

    public void setIntegral(int integral) {
        Integral = integral;
    }

    public int getReturnIntegral() {
        return ReturnIntegral;
    }

    public void setReturnIntegral(int returnIntegral) {
        ReturnIntegral = returnIntegral;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public double getGoodsAmount() {
        return GoodsAmount;
    }

    public void setGoodsAmount(double goodsAmount) {
        GoodsAmount = goodsAmount;
    }

    public double getOrderAmount() {
        return OrderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        OrderAmount = orderAmount;
    }

    public double getShippingFree() {
        return ShippingFree;
    }

    public void setShippingFree(double shippingFree) {
        ShippingFree = shippingFree;
    }

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

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public ArrayList<OrderGoodsBuyListBean> getOrderGoodsBuyList() {
        return OrderGoodsBuyList;
    }

    public void setOrderGoodsBuyList(ArrayList<OrderGoodsBuyListBean> orderGoodsBuyList) {
        OrderGoodsBuyList = orderGoodsBuyList;
    }
}
