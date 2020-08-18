package com.lzyyd.hsq.util;

import com.lzyyd.hsq.R;

/**
 * Create by liguo on 2020/7/20
 * Describe:
 */
public enum HomeGridRvEnum {
    STATUS0(0, "京东", R.mipmap.ic_banner,"icon-1@2x.png"),
    STATUS1(1, "淘宝", R.mipmap.ic_banner,"icon-2@2x.png"),
    STATUS2(2, "天猫", R.mipmap.ic_banner,"icon-3@2x.png"),
    STATUS3(3, "拼多多", R.mipmap.ic_banner,"icon-4@2x.png"),
    STATUS4(4,"加油充电", R.mipmap.ic_banner,"icon-1@2x.png"),
    STATUS5(5, "腾讯视频", R.mipmap.ic_banner,"icon6@2x.png"),
    STATUS6(6, "唯品会", R.mipmap.ic_banner,"icon7@2x.png"),
    STATUS7(7, "饿了吗", R.mipmap.ic_banner,"icon-8@2x.png"),
    STATUS8(8, "当当", R.mipmap.ic_banner,"icon-9@2x.png"),
    STATUS9(9,"网易云音乐", R.mipmap.ic_banner,"icon-1@2x.png");

    private int id;
    private String statusMsg;
    private int srcmsg;
    private String msgAddress;

    private HomeGridRvEnum(int id, String statusMsg, int srcmsg,String msgAddress) {
        this.id = id;
        this.statusMsg = statusMsg;
        this.srcmsg = srcmsg;
        this.msgAddress = msgAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public int getSrcmsg() {
        return srcmsg;
    }

    public void setSrcmsg(int srcmsg) {
        this.srcmsg = srcmsg;
    }

    public String getMsgAddress() {
        return msgAddress;
    }

    public void setMsgAddress(String msgAddress) {
        this.msgAddress = msgAddress;
    }

    public static HomeGridRvEnum getPageByValue(int type) {
        for (HomeGridRvEnum p : values()) {
            if (p.getId() == type)
                return p;
        }
        return null;
    }
}
