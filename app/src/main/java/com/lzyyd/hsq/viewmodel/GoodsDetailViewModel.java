package com.lzyyd.hsq.viewmodel;

import android.app.Application;
import android.os.Bundle;

import com.lzyyd.hsq.activity.ShoppingcartActivity;
import com.lzyyd.hsq.activity.StoreActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.GoodsChooseBean;
import com.lzyyd.hsq.bean.GoodsDetailInfoBean;
import com.lzyyd.hsq.data.DataRepository;
import com.lzyyd.hsq.http.callback.HttpResultCallBack;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.databinding.ObservableBoolean;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.RxUtils;

public class GoodsDetailViewModel extends BaseViewModel<DataRepository> {

    private Application application;
    private GoodsDetailDataCallBack goodsDetailDataCallBack;
    private String goodsId;
    private int storeId;

    public ObservableBoolean observableBoolean = new ObservableBoolean();
    public ObservableBoolean goodsBoolean = new ObservableBoolean(false);

    public GoodsDetailViewModel(Application application, DataRepository dataRepository) {
        super(application,dataRepository);
        this.application = application;
    }

    public void setClickCallBack(GoodsDetailDataCallBack goodsDetailDataCallBack){
        this.goodsDetailDataCallBack = goodsDetailDataCallBack;
    }

    public void setClickSpecifications(){
        goodsDetailDataCallBack.getClickPop();
    }


    public void setCollect(){
        if (observableBoolean.get()){
            deleteCollect(goodsId,"1", ProApplication.SESSIONID());
        }else {
            addGoodCollect(goodsId,"1",ProApplication.SESSIONID());
        }
    }

    public void setJumpStore(){
        Bundle bundle = new Bundle();
        bundle.putInt("storeId",storeId);
        startActivity(StoreActivity.class,bundle);
    }

    public void addGoodCollect(String OtherId, String CollectType, String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Collect");
        params.put("fun", "CollectCreate");
        params.put("OtherId", OtherId);
        params.put("CollectType", CollectType);
        params.put("SessionId", SessionId);
                model.addGoodCollect(params)
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
                    public void onResponse(String collectBean, String status, Object page) {
                        dismissDialog();
                        goodsDetailDataCallBack.addCollectSuccess(collectBean);
                        observableBoolean.set(true);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        dismissDialog();
                        goodsDetailDataCallBack.addCollectFail(msg);
                    }
                });
    }

    /**
     * 是否有收藏
     *
     * @param goodsId
     * @param SessionId
     */
    public void isCollect(String goodsId, String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Collect");
        params.put("fun", "IsCollect");
        params.put("CollectId", goodsId);
        params.put("SessionId", SessionId);
        model.DeleteCollectGood(params)
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
                    public void onResponse(String collectBean, String status, Object page) {
//                        selfGoodsDetailContract.addCollectSuccess(collectBean);
                        dismissDialog();
                        goodsDetailDataCallBack.isGoodsCollectSuccess(collectBean);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        dismissDialog();
//                        selfGoodsDetailContract.addCollectFail(msg);
                    }

                });
    }

    /**
     * 删除收藏
     *
     * @param SessionId
     */
    public void deleteCollect(String goodsId, String CollectType, String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Collect");
        params.put("fun", "CollectDeleteGoods");
        params.put("OtherId", goodsId);
        params.put("CollectType", CollectType);
        params.put("SessionId", SessionId);
        model.DeleteCollectGood(params)
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
                    public void onResponse(String collectBeans, String status, Object page) {
                        dismissDialog();
                        goodsDetailDataCallBack.deleteCollectSuccess(collectBeans);
                        observableBoolean.set(false);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        dismissDialog();
                    }
                });
    }


    public void getGoodsDetail(String GoodsId,String SessionId){
        this.goodsId = GoodsId;
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Goods");
        params.put("fun", "GoodsGet");
        params.put("GoodsId",GoodsId);
        params.put("SessionId",SessionId);
        model.getGoodsDetails(params)
                .compose(RxUtils.schedulersTransformer())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>(){
                    @Override
                    public void accept(Disposable disposable){
                        showDialog();
                    }
                })
                .subscribe(new HttpResultCallBack<GoodsDetailInfoBean<ArrayList<GoodsChooseBean>>,String>() {

                    @Override
                    public void onResponse(GoodsDetailInfoBean<ArrayList<GoodsChooseBean>> s, String status, String page) {
                        dismissDialog();
                        storeId = s.getStoreId();
                        goodsDetailDataCallBack.getDataSuccess(s);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        dismissDialog();
                        goodsDetailDataCallBack.getDataFail(msg);
                    }

                });

    }





    /**
     * 加入购物车
     *
     * @param GoodsId
     * @param AttrId
     * @param Num
     * @param SessionId
     */
    public void addCartinterf(String GoodsId, String AttrId, String Num, String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Cart");
        params.put("fun", "CartCreate");
        params.put("GoodsId", GoodsId);
        if (AttrId.equals("")){
            AttrId = "0";
        }
        params.put("attr_id", AttrId);
        params.put("Num", Num);
        params.put("SessionId", SessionId);
        model.addCart(params)
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
                    public void onResponse(String collectBeans, String status, Object page) {
                        dismissDialog();
                        goodsDetailDataCallBack.addCartSuccess(collectBeans);

                    }

                    @Override
                    public void onErr(String msg, String status) {
                        dismissDialog();
                        goodsDetailDataCallBack.addCartFail(msg);
                    }
                });
    }


    public void getKey(String GoodsId, String AttrId, String Num, String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "OrderInfo");
        params.put("fun", "BuyRedis");
        params.put("GoodsId", GoodsId);
        if (AttrId.equals("")){
            AttrId = "0";
        }
        params.put("attr_id", AttrId);
        params.put("Num", Num);
        params.put("SessionId", SessionId);
        model.addCart(params)
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
                    public void onResponse(String collectBeans, String status, Object page) {
                        dismissDialog();
                        goodsDetailDataCallBack.SureOrderSuccess(collectBeans);

                    }

                    @Override
                    public void onErr(String msg, String status) {
                        dismissDialog();
                        goodsDetailDataCallBack.SureOrderFail(msg);
                    }
                });
    }

    public void jumpShoppingCart(){
        startActivity(ShoppingcartActivity.class);
    }


    public void sureOrder() {
        goodsDetailDataCallBack.sureOrder();
    }

    public void addCart(){
        goodsDetailDataCallBack.addCart();
    }


    public interface GoodsDetailDataCallBack{
        public void getDataSuccess(GoodsDetailInfoBean<ArrayList<GoodsChooseBean>> goodsListBeans);
        public void getDataFail(String msg);
        public void getClickPop();
        public void addCartSuccess(String msg);
        public void addCartFail(String msg);
        public void SureOrderSuccess(String msg);
        public void SureOrderFail(String msg);
        public void sureOrder();
        public void addCart();
        public void addCollectSuccess(String collectBean);

        public void addCollectFail(String msg);

        public void isGoodsCollectSuccess(String msg);

        public void deleteCollectSuccess(String msg);
    }

}
