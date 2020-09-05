package com.lzyyd.hsq.viewmodel;

import android.app.Application;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.View;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.activity.ForgetSettingPsdActivity;
import com.lzyyd.hsq.data.DataRepository;
import com.lzyyd.hsq.databinding.ActivityForgetpasswordBinding;
import com.lzyyd.hsq.http.callback.HttpResultCallBack;
import com.lzyyd.hsq.model.ForgetPsdModel;

import java.util.HashMap;

import androidx.databinding.ObservableField;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.RxUtils;

public class ForgetPasswordViewModel extends BaseViewModel<DataRepository> {

    private ForgetPsdModel forgetPsdModel;
    private String mobileStr;
    private Application application;
    public ObservableField<Boolean> isClick = new ObservableField<>(true);
    public ObservableField<String> str = new ObservableField<>();
    public ObservableField<Integer> colorInt = new ObservableField<>();
    private OnForgetPsdListener onForgetPsdListener;

    MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000, 1000);

    public void setListener(OnForgetPsdListener listener){
        this.onForgetPsdListener = listener;
    }

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
     * 发送短信验证码
     *
     * @param sessionId
     */
    public void SendSms(String UserName,String sessionId) {

        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "SendSms");
        params.put("fun", "ForgotPassword");
        params.put("UserName", UserName);
        params.put("SessionId", sessionId);
        model.register(params)
                .compose(RxUtils.schedulersTransformer())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>(){
                    @Override
                    public void accept(Disposable disposable){
                        showDialog();
                    }
                })
                .subscribe(new HttpResultCallBack<String,String>() {

                    @Override
                    public void onResponse(String o, String status, String page) {
                        onForgetPsdListener.onSendVcodeSuccess(status);
                        dismissDialog();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        onForgetPsdListener.onSendVcodeFail(msg);
                        dismissDialog();
                    }

                });
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

    public interface OnForgetPsdListener{
        public void onSendVcodeSuccess(String msg);
        public void onSendVcodeFail(String msg);
    }
}
