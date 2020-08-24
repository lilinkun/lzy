package com.lzyyd.hsq.activity;

import android.graphics.Color;
import android.os.Bundle;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.databinding.ActivityVipBinding;
import com.lzyyd.hsq.util.Eyes;
import com.lzyyd.hsq.viewmodel.CollectViewModel;
import com.lzyyd.hsq.viewmodel.VipViewModel;

import androidx.lifecycle.ViewModelProviders;

/**
 * Create by liguo on 2020/8/24
 * Describe:
 */
public class VipActivity extends BaseActivity<ActivityVipBinding, VipViewModel> {
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_vip;
    }

    @Override
    public int initVariableId() {
        return BR.vip;
    }

    @Override
    public VipViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(VipViewModel.class);
    }

    @Override
    public void initData() {
        Eyes.setStatusBarColor1(this, Color.parseColor("#1C1714"));
    }
}
