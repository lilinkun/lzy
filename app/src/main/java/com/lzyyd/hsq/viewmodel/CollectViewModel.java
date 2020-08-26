package com.lzyyd.hsq.viewmodel;

import android.app.Application;

import com.lzyyd.hsq.bean.CollectListBean;
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
 * Create by liguo on 2020/8/11
 * Describe:
 */
public class CollectViewModel extends BaseViewModel<DataRepository> {

    private CollectGoodsListener listener;

    public CollectViewModel(Application application,DataRepository dataRepository){
        super(application,dataRepository);
    }

    public void setListener(CollectGoodsListener listener){
        this.listener = listener;
    }

    public void getCollectDataList(String PageIndex, String PageCount, String CollectType, String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Collect");
        params.put("fun", "CollectList");
        params.put("PageIndex", PageIndex);
        params.put("PageCount", PageCount);
        params.put("CollectType", CollectType);
        params.put("SessionId", SessionId);
        model.GoodCollectList(params)
                .compose(RxUtils.schedulersTransformer())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>(){
                    @Override
                    public void accept(Disposable disposable){
                        showDialog();
                    }
                })
                .subscribe(new HttpResultCallBack<ArrayList<CollectListBean>, Object>() {

                    @Override
                    public void onResponse(ArrayList<CollectListBean> collectBean, String status, Object page) {
                        dismissDialog();
                        listener.getCollectDataSuccess(collectBean);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        dismissDialog();
                        listener.getCollectFail(msg);
                    }
                });
    }

    public interface CollectGoodsListener{

        public void getCollectDataSuccess(ArrayList<CollectListBean> msg);
        public void getCollectFail(String msg);


    }
}
