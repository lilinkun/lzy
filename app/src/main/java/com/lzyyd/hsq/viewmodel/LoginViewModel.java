package com.lzyyd.hsq.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import com.lzyyd.hsq.activity.ForgetPasswordActivity;
import com.lzyyd.hsq.activity.IntegralActivity;
import com.lzyyd.hsq.activity.MainActivity;
import com.lzyyd.hsq.activity.RegisterActivity;
import com.lzyyd.hsq.activity.SeckillActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.LoginBean;
import com.lzyyd.hsq.bean.UrlBean;
import com.lzyyd.hsq.data.DataRepository;
import com.lzyyd.hsq.http.callback.HttpResultCallBack;
import com.lzyyd.hsq.model.LoginModel;
import com.lzyyd.hsq.util.HsqAppUtil;
import com.lzyyd.hsq.util.UToast;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

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

import static android.content.Context.MODE_PRIVATE;

public class LoginViewModel extends BaseViewModel<DataRepository> {
    Context context;
    LoginModel loginModel;
    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();
    private LoginCallBack loginCallBack;

    public void setListener(LoginCallBack loginCallBack){
        this.loginCallBack = loginCallBack;
    }

    public ObservableField<String> usernameField = new ObservableField<>();
    public ObservableField<String> passWordField = new ObservableField<>();

    public class UIChangeObservable {
        //密码开关观察者
        public SingleLiveEvent<Boolean> pSwitchEvent = new SingleLiveEvent<>();
    }

    public LoginViewModel(Application application, DataRepository dataRepository) {
        super(application,dataRepository);
        this.context = application.getApplicationContext();
        loginModel = new LoginModel();
        SharedPreferences sharedPreferences = context.getSharedPreferences(HsqAppUtil.LOGIN, MODE_PRIVATE);
        if (sharedPreferences.getString(HsqAppUtil.USERNAME,"") != "") {
            usernameField.set(sharedPreferences.getString(HsqAppUtil.USERNAME, ""));
        }
    }

    public void register(View view){
        startActivity(RegisterActivity.class);
    }


    public void forgetPsd(View view){
        startActivity(ForgetPasswordActivity.class);
    }

    public void clickLogin(){

        if (StringUtils.isEmpty(usernameField.get())){
            UToast.show(context,"请输入用户名");
        }else if (StringUtils.isEmpty(passWordField.get())){
            UToast.show(context,"请输入密码");
        }else {
            login();
        }
    }

    public void login(){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "Login");
        params.put("UserName",usernameField.get());
        params.put("PassWord",passWordField.get());
        params.put("SessionId", ProApplication.SESSIONID());
        model.login(params)
                .compose(RxUtils.schedulersTransformer())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>(){
                    @Override
                    public void accept(Disposable disposable){
                        showDialog();
                    }
                })
                .subscribe(new HttpResultCallBack<LoginBean,String>() {
                    @Override
                    public void onResponse(LoginBean mLoginBean, String status, String page) {
                        dismissDialog();

                        ProApplication.PROJECT = mLoginBean.getProject();
                        ProApplication.LEVEL = mLoginBean.getUserLevel();
                        ProApplication.CCQTYPE = mLoginBean.getCcqType();

                        SharedPreferences sharedPreferences = context.getSharedPreferences(HsqAppUtil.LOGIN, MODE_PRIVATE);
                        sharedPreferences.edit().putString("sessionid", ProApplication.SESSIONID()).putBoolean(HsqAppUtil.LOGIN, true)
                                .putString(HsqAppUtil.ACCOUNT, mLoginBean.getNickName()).putString(HsqAppUtil.TELEPHONE, mLoginBean.getMobile())
                                .putString(HsqAppUtil.USERNAME, mLoginBean.getUserName()).putString(HsqAppUtil.USERID, mLoginBean.getUserId())
                                .putString(HsqAppUtil.VIPVALIDITY, mLoginBean.getVipValidity())
                                .putString(HsqAppUtil.USERLEVEL, mLoginBean.getUserLevel() + "")
                                .putString(HsqAppUtil.USERLEVELNAME, mLoginBean.getUserLevelName()).commit();

                        startActivity(MainActivity.class);
                        finish();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        dismissDialog();
                        UToast.show(context,msg);
                    }

                });

    }


    public void seckill(View view){

        loginCallBack.getOnclickWxLogin();

    }


    public void setChecked(boolean isCheck){
        //让观察者的数据改变,逻辑从ViewModel层转到View层，在View层的监听则会被调用
        uc.pSwitchEvent.setValue(uc.pSwitchEvent.getValue() == null || !uc.pSwitchEvent.getValue());
        //binding.etInputVcode.setInputType(isCheck ? InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD : InputType.TYPE_CLASS_TEXT);

    }

    /**
     * 获取图片地址前缀
     */
    public void getUrl() {
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

        public void getOnclickWxLogin();
    }

}
