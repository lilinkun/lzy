package com.lzyyd.hsq.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Create by liguo on 2020/8/15
 * Describe:
 */
public class ProApplication extends Application {
    private static Context mContext;
    private static ProApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        displayMetrics.scaledDensity = displayMetrics.density;
        disableAPIDialog();
    }

    /**
     * 反射 禁止弹窗
     */
    private void disableAPIDialog() {
        if (Build.VERSION.SDK_INT < 28) return;
        try {
            Class clazz = Class.forName("android.app.ActivityThread");
            Method currentActivityThread = clazz.getDeclaredMethod("currentActivityThread");
            currentActivityThread.setAccessible(true);
            Object activityThread = currentActivityThread.invoke(null);
            Field mHiddenApiWarningShown = clazz.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    /**
     * 获得当前app运行的AppContext
     *
     * @return
     */
    public static ProApplication getInstance() {
        return instance;
    }


    public static String SESSIONID() {
        return "wlm" + getUniqueId(ProApplication.context());
    }

    public static String HEADIMG = "";

    public static String BANNERIMG = "";

    public static String HOMEADDRESS = "";

    public static String CUSTOMERIMG = "";

    public static String SHAREDIMG = "";

    public static String SHAREDMEIMG = "";

    public static String REGISTERREQUIREMENTS = "";

    public static String UPGRADEURL = "";

    public static String UPGRADETOKEN = "";

    public static String VIPVALIDITY = "";

    public static int USERLEVEL = 0;

    public static String PHONE = "";

    public static String SERVIESVIP = "";

    public static String LOGISTICSURL = "";

    public static Boolean isAudinLogin = false;


    public static final String IMG_BIG = "imgdb/";
    public static final String IMG_HOME_ADDRESS = "imgdb/velet/";
    public static final String IMG_SMALL = "img/300/300/";
    public static final String IMG_SMALL_ = "img/150/150/";


    public static synchronized ProApplication context() {
        return (ProApplication) mContext;
    }

    public static String getUniqueId(Application context) {
        String androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        String id = androidID + Build.SERIAL;
        try {
            return toMD5(id);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return id;
        }
    }


    private static String toMD5(String text) throws NoSuchAlgorithmException {
        //获取摘要器 MessageDigest
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        //通过摘要器对字符串的二进制字节数组进行hash计算
        byte[] digest = messageDigest.digest(text.getBytes());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digest.length; i++) {
            //循环每个字符 将计算结果转化为正整数;
            int digestInt = digest[i] & 0xff;
            //将10进制转化为较短的16进制
            String hexString = Integer.toHexString(digestInt);
            //转化结果如果是个位数会省略0,因此判断并补0
            if (hexString.length() < 2) {
                sb.append(0);
            }
            //将循环结果添加到缓冲区
            sb.append(hexString);
        }
        //返回整个结果
        return sb.toString();
    }
}
