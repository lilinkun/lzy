package com.lzyyd.hsq.viewmodel;

import android.app.Application;

import com.lzyyd.hsq.bean.BalanceDetailBean;
import com.lzyyd.hsq.data.DataRepository;
import com.lzyyd.hsq.http.callback.HttpResultCallBack;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.RxUtils;

/**
 * Create by liguo on 2020/8/10
 * Describe:
 */
public class WalletViewModel extends BaseViewModel<DataRepository> {

    private OnWalletListener onWalletListener;

    public WalletViewModel(@NonNull Application application,  DataRepository model) {
        super(application,model);
    }

    public void setListener(OnWalletListener onWalletListener){
        this.onWalletListener = onWalletListener;
    }


    public void getPriceData(String PageIndex, String PageCount, String type, String SessionId) {
        HashMap<String, String> params = new HashMap<>();
            params.put("cls", "BankCurrency");
            params.put("fun", "CurrencyList");
            params.put("PageIndex", PageIndex);
            params.put("PageCount", PageCount);
            params.put("type", type);
            params.put("SessionId", SessionId);
            model.getAmountPrice(params)
                .compose(RxUtils.schedulersTransformer())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>(){
                    @Override
                    public void accept(Disposable disposable){
                                showDialog();
                            }
                        })
                .subscribe(new HttpResultCallBack<ArrayList<BalanceDetailBean>, Object>() {
                    @Override
                    public void onResponse(ArrayList<BalanceDetailBean> balanceDetailBeans, String status, Object page) {
                        onWalletListener.getDataSuccess(balanceDetailBeans);
                        dismissDialog();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        onWalletListener.getDataFail(msg);
                        dismissDialog();
                    }
                });
    }

    public interface OnWalletListener{
        public void getDataSuccess(ArrayList<BalanceDetailBean> balanceDetailBeans);
        public void getDataFail(String msg);
    }

}
