package com.lzyyd.hsq.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseFragment;
import com.lzyyd.hsq.databinding.FragmentWaitReceiveBinding;
import com.lzyyd.hsq.viewmodel.OrderListViewModel;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

/**
 * Create by liguo on 2020/8/13
 * Describe:
 */
public class OrderWaitReceiveFragment extends BaseFragment<FragmentWaitReceiveBinding, OrderListViewModel> {

    @Override
    public int initVariableId() {
        return BR.orderlist;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_wait_receive;
    }

    @Override
    public OrderListViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(OrderListViewModel.class);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initViewObservable() {

    }
}