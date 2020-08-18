package com.lzyyd.hsq.bean;

/**
 * Create by liguo on 2020/8/17
 * Describe:
 */
public class OrderGroupBean<T> {
    private CartListBean<T> orderListBean;
    private boolean isChoosed;
    private boolean isEditor; //自己对该组的编辑状态
    private boolean ActionBarEditor;// 全局对该组的编辑状态

    public CartListBean<T> getOrderListBean() {
        return orderListBean;
    }

    public void setOrderListBean(CartListBean<T> orderListBean) {
        this.orderListBean = orderListBean;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }

    public boolean isEditor() {
        return isEditor;
    }

    public void setEditor(boolean editor) {
        isEditor = editor;
    }

    public boolean isActionBarEditor() {
        return ActionBarEditor;
    }

    public void setActionBarEditor(boolean actionBarEditor) {
        ActionBarEditor = actionBarEditor;
    }
}
