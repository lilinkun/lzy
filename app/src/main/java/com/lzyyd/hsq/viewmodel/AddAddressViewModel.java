package com.lzyyd.hsq.viewmodel;

import android.app.Application;

import com.lzyyd.hsq.bean.AddressBean;
import com.lzyyd.hsq.bean.LocalBean;
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
public class AddAddressViewModel extends BaseViewModel<DataRepository> {

    private GetLocalData getLocalData;

    public AddAddressViewModel(Application application,DataRepository dataRepository){

        super(application,dataRepository);

    }

    public void setLocalCallBack(GetLocalData getLocalData){
        this.getLocalData = getLocalData;
    }


    public void getLocalData(String parentId, final int localType) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Region");
        params.put("fun", "RegionListDrop");
        params.put("ParentId", parentId);
        model.getLocalData(params)
                .compose(RxUtils.schedulersTransformer())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>(){
                    @Override
                    public void accept(Disposable disposable){
                        showDialog();
                    }
                })
                .subscribe(new HttpResultCallBack<ArrayList<LocalBean>, Object>() {
                    @Override
                    public void onResponse(ArrayList<LocalBean> provinceBeans, String status, Object page) {
                        dismissDialog();
                        getLocalData.getDataSuccess(provinceBeans, localType);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        dismissDialog();
                        getLocalData.getDataFail(msg);
                    }
                });
    }

    public void getSaveAddress(String Name, String Province, String City, String District, String Address, String ZipCode, String Mobile, String IsDefault, String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "ReceiptAddress");
        params.put("fun", "ReceiptAddressCreate");
        params.put("Name", Name);
        params.put("prov", Province);
        params.put("city", City);
        params.put("area", District);
        params.put("Address", Address);
        params.put("Post", ZipCode);
        params.put("Mobile", Mobile);
        params.put("IsDefault", IsDefault);
        params.put("SessionId", SessionId);
            model.getSaveAddress(params)
                .compose(RxUtils.schedulersTransformer())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<AddressBean,String>() {

                    @Override
                    public void onResponse(AddressBean o, String status, String page) {
                        getLocalData.getSaveSuccess();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        getLocalData.getSaveFail(msg);
                    }
                });
    }

    /**
     * 修改地址
     */
    public void modifyAddress(String AddressId, String Consignee, String Province, String City, String District, String Address, String ZipCode, String Mobile, String IsDefault, String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "ReceiptAddress");
        params.put("fun", "ReceiptAddressUpdate");
        params.put("Name", Consignee);
        params.put("AddressID", AddressId);
        params.put("prov", Province);
        params.put("city", City);
        params.put("area", District);
        params.put("Address", Address);
        params.put("Post", ZipCode);
        params.put("Mobile", Mobile);
        params.put("IsDefault", IsDefault);
        params.put("SessionId", SessionId);
            model.getSaveAddress(params)
                .compose(RxUtils.schedulersTransformer())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack() {

                    @Override
                    public void onResponse(Object o, String status, Object page) {
                        getLocalData.modifySuccess();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        getLocalData.modifyFail(msg);
                    }
                });
    }



    public interface GetLocalData{
        public void getDataSuccess(ArrayList<LocalBean> provinceBeans,int localType);
        public void getDataFail(String msg);

        public void getSaveSuccess();
        public void getSaveFail(String msg);

        public void modifySuccess();
        public void modifyFail(String msg);
    }
}
