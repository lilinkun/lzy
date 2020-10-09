package com.lzyyd.hsq.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.lzyyd.hsq.activity.ChooseWxLoginActivity;
import com.lzyyd.hsq.activity.MainActivity;
import com.lzyyd.hsq.activity.ModifyPayActivity;
import com.lzyyd.hsq.activity.RegisterActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.LoginBean;
import com.lzyyd.hsq.bean.UrlBean;
import com.lzyyd.hsq.bean.WxUserInfo;
import com.lzyyd.hsq.data.DataRepository;
import com.lzyyd.hsq.http.callback.HttpResultCallBack;
import com.lzyyd.hsq.model.LoginModel;
import com.lzyyd.hsq.util.ActivityUtil;
import com.lzyyd.hsq.util.HsqAppUtil;
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

    public void register(WxUserInfo wxUserInfo){
        Bundle bundle = new Bundle();
        bundle.putSerializable("wx", wxUserInfo);
        startActivity(ChooseWxLoginActivity.class,bundle,0x1234);
    }


    public void forgetPsd(View view){
        Bundle bundle = new Bundle();
        bundle.putInt("type",1);
        startActivity(ModifyPayActivity.class,bundle,0x1234);
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
                        ProApplication.ISUSEQSQ = mLoginBean.getIsUseQsq();

                        SharedPreferences sharedPreferences = context.getSharedPreferences(HsqAppUtil.LOGIN, MODE_PRIVATE);
                        sharedPreferences.edit().putString("sessionid", ProApplication.SESSIONID()).putBoolean(HsqAppUtil.LOGIN, true)
                                .putString(HsqAppUtil.ACCOUNT, mLoginBean.getNickName()).putString(HsqAppUtil.TELEPHONE, mLoginBean.getMobile())
                                .putString(HsqAppUtil.USERNAME, mLoginBean.getUserName()).putString(HsqAppUtil.USERID, mLoginBean.getUserId())
                                .putString(HsqAppUtil.VIPVALIDITY, mLoginBean.getVipValidity())
                                .putString(HsqAppUtil.USERLEVEL, mLoginBean.getUserLevel() + "")
                                .putString(HsqAppUtil.CCQTYPE,mLoginBean.getCcqType()+"")
                                .putString(HsqAppUtil.LEVEL,mLoginBean.getUserLevel()+"")
                                .putString(HsqAppUtil.PROJECT,mLoginBean.getProject()+"")
                                .putString(HsqAppUtil.HEADIMGURL,mLoginBean.getPortrait())
                                .putString(HsqAppUtil.OTHERUSERNAME,mLoginBean.getOtherUserName())
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


    public void wxlogin(final String openid, final String unionid, String TPLType, String sessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "WxLogin");
        params.put("openid", openid);
        params.put("unionid", unionid);
        params.put("SessionId", sessionId);
        params.put("TPLType", TPLType);
        params.put("MobileType", "android");
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

                        SharedPreferences sharedPreferences = context.getSharedPreferences(HsqAppUtil.LOGIN, MODE_PRIVATE);
                        sharedPreferences.edit().putString("sessionid", ProApplication.SESSIONID()).putBoolean(HsqAppUtil.LOGIN, true)
                                .putString(HsqAppUtil.ACCOUNT, mLoginBean.getNickName()).putString(HsqAppUtil.TELEPHONE, mLoginBean.getMobile())
                                .putString(HsqAppUtil.USERNAME, mLoginBean.getUserName()).putString(HsqAppUtil.USERID, mLoginBean.getUserId())
                                .putString(HsqAppUtil.VIPVALIDITY, mLoginBean.getVipValidity())
                                .putString(HsqAppUtil.USERLEVEL, mLoginBean.getUserLevel() + "")
                                .putString(HsqAppUtil.CCQTYPE,mLoginBean.getCcqType()+"")
                                .putString(HsqAppUtil.LEVEL,mLoginBean.getUserLevel()+"")
                                .putString(HsqAppUtil.PROJECT,mLoginBean.getProject()+"")
                                .putString(HsqAppUtil.OTHERUSERNAME,mLoginBean.getOtherUserName())
                                .putString(HsqAppUtil.USERLEVELNAME, mLoginBean.getUserLevelName()).commit();

                        startActivity(MainActivity.class);
                        finish();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        dismissDialog();
                        loginCallBack.getWxLoginFail(msg);

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

    public void setBingWx(String UserName,String OpenId,String UnionId,String NickName,String PassWord,String Portrait,String sessionId,String TPLType){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "UserBaseBind");
        params.put("UserName", UserName);
        params.put("OpenId", OpenId);
        params.put("UnionId", UnionId);
        params.put("NickName", NickName);
        params.put("PassWord", PassWord);
        params.put("Portrait", Portrait);
        params.put("SessionId", sessionId);
        params.put("TPLType", TPLType);
        params.put("MobileType", "android");
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

                        SharedPreferences sharedPreferences = context.getSharedPreferences(HsqAppUtil.LOGIN, MODE_PRIVATE);
                        sharedPreferences.edit().putString("sessionid", ProApplication.SESSIONID()).putBoolean(HsqAppUtil.LOGIN, true)
                                .putString(HsqAppUtil.ACCOUNT, mLoginBean.getNickName()).putString(HsqAppUtil.TELEPHONE, mLoginBean.getMobile())
                                .putString(HsqAppUtil.USERNAME, mLoginBean.getUserName()).putString(HsqAppUtil.USERID, mLoginBean.getUserId())
                                .putString(HsqAppUtil.VIPVALIDITY, mLoginBean.getVipValidity())
                                .putString(HsqAppUtil.USERLEVEL, mLoginBean.getUserLevel() + "")
                                .putString(HsqAppUtil.CCQTYPE,mLoginBean.getCcqType()+"")
                                .putString(HsqAppUtil.LEVEL,mLoginBean.getUserLevel()+"")
                                .putString(HsqAppUtil.PROJECT,mLoginBean.getProject()+"")
                                .putString(HsqAppUtil.USERLEVELNAME, mLoginBean.getUserLevelName()).commit();

                        startActivity(MainActivity.class);

                        ActivityUtil.finishAll();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        dismissDialog();
                        UToast.show(context,msg);

                    }

                });
    }


    public interface LoginCallBack{
        public void getUrlSuccess(UrlBean urlBean);
        public void getUrlFail(String msg);

        public void getOnclickWxLogin();

        public void getWxLoginFail(String msg);
    }

}
