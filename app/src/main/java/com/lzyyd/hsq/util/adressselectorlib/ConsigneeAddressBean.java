package com.lzyyd.hsq.util.adressselectorlib;

import com.lzyyd.hsq.bean.LocalBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wepon on 2017/12/4.
 * 数据模型
 */

public class ConsigneeAddressBean implements Serializable {

    private List<LocalBean> province;
    private List<LocalBean> city;
    private List<LocalBean> district;

    public List<LocalBean> getProvince() {
        return province;
    }

    public void setProvince(List<LocalBean> province) {
        this.province = province;
    }

    public List<LocalBean> getCity() {
        return city;
    }

    public void setCity(List<LocalBean> city) {
        this.city = city;
    }

    public List<LocalBean> getDistrict() {
        return district;
    }

    public void setDistrict(List<LocalBean> district) {
        this.district = district;
    }

    public static class AddressItemBean implements Serializable {
        private String i;
        private String n;
        private String p;

        public String getI() {
            return i;
        }

        public void setI(String i) {
            this.i = i;
        }

        public String getN() {
            return n;
        }

        public void setN(String n) {
            this.n = n;
        }

        public String getP() {
            return p;
        }

        public void setP(String p) {
            this.p = p;
        }
    }
}
