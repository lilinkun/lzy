package com.lzyyd.hsq.viewmodel;

import android.app.Application;

import com.lzyyd.hsq.bean.CategoryBean;
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
import me.goldze.mvvmhabit.utils.StringUtils;

/**
 * Create by liguo on 2020/8/24
 * Describe:
 */
public class ChuangkeViewModel extends BaseViewModel<DataRepository> {

    private ChuangkeDataCallBack chuangkeDataCallBack;
    private ChuangkeCategoryDateCallBack chuangkeCategoryDateCallBack;

    public ChuangkeViewModel(@NonNull Application application,DataRepository dataRepository) {
        super(application,dataRepository);
    }

    public void setChuangkeCategoryDateCallBack(ChuangkeCategoryDateCallBack chuangkeCategoryDateCallBack){
        this.chuangkeCategoryDateCallBack = chuangkeCategoryDateCallBack;
    }

    public void getCategoryData(int PageIndex, int PageCount){

        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Category");
        params.put("fun", "CategoryVipList");
        params.put("PageIndex",PageIndex+"");
        params.put("PageCount",PageCount+"");
        model.getCategoryListVip(params)
                .compose(RxUtils.schedulersTransformer())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>(){
                    @Override
                    public void accept(Disposable disposable){
                        showDialog();
                    }
                })
                .subscribe(new HttpResultCallBack<ArrayList<CategoryBean>, PageBean>() {
                    @Override
                    public void onResponse(ArrayList<CategoryBean> goodsListBeans, String status, PageBean page) {
                        chuangkeCategoryDateCallBack.getCategorySuccess(goodsListBeans, page);
                        dismissDialog();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        chuangkeCategoryDateCallBack.getCategoryFail(msg);
                        dismissDialog();

                    }
                });

    }

    public void setChuangkeDataCallBack(ChuangkeDataCallBack chuangkeDataCallBack){
        this.chuangkeDataCallBack = chuangkeDataCallBack;
    }


    public void getSelfData(int PageIndex, int PageCount, int GoodsType,String CategoryId){

        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Goods");
        params.put("fun", "GoodsListVip");
        params.put("PageIndex",PageIndex+"");
        params.put("PageCount",PageCount+"");
        params.put("GoodsType",GoodsType+"");
        if (CategoryId != null && StringUtils.isEmpty(CategoryId)) {
            params.put("CategoryId", CategoryId + "");
        }
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
                        chuangkeDataCallBack.getDataSuccess(goodsListBeans, page);
                        dismissDialog();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        chuangkeDataCallBack.getDataFail(msg);
                        dismissDialog();

                    }
                });

    }

    public interface ChuangkeDataCallBack{

        public void getDataSuccess(ArrayList<GoodsListBean> goodsListBeans, PageBean page);
        public void getDataFail(String msg);

    }

    public interface ChuangkeCategoryDateCallBack{
        public void getCategorySuccess(ArrayList<CategoryBean> goodsListBeans, PageBean page);
        public void getCategoryFail(String msg);
    }

}
