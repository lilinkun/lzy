package com.lzyyd.hsq.activity;

import android.content.SharedPreferences;
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
import com.lzyyd.hsq.viewmodel.PersonalInfoViewModel;

import androidx.lifecycle.ViewModelProviders;

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
    public void getUserInfoSuccess(LoginBean loginBean) {
        binding.setPersoninfo(loginBean);
    }

    @Override
    public void getUserInfoFail(String msg) {

    }
}
