package com.lzyyd.hsq.viewmodel;

import android.app.Application;

import com.lzyyd.hsq.bean.UrlBean;
import com.lzyyd.hsq.data.DataRepository;
import com.lzyyd.hsq.databinding.ActivityMainBinding;
import com.lzyyd.hsq.http.callback.HttpResultCallBack;
import com.lzyyd.hsq.model.MVVMModel;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.RxUtils;

public class MVVMViewModel extends BaseViewModel<DataRepository> {


    private MVVMModel mvvmModel;
    private ActivityMainBinding binding;

    public MVVMViewModel(Application application, DataRepository dataRepository) {
        super(application,dataRepository);
        mvvmModel = new MVVMModel();
    }

    /**
     * 获取图片地址前缀
     */
    public void getUrl(final LoginCallBack loginCallBack) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Home");
        params.put("fun", "SettingParameter");

        model.getUrl(params)
                .compose(RxUtils.schedulersTransformer())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>(){
                    @Override
                    public void accept(Disposable disposable){
                        showDialog();
                    }
                })
                .subscribe(new HttpResultCallBack<UrlBean, Object>() {

                    @Override
                    public void onResponse(UrlBean urlBean, String status, Object page) {
                        dismissDialog();
                        loginCallBack.getUrlSuccess(urlBean);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        dismissDialog();
                        loginCallBack.getUrlFail(msg);
                    }

                });

    }


    public interface LoginCallBack{
        public void getUrlSuccess(UrlBean urlBean);
        public void getUrlFail(String msg);
    }

}
