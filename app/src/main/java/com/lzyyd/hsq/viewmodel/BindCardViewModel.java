package com.lzyyd.hsq.viewmodel;

import android.app.Application;
import android.content.SharedPreferences;

import com.lzyyd.hsq.activity.MainActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.BankBean;
import com.lzyyd.hsq.bean.LoginBean;
import com.lzyyd.hsq.bean.UserBankBean;
import com.lzyyd.hsq.data.DataRepository;
import com.lzyyd.hsq.http.callback.HttpResultCallBack;
import com.lzyyd.hsq.util.HsqAppUtil;
import com.lzyyd.hsq.util.UToast;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.RxUtils;

import static android.content.Context.MODE_PRIVATE;

/**
 * Create by liguo on 2020/8/28
 * Describe:
 */
public class BindCardViewModel extends BaseViewModel<DataRepository> {

    private OnBankListener onBankListener;

    public BindCardViewModel(@NonNull Application application,DataRepository dataRepository) {
        super(application,dataRepository);
    }

    public void setListener(OnBankListener onBankListener){
        this.onBankListener = onBankListener;
    }

    public void getBankCard(String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "UserBaseBankGet");
        params.put("SessionId", SessionId);
        model.getBankCard(params)
                .compose(RxUtils.schedulersTransformer())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>(){
                    @Override
                    public void accept(Disposable disposable){
                        showDialog();
                    }
                })
                .subscribe(new HttpResultCallBack<UserBankBean,Object>() {
                    @Override
                    public void onResponse(UserBankBean balanceDetailBeans, String status, Object page) {
                        onBankListener.getBankSuccess(balanceDetailBeans);
                        dismissDialog();

                    }

                    @Override
                    public void onErr(String msg, String status) {
                        dismissDialog();
                        onBankListener.getBankFail(msg);
                    }

                });
    }


    public void getBankInfo(String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "BankNameList");
        params.put("SessionId", SessionId);

        model.getBankInfo(params)
                .compose(RxUtils.schedulersTransformer())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>(){
                    @Override
                    public void accept(Disposable disposable){
                        showDialog();
                    }
                })
                .subscribe(new HttpResultCallBack<ArrayList<BankBean>, Object>() {

                    @Override
                    public void onResponse(ArrayList<BankBean> addressBeans, String status, Object page) {
                        onBankListener.getBankInfoSuccess(addressBeans);
                        dismissDialog();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        onBankListener.getBankInfoFail(msg);
                        dismissDialog();
                    }
                });
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
                        onBankListener.onSendVcodeSuccess(status);
                        dismissDialog();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        onBankListener.onSendVcodeFail(msg);
                        dismissDialog();
                    }

                });
    }

    public void upBankInfo(String BankName, String BankDetails, String BankUserName, String BankNo, String Code, String UserCode, String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "UserBaseBankUpdate");
        params.put("BankName", BankName);
        params.put("BankDetails", BankDetails);
        params.put("BankUserName", BankUserName);
        params.put("BankNo", BankNo);
        params.put("Code", Code);
        params.put("UserCode", UserCode);
        params.put("SessionId", SessionId);

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
                .subscribe(new HttpResultCallBack<String, String>() {

                    @Override
                    public void onResponse(String s, String status, String page) {
                        onBankListener.upBankInfoSuccess(s);
                        dismissDialog();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        onBankListener.upBankInfoFail(msg);
                        dismissDialog();
                    }
                });
    }

    public interface OnBankListener{
        public void getBankSuccess(UserBankBean userBankBean);
        public void getBankFail(String msg);

        public void onSendVcodeSuccess(String status);
        public void onSendVcodeFail(String msg);

        public void getBankInfoSuccess(ArrayList<BankBean> bankBeans);
        public void getBankInfoFail(String msg);

        public void upBankInfoSuccess(String msg);
        public void upBankInfoFail(String msg);
    }
}
