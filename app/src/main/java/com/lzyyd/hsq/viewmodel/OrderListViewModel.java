package com.lzyyd.hsq.viewmodel;

import android.app.Application;

import com.lzyyd.hsq.bean.GoodsChooseBean;
import com.lzyyd.hsq.bean.GoodsDetailInfoBean;
import com.lzyyd.hsq.bean.OrderBean;
import com.lzyyd.hsq.bean.OrderListBean;
import com.lzyyd.hsq.bean.PageBean;
import com.lzyyd.hsq.data.DataRepository;
import com.lzyyd.hsq.http.callback.HttpResultCallBack;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.RxUtils;

/**
 * Create by liguo on 2020/8/12
 * Describe:
 */
public class OrderListViewModel extends BaseViewModel<DataRepository> {

    private OnGetDataCallback onGetDataCallback;

    public OrderListViewModel(Application application,DataRepository dataRepository){
        super(application,dataRepository);
    }

    public void setListener(OnGetDataCallback onGetDataCallback){
        this.onGetDataCallback = onGetDataCallback;
    }

    public void getOrderData(String PageIndex, String PageCount, String OrderStatus, String SessionId) {

        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "OrderInfo");
        params.put("fun", "OrderInfoVipList");
        params.put("PageIndex", PageIndex);
        params.put("PageCount", PageCount);
        params.put("OrderStatus", OrderStatus);
        params.put("SessionId", SessionId);
        model.getOrderList(params)
                .compose(RxUtils.schedulersTransformer())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>(){
                    @Override
                    public void accept(Disposable disposable){
                        showDialog();
                    }
                })
                .subscribe(new HttpResultCallBack<ArrayList<OrderListBean>, PageBean>() {


                    @Override
                    public void onResponse(ArrayList<OrderListBean> s, String status, PageBean page) {
                        dismissDialog();
                        onGetDataCallback.getDataSuccess(s,page);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        dismissDialog();
                        onGetDataCallback.getDataFail(msg);
                    }

                });
    }

    public void sureReceipt(String OrderId,String SessionId){
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
                .subscribe(new HttpResultCallBack<String, String>() {
                    @Override
                    public void onResponse(String s, String status, String page) {
                        onGetDataCallback.sureReceiptSuccess(s);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        onGetDataCallback.sureReceiptFail(msg);
                    }
                });
    }

    public interface OnGetDataCallback{
        public void getDataSuccess(ArrayList<OrderListBean> orderListBeans,PageBean pageBean);
        public void getDataFail(String msg);

        public void sureReceiptSuccess(String msg);
        public void sureReceiptFail(String msg);
    }
}
