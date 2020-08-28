package com.lzyyd.hsq.activity;

import android.os.Bundle;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.UserBankBean;
import com.lzyyd.hsq.databinding.ActivityBindCardBinding;
import com.lzyyd.hsq.viewmodel.BindCardViewModel;
import com.lzyyd.hsq.viewmodel.SureOrderViewModel;

import androidx.lifecycle.ViewModelProviders;

/**
 * Create by liguo on 2020/8/28
 * Describe:
 */
public class BindCardActivity extends BaseActivity<ActivityBindCardBinding, BindCardViewModel> implements BindCardViewModel.OnBankListener {


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_bind_card;
    }

    @Override
    public int initVariableId() {
        return BR.bindCard;
    }

    @Override
    public BindCardViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(BindCardViewModel.class);
    }

    @Override
    public void initData() {
        viewModel.setListener(this);
        viewModel.getBankCard(ProApplication.SESSIONID());
    }

    @Override
    public void getBankSuccess(UserBankBean userBankBean) {

    }

    @Override
    public void getBankFail(String msg) {

    }
}
