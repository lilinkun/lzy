package com.lzyyd.hsq.activity;

import android.os.Bundle;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.databinding.ActivityStoreBinding;
import com.lzyyd.hsq.viewmodel.StoreViewModel;

/**
 * Create by liguo on 2020/9/2
 * Describe:
 */
public class StoreActivity extends BaseActivity<ActivityStoreBinding, StoreViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_store;
    }

    @Override
    public int initVariableId() {
        return 0;
    }
}
