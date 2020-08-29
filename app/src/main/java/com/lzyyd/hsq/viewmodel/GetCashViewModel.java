package com.lzyyd.hsq.viewmodel;

import android.app.Application;

import com.lzyyd.hsq.data.DataRepository;
import com.lzyyd.hsq.http.callback.HttpResultCallBack;

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
public class GetCashViewModel extends BaseViewModel<DataRepository> {

    private OnCashListener onCashListener;
    public ObservableField<String> inputAmount = new ObservableField<>();

    public GetCashViewModel(Application application,DataRepository dataRepository){
        super(application,dataRepository);
    }

    public void setListener(OnCashListener onCashListener){
        this.onCashListener = onCashListener;
    }

    public BindingCommand bindingCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            onCashListener.onClickBtn();
        }
    });

    public void getCash(String TransactionAamount, String Brokerage, String PassWordTwo, String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "WithdrawCash");
        params.put("fun", "WithdrawCash_Add");
        params.put("TransactionAamount", TransactionAamount);
        params.put("Brokerage", Brokerage);
        params.put("PassWordTwo", PassWordTwo);
        params.put("SessionId", SessionId);
        model.getCash(params)
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
                    public void onResponse(String grouponDetailBean, String status, Object page) {
                        onCashListener.getCashSuccess();
                        dismissDialog();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        onCashListener.getCashFail(msg);
                        dismissDialog();
                    }

                });

    }

    public interface OnCashListener{
        public void getCashSuccess();
        public void getCashFail(String msg);

        public void onClickBtn();
    }

}
