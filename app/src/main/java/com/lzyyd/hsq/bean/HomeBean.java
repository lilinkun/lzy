package com.lzyyd.hsq.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by liguo on 2020/8/26
 * Describe:
 */
public class HomeBean {
    private ArrayList<HomeItemBean> GoodsList1;
    private ArrayList<HomeItemBean> GoodsList2;
    private ArrayList<HomeItemBean> GoodsList3;
    private ArrayList<HomeItemBean> GoodsList4;
    private ArrayList<HomeItemBean> GoodsList5;
    private ArrayList<HomeItemBean> GoodsList6;
    private ArrayList<FlashBean> flash;
    private ArrayList<HomeGridListItemBean> SqIcon;
    private HomeGridItemBean Vip;
    private HomeGridItemBean Sq;
    private List<ArticleDetailBean> News;
    private ArrayList<FlashBean> Ccq;

    public ArrayList<FlashBean> getCcq() {
        return Ccq;
    }

    public void setCcq(ArrayList<FlashBean> ccq) {
        Ccq = ccq;
    }

    public List<ArticleDetailBean> getNews() {
        return News;
    }

    public void setNews(List<ArticleDetailBean> news) {
        News = news;
    }

    public HomeGridItemBean getVip() {
        return Vip;
    }

    public void setVip(HomeGridItemBean vip) {
        Vip = vip;
    }

    public HomeGridItemBean getSq() {
        return Sq;
    }

    public void setSq(HomeGridItemBean sq) {
        Sq = sq;
    }

    public ArrayList<HomeGridListItemBean> getSqIcon() {
        return SqIcon;
    }

    public void setSqIcon(ArrayList<HomeGridListItemBean> sqIcon) {
        SqIcon = sqIcon;
    }

    public ArrayList<HomeItemBean> getGoodsList1() {
        return GoodsList1;
    }

    public void setGoodsList1(ArrayList<HomeItemBean> goodsList1) {
        GoodsList1 = goodsList1;
    }

    public ArrayList<HomeItemBean> getGoodsList2() {
        return GoodsList2;
    }

    public void setGoodsList2(ArrayList<HomeItemBean> goodsList2) {
        GoodsList2 = goodsList2;
    }

    public ArrayList<HomeItemBean> getGoodsList3() {
        return GoodsList3;
    }

    public void setGoodsList3(ArrayList<HomeItemBean> goodsList3) {
        GoodsList3 = goodsList3;
    }

    public ArrayList<HomeItemBean> getGoodsList4() {
        return GoodsList4;
    }

    public void setGoodsList4(ArrayList<HomeItemBean> goodsList4) {
        GoodsList4 = goodsList4;
    }

    public ArrayList<HomeItemBean> getGoodsList5() {
        return GoodsList5;
    }

    public void setGoodsList5(ArrayList<HomeItemBean> goodsList5) {
        GoodsList5 = goodsList5;
    }

    public ArrayList<HomeItemBean> getGoodsList6() {
        return GoodsList6;
    }

    public void setGoodsList6(ArrayList<HomeItemBean> goodsList6) {
        GoodsList6 = goodsList6;
    }

    public ArrayList<FlashBean> getFlash() {
        return flash;
    }

    public void setFlash(ArrayList<FlashBean> flash) {
        this.flash = flash;
    }
}
