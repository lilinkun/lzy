package com.lzyyd.hsq.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.adapter.CcqAdapter;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseFragment;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.CcqListBean;
import com.lzyyd.hsq.databinding.FragmentCcqBinding;
import com.lzyyd.hsq.util.UToast;
import com.lzyyd.hsq.viewmodel.CcqViewModel;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Create by liguo on 2020/8/27
 * Describe:
 */
public class CcqFragment extends BaseFragment<FragmentCcqBinding, CcqViewModel> implements CcqViewModel.CcqListCallBack {

    private int status = 0;

    public CcqFragment(int position){
        this.status = position;
    }

    @Override
    public int initVariableId() {
        return BR.ccq;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public CcqViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(CcqViewModel.class);
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_ccq;
    }

    @Override
    public void initData() {
        viewModel.setListener(this);
        viewModel.getCcqList("1","20",status+"", ProApplication.SESSIONID());
    }

    @Override
    public void initViewObservable() {

    }

    @Override
    public void getCcqListBeanSuccess(ArrayList<CcqListBean> ccqListBeans) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        CcqAdapter ccqAdapter = new CcqAdapter(getActivity());

        ccqAdapter.getItems().addAll(ccqListBeans);

        binding.ccqRv.setLayoutManager(linearLayoutManager);
        binding.ccqRv.setAdapter(ccqAdapter);

    }

    @Override
    public void getCcqListBeanFail(String msg) {
    }
}
