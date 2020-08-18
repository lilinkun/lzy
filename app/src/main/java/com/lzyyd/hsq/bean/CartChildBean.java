package com.lzyyd.hsq.bean;

/**
 * Create by liguo on 2020/8/17
 * Describe:
 */
public class CartChildBean {
    private CartBean orderBean;
    private boolean isChoosed;
    private String parentId;

    public CartBean getOrderBean() {
        return orderBean;
    }

    public void setOrderBean(CartBean orderBean) {
        this.orderBean = orderBean;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
