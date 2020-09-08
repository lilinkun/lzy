package com.lzyyd.hsq.activity;

import android.os.Bundle;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.adapter.WalletAdapter;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.BalanceBean;
import com.lzyyd.hsq.bean.BalanceDetailBean;
import com.lzyyd.hsq.databinding.ActivityIntegralListBinding;
import com.lzyyd.hsq.util.Eyes;
import com.lzyyd.hsq.viewmodel.WalletViewModel;

import java.util.ArrayList;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Create by liguo on 2020/8/31
 * Describe:
 */
public class IntegralListActivity extends BaseActivity<ActivityIntegralListBinding, WalletViewModel> implements WalletViewModel.OnWalletListener {

    private int PAGE_INDEX = 1;
    private int PAGE_COUNT = 20;
    private WalletAdapter walletAdapter;


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_integral_list;
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

        int type = getIntent().getExtras().getInt("type");

        if (type == 3){
            binding.setTitlename("· 积分明细 ·");
        }else if (type == 4){
            binding.setTitlename("· 预到账明细 ·");
        }else if (type == 7){
            binding.setTitlename("· 优惠券明细 ·");
        }

        viewModel.setListener(this);
        viewModel.getPriceData(PAGE_INDEX + "", PAGE_COUNT+"", type+"", ProApplication.SESSIONID());
    }

    @Override
    public void getDataSuccess(ArrayList<BalanceDetailBean> balanceDetailBeans) {
        if (walletAdapter == null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

            walletAdapter = new WalletAdapter(this);

            binding.rvIntegralList.setLayoutManager(linearLayoutManager);

            binding.rvIntegralList.setAdapter(walletAdapter);

            walletAdapter.getItems().addAll(balanceDetailBeans);
        }else {
            walletAdapter.getItems().clear();
            walletAdapter.getItems().addAll(balanceDetailBeans);
            walletAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void getBalanceSuccess(BalanceBean balanceBean) {
    }

    @Override
    public void getBalanceFail(String msg) {

    }

    @Override
    public void finishForResult() {

    }
}
