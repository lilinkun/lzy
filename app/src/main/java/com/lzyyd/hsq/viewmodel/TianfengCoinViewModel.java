package com.lzyyd.hsq.viewmodel;

import android.app.Application;

import com.lzyyd.hsq.data.DataRepository;
import com.lzyyd.hsq.http.callback.HttpResultCallBack;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.RxUtils;

/**
 * Create by liguo on 2020/8/31
 * Describe:
 */
public class TianfengCoinViewModel extends BaseViewModel<DataRepository> {

    public ObservableField<String> transferAmount = new ObservableField<>();
    public ObservableField<String> payPassword = new ObservableField<>();
    public ObservableField<String> tianfengbalance = new ObservableField<>();

    public ObservableField<String> amountPoint = new ObservableField<>();

    private OnTransferoutListener onTransferoutListener;

    public TianfengCoinViewModel(@NonNull Application application, DataRepository model) {
        super(application, model);
    }

    public void setListener(OnTransferoutListener onTransferoutListener){
        this.onTransferoutListener = onTransferoutListener;
    }

    public void clickTransferout(){
        onTransferoutListener.clickTransferout();
    }

    public void setTianfengbalance(String SessionId){

        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "GetBoosBalance");
        params.put("SessionId", SessionId);
        model.setTtianfeng(params)
                .compose(RxUtils.schedulersTransformer())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>(){
                    @Override
                    public void accept(Disposable disposable){
                        showDialog();
                    }
                })
                .subscribe(new HttpResultCallBack<Integer, String>() {
                    @Override
                    public void onResponse(Integer s, String status, String page) {
                        dismissDialog();
                        onTransferoutListener.getTianfengBalanceSuccess(s+"");
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        dismissDialog();
                        onTransferoutListener.getTianfengBalanceFail(msg);
                    }

                });
    }

    public void setTransferout(String BounsPrice,String PassWordTwo,String Brokerage,String type,String SessionId){

        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "BankCurrency");
        params.put("fun", "CurrencyBoosConvert");
        params.put("BounsPrice", BounsPrice);
        params.put("PassWordTwo", PassWordTwo);
        params.put("Brokerage", Brokerage);
        params.put("type", type);
        params.put("SessionId", SessionId);
        model.setTransferout(params)
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
                        dismissDialog();
                        onTransferoutListener.getTransferoutSuccess(s);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        dismissDialog();
                        onTransferoutListener.getTransferoutFail(msg);
                    }

                });
    }

    public interface OnTransferoutListener{
        public void getTransferoutSuccess(String msg);
        public void getTransferoutFail(String msg);

        public void getTianfengBalanceSuccess(String msg);
        public void getTianfengBalanceFail(String msg);

        public void clickTransferout();
    }


}
