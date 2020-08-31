package com.lzyyd.hsq.viewmodel;

import android.app.Application;
import android.content.DialogInterface;
import android.os.Bundle;

import com.lzyyd.hsq.activity.BalanceTransferOutActivity;
import com.lzyyd.hsq.activity.BindCardActivity;
import com.lzyyd.hsq.activity.GetCashActivity;
import com.lzyyd.hsq.activity.PointActivity;
import com.lzyyd.hsq.activity.RechargeActivity;
import com.lzyyd.hsq.activity.TianfengCoinActivity;
import com.lzyyd.hsq.activity.TransferoutActivity;
import com.lzyyd.hsq.activity.WalletActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.BalanceBean;
import com.lzyyd.hsq.bean.BalanceDetailBean;
import com.lzyyd.hsq.bean.UserBankBean;
import com.lzyyd.hsq.data.DataRepository;
import com.lzyyd.hsq.http.callback.HttpResultCallBack;
import com.lzyyd.hsq.util.UToast;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
    private String balance;
    private BalanceBean balanceBean;
    private Application application;

    public WalletViewModel(@NonNull Application application,  DataRepository model) {
        super(application,model);
        this.application = application;
    }

    public void setListener(OnWalletListener onWalletListener){
        this.onWalletListener = onWalletListener;
    }


    public void getTransferout(){
        if (balance != null && balance.trim().length() > 0) {
            Bundle bundle = new Bundle();
            bundle.putString("balance", balance);
            startActivity(TransferoutActivity.class, bundle, PointActivity.foresult);
        }
    }

    public void getReCharge(){
        if (balance != null && balance.trim().length() > 0) {
            startActivity(RechargeActivity.class, null, PointActivity.RECHARGEFORESULT);
        }
    }

    public void getJumpGetCash(){

        getBankCard(ProApplication.SESSIONID());

    }

    public void getJumpTianfeng(){
        Bundle bundle = new Bundle();
        bundle.putDouble("balance",balanceBean.getMoney2Balance());
        startActivity(TianfengCoinActivity.class,bundle, WalletActivity.TRANFERRESULT);
    }

    public void getJumpTranferout(){

        Bundle bundle = new Bundle();
        bundle.putDouble("balance",balanceBean.getMoney5Balance());
        startActivity(BalanceTransferOutActivity.class,bundle, WalletActivity.TRANFERRESULT);

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
                    public void onResponse(BalanceBean balanceBeans, String status, Object page) {
                        dismissDialog();
                        balance = balanceBeans.getMoney2Balance()+"";
                        balanceBean = balanceBeans;
                        onWalletListener.getBalanceSuccess(balanceBeans);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        dismissDialog();
                        onWalletListener.getBalanceFail(msg);
                    }
                });
    }


    public void getBankCard(String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "UserBaseBankGet");
        params.put("SessionId", SessionId);
        model.getBankCard(params)
                .compose(RxUtils.schedulersTransformer())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>(){
                    @Override
                    public void accept(Disposable disposable){
                        showDialog();
                    }
                })
                .subscribe(new HttpResultCallBack<UserBankBean,Object>() {
                    @Override
                    public void onResponse(UserBankBean bankBean, String status, Object page) {
                        dismissDialog();
                        if (bankBean.getBankNo() != null && bankBean.getBankNo().length() > 0){
                            if (balanceBean != null) {
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("balance",balanceBean);
                                bundle.putSerializable("bankBean",bankBean);
                                startActivity(GetCashActivity.class,bundle, WalletActivity.WALLRESULT);
                            }
                        }else {
                            UToast.show(application,"请绑定银行卡在提现");
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        dismissDialog();
                    }

                });
    }

    public void finished(){
        onWalletListener.finishForResult();
    }

    public interface OnWalletListener{
        public void getDataSuccess(ArrayList<BalanceDetailBean> balanceDetailBeans);
        public void getDataFail(String msg);

        public void getBalanceSuccess(BalanceBean balanceBean);
        public void getBalanceFail(String msg);

        public void finishForResult();


    }

}
