package com.lzyyd.hsq.viewmodel;

import android.app.Application;

import com.lzyyd.hsq.bean.GoodsListBean;
import com.lzyyd.hsq.bean.PageBean;
import com.lzyyd.hsq.data.DataRepository;
import com.lzyyd.hsq.http.callback.HttpResultCallBack;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.StringUtils;

/**
 * Create by liguo on 2020/9/2
 * Describe:
 */
public class StoreViewModel extends BaseViewModel<DataRepository> {

    private OnStoreListener onStoreListener;

    public ObservableField<String> imgField = new ObservableField<>();

    public StoreViewModel(@NonNull Application application, DataRepository model) {
        super(application, model);
    }

    public void setListener(OnStoreListener listener){
        this.onStoreListener = listener;
    }

    public void getSelfData(int PageIndex, int PageCount, String StoreId,String SessionId){

        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Goods");
        params.put("fun", "GoodsListVip");
        params.put("PageIndex",PageIndex+"");
        params.put("PageCount",PageCount+"");
        params.put("StoreId",StoreId+"");
        params.put("SessionId",SessionId);
        model.getGoodsListVip(params)
                .compose(RxUtils.schedulersTransformer())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>(){
                    @Override
                    public void accept(Disposable disposable){
                        showDialog();
                    }
                })
                .subscribe(new HttpResultCallBack<ArrayList<GoodsListBean>, PageBean>() {
                    @Override
                    public void onResponse(ArrayList<GoodsListBean> goodsListBeans, String status, PageBean page) {
                        onStoreListener.getDataSuccess(goodsListBeans);
                        dismissDialog();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        onStoreListener.getDataFail(msg);
                        dismissDialog();

                    }
                });

    }

    public interface OnStoreListener{
        public void getDataSuccess(ArrayList<GoodsListBean> goodsListBeans);
        public void getDataFail(String msg);
    }

}
