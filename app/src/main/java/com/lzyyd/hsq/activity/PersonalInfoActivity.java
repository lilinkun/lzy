package com.lzyyd.hsq.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.view.KeyEvent;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.CheckBean;
import com.lzyyd.hsq.bean.LoginBean;
import com.lzyyd.hsq.databinding.ActivityPersonalInfoBinding;
import com.lzyyd.hsq.util.Eyes;
import com.lzyyd.hsq.util.HsqAppUtil;
import com.lzyyd.hsq.util.UToast;
import com.lzyyd.hsq.viewmodel.PersonalInfoViewModel;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.ViewModelProviders;

import static java.security.spec.MGF1ParameterSpec.SHA1;

/**
 * Created by LG on 2018/11/19.
 */

public class PersonalInfoActivity extends BaseActivity<ActivityPersonalInfoBinding, PersonalInfoViewModel> implements PersonalInfoViewModel.OnPersonelInfoCallback {

    private boolean isChangeSuccess = false;

    private CheckBean bean;
    private double code = 0;

    public void initData() {
        Eyes.setStatusBarWhiteColor(this, getResources().getColor(R.color.white));
        viewModel.setListener(this);
        viewModel.getVCode(this);

        SharedPreferences sharedPreferences = getSharedPreferences(HsqAppUtil.LOGIN, MODE_PRIVATE);

        viewModel.getUserInfo(sharedPreferences.getString(HsqAppUtil.USERNAME, ""), ProApplication.SESSIONID());

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isChangeSuccess) {
                setResult(RESULT_OK);
            }
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_personal_info;
    }

    @Override
    public int initVariableId() {
        return BR.personalViewModel;
    }

    @Override
    public PersonalInfoViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(PersonalInfoViewModel.class);
    }

    @Override
    public void getUserInfoSuccess(LoginBean mLoginBean) {
        binding.setPersoninfo(mLoginBean);

        SharedPreferences sharedPreferences = getSharedPreferences(HsqAppUtil.LOGIN, MODE_PRIVATE);
        sharedPreferences.edit().putString("sessionid", ProApplication.SESSIONID()).putBoolean(HsqAppUtil.LOGIN, true)
                .putString(HsqAppUtil.ACCOUNT, mLoginBean.getNickName()).putString(HsqAppUtil.TELEPHONE, mLoginBean.getMobile())
                .putString(HsqAppUtil.USERNAME, mLoginBean.getUserName()).putString(HsqAppUtil.USERID, mLoginBean.getUserId())
                .putString(HsqAppUtil.VIPVALIDITY, mLoginBean.getVipValidity())
                .putString(HsqAppUtil.USERLEVEL, mLoginBean.getUserLevel() + "")
                .putString(HsqAppUtil.CCQTYPE,mLoginBean.getCcqType()+"")
                .putString(HsqAppUtil.LEVEL,mLoginBean.getUserLevel()+"")
                .putString(HsqAppUtil.PROJECT,mLoginBean.getProject()+"")
                .putString(HsqAppUtil.USERLEVELNAME, mLoginBean.getUserLevelName()).commit();
    }

    @Override
    public void getUserInfoFail(String msg) {
        UToast.show(this,msg);
    }


}
