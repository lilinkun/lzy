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

public class GoodsListViewModel extends BaseViewModel<DataRepository> {
    private GetGoodsListCallBack getGoodsListCallBack;

    public GoodsListViewModel(@NonNull Application application,DataRepository dataRepository) {
        super(application,dataRepository);
    }

    public void setListener(GetGoodsListCallBack getGoodsListCallBack){
        this.getGoodsListCallBack = getGoodsListCallBack;
    }

    public void getGoodsListData(int PageIndex, int PageCount, int GoodsType,String OrderBy,String SessionId){

        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Goods");
        params.put("fun", "GoodsListGuest");
        params.put("PageIndex",PageIndex+"");
        params.put("PageCount",PageCount+"");
        params.put("GoodsType",GoodsType+"");
        params.put("OrderBy",OrderBy);
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
                        getGoodsListCallBack.getDataSuccess(goodsListBeans, page);
                        dismissDialog();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        getGoodsListCallBack.getDataFail(msg);
                        dismissDialog();

                    }
                });

    }

    public interface GetGoodsListCallBack{
        public void getDataSuccess(ArrayList<GoodsListBean> goodsListBeans,PageBean page);
        public void getDataFail(String msg);
    }

}
