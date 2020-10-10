package com.lzyyd.hsq.activity;

import android.graphics.Color;
import android.os.Bundle;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.databinding.ActivityUserAgreementBinding;
import com.lzyyd.hsq.util.Eyes;
import com.lzyyd.hsq.viewmodel.UserAgreementViewModel;
import com.lzyyd.hsq.viewmodel.VipViewModel;

import androidx.lifecycle.ViewModelProviders;

/**
 * Create by liguo on 2020/10/10
 * Describe:
 */
public class UserAgreementActivity extends BaseActivity<ActivityUserAgreementBinding, UserAgreementViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_user_agreement;
    }

    @Override
    public int initVariableId() {
        return BR.UserAgreement;
    }


    @Override
    public UserAgreementViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(UserAgreementViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();

        Eyes.setStatusBarColorDark(this, Color.parseColor("#1C1714"));
    }
}
