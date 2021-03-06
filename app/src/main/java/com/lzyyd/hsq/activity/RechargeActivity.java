package com.lzyyd.hsq.activity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.bean.BalanceBean;
import com.lzyyd.hsq.databinding.ActivityRechargeBinding;
import com.lzyyd.hsq.util.Eyes;
import com.lzyyd.hsq.util.HsqAppUtil;
import com.lzyyd.hsq.viewmodel.RechargeViewModel;

import androidx.lifecycle.ViewModelProviders;

/**
 * Create by liguo on 2020/8/11
 * Describe:
 */
public class RechargeActivity extends BaseActivity<ActivityRechargeBinding, RechargeViewModel> {
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_recharge;
    }

    @Override
    public int initVariableId() {
        return BR.recharge;
    }

    @Override
    public RechargeViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(RechargeViewModel.class);
    }


    @Override
    public void initData() {
        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));

        BalanceBean balanceBean = (BalanceBean) getIntent().getExtras().getSerializable("balance");


        SharedPreferences sharedPreferences = getSharedPreferences(HsqAppUtil.LOGIN,MODE_PRIVATE);
        String headimg = sharedPreferences.getString(HsqAppUtil.HEADIMGURL,"");
        String username = sharedPreferences.getString(HsqAppUtil.USERNAME,"");

        binding.setPrice(String.valueOf(balanceBean.getMoney2Balance()));

        binding.setUsername(username);
        binding.setHeadimg(headimg);

    }

}
