package com.lzyyd.hsq.bean;

import androidx.databinding.ObservableField;

public class SeckillGoodsBean {

    private ObservableField<String> goodsImg = new ObservableField<>();
    private ObservableField<String> goodsTitles = new ObservableField<>();
    private ObservableField<String> seckilltime = new ObservableField<>();
    private ObservableField<Double> goodsPrice= new ObservableField<>();

    public SeckillGoodsBean(String goodsImg, String goodsTitles, String seckilltime, double goodsPrice) {
        this.goodsImg.set(goodsImg);
        this.goodsTitles.set(goodsTitles);
        this.seckilltime.set(seckilltime);
        this.goodsPrice.set(goodsPrice);
    }

    public ObservableField<String> getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg.set(goodsImg);
    }

    public ObservableField<String> getGoodsTitles() {
        return goodsTitles;
    }

    public void setGoodsTitles(String goodsTitles) {
        this.goodsTitles.set(goodsTitles);
    }

    public ObservableField<String> getSeckilltime() {
        return seckilltime;
    }

    public void setSeckilltime(String seckilltime) {
        this.seckilltime.set(seckilltime);
    }

    public ObservableField<Double> getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice.set(goodsPrice);
    }
}
