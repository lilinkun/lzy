package com.lzyyd.hsq.bean;

/**
 * Create by liguo on 2020/7/23
 * Describe:
 */
public class OrderBean {
    private String CartId;
    private String CreateTime;
    private String UserId;
    private String UserName;
    private int Num;
    private String GoodsId;
    private String BrandId;
    private String GoodsSn;
    private String GoodsSpec1;
    private String GoodsSpec2;
    private String GoodsName;
    private int GoodsNumber;
    private String GoodsWeight;
    private double Price;
    private double MarketPrice;
    private boolean IsOnSale;
    private boolean IsDelete;
    private boolean IsCarriage;
    private int GoodsType;
    private int Integral;
    private String GoodsImg;
    private String GoodsImgList;
    private String GoodsIndexImg;
    private String spec1;
    private String spec2;

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getBrandId() {
        return BrandId;
    }

    public void setBrandId(String brandId) {
        BrandId = brandId;
    }

    public String getCartId() {
        return CartId;
    }

    public void setCartId(String cartId) {
        CartId = cartId;
    }

    public String getGoodsSn() {
        return GoodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        GoodsSn = goodsSn;
    }

    public int getGoodsType() {
        return GoodsType;
    }

    public void setGoodsType(int goodsType) {
        GoodsType = goodsType;
    }

    public String getGoodsWeight() {
        return GoodsWeight;
    }

    public void setGoodsWeight(String goodsWeight) {
        GoodsWeight = goodsWeight;
    }

    public String getGoodsSpec1() {
        return GoodsSpec1;
    }

    public void setGoodsSpec1(String goodsSpec1) {
        GoodsSpec1 = goodsSpec1;
    }

    public String getGoodsSpec2() {
        return GoodsSpec2;
    }

    public void setGoodsSpec2(String goodsSpec2) {
        GoodsSpec2 = goodsSpec2;
    }

    public String getGoodsId() {
        return GoodsId;
    }

    public void setGoodsId(String goodsId) {
        GoodsId = goodsId;
    }

    public int getNum() {
        return Num;
    }

    public void setNum(int num) {
        Num = num;
    }

    public boolean isOnSale() {
        return IsOnSale;
    }

    public void setOnSale(boolean onSale) {
        IsOnSale = onSale;
    }

    public boolean isDelete() {
        return IsDelete;
    }

    public void setDelete(boolean delete) {
        IsDelete = delete;
    }

    public boolean isCarriage() {
        return IsCarriage;
    }

    public void setCarriage(boolean carriage) {
        IsCarriage = carriage;
    }

    public int getIntegral() {
        return Integral;
    }

    public void setIntegral(int integral) {
        Integral = integral;
    }

    public String getGoodsName() {
        return GoodsName;
    }

    public void setGoodsName(String goodsName) {
        GoodsName = goodsName;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public double getMarketPrice() {
        return MarketPrice;
    }

    public void setMarketPrice(double marketPrice) {
        MarketPrice = marketPrice;
    }

    public String getGoodsImg() {
        return GoodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        GoodsImg = goodsImg;
    }

    public String getGoodsImgList() {
        return GoodsImgList;
    }

    public void setGoodsImgList(String goodsImgList) {
        GoodsImgList = goodsImgList;
    }

    public String getGoodsIndexImg() {
        return GoodsIndexImg;
    }

    public void setGoodsIndexImg(String goodsIndexImg) {
        GoodsIndexImg = goodsIndexImg;
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

    public int getGoodsNumber() {
        return GoodsNumber;
    }

    public void setGoodsNumber(int goodsNumber) {
        GoodsNumber = goodsNumber;
    }

    public String getSpec1() {
        return spec1;
    }

    public void setSpec1(String spec1) {
        this.spec1 = spec1;
    }

    public String getSpec2() {
        return spec2;
    }

    public void setSpec2(String spec2) {
        this.spec2 = spec2;
    }
}
