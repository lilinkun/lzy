package com.lzyyd.hsq.viewmodel;

import android.app.Application;
import android.os.Bundle;

import com.lzyyd.hsq.activity.GetCashActivity;
import com.lzyyd.hsq.activity.WalletActivity;
import com.lzyyd.hsq.bean.OrderDetailAddressBean;
import com.lzyyd.hsq.bean.UserBankBean;
import com.lzyyd.hsq.data.DataRepository;
import com.lzyyd.hsq.http.callback.HttpResultCallBack;
import com.lzyyd.hsq.util.UToast;

import java.util.HashMap;

import androidx.annotation.NonNull;
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
public class OrderDetailViewModel extends BaseViewModel<DataRepository> {

    private OrderDetailListener orderDetailListener;

    public OrderDetailViewModel(@NonNull Application application, DataRepository model) {
        super(application, model);
    }

    public void setLisener(OrderDetailListener orderDetailListener){
        this.orderDetailListener = orderDetailListener;
    }

    /**
     * 　订单详情
     *
     * @param OrderSn
     * @param SessionId
     */
    public void cartBuy(String OrderSn, String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "OrderInfo");
        params.put("fun", "OrderInfoGoodsDetail");
        params.put("OrderSn", OrderSn);
        params.put("SessionId", SessionId);
        model.getOrderDetail(params)
                .compose(RxUtils.schedulersTransformer())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>(){
                    @Override
                    public void accept(Disposable disposable){
                        showDialog();
                    }
                })
                .subscribe(new HttpResultCallBack<OrderDetailAddressBean, Object>() {
                    @Override
                    public void onResponse(OrderDetailAddressBean orderDetailBeans, String status, Object page) {
                        orderDetailListener.setDataSuccess(orderDetailBeans);
                        dismissDialog();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        orderDetailListener.setDataFail(msg);
                    }
                });


    }

    public void exitOrder(String OrderId, String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "OrderInfo");
        params.put("fun", "OrderInfoVIPCancel");
        params.put("OrderSn", OrderId);
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
                    public void onResponse(String collectDeleteBean, String status, String page) {
                        orderDetailListener.exitOrderSuccess(collectDeleteBean);
                        dismissDialog();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        orderDetailListener.exitOrderFail(msg);
                        dismissDialog();
                    }

                });
    }

    public void cancelOrder(String OrderId, String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "OrderInfo");
        params.put("fun", "OrderInfoVIPRefund");
        params.put("OrderSn", OrderId);
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
                    public void onResponse(String collectDeleteBean, String status, String page) {
                        orderDetailListener.cancelOrderSuccess(collectDeleteBean);
                        dismissDialog();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        orderDetailListener.cancelOrderFail(msg);
                        dismissDialog();
                    }

                });
    }


    /**
     * 确认收货
     */
    public void sureReceipt(String OrderId, String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "OrderInfo");
        params.put("fun", "OrderInfoVIPConfirm");
        params.put("OrderSn", OrderId);
        params.put("SessionId", SessionId);
        model.sureReceipt(params)
                .compose(RxUtils.schedulersTransformer())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>(){
                    @Override
                    public void accept(Disposable disposable){
                        showDialog();
                    }
                })
                .subscribe(new HttpResultCallBack<String, Object>() {
                    @Override
                    public void onResponse(String collectDeleteBean, String status, Object page) {
                        orderDetailListener.sureReceiptSuccess(collectDeleteBean);
                        dismissDialog();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        orderDetailListener.sureReceiptFail(msg);
                        dismissDialog();
                    }
                });
    }

    public interface OrderDetailListener{
        public void setDataSuccess(OrderDetailAddressBean orderDetailBeans);
        public void setDataFail(String msg);

        public void exitOrderSuccess(String collectDeleteBean);
        public void exitOrderFail(String msg);

        public void cancelOrderSuccess(String collectDeleteBean);
        public void cancelOrderFail(String msg);

        public void sureReceiptSuccess(String collectDeleteBean);
        public void sureReceiptFail(String msg);
    }

}
