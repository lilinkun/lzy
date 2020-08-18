package com.lzyyd.hsq.viewmodel;


import android.app.Application;
import android.os.Bundle;

import com.lzyyd.hsq.activity.AddAddressActivity;
import com.lzyyd.hsq.bean.AddressBean;
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
 * Create by liguo on 2020/8/17
 * Describe:
 */
public class AddressListViewModel extends BaseViewModel<DataRepository> {

    public AddressListViewModel(Application application,DataRepository dataRepository){

        super(application,dataRepository);

    }


    public void getDatalist(String PageIndex,String PageCount,String SessionId,AddressCallBack addressCallBack){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "ReceiptAddress");
        params.put("fun", "ReceiptAddressList");
        params.put("PageIndex", PageIndex);
        params.put("PageCount", PageCount);
        params.put("SessionId", SessionId);
        model.getAddressListData(params)
                .compose(RxUtils.schedulersTransformer())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>(){
                    @Override
                    public void accept(Disposable disposable){
                        showDialog();
                    }
                })
                .subscribe(new HttpResultCallBack<ArrayList<AddressBean>, PageBean>() {
                    @Override
                    public void onResponse(ArrayList<AddressBean> addressBeans, String status, PageBean page) {
                        dismissDialog();
                        addressCallBack.getAddressSuccess(addressBeans);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        dismissDialog();
                        addressCallBack.getAddressFail(msg);
                    }

                });
    }


    public interface AddressCallBack{
        public void getAddressSuccess(ArrayList<AddressBean> addressBeans);
        public void getAddressFail(String msg);
    }


    public void onJumpAddAddress(){
        startActivity(AddAddressActivity.class,null,0x123);
    }



}
