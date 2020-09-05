package com.lzyyd.hsq.activity;

import android.os.Bundle;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.databinding.ActivityForgetpasswordBinding;
import com.lzyyd.hsq.util.Eyes;
import com.lzyyd.hsq.viewmodel.ForgetPasswordViewModel;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class ForgetPasswordActivity extends BaseActivity<ActivityForgetpasswordBinding,ForgetPasswordViewModel> {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_forgetpassword;
    }

    @Override
    public int initVariableId() {
        return BR.forget;
    }

    @Override
    public ForgetPasswordViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(ForgetPasswordViewModel.class);
    }

    @Override
    public void initData() {
        Eyes.setStatusBarColor1(this,getResources().getColor(R.color.white));
    }
}
