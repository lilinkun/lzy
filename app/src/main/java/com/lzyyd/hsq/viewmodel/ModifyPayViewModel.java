package com.lzyyd.hsq.viewmodel;

import android.app.Application;

import com.lzyyd.hsq.bean.LoginBean;
import com.lzyyd.hsq.data.DataRepository;
import com.lzyyd.hsq.http.callback.HttpResultCallBack;

import java.util.HashMap;

import androidx.annotation.NonNull;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.RxUtils;

/**
 * Create by liguo on 2020/9/1
 * Describe:
 */
public class ModifyPayViewModel extends BaseViewModel<DataRepository> {

    private ModifyPsdListener modifyPsdListener;

    public ModifyPayViewModel(@NonNull Application application, DataRepository model) {
        super(application, model);
    }

    public void setListener(ModifyPsdListener listener){
        this.modifyPsdListener = listener;
    }

    public void SendVCode(){
        modifyPsdListener.sendVcode();
    }

    public void OnSureChange(){
        modifyPsdListener.onSure();
    }

    /**
     * 发送短信验证码
     *
     * @param sessionId
     */
    public void SendSms(String sessionId) {

        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "SendSms");
        params.put("fun", "SendSafetyVerificationCode");
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
                        modifyPsdListener.onSendVcodeSuccess(status);
                        dismissDialog();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        modifyPsdListener.onSendVcodeFail(msg);
                        dismissDialog();
                    }

                });
    }

    /**
     * 发送短信验证码
     *
     * @param sessionId
     */
    public void SendSmsForgetPsd(String UserName,String sessionId) {

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
                        modifyPsdListener.onSendVcodeSuccess(status);
                        dismissDialog();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        modifyPsdListener.onSendVcodeFail(msg);
                        dismissDialog();
                    }

                });
    }

    /**
     * 修改支付密码
     *
     * @param sessionId
     */
    public void modifyPsd(String Code, String PassWord, String ConfirmPassWord, String sessionId) {

        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "UserBaseChangePassWord_Two");
        params.put("Code", Code);
        params.put("PassWord", PassWord);
        params.put("ConfirmPassWord", ConfirmPassWord);
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
                        modifyPsdListener.modifySuccess();
                        dismissDialog();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        modifyPsdListener.modifyFail(msg);
                        dismissDialog();
                    }

                });
    }

    /**
     * 修改用户密码
     *
     * @param sessionId
     */
    public void modifyUserPsd(String UserName,String Code, String PassWord, String ConfirmPassWord, String sessionId) {

        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "ForgotPassword");
        params.put("UserName",UserName);
        params.put("Code", Code);
        params.put("PassWord", PassWord);
        params.put("ConfirmPassWord", ConfirmPassWord);
        params.put("SessionId", sessionId);
        model.login(params)
                .compose(RxUtils.schedulersTransformer())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>(){
                    @Override
                    public void accept(Disposable disposable){
                        showDialog();
                    }
                })
                .subscribe(new HttpResultCallBack<LoginBean,String>() {
                    @Override
                    public void onResponse(LoginBean o, String status, String page) {
                        modifyPsdListener.modifyUserPsdSuccess(o);
                        dismissDialog();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        modifyPsdListener.modifyUserPsdFail(msg);
                        dismissDialog();
                    }

                });
    }



    public interface ModifyPsdListener{

        public void onSendVcodeSuccess(String msg);
        public void onSendVcodeFail(String str);

        public void modifySuccess();
        public void modifyFail(String str);

        public void modifyUserPsdSuccess(LoginBean loginBean);
        public void modifyUserPsdFail(String str);

        public void sendVcode();
        public void onSure();
    }

}
