package com.lzyyd.hsq.viewmodel;

import android.app.Application;
import android.view.View;

import com.lzyyd.hsq.activity.GoodsListActivity;
import com.lzyyd.hsq.bean.CartBean;
import com.lzyyd.hsq.bean.CartListBean;
import com.lzyyd.hsq.bean.CollectBean;
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
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.utils.RxUtils;

/**
 * Create by liguo on 2020/8/11
 * Describe:
 */
public class ShoppingcartViewModel extends BaseViewModel<DataRepository> {

    public ObservableField<Boolean> isGoodsField = new ObservableField<>(false);
    private CartCallback cartCallback;

    public ShoppingcartViewModel(Application application,DataRepository dataRepository){
        super(application,dataRepository);
    }

    public void setCallBack(CartCallback cartCallback){
        this.cartCallback = cartCallback;
    }


    public void setJumpGoodsDetail(){
        startActivity(GoodsListActivity.class);
    }

    /**
     * 获取购物车列表
     *
     * @param SessionId
     */
    public void getGoodsCartList(String SessionId) {
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
//                        showDialog();
                    }
                })
                .subscribe(new HttpResultCallBack<ArrayList<CartListBean<ArrayList<CartBean>>>, String>() {
                    @Override
                    public void onResponse(ArrayList<CartListBean<ArrayList<CartBean>>> storeCartBeans, String status, String page) {
//                        dismissDialog();
                        if (storeCartBeans.size() > 0){
                            isGoodsField.set(true);
                        }
                        cartCallback.getCartListSuccess(storeCartBeans);
                    }

                    @Override
                    public void onErr(String msg, String status) {
//                       dismissDialog();
                       cartCallback.getCartListFail(msg);
                    }
                });
    }

    /**
     * 修改购物车信息
     * @param Num
     * @param CartId
     * @param SessionId
     */
    public void modifyOrder(final int Num, String CartId,final View view, String SessionId,final int total){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","Cart");
        params.put("fun","CartUpdate");
        params.put("Num",Num+"");
        params.put("CartId",CartId);
        params.put("SessionId",SessionId);
        model.modifyOrder(params)
                .compose(RxUtils.schedulersTransformer())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<CollectBean,Object>() {
                    @Override
                    public void onResponse(CollectBean orderListBeans, String status,Object page) {
                        cartCallback.modifyOrderSuccess(orderListBeans,Num,view,total);
                        dismissDialog();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        dismissDialog();
                    }
                });
    }


    /**
     * 删除信息
     * @param CartId
     * @param SessionId
     */
    public void deleteOrder(String CartId,String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","Cart");
        params.put("fun","CartDelete");
        params.put("CartId",CartId);
        params.put("SessionId",SessionId);
        model.deleteGoods(params)
                .compose(RxUtils.schedulersTransformer())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>(){
                    @Override
                    public void accept(Disposable disposable){
                        showDialog();
                    }
                })
                .subscribe(new HttpResultCallBack<String,Object>() {
                    @Override
                    public void onResponse(String b, String status,Object page) {
                        dismissDialog();
                        cartCallback.deleteGoodsSuccess(b);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        dismissDialog();
                        cartCallback.deleteGoodsFail(msg);
                    }
                });
    }

    public void getKey(String CartId, String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "OrderInfo");
        params.put("fun", "CartSaveRedis");
        params.put("CartId", CartId);
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
                        cartCallback.SureOrderSuccess(collectBeans);

                    }

                    @Override
                    public void onErr(String msg, String status) {
                        dismissDialog();
                        cartCallback.SureOrderFail(msg);
                    }
                });
    }


    //全选按钮的点击事件
    public View.OnClickListener allClickCommand = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            cartCallback.allClick();
        }
    };

    public View.OnClickListener editCommand = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            cartCallback.editClick();
        }
    };

    public BindingCommand bindingCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            cartCallback.cartPay();
        }
    });

    public BindingCommand bindingDelCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            cartCallback.cartDel();
        }
    });



    public interface CartCallback{
        public void getCartListSuccess(ArrayList<CartListBean<ArrayList<CartBean>>> storeCartBeans);
        public void getCartListFail(String msg);
        public void SureOrderSuccess(String msg);
        public void SureOrderFail(String msg);
        public void deleteGoodsSuccess(String msg);
        public void deleteGoodsFail(String msg);
        public void allClick();
        public void editClick();
        public void modifyOrderSuccess(CollectBean collectBean, int num, View view,int total);
        public void cartPay();
        public void cartDel();
    }


}
