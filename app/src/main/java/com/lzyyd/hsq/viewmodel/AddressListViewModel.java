package com.lzyyd.hsq.viewmodel;


import android.app.Application;
import android.os.Bundle;

import com.lzyyd.hsq.activity.AddAddressActivity;
import com.lzyyd.hsq.activity.AddressListActivity;
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

    private AddressCallBack addressCallBack;

    public AddressListViewModel(Application application,DataRepository dataRepository){

        super(application,dataRepository);

    }

    public void setListener(AddressCallBack addressCallBack){
        this.addressCallBack = addressCallBack;
    }


    public void isDefault(String UserAddressId, String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "ReceiptAddress");
        params.put("fun", "ReceiptAddressSetDefault");
        params.put("AddressID", UserAddressId);
        params.put("SessionId", SessionId);
        model.isDefault(params)
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
                    public void onResponse(String s, String status, Object page) {
                        dismissDialog();
                        addressCallBack.isDefaultSuccess(s);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        dismissDialog();
                        addressCallBack.isDefaultFail(msg);
                    }

                });
    }

    /**
     * 删除地址
     *
     * @param userAddressId
     */
    public void deletAddress(String userAddressId, String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "ReceiptAddress");
        params.put("fun", "ReceiptAddressDelete");
        params.put("AddressID", userAddressId);
        params.put("SessionId", SessionId);

        model.isDefault(params)
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
                    public void onResponse(String s, String status, Object page) {
                        dismissDialog();
                        addressCallBack.deleteSuccess(s);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        dismissDialog();
                        addressCallBack.deleteFail(msg);
                    }

                });
    }


    public void getDatalist(String PageIndex,String PageCount,String SessionId){
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

        public void isDefaultSuccess(String isDefaultStr);
        public void isDefaultFail(String msg);


        public void deleteSuccess(String s);
        public void deleteFail(String msg);
    }


    public void onJumpAddAddress(){
        startActivity(AddAddressActivity.class,null,0x1231);
    }



}
