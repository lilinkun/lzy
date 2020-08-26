package com.lzyyd.hsq.bean;

import java.util.ArrayList;

/**
 * Create by liguo on 2020/8/26
 * Describe:
 */
public class OrderListBean {
    private String OrderId;
    private String OrderSn;
    private String UserId;
    private String UserName;
    private String BestTime;
    private int Integral;
    //未付款 = 0,
    //已付款 = 1,
    //已发货 = 2,
    //交易成功 = 3,
    //交易关闭 = 4,
    //退单 = 5,
    private int OrderStatus;
    private String OrderStatusName;
    private String Consignee;
    private String OrderTypeName;
    private int OrderType;
    private String Address;
    private int DeliveryMode;
    private String AddressName;
    private String Mobile;
    private double Money1;
    private double Money3;
    private double Money5;
    private String StoreLogo;
    private String StoreName;
    private double ShippingFree;
    private int isDel;
    private double SettleMoney64;
    private double SettleMoney64_2;
    private String EffectivePaymentDate;
    private int MemberLevel;
    private String UserLevelName;
    private int IsPresell;
    private double OrderAmount;
    private ArrayList<OrderGoodsBuyListBean> list;


    public double getOrderAmount() {
        return OrderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        OrderAmount = orderAmount;
    }

    public ArrayList<OrderGoodsBuyListBean> getList() {
        return list;
    }

    public void setList(ArrayList<OrderGoodsBuyListBean> list) {
        this.list = list;
    }

    public double getShippingFree() {
        return ShippingFree;
    }

    public void setShippingFree(double shippingFree) {
        ShippingFree = shippingFree;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getOrderSn() {
        return OrderSn;
    }

    public void setOrderSn(String orderSn) {
        OrderSn = orderSn;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getBestTime() {
        return BestTime;
    }

    public void setBestTime(String bestTime) {
        BestTime = bestTime;
    }

    public int getIntegral() {
        return Integral;
    }

    public void setIntegral(int integral) {
        Integral = integral;
    }

    public int getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        OrderStatus = orderStatus;
    }

    public String getOrderStatusName() {
        return OrderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        OrderStatusName = orderStatusName;
    }

    public String getConsignee() {
        return Consignee;
    }

    public void setConsignee(String consignee) {
        Consignee = consignee;
    }

    public String getOrderTypeName() {
        return OrderTypeName;
    }

    public void setOrderTypeName(String orderTypeName) {
        OrderTypeName = orderTypeName;
    }

    public int getOrderType() {
        return OrderType;
    }

    public void setOrderType(int orderType) {
        OrderType = orderType;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getDeliveryMode() {
        return DeliveryMode;
    }

    public void setDeliveryMode(int deliveryMode) {
        DeliveryMode = deliveryMode;
    }

    public String getAddressName() {
        return AddressName;
    }

    public void setAddressName(String addressName) {
        AddressName = addressName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public double getMoney1() {
        return Money1;
    }

    public void setMoney1(double money1) {
        Money1 = money1;
    }

    public double getMoney3() {
        return Money3;
    }

    public void setMoney3(double money3) {
        Money3 = money3;
    }

    public double getMoney5() {
        return Money5;
    }

    public void setMoney5(double money5) {
        Money5 = money5;
    }

    public String getStoreLogo() {
        return StoreLogo;
    }

    public void setStoreLogo(String storeLogo) {
        StoreLogo = storeLogo;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public double getSettleMoney64() {
        return SettleMoney64;
    }

    public void setSettleMoney64(double settleMoney64) {
        SettleMoney64 = settleMoney64;
    }

    public double getSettleMoney64_2() {
        return SettleMoney64_2;
    }

    public void setSettleMoney64_2(double settleMoney64_2) {
        SettleMoney64_2 = settleMoney64_2;
    }

    public String getEffectivePaymentDate() {
        return EffectivePaymentDate;
    }

    public void setEffectivePaymentDate(String effectivePaymentDate) {
        EffectivePaymentDate = effectivePaymentDate;
    }

    public int getMemberLevel() {
        return MemberLevel;
    }

    public void setMemberLevel(int memberLevel) {
        MemberLevel = memberLevel;
    }

    public String getUserLevelName() {
        return UserLevelName;
    }

    public void setUserLevelName(String userLevelName) {
        UserLevelName = userLevelName;
    }

    public int getIsPresell() {
        return IsPresell;
    }

    public void setIsPresell(int isPresell) {
        IsPresell = isPresell;
    }
}
