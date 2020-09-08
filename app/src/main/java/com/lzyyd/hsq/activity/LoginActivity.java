package com.lzyyd.hsq.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.UrlBean;
import com.lzyyd.hsq.bean.WxUserInfo;
import com.lzyyd.hsq.databinding.ActivityLoginBinding;
import com.lzyyd.hsq.interf.IWxLoginListener;
import com.lzyyd.hsq.util.ActivityUtil;
import com.lzyyd.hsq.util.Eyes;
import com.lzyyd.hsq.util.HsqAppUtil;
import com.lzyyd.hsq.util.UToast;
import com.lzyyd.hsq.viewmodel.LoginViewModel;
import com.lzyyd.hsq.wxapi.WXEntryActivity;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class LoginActivity extends BaseActivity<ActivityLoginBinding,LoginViewModel> implements LoginViewModel.LoginCallBack, IWxLoginListener {

    LoginViewModel loginViewModel;
    IWXAPI iwxapi = null;
    private WxUserInfo wxSuccess;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    @Override
    public int initVariableId() {
        return BR.login;
    }

    @Override
    public LoginViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(LoginViewModel.class);
    }

    @Override
    public void initData() {
        Eyes.translucentStatusBar(this);

        ActivityUtil.addActivity(this);

        iwxapi = WXAPIFactory.createWXAPI(this, HsqAppUtil.APP_ID, true);
        iwxapi.registerApp(HsqAppUtil.APP_ID);
        WXEntryActivity.wxType(HsqAppUtil.WXTYPE_LOGIN);
        WXEntryActivity.setLoginListener(this);

        SharedPreferences sharedPreferences = getSharedPreferences(HsqAppUtil.LOGIN,MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();


        viewModel.setListener(this);
        viewModel.getUrl();

    }

    @Override
    public void initViewObservable() {
        viewModel.uc.pSwitchEvent.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.etInputVcode.setInputType(aBoolean ? InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD : InputType.TYPE_CLASS_TEXT);
            }
        });

    }

    @Override
    public void getUrlSuccess(UrlBean urlBean) {
        ProApplication.HEADIMG = urlBean.getImgUrl() + ProApplication.IMG_SMALL;
        ProApplication.BANNERIMG = urlBean.getImgUrl() + ProApplication.IMG_BIG;
        ProApplication.HOMEADDRESS = urlBean.getImgUrl() + ProApplication.IMG_HOME_ADDRESS;
        ProApplication.CUSTOMERIMG = urlBean.getServiesUrl();
        ProApplication.SHAREDIMG = urlBean.getSharedWebUrl();
        ProApplication.REGISTERREQUIREMENTS = urlBean.getRegisterRequirements();
        ProApplication.LOGISTICSURL = urlBean.getLogisticsUrl();
        ProApplication.UPGRADEURL = urlBean.getUpgradeUrl();
        ProApplication.UPGRADETOKEN = urlBean.getUpgradeToken();
        ProApplication.PHONE = urlBean.getKFMobile();
        ProApplication.SERVIESVIP = urlBean.getServiesVip();
        ProApplication.SHAREDMEIMG = urlBean.getShareImg();
        ProApplication.USERLEVELPRICE10 = urlBean.getUserLevelPrice10();
        ProApplication.USERLEVELPRICE20 = urlBean.getUserLevelPrice20();
        ProApplication.KFMOBILE = urlBean.getKFMobile();
        SharedPreferences sharedPreferences = getSharedPreferences(HsqAppUtil.LOGIN, MODE_PRIVATE);
        sharedPreferences.edit().putString(HsqAppUtil.IMG, ProApplication.HEADIMG).putString(HsqAppUtil.BANNERIMG, ProApplication.BANNERIMG)
                .putString(HsqAppUtil.CUSTOMER, ProApplication.CUSTOMERIMG).putString(HsqAppUtil.SHAREDIMG, ProApplication.SHAREDIMG)
                .putString(HsqAppUtil.SHAREDMEIMG,"").commit();
    }

    @Override
    public void getUrlFail(String msg) {
        UToast.show(this,msg);
    }

    @Override
    public void getOnclickWxLogin() {
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_微信登录";
        iwxapi.sendReq(req);
    }

    @Override
    public void getWxLoginFail(String msg) {
        viewModel.register(wxSuccess);
    }

    @Override
    public void setWxLoginSuccess(WxUserInfo wxSuccess) {
        this.wxSuccess = wxSuccess;

        viewModel.wxlogin(wxSuccess.getOpenid(),wxSuccess.getUnionid(),2 + "", ProApplication.SESSIONID());
    }

    @Override
    public void setWxLoginFail(String msg) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == RESULT_OK && requestCode == 0x1234){
            finish();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
