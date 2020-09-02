package com.lzyyd.hsq.viewmodel;

import android.app.Application;

import com.lzyyd.hsq.bean.GoodsListBean;
import com.lzyyd.hsq.bean.PageBean;
import com.lzyyd.hsq.data.DataRepository;
import com.lzyyd.hsq.http.callback.HttpResultCallBack;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.RxUtils;

/**
 * Create by liguo on 2020/9/2
 * Describe:
 */
public class SearchViewModel extends BaseViewModel<DataRepository> {

    private OnDataListener onDataListener;

    public SearchViewModel(@NonNull Application application, DataRepository model) {
        super(application, model);
    }

    public void setListener(OnDataListener onDataListener){
        this.onDataListener = onDataListener;
    }

    public void getData(String PageIndex, String PageCount, String GoodsType,String OrderBy, String GoodsName,String SessionId){

        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Goods");
        params.put("fun", "GoodsListVip");
        params.put("PageIndex", PageIndex);
        params.put("PageCount", PageCount);
        params.put("GoodsName", GoodsName);
        params.put("GoodsFlag", "2");
        if (!GoodsType.equals("")) {
            params.put("GoodsType", GoodsType);
        }
        params.put("OrderBy", OrderBy);
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
                        onDataListener.getDataSuccess(goodsListBeans);
                        dismissDialog();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        onDataListener.getDataFail(msg);
                        dismissDialog();

                    }
                });

    }

    public interface OnDataListener{
        public void getDataSuccess(ArrayList<GoodsListBean> goodsListBeans);
        public void getDataFail(String msg);
    }

}
