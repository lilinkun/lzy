package com.lzyyd.hsq.activity;

import android.os.Bundle;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.databinding.ActivityMyqrcodeBinding;
import com.lzyyd.hsq.viewmodel.MyQrcodeViewModel;

import androidx.lifecycle.ViewModelProviders;

/**
 * Create by liguo on 2020/8/13
 * Describe:
 */
public class MyQrcodeActivity extends BaseActivity<ActivityMyqrcodeBinding, MyQrcodeViewModel> {


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_myqrcode;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public MyQrcodeViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(MyQrcodeViewModel.class);
    }
}
