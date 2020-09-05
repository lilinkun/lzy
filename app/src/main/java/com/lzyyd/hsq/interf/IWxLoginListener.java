package com.lzyyd.hsq.interf;


import com.lzyyd.hsq.bean.WxUserInfo;

/**
 * Created by LG on 2019/9/5.
 */
public interface IWxLoginListener {

    public void setWxLoginSuccess(WxUserInfo wxSuccess);


    public void setWxLoginFail(String msg);
}
