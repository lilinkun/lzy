package com.lzyyd.hsq.viewmodel;

import android.app.Application;
import android.os.Handler;
import android.view.View;

import com.lzyyd.hsq.bean.GoodsListBean;
import com.lzyyd.hsq.bean.HomeBean;
import com.lzyyd.hsq.bean.PageBean;
import com.lzyyd.hsq.data.DataRepository;
import com.lzyyd.hsq.http.callback.HttpResultCallBack;
import com.lzyyd.hsq.util.UToast;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.RxUtils;

/**
 * Create by liguo on &{DATE}
 * Describe:
 */
public class HomeFragmentViewModel extends BaseViewModel<DataRepository> {

    private Application application;
    private HomeDataCallBack homeDataCallBack;

    public HomeFragmentViewModel(@NonNull Application application,DataRepository dataRepository) {
        super(application,dataRepository);
        this.application = application;
    }

    public void setCallBack(HomeDataCallBack homeDataCallBack){
        this.homeDataCallBack = homeDataCallBack;
    }

    public void setImageClick(){
        UToast.show(application,"home.imageview");
    }

    public void getSelfData(int PageIndex, int PageCount, int GoodsType){

        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Goods");
        params.put("fun", "GoodsListVip");
        params.put("PageIndex",PageIndex+"");
        params.put("PageCount",PageCount+"");
        params.put("GoodsType",GoodsType+"");
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
                        homeDataCallBack.getDataSuccess(goodsListBeans, page);
                        dismissDialog();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        homeDataCallBack.getDataFail(msg);
                        dismissDialog();

                    }
                });

    }


    public void getHomeData(String SessionId){

        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Home");
        params.put("fun", "Mobile");
        params.put("SessionId",SessionId);
        model.getHomeData(params)
                .compose(RxUtils.schedulersTransformer())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>(){
                    @Override
                    public void accept(Disposable disposable){
                        showDialog();
                    }
                })
                .subscribe(new HttpResultCallBack<HomeBean, String>() {
                    @Override
                    public void onResponse(HomeBean homeBean, String status, String page) {
                        homeDataCallBack.getHomeDataSuccess(homeBean);
                        dismissDialog();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        homeDataCallBack.getHomeDataFail(msg);
                        dismissDialog();
                    }

                });
    }

    public void onClick(int position){
        homeDataCallBack.onHomeClick(position);
    }


    public interface HomeDataCallBack{
        public void getDataSuccess(ArrayList<GoodsListBean> goodsListBeans, PageBean pageBean);
        public void getDataFail(String msg);

        public void getHomeDataSuccess(HomeBean homeBean);
        public void getHomeDataFail(String msg);

        public void onHomeClick(int position);
    }

}