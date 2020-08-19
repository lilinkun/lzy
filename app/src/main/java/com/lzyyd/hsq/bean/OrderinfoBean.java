package com.lzyyd.hsq.bean;

import java.util.ArrayList;

/**
 * Create by liguo on 2020/8/19
 * Describe:
 */
public class OrderinfoBean {
    private double Money1Balance; //现金流水
    private double Money2Balance; //电子币
    private double Money3Balance; //积分
    private double Money4Balance; //预到帐余额
    private double Money5Balance; //余额
    private double Money6Balance; //优惠券
    private int Integral;
    private double GoodsAmount;
    private int ReturnIntegral;
    private double Discount;
    private double OrderAmount;
    private double ShippingFree;
    private AddressBean Address;
    private ArrayList<OrderInfoBuyListBean> OrderInfoBuyList;

    public double getMoney1Balance() {
        return Money1Balance;
    }

    public void setMoney1Balance(double money1Balance) {
        Money1Balance = money1Balance;
    }

    public double getMoney2Balance() {
        return Money2Balance;
    }

    public void setMoney2Balance(double money2Balance) {
        Money2Balance = money2Balance;
    }

    public double getMoney3Balance() {
        return Money3Balance;
    }

    public void setMoney3Balance(double money3Balance) {
        Money3Balance = money3Balance;
    }

    public double getMoney4Balance() {
        return Money4Balance;
    }

    public void setMoney4Balance(double money4Balance) {
        Money4Balance = money4Balance;
    }

    public double getMoney5Balance() {
        return Money5Balance;
    }

    public void setMoney5Balance(double money5Balance) {
        Money5Balance = money5Balance;
    }

    public double getMoney6Balance() {
        return Money6Balance;
    }

    public void setMoney6Balance(double money6Balance) {
        Money6Balance = money6Balance;
    }

    public int getIntegral() {
        return Integral;
    }

    public void setIntegral(int integral) {
        Integral = integral;
    }

    public double getGoodsAmount() {
        return GoodsAmount;
    }

    public void setGoodsAmount(double goodsAmount) {
        GoodsAmount = goodsAmount;
    }

    public int getReturnIntegral() {
        return ReturnIntegral;
    }

    public void setReturnIntegral(int returnIntegral) {
        ReturnIntegral = returnIntegral;
    }

    public double getDiscount() {
        return Discount;
    }

    public void setDiscount(double discount) {
        Discount = discount;
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

    public AddressBean getAddress() {
        return Address;
    }

    public void setAddress(AddressBean address) {
        Address = address;
    }

    public ArrayList<OrderInfoBuyListBean> getOrderInfoBuyList() {
        return OrderInfoBuyList;
    }

    public void setOrderInfoBuyList(ArrayList<OrderInfoBuyListBean> orderInfoBuyList) {
        OrderInfoBuyList = orderInfoBuyList;
    }
}
