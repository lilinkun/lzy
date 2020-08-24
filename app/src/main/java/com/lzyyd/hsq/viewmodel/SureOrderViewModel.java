package com.lzyyd.hsq.viewmodel;

import android.app.Application;

import com.lzyyd.hsq.bean.OrderinfoBean;
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
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.RxUtils;

/**
 * Create by liguo on 2020/7/21
 * Describe:
 */
public class SureOrderViewModel extends BaseViewModel<DataRepository> {

    public ObservableField<String> priceField = new ObservableField<>();
    private SureOrderCallBack sureOrderCallBack;

    public SureOrderViewModel(@NonNull Application application,DataRepository dataRepository) {
        super(application,dataRepository);
    }

    public void setOnClick(SureOrderCallBack sureOrderCallBack){
        this.sureOrderCallBack = sureOrderCallBack;
    }


    public void getData(String Key,String AddressID,String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "OrderInfo");
        params.put("fun", "OrderInfoBuyGet");
        params.put("Key", Key);
        params.put("AddressID", AddressID);
        params.put("SessionId", SessionId);
        model.OrderInfoBuyGet(params)
                .compose(RxUtils.schedulersTransformer())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>(){
                    @Override
                    public void accept(Disposable disposable){
                        showDialog();
                    }
                })
                .subscribe(new HttpResultCallBack<OrderinfoBean, String>() {
                    @Override
                    public void onResponse(OrderinfoBean addressBeans, String status, String page) {
                        dismissDialog();
                        sureOrderCallBack.getOrderInfoSuccess(addressBeans);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        dismissDialog();
                        sureOrderCallBack.getOrderInfoFail(msg);
                    }

                });
    }

    //确认订单
    public void sureOrder(String Key,String OrderInfo,String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "OrderInfo");
        params.put("fun", "OrderInfo_Add");
        params.put("Key", Key);
        params.put("OrderInfo", OrderInfo);
        params.put("SessionId", SessionId);
        model.OrderSaveRedis(params)
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
                    public void onResponse(String addressBeans, String status, String page) {
                        dismissDialog();
                        sureOrderCallBack.getOrderSuccess(addressBeans);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        dismissDialog();
                        sureOrderCallBack.getOrderFail(msg);
                    }

                });
    }

    public BindingCommand  SureOrderOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            sureOrderCallBack.orderClick();
        }
    });


    public interface SureOrderCallBack{
        public void getOrderInfoSuccess(OrderinfoBean addressBeans);
        public void getOrderInfoFail(String msg);
        public void getOrderSuccess(String msg);
        public void getOrderFail(String msg);
        public void orderClick();

    }
}
