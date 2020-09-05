package com.lzyyd.hsq.interf;

/**
 * Create by liguo on 2020/9/4
 * Describe:
 */
public interface IWxPayListener {

    public void setWxPaySuccess(String msg);

    public void setWxPayFail(String msg);
}
