package com.lzyyd.hsq.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.adapter.GoodsListAdapter;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.BalanceBean;
import com.lzyyd.hsq.bean.GoodsListBean;
import com.lzyyd.hsq.bean.LoginBean;
import com.lzyyd.hsq.bean.PageBean;
import com.lzyyd.hsq.databinding.ActivityIntegralBinding;
import com.lzyyd.hsq.ui.GridSpacingItemDecoration;
import com.lzyyd.hsq.util.Eyes;
import com.lzyyd.hsq.util.HsqAppUtil;
import com.lzyyd.hsq.viewmodel.IntegralViewModel;

import java.util.ArrayList;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * Create by liguo on 2020/7/20
 * Describe:
 */
public class IntegralActivity extends BaseActivity<ActivityIntegralBinding,IntegralViewModel> implements IntegralViewModel.GetGoodsListCallBack {

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

//        int integral = getIntent().getExtras().getInt("integral");
//        binding.setIntegralCount(integral + "");

        viewModel.setListener(this);
        viewModel.getGoodsListData(1,20,2, ProApplication.SESSIONID());
        viewModel.getBalance(ProApplication.SESSIONID());
    }


    @Override
    public void getDataSuccess(ArrayList<GoodsListBean> goodsListBeans, PageBean page) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        GoodsListAdapter goodsListAdapter = new GoodsListAdapter(this,2);
        goodsListAdapter.getItems().addAll(goodsListBeans);


        StaggeredGridLayoutManager gridLayoutManager1 = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        binding.rvIntegral.addItemDecoration(new GridSpacingItemDecoration(2, 20, false));
//        gridLayoutManager1.setOrientation(GridLayoutManager.VERTICAL);
        binding.rvIntegral.setLayoutManager(gridLayoutManager1);
        binding.rvIntegral.setAdapter(goodsListAdapter);

    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void getBalanceSuccess(BalanceBean balanceBean) {
        binding.setIntegralCount(balanceBean.getMoney3Balance()+"");
    }

    @Override
    public void getBalanceFail(String msg) {

    }

}
