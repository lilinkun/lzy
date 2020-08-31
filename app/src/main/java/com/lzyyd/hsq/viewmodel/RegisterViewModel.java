package com.lzyyd.hsq.viewmodel;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.lzyyd.hsq.data.DataRepository;
import com.lzyyd.hsq.databinding.ActivityRegisterBinding;
import com.lzyyd.hsq.http.callback.HttpCallBack;
import com.lzyyd.hsq.http.callback.HttpResultCallBack;
import com.lzyyd.hsq.util.UToast;

import java.util.HashMap;

import androidx.databinding.ObservableField;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.StringUtils;


public class RegisterViewModel extends BaseViewModel<DataRepository> {

    Context context;
    public int statusHeight;
    private boolean isSeeFile = false;
    private ActivityRegisterBinding binding;
    public ObservableField<String> mobileStr = new ObservableField<>();
    public ObservableField<String> vcodeStr = new ObservableField<>();
    public ObservableField<String> passwordStr = new ObservableField<>();
    public ObservableField<String> invitedStr = new ObservableField<>();
    public ObservableField<String> nicknameStr = new ObservableField<>();
    private OnRegisterListener listener;

    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        //密码开关观察者
        public SingleLiveEvent<Boolean> pSwitchEvent = new SingleLiveEvent<>();
    }

    public RegisterViewModel(Application application,DataRepository dataRepository) {
        super(application,dataRepository);
        this.context = application.getApplicationContext();
        //获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusHeight = context.getResources().getDimensionPixelSize(resourceId);
        }

    }

    public void setBinding(ActivityRegisterBinding binding){
        this.binding = binding;
    }

    public void setChecked(boolean isCheck){
        uc.pSwitchEvent.setValue(isCheck);
        isSeeFile = isCheck;
    }

    public void setListener(OnRegisterListener listener){
        this.listener = listener;
    }


    public void clickRegister(){

        if (StringUtils.isEmpty(mobileStr.get())){
            UToast.show(context,"请输入手机号码");
        }else if (StringUtils.isEmpty(vcodeStr.get())){
            UToast.show(context,"请输入验证码");
        }else if (StringUtils.isEmpty(nicknameStr.get()) || nicknameStr.get().trim().length() < 2 || nicknameStr.get().trim().length() > 22){
            UToast.show(context,"请输入正确昵称");
        }else if (StringUtils.isEmpty(passwordStr.get())){
            UToast.show(context,"请输入密码");
        }else if (StringUtils.isEmpty(invitedStr.get())){
            UToast.show(context,"请输入邀请码");
        }else if (!isSeeFile){
            UToast.show(context,"请同意阅读《用户协议》");
        }else {
            register();
        }
    }

    public void register(){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "Register");
        params.put("Mobile",mobileStr.get());
        params.put("Code",vcodeStr.get());
        params.put("PassWord",passwordStr.get());
        params.put("NickName",nicknameStr.get());
        params.put("Referees",invitedStr.get());
        params.put("OpenId","11212121");
        params.put("TPLType","2");
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
                        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        dismissDialog();
                        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
                    }

                });
    }

    //发送验证码
    public void sendVCode(){

        if (StringUtils.isEmpty(mobileStr.get())){
            UToast.show(context,"请填写手机号或账号");
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "SendSms");
        params.put("fun", "RegisterCode");
        params.put("phone",mobileStr.get());

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
                        dismissDialog();
                        Toast.makeText(context,status,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        dismissDialog();
                        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
                    }

                });

    }

    public interface OnRegisterListener{
        public void sendCode();
    }

}
