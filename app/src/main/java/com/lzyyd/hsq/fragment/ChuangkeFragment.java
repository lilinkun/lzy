package com.lzyyd.hsq.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.adapter.ChuangkeAdapter;
import com.lzyyd.hsq.adapter.TabPageAdapter;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseFragment;
import com.lzyyd.hsq.bean.CategoryBean;
import com.lzyyd.hsq.bean.GoodsListBean;
import com.lzyyd.hsq.bean.PageBean;
import com.lzyyd.hsq.databinding.FragmentChuangkeBinding;
import com.lzyyd.hsq.viewmodel.ChuangkeViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

/**
 * Create by liguo on 2020/8/24
 * Describe:
 */
public class ChuangkeFragment extends BaseFragment<FragmentChuangkeBinding, ChuangkeViewModel> implements ChuangkeViewModel.ChuangkeDataCallBack {

    private String categoryId = "";

    public ChuangkeFragment(String categoryId){
        this.categoryId = categoryId;
    }

    @Override
    public int initVariableId() {
        return BR.chuangke;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_chuangke;
    }

    @Override
    public ChuangkeViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(ChuangkeViewModel.class);
    }


    @Override
    public void initData() {
        viewModel.setChuangkeDataCallBack(this);
        viewModel.getSelfData(1,20,4,categoryId);

        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.getSelfData(1,20,4,categoryId);
            }
        });
    }

    @Override
    public void initViewObservable() {

    }


    @Override
    public void getDataSuccess(ArrayList<GoodsListBean> goodsListBeans, PageBean page) {

        if (binding.refreshLayout.isRefreshing()){
            binding.refreshLayout.setRefreshing(false);
        }

        ChuangkeAdapter goodsListAdapter = new ChuangkeAdapter(getActivity());
        goodsListAdapter.getItems().addAll(goodsListBeans);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.rvChuangke.setLayoutManager(linearLayoutManager);
        binding.rvChuangke.setAdapter(goodsListAdapter);
    }

    @Override
    public void getDataFail(String msg) {

    }
}
