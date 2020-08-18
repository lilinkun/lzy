package com.lzyyd.hsq.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.adapter.AddressAdapter;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.AddressBean;
import com.lzyyd.hsq.databinding.ActivityAddressBinding;
import com.lzyyd.hsq.util.UToast;
import com.lzyyd.hsq.viewmodel.AddressListViewModel;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Create by liguo on 2020/8/12
 * Describe:
 */
public class AddressListActivity extends BaseActivity<ActivityAddressBinding, AddressListViewModel> implements AddressListViewModel.AddressCallBack, SwipeRefreshLayout.OnRefreshListener {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_address;
    }

    @Override
    public int initVariableId() {
        return BR.addresslist;
    }

    @Override
    public AddressListViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(AddressListViewModel.class);
    }

    @Override
    public void initData() {
        viewModel.getDatalist("1","20", ProApplication.SESSIONID(),this);

        binding.refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void getAddressSuccess(ArrayList<AddressBean> addressBeans) {

        if (binding.refreshLayout.isRefreshing()){
            binding.refreshLayout.setRefreshing(false);
        }

        binding.llEmpty.setVisibility(View.GONE);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        AddressAdapter addressAdapter = new AddressAdapter(this,addressBeans,BR.addressbeans);

        binding.rvChooseAddress.setLayoutManager(linearLayoutManager);
        binding.rvChooseAddress.setAdapter(addressAdapter);

    }

    @Override
    public void getAddressFail(String msg) {
//        UToast.show(this,msg);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == 0x123){
                viewModel.getDatalist("1","20", ProApplication.SESSIONID(),this);
            }
        }
    }

    @Override
    public void onRefresh() {
        viewModel.getDatalist("1","20", ProApplication.SESSIONID(),this);
    }
}
