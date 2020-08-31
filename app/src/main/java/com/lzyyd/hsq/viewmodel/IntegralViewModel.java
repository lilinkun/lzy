package com.lzyyd.hsq.viewmodel;

import android.app.Application;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.lzyyd.hsq.activity.IntegralListActivity;
import com.lzyyd.hsq.bean.GoodsListBean;
import com.lzyyd.hsq.bean.PageBean;
import com.lzyyd.hsq.data.DataRepository;
import com.lzyyd.hsq.http.callback.HttpResultCallBack;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.RxUtils;

/**
 * Create by liguo on 2020/7/20
 * Describe:
 */
public class IntegralViewModel extends BaseViewModel<DataRepository> {

    private GetGoodsListCallBack getGoodsListCallBack;

    public IntegralViewModel(@NonNull Application application, DataRepository repository) {
        super(application,repository);
    }

    public void setListener(GetGoodsListCallBack listener){
        this.getGoodsListCallBack = listener;
    }

    public void getGoodsListData(int PageIndex, int PageCount, int GoodsType,String SessionId){

        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Goods");
        params.put("fun", "GoodsListGuest");
        params.put("PageIndex",PageIndex+"");
        params.put("PageCount",PageCount+"");
        params.put("GoodsType",GoodsType+"");
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


    public void integralList(){
        startActivity(IntegralListActivity.class);
    }

    public interface GetGoodsListCallBack{
        public void getDataSuccess(ArrayList<GoodsListBean> goodsListBeans,PageBean page);
        public void getDataFail(String msg);
    }

}
