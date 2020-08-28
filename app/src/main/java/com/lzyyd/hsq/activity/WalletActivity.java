package com.lzyyd.hsq.activity;

import android.os.Bundle;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.adapter.WalletAdapter;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.BalanceDetailBean;
import com.lzyyd.hsq.databinding.ActivityWalletBinding;
import com.lzyyd.hsq.util.Eyes;
import com.lzyyd.hsq.viewmodel.WalletViewModel;

import java.util.ArrayList;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Create by liguo on 2020/8/10
 * Describe:
 */
public class WalletActivity extends BaseActivity<ActivityWalletBinding, WalletViewModel> implements WalletViewModel.OnWalletListener {

    private int PAGE_INDEX = 1;
    private int PAGE_COUNT = 20;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_wallet;
    }

    @Override
    public int initVariableId() {
        return BR.wallet;
    }

    @Override
    public WalletViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(WalletViewModel.class);
    }

    @Override
    public void initData() {
        Eyes.setStatusBarWhiteColor(this, getResources().getColor(R.color.white));

        viewModel.setListener(this);
        viewModel.getPriceData(PAGE_INDEX + "", PAGE_COUNT+"", "3", ProApplication.SESSIONID());
    }


    @Override
    public void getDataSuccess(ArrayList<BalanceDetailBean> balanceDetailBeans) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        WalletAdapter walletAdapter = new WalletAdapter(this);

        binding.rvWallet.setLayoutManager(linearLayoutManager);

        binding.rvWallet.setAdapter(walletAdapter);


    }

    @Override
    public void getDataFail(String msg) {

    }
}
