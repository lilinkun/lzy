package com.lzyyd.hsq.viewmodel;

import android.app.Application;
import android.os.Bundle;

import com.lzyyd.hsq.activity.AddressListActivity;
import com.lzyyd.hsq.bean.GoodsChooseBean;
import com.lzyyd.hsq.bean.GoodsDetailInfoBean;
import com.lzyyd.hsq.data.DataRepository;
import com.lzyyd.hsq.http.callback.HttpResultCallBack;
import com.lzyyd.hsq.util.HsqAppUtil;

import java.util.ArrayList;
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
 * Create by liguo on 2020/9/1
 * Describe:
 */
public class TakeGoodsViewModel extends BaseViewModel<DataRepository> {

    private OnTakeGoodsListener onTakeGoodsListener;
    public ObservableField<String> usernameField = new ObservableField<>();
    public ObservableField<Integer> numField = new ObservableField<>();
    public ObservableField<Integer> reduceField = new ObservableField<>();
    public ObservableField<String> psdField = new ObservableField<>();

    public TakeGoodsViewModel(@NonNull Application application, DataRepository model) {
        super(application, model);
    }

    public void setListener(OnTakeGoodsListener onTakeGoodsListener){
        this.onTakeGoodsListener = onTakeGoodsListener;
    }


    public BindingCommand bindingCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            onTakeGoodsListener.onTakeGoods();
        }
    });

    public void onChooseAddress(){
        Bundle bundle = new Bundle();
        bundle.putString(HsqAppUtil.ADDRESS,"ADDRESS");
        startActivity(AddressListActivity.class,bundle,0x212);
    }


    public void getTakeGoods(String PassWordTwo,String GoodsId,String ShippingFree,String OrderAmount,String goodsNum,String AddressId,String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "OrderInfo");
        params.put("fun", "UserBasePick");
        params.put("PassWordTwo",PassWordTwo);
        params.put("ShippingFree",ShippingFree);
        params.put("OrderAmount",OrderAmount);
        params.put("GoodsId",GoodsId);
        params.put("goodsNum",goodsNum);
        params.put("AddressId",AddressId);
        params.put("SessionId",SessionId);
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
                    public void onResponse(String s, String status, String page) {
                        dismissDialog();
                        onTakeGoodsListener.getTakeGoodsSuccess(s);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        dismissDialog();
                        onTakeGoodsListener.getTakeGoodsFail(msg);
                    }

                });

    }


    public void getGoodsDetail(String GoodsId,String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Goods");
        params.put("fun", "GoodsGet");
        params.put("GoodsId",GoodsId);
        params.put("SessionId",SessionId);
        model.getGoodsDetails(params)
                .compose(RxUtils.schedulersTransformer())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>(){
                    @Override
                    public void accept(Disposable disposable){
                        showDialog();
                    }
                })
                .subscribe(new HttpResultCallBack<GoodsDetailInfoBean<ArrayList<GoodsChooseBean>>,String>() {

                    @Override
                    public void onResponse(GoodsDetailInfoBean<ArrayList<GoodsChooseBean>> s, String status, String page) {
                        dismissDialog();
                        onTakeGoodsListener.getDataSuccess(s);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        dismissDialog();
                        onTakeGoodsListener.getDataFail(msg);
                    }

                });

    }


    public interface OnTakeGoodsListener{
        public void onTakeGoods();

        public void getTakeGoodsSuccess(String msg);
        public void getTakeGoodsFail(String msg);

        public void getDataSuccess(GoodsDetailInfoBean<ArrayList<GoodsChooseBean>> goodsListBeans);
        public void getDataFail(String msg);
    }

}
