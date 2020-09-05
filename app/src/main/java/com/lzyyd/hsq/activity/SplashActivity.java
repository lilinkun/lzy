package com.lzyyd.hsq.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.WindowManager;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.UrlBean;
import com.lzyyd.hsq.databinding.ActivitySplashBinding;
import com.lzyyd.hsq.util.HsqAppUtil;
import com.lzyyd.hsq.util.UToast;
import com.lzyyd.hsq.viewmodel.MVVMViewModel;

import androidx.lifecycle.ViewModelProviders;

/**
 * Create by liguo on 2020/8/28
 * Describe:
 */
public class SplashActivity extends BaseActivity<ActivitySplashBinding, MVVMViewModel> implements MVVMViewModel.LoginCallBack {

    MyCountDownTimer myCountDownTimer = new MyCountDownTimer(1000, 1000);

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_splash;
    }

    @Override
    public int initVariableId() {
        return BR.mvvm;
    }

    @Override
    public void initData() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏

//        myCountDownTimer.start();

        viewModel.getUrl(this);
    }

    @Override
    public void getUrlSuccess(UrlBean urlBean) {
        ProApplication.HEADIMG = urlBean.getImgUrl() + ProApplication.IMG_SMALL;
        ProApplication.BANNERIMG = urlBean.getImgUrl() + ProApplication.IMG_BIG;
        ProApplication.HOMEADDRESS = urlBean.getImgUrl() + ProApplication.IMG_HOME_ADDRESS;
        ProApplication.CUSTOMERIMG = urlBean.getServiesUrl();
        ProApplication.SHAREDIMG = urlBean.getSharedWebUrl();
        ProApplication.REGISTERREQUIREMENTS = urlBean.getRegisterRequirements();
        ProApplication.LOGISTICSURL = urlBean.getLogisticsUrl();
        ProApplication.UPGRADEURL = urlBean.getUpgradeUrl();
        ProApplication.UPGRADETOKEN = urlBean.getUpgradeToken();
        ProApplication.PHONE = urlBean.getKFMobile();
        ProApplication.SERVIESVIP = urlBean.getServiesVip();
        ProApplication.SHAREDMEIMG = urlBean.getShareImg();
        ProApplication.USERLEVELPRICE10 = urlBean.getUserLevelPrice10();
        ProApplication.USERLEVELPRICE20 = urlBean.getUserLevelPrice20();
        ProApplication.CCQGOODSID = urlBean.getCcqGoodsId();
        SharedPreferences sharedPreferences = getSharedPreferences(HsqAppUtil.LOGIN, MODE_PRIVATE);
        sharedPreferences.edit().putString(HsqAppUtil.IMG, ProApplication.HEADIMG).putString(HsqAppUtil.BANNERIMG, ProApplication.BANNERIMG)
                .putString(HsqAppUtil.CUSTOMER, ProApplication.CUSTOMERIMG).putString(HsqAppUtil.SHAREDIMG, ProApplication.SHAREDIMG)
                .putString(HsqAppUtil.SHAREDMEIMG,"").commit();

        turnHome();
    }

    @Override
    public MVVMViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(MVVMViewModel.class);
    }

    @Override
    public void getUrlFail(String msg) {
        UToast.show(this,msg);
    }

    /**
     * 获取验证码倒计时
     */
    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程
        @Override
        public void onTick(long l) {

        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            turnHome();

        }
    }

    private void turnHome() {
        Intent intent = null;
        SharedPreferences sharedPreferences = getSharedPreferences(HsqAppUtil.LOGIN, MODE_PRIVATE);
        if (sharedPreferences.getBoolean(HsqAppUtil.LOGIN, false) == true) {
            intent = new Intent(getBaseContext(), MainActivity.class);
        } else {
            intent = new Intent(getBaseContext(), LoginActivity.class);
        }
        //启动MainActivity
        startActivity(intent);
        finish();
    }
}
