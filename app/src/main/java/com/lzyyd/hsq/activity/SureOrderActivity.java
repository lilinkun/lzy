package com.lzyyd.hsq.activity;

import android.os.Bundle;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.adapter.SureOrderAdapter;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.OrderinfoBean;
import com.lzyyd.hsq.databinding.ActivitySureOrderBinding;
import com.lzyyd.hsq.util.HsqAppUtil;
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
public class SureOrderActivity extends BaseActivity<ActivitySureOrderBinding, SureOrderViewModel> implements SureOrderViewModel.SureOrderCallBack {


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

        Bundle bundle = getIntent().getExtras();

        String key = bundle.getString(HsqAppUtil.KEY);

        viewModel.getData(key,"0", ProApplication.SESSIONID(),this);


    }

    @Override
    public void getOrderInfoSuccess(OrderinfoBean orderinfoBean) {

        SureOrderAdapter sureOrderAdapter = new SureOrderAdapter(this);

        sureOrderAdapter.getItems().addAll(orderinfoBean.getOrderInfoBuyList());

        binding.setAddress(orderinfoBean.getAddress());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        binding.rvSureOrder.setLayoutManager(linearLayoutManager);
        binding.rvSureOrder.setAdapter(sureOrderAdapter);


    }

    @Override
    public void getOrderInfoFail(String msg) {

    }
}
