package com.lzyyd.hsq.viewmodel;

import android.app.Application;
import android.content.Context;

import com.lzyyd.hsq.activity.AddressListActivity;
import com.lzyyd.hsq.activity.BindCardActivity;
import com.lzyyd.hsq.activity.LoginActivity;
import com.lzyyd.hsq.activity.ModifyPayActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.LoginBean;
import com.lzyyd.hsq.data.DataRepository;
import com.lzyyd.hsq.http.callback.HttpResultCallBack;
import com.lzyyd.hsq.util.DataCleanManager;
import com.lzyyd.hsq.util.UToast;
import com.lzyyd.hsq.util.UpdateManager;

import java.util.HashMap;

import androidx.annotation.NonNull;
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
 * Create by liguo on 2020/8/10
 * Describe:
 */
public class PersonalInfoViewModel extends BaseViewModel<DataRepository> {

    public ObservableField<String> vcodeInt = new ObservableField<>();
    public ObservableField<String> totalCacheSizeInt = new ObservableField<>();
    private Context context;
    private OnPersonelInfoCallback onPersonelInfoCallback;

    public PersonalInfoViewModel(@NonNull Application application, DataRepository repository) {
        super(application,repository);
    }

    public void setListener(OnPersonelInfoCallback listener){
        this.onPersonelInfoCallback = listener;
    }


    public void getVCode(Context context){
        this.context = context;
        double code = UpdateManager.getInstance().getVersionName(context);
        try {
            String cacheSize = DataCleanManager.getTotalCacheSize(context);
            if (!cacheSize.equals("0K")) {
                totalCacheSizeInt.set(cacheSize);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        vcodeInt.set("v" + code);
    }

    public void deleteCache(){
        if (totalCacheSizeInt != null && totalCacheSizeInt.get() != null){
        if (DataCleanManager.clearAllCache(context)) {
            if (totalCacheSizeInt.get().trim().length() > 0) {
                UToast.show(context, "清除缓存成功");
            }
            totalCacheSizeInt.set("");
        }
        };
    }

    public void updateApp(){

        HashMap<String,String> params = new HashMap<>();
        params.put("api_token", "");

        model.sendVCode(params)
                .compose(RxUtils.schedulersTransformer())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>(){
                    @Override
                    public void accept(Disposable disposable){
                        showDialog();
                    }
                })
                .subscribe(new HttpResultCallBack<String,String>() {
                    @Override
                    public void onResponse(String s, String status, String page) {

                    }

                    @Override
                    public void onErr(String msg, String status) {

                    }

                });


    }

    public void setJumpAddress(){
        startActivity(AddressListActivity.class);
    }

    public void setJumpBank(){
        startActivity(BindCardActivity.class);
    }


    public void getUserInfo(String UserName,String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "UserBaseVipGet");
        params.put("UserName",UserName);
        params.put("SessionId", SessionId);
        model.getUserInfo(params)
                .compose(RxUtils.schedulersTransformer())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>(){
                    @Override
                    public void accept(Disposable disposable){
                        showDialog();
                    }
                })
                .subscribe(new HttpResultCallBack<LoginBean, Object>() {
                    @Override
                    public void onResponse(LoginBean ccqBean, String status, Object page) {
                        dismissDialog();
                        onPersonelInfoCallback.getUserInfoSuccess(ccqBean);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        dismissDialog();
                        onPersonelInfoCallback.getUserInfoFail(msg);
                    }
                });
    }


    public void LoginOut() {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "Logout");
        params.put("SessionId", ProApplication.SESSIONID());
        model.register(params)
                .compose(RxUtils.schedulersTransformer())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>(){
                    @Override
                    public void accept(Disposable disposable){
                        showDialog();
                    }
                })
                .subscribe(new HttpResultCallBack<String,String>() {
                    @Override
                    public void onResponse(String s, String status, String page) {
                        dismissDialog();
                        startActivity(LoginActivity.class);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        dismissDialog();
                        onPersonelInfoCallback.getUserInfoFail(msg);
                    }

                });
    }


    public BindingCommand bindingCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivity(ModifyPayActivity.class);
        }
    });

    public interface OnPersonelInfoCallback{

        public void getUserInfoSuccess(LoginBean loginBean);
        public void getUserInfoFail(String msg);

    }

}
