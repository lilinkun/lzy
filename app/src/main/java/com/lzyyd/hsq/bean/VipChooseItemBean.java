package com.lzyyd.hsq.bean;

/**
 * Create by liguo on 2020/8/25
 * Describe:
 */
public class VipChooseItemBean {
    private String GoodsSn;
    private int num;
    private String GoodsId;
    private double price;
    private String goodsImg;
    private String goodsName;
    private int qty;
    private int isPresell;
    private String beginDate;
    private String goodsSpec1;
    private String goodsSpec2;
    private GoodsChooseBean goodsChooseBean;

    public String getGoodsSn() {
        return GoodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        GoodsSn = goodsSn;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getGoodsId() {
        return GoodsId;
    }

    public void setGoodsId(String goodsId) {
        GoodsId = goodsId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public GoodsChooseBean getGoodsChooseBean() {
        return goodsChooseBean;
    }

    public void setGoodsChooseBean(GoodsChooseBean goodsChooseBean) {
        this.goodsChooseBean = goodsChooseBean;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getIsPresell() {
        return isPresell;
    }

    public void setIsPresell(int isPresell) {
        this.isPresell = isPresell;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getGoodsSpec1() {
        return goodsSpec1;
    }

    public void setGoodsSpec1(String goodsSpec1) {
        this.goodsSpec1 = goodsSpec1;
    }

    public String getGoodsSpec2() {
        return goodsSpec2;
    }

    public void setGoodsSpec2(String goodsSpec2) {
        this.goodsSpec2 = goodsSpec2;
    }
}
