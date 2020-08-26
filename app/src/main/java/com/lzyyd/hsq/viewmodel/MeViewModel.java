package com.lzyyd.hsq.viewmodel;

import android.app.Application;
import android.os.Bundle;

import com.lzyyd.hsq.activity.AddressListActivity;
import com.lzyyd.hsq.activity.CollectGoodsActivity;
import com.lzyyd.hsq.activity.GetCashActivity;
import com.lzyyd.hsq.activity.MyQrcodeActivity;
import com.lzyyd.hsq.activity.OrderListActivity;
import com.lzyyd.hsq.activity.PersonalInfoActivity;
import com.lzyyd.hsq.activity.RechargeActivity;
import com.lzyyd.hsq.activity.VipActivity;
import com.lzyyd.hsq.activity.WalletActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.BalanceBean;
import com.lzyyd.hsq.data.DataRepository;
import com.lzyyd.hsq.http.callback.HttpResultCallBack;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.RxUtils;

/**
 * Create by liguo on 2020/8/6
 * Describe:
 */
public class MeViewModel extends BaseViewModel<DataRepository> {

    private MeBackCall meBackCall;
    public ObservableField<Integer> OrderAllNum = new ObservableField<>();
    public ObservableField<Integer> ccqField = new ObservableField<>(ProApplication.CCQTYPE);

    public MeViewModel(@NonNull Application application, DataRepository model) {
        super(application, model);

    }

    public void setListener(MeBackCall meBackCall){
        this.meBackCall = meBackCall;
    }

    public void setJumpPersonal(){
        startActivity(PersonalInfoActivity.class);
    }


    public void setJumpWallet(){
        startActivity(WalletActivity.class);
    }

    public void setJumpCollect(){
        startActivity(CollectGoodsActivity.class);
    }

    public void setJumpGetCash(){
        startActivity(GetCashActivity.class);
    }

    public void setJumpReCharge(){
        startActivity(RechargeActivity.class);
    }

    public void setJumpVip(){
        startActivity(VipActivity.class);
    }

    public void setJumpOrderlist(int position){
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        startActivity(OrderListActivity.class,bundle);
    }

    public void setJumpQrcode(){
        startActivity(MyQrcodeActivity.class);
    }


    public void setJumpAddress(){
        startActivity(AddressListActivity.class);
    }


    public void getBalance(String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "BankBase_GetBalance");
        params.put("SessionId", SessionId);
        model.getBalance(params)
                .compose(RxUtils.schedulersTransformer())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>(){
                    @Override
                    public void accept(Disposable disposable){
                        showDialog();
                    }
                })
                .subscribe(new HttpResultCallBack<BalanceBean, Object>() {
                    @Override
                    public void onResponse(BalanceBean balanceBean, String status, Object page) {
                        dismissDialog();
                        meBackCall.getBalanceSuccess(balanceBean);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        dismissDialog();
                        meBackCall.getBalanceFail(msg);
                    }
                });
    }


    public interface MeBackCall{
        public void getBalanceSuccess(BalanceBean balanceBean);
        public void getBalanceFail(String msg);
    }
}
