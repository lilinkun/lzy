package com.lzyyd.hsq.bean;

import java.io.Serializable;

/**
 * Create by liguo on 2020/8/28
 * Describe:
 */
public class UserBankBean implements Serializable {
    private String UserName;
    private String BankDetails;
    private int BankName;
    private String BankNameDesc;
    private String BankNo;
    private String BankUserName;
    private double Rate;
    private String UserCode;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getBankDetails() {
        return BankDetails;
    }

    public void setBankDetails(String bankDetails) {
        BankDetails = bankDetails;
    }

    public int getBankName() {
        return BankName;
    }

    public void setBankName(int bankName) {
        BankName = bankName;
    }

    public String getBankNameDesc() {
        return BankNameDesc;
    }

    public void setBankNameDesc(String bankNameDesc) {
        BankNameDesc = bankNameDesc;
    }

    public String getBankNo() {
        return BankNo;
    }

    public void setBankNo(String bankNo) {
        BankNo = bankNo;
    }

    public String getBankUserName() {
        return BankUserName;
    }

    public void setBankUserName(String bankUserName) {
        BankUserName = bankUserName;
    }

    public double getRate() {
        return Rate;
    }

    public void setRate(double rate) {
        Rate = rate;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String userCode) {
        UserCode = userCode;
    }
}
