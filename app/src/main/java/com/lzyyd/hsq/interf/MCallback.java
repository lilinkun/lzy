package com.lzyyd.hsq.interf;

import com.lzyyd.hsq.bean.Account;

public interface MCallback {
    public void onSuccess(Account account);
    public  void onFailed();
}
