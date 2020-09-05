package com.lzyyd.hsq.activity;

import android.os.Bundle;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.databinding.ActivityPayResultBinding;
import com.lzyyd.hsq.viewmodel.PayResultViewModel;

import androidx.lifecycle.ViewModelProviders;

/**
 * Create by liguo on 2020/9/3
 * Describe:
 */
public class PayResultActivity extends BaseActivity<ActivityPayResultBinding, PayResultViewModel> {


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_pay_result;
    }

    @Override
    public int initVariableId() {
        return BR.payresult;
    }

    @Override
    public PayResultViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(PayResultViewModel.class);
    }


}
