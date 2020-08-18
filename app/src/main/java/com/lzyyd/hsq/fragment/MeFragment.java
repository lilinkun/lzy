package com.lzyyd.hsq.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseFragment;
import com.lzyyd.hsq.databinding.FragmentMeBinding;
import com.lzyyd.hsq.viewmodel.MeViewModel;

import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModelProviders;

/**
 * Create by liguo on 2020/8/6
 * Describe:
 */
public class MeFragment extends BaseFragment<FragmentMeBinding, MeViewModel> {

    @Override
    public int initVariableId() {
        return BR.me;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_me;
    }

    @Override
    public MeViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(MeViewModel.class);
    }

    @Override
    public void initData() {
//        binding.orderLayout.nimAllOrder.setNum(4);
    }

    @Override
    public void initViewObservable() {

    }
}
