package com.lzyyd.hsq.viewmodel;

import android.app.Application;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.bean.CartBean;
import com.lzyyd.hsq.bean.CartListBean;
import com.lzyyd.hsq.bean.PageBean;
import com.lzyyd.hsq.bean.StoreCartBean;
import com.lzyyd.hsq.data.DataRepository;
import com.lzyyd.hsq.http.callback.HttpResultCallBack;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.databinding.ObservableField;
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
public class ShoppingcartViewModel extends BaseViewModel<DataRepository> {

    public ObservableField<Boolean> isGoodsField = new ObservableField<>(false);


    public ShoppingcartViewModel(Application application,DataRepository dataRepository){
        super(application,dataRepository);
    }


    /**
     * 获取购物车列表
     *
     * @param SessionId
     */
    public void getGoodsCartList(String SessionId,final CartCallback cartCallback) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Cart");
        params.put("fun", "Cart_GetLitsByUserId");
        params.put("SessionId", SessionId);
        model.getCartList(params)
                .compose(RxUtils.schedulersTransformer())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>(){
                    @Override
                    public void accept(Disposable disposable){
                        showDialog();
                    }
                })
                .subscribe(new HttpResultCallBack<ArrayList<CartListBean<ArrayList<CartBean>>>, String>() {
                    @Override
                    public void onResponse(ArrayList<CartListBean<ArrayList<CartBean>>> storeCartBeans, String status, String page) {
                        dismissDialog();
                        if (storeCartBeans.size() > 0){
                            isGoodsField.set(true);
                        }
                        cartCallback.getCartListSuccess(storeCartBeans);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                       dismissDialog();
                       cartCallback.getCartListFail(msg);
                    }
                });
    }


    public interface CartCallback{
        public void getCartListSuccess(ArrayList<CartListBean<ArrayList<CartBean>>> storeCartBeans);
        public void getCartListFail(String msg);
    }


}
