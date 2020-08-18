package com.lzyyd.hsq.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.adapter.IntegralTitleAdapter;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.databinding.ActivityIntegralBinding;
import com.lzyyd.hsq.util.Eyes;
import com.lzyyd.hsq.viewmodel.IntegralViewModel;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * Create by liguo on 2020/7/20
 * Describe:
 */
public class IntegralActivity extends BaseActivity<ActivityIntegralBinding,IntegralViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_integral;
    }

    @Override
    public int initVariableId() {
        return BR.integral;
    }

    @Override
    public IntegralViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(IntegralViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();

        Eyes.setStatusBarColor1(this, Color.parseColor("#FF3C38"));


        IntegralTitleAdapter integralTitleAdapter = new IntegralTitleAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        binding.rvIntegralTitle.setLayoutManager(linearLayoutManager);
        binding.rvIntegralTitle.setAdapter(integralTitleAdapter);
        binding.rvIntegralTitle.setHorizontalScrollBarEnabled(true);
        binding.rvIntegralTitle.setScrollBarSize(20);
        binding.rvIntegralTitle.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_INSET);
        binding.rvb.bindRecyclerView(binding.rvIntegralTitle);
    }
}
