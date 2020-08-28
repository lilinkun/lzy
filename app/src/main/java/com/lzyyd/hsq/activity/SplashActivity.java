package com.lzyyd.hsq.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.WindowManager;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.util.HsqAppUtil;

/**
 * Create by liguo on 2020/8/28
 * Describe:
 */
public class SplashActivity extends BaseActivity{

    MyCountDownTimer myCountDownTimer = new MyCountDownTimer(1000, 1000);

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_splash;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public void initData() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏

        myCountDownTimer.start();

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
