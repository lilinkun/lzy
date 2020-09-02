package com.lzyyd.hsq.viewmodel;

import android.app.Application;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.View;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.activity.ForgetSettingPsdActivity;
import com.lzyyd.hsq.data.DataRepository;
import com.lzyyd.hsq.databinding.ActivityForgetpasswordBinding;
import com.lzyyd.hsq.model.ForgetPsdModel;

import androidx.databinding.ObservableField;
import me.goldze.mvvmhabit.base.BaseViewModel;

public class ForgetPasswordViewModel extends BaseViewModel<DataRepository> {

    private ForgetPsdModel forgetPsdModel;
    private String mobileStr;
    private Application application;
    public ObservableField<Boolean> isClick = new ObservableField<>(true);
    public ObservableField<String> str = new ObservableField<>();
    public ObservableField<Integer> colorInt = new ObservableField<>();

    MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000, 1000);

    public ForgetPasswordViewModel(Application application, DataRepository dataRepository) {
        super(application,dataRepository);
        this.application = application;
        this.forgetPsdModel = new ForgetPsdModel();
        str.set(application.getResources().getString(R.string.tv_forget_get_vcode));
        colorInt.set(Color.parseColor("#ffa37d5f"));
    }

    public String getMobileStr() {
        return mobileStr;
    }

    public void setMobileStr(String mobileStr) {
        this.mobileStr = mobileStr;
    }

    public void setNewPsd(View view){

        startActivity(ForgetSettingPsdActivity.class);
    }

    public void setVCodeClick(View view){
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
            //防止计时过程中重复点击
            isClick.set(false);
            str.set(l / 1000 + "s");
            colorInt.set(Color.parseColor("#b2b2b2"));
        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            //重新给Button设置文字
            str.set(application.getResources().getString(R.string.tv_forget_get_vcode));
            //设置可点击
            isClick.set(true);

            colorInt.set(Color.parseColor("#FE744A"));
        }
    }
}
