package com.lzyyd.hsq.bean;

/**
 * Create by liguo on 2020/8/10
 * Describe:
 */
public class PersonalInfoBean {
    private LoginBean user_data;
    private CountBean bank_data;

    public LoginBean getUser_data() {
        return user_data;
    }

    public void setUser_data(LoginBean user_data) {
        this.user_data = user_data;
    }

    public CountBean getBank_data() {
        return bank_data;
    }

    public void setBank_data(CountBean bank_data) {
        this.bank_data = bank_data;
    }
}
