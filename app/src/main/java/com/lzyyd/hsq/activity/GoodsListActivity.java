package com.lzyyd.hsq.activity;

import android.graphics.Color;
import android.os.Bundle;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.adapter.GoodsListAdapter;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.GoodsListBean;
import com.lzyyd.hsq.bean.PageBean;
import com.lzyyd.hsq.databinding.ActivityGoodslistBinding;
import com.lzyyd.hsq.ui.GridSpacingItemDecoration;
import com.lzyyd.hsq.util.Eyes;
import com.lzyyd.hsq.viewmodel.GoodsListViewModel;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class GoodsListActivity extends BaseActivity<ActivityGoodslistBinding, GoodsListViewModel> implements GoodsListViewModel.GetGoodsListCallBack {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Eyes.setStatusBarLightMode(this, Color.parseColor("#FFFFFF"));

    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_goodslist;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public GoodsListViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(GoodsListViewModel.class);
    }

    @Override
    public void initData() {
        viewModel.setListener(this);
        viewModel.getGoodsListData(1,20,1, ProApplication.SESSIONID());
    }

    @Override
    public void getDataSuccess(ArrayList<GoodsListBean> goodsListBeans, PageBean page) {

        GoodsListAdapter goodsListAdapter = new GoodsListAdapter(this);
        goodsListAdapter.getItems().addAll(goodsListBeans);


        StaggeredGridLayoutManager gridLayoutManager1 = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        binding.rvGoodsList.addItemDecoration(new GridSpacingItemDecoration(2, 20, false));
//        gridLayoutManager1.setOrientation(GridLayoutManager.VERTICAL);
        binding.rvGoodsList.setLayoutManager(gridLayoutManager1);
        binding.rvGoodsList.setAdapter(goodsListAdapter);
    }

    @Override
    public void getDataFail(String msg) {

    }
}
