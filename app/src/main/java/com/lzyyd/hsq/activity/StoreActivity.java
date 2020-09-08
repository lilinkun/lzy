package com.lzyyd.hsq.activity;

import android.os.Bundle;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.adapter.GoodsListAdapter;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.GoodsListBean;
import com.lzyyd.hsq.databinding.ActivityStoreBinding;
import com.lzyyd.hsq.ui.GridSpacingItemDecoration;
import com.lzyyd.hsq.util.Eyes;
import com.lzyyd.hsq.util.UToast;
import com.lzyyd.hsq.viewmodel.StoreViewModel;

import java.util.ArrayList;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import me.tatarka.bindingcollectionadapter2.BR;

/**
 * Create by liguo on 2020/9/2
 * Describe:
 */
public class StoreActivity extends BaseActivity<ActivityStoreBinding, StoreViewModel> implements StoreViewModel.OnStoreListener {

    private GoodsListAdapter goodsListAdapter;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_store;
    }

    @Override
    public int initVariableId() {
        return BR.store;
    }


    @Override
    public StoreViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(StoreViewModel.class);
    }

    @Override
    public void initData() {
        Eyes.setStatusBarWhiteColor(this, getResources().getColor(R.color.white));

        int storeId = getIntent().getExtras().getInt("storeId");

        viewModel.setListener(this);
        viewModel.getSelfData(1,80,storeId+"", ProApplication.SESSIONID());

    }

    @Override
    public void getDataSuccess(ArrayList<GoodsListBean> goodsListBeans) {
        if (goodsListAdapter == null) {
            binding.setImgRes(goodsListBeans.get(0).getStoreLogo());
            binding.setName(goodsListBeans.get(0).getStoreName());
            goodsListAdapter = new GoodsListAdapter(this,1);
            goodsListAdapter.getItems().addAll(goodsListBeans);
            StaggeredGridLayoutManager gridLayoutManager1 = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            binding.rvStore.addItemDecoration(new GridSpacingItemDecoration(2, 20, false));
//        gridLayoutManager1.setOrientation(GridLayoutManager.VERTICAL);
            binding.rvStore.setLayoutManager(gridLayoutManager1);
            binding.rvStore.setAdapter(goodsListAdapter);
        }else {
            goodsListAdapter.getItems().clear();
            goodsListAdapter.getItems().addAll(goodsListBeans);
            goodsListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getDataFail(String msg) {
        UToast.show(this,msg);
    }
}
