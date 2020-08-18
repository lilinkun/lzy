package com.lzyyd.hsq.viewmodel;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.lzyyd.hsq.activity.ShoppingcartActivity;
import com.lzyyd.hsq.bean.GoodsBean;
import com.lzyyd.hsq.bean.GoodsChooseBean;
import com.lzyyd.hsq.bean.GoodsDetailBean;
import com.lzyyd.hsq.bean.GoodsDetailInfoBean;
import com.lzyyd.hsq.bean.GoodsListBean;
import com.lzyyd.hsq.bean.ResultBean;
import com.lzyyd.hsq.data.DataRepository;
import com.lzyyd.hsq.http.callback.HttpResultCallBack;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.tatarka.bindingcollectionadapter2.BR;

public class GoodsDetailViewModel extends BaseViewModel<DataRepository> {

    private Application application;
    private GoodsDetailDataCallBack goodsDetailDataCallBack;

    public ObservableBoolean observableBoolean = new ObservableBoolean();

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
        addSubscribe(model.login()
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(new Consumer<Disposable>(){
                    @Override
                    public void accept(Disposable disposable){
                        showDialog();
                    }
                })
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        dismissDialog();
                        observableBoolean.set(!observableBoolean.get());
                    }
                }));
    }

    public void getGoodsDetail(String GoodsId,String SessionId){
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

    public void jumpShoppingCart(){
        startActivity(ShoppingcartActivity.class);
    }


    public void sureOrder() {
        goodsDetailDataCallBack.sureOrder();
    }


    public interface GoodsDetailDataCallBack{
        public void getDataSuccess(GoodsDetailInfoBean<ArrayList<GoodsChooseBean>> goodsListBeans);
        public void getDataFail(String msg);
        public void getClickPop();
        public void addCartSuccess(String msg);
        public void addCartFail(String msg);
        public void sureOrder();
    }

}
