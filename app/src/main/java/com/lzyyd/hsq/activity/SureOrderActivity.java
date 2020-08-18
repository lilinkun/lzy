package com.lzyyd.hsq.activity;

import android.os.Bundle;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.adapter.SureOrderAdapter;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.databinding.ActivitySureOrderBinding;
import com.lzyyd.hsq.viewmodel.RechargeViewModel;
import com.lzyyd.hsq.viewmodel.SureOrderViewModel;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.tatarka.bindingcollectionadapter2.BR;

/**
 * Create by liguo on 2020/7/21
 * Describe:
 */
public class SureOrderActivity extends BaseActivity<ActivitySureOrderBinding, SureOrderViewModel> {


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_sure_order;
    }

    @Override
    public int initVariableId() {
        return BR.sureorder;
    }

    @Override
    public SureOrderViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(SureOrderViewModel.class);
    }

    @Override
    public void initData() {

        SureOrderAdapter sureOrderAdapter = new SureOrderAdapter(this,null,BR.orders);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        binding.rvSureOrder.setLayoutManager(linearLayoutManager);
        binding.rvSureOrder.setAdapter(sureOrderAdapter);
    }
}
