package com.lzyyd.hsq.activity;

import android.os.Bundle;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.databinding.ActivityCollectBinding;
import com.lzyyd.hsq.util.Eyes;
import com.lzyyd.hsq.viewmodel.CollectViewModel;
import com.lzyyd.hsq.viewmodel.ForgetPasswordViewModel;
import com.lzyyd.hsq.viewmodel.GoodsListViewModel;

import androidx.lifecycle.ViewModelProviders;

/**
 * Create by liguo on 2020/8/11
 * Describe:
 */
public class CollectGoodsActivity extends BaseActivity<ActivityCollectBinding, CollectViewModel> {


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_collect;
    }

    @Override
    public int initVariableId() {
        return BR.collect;
    }

    @Override
    public CollectViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(CollectViewModel.class);
    }

    @Override
    public void initData() {
        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));
    }
}
