package com.lzyyd.hsq.activity;

import android.os.Bundle;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.databinding.ActivityPayBinding;
import com.lzyyd.hsq.viewmodel.PayViewModel;

/**
 * Create by liguo on 2020/8/26
 * Describe:
 */
public class PayActivity extends BaseActivity<ActivityPayBinding, PayViewModel> {


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_pay;
    }

    @Override
    public int initVariableId() {
        return 0;
    }
}
