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
import com.lzyyd.hsq.ui.GridSpacingItemDecoration;
import com.lzyyd.hsq.ui.SpacesItemDecoration;
import com.lzyyd.hsq.util.HsqAppUtil;
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
public class AddressListActivity extends BaseActivity<ActivityAddressBinding, AddressListViewModel> implements AddressListViewModel.AddressCallBack, SwipeRefreshLayout.OnRefreshListener, AddressAdapter.SetOnItemClickListener, AddressAdapter.OnDeleteAddress {

    private AddressAdapter addressAdapter;
    private ArrayList<AddressBean> addressBeans;
    public int resultAddAddress = 0x1231;
    private boolean isOrder = false;

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
        viewModel.setListener(this);
        viewModel.getDatalist("1","80", ProApplication.SESSIONID());

        if(getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().getString(HsqAppUtil.ADDRESS) != null){
            isOrder = true;
        }

        binding.refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void getAddressSuccess(ArrayList<AddressBean> addressBeans) {

        if (binding.refreshLayout.isRefreshing()){
            binding.refreshLayout.setRefreshing(false);
        }

        binding.rvChooseAddress.setVisibility(View.VISIBLE);
        this.addressBeans = addressBeans;
        if (addressBeans.size() > 0) {
            binding.llEmpty.setVisibility(View.GONE);
        }
        if (addressAdapter == null) {

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

            binding.rvChooseAddress.addItemDecoration(new SpacesItemDecoration( 20));

            binding.rvChooseAddress.setLayoutManager(linearLayoutManager);
            addressAdapter = new AddressAdapter(this, addressBeans, getLayoutInflater(), this);
            binding.rvChooseAddress.setAdapter(addressAdapter);
            addressAdapter.setOnItemclick(this);
        } else {
            addressAdapter.setData(addressBeans);
        }

    }

    @Override
    public void getAddressFail(String msg) {
        if (msg.contains("查无数据")){
            binding.rvChooseAddress.setVisibility(View.GONE);
            binding.llEmpty.setVisibility(View.VISIBLE);
        }else {
            UToast.show(this, msg);
        }
    }

    @Override
    public void isDefaultSuccess(String isDefaultStr) {
        UToast.show(this,"设置默认成功");
        viewModel.getDatalist("1", "80", ProApplication.SESSIONID());
    }

    @Override
    public void isDefaultFail(String msg) {

    }

    @Override
    public void deleteSuccess(String s) {
        viewModel.getDatalist("1", "80", ProApplication.SESSIONID());
    }

    @Override
    public void deleteFail(String msg) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == resultAddAddress){
                viewModel.getDatalist("1","80", ProApplication.SESSIONID());
            }
        }
    }

    @Override
    public void onRefresh() {
        viewModel.getDatalist("1","80", ProApplication.SESSIONID());
    }

    @Override
    public void onItemClick(int position) {

        if (isOrder){
            Intent intent = new Intent();
            intent.putExtra(HsqAppUtil.ADDRESS,addressBeans.get(position));
            setResult(RESULT_OK,intent);
            finish();
        }

    }

    @Override
    public void delete(String userAddressId) {
        viewModel.deletAddress(userAddressId, ProApplication.SESSIONID());
    }

    @Override
    public void modify(int position) {
        AddressBean addressBean = addressBeans.get(position);
        if (addressBean.isDefault()) {
            String isDefault = "1";
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable("addressBean", addressBean);
        startActivity( AddAddressActivity.class,bundle, resultAddAddress);

    }

    @Override
    public void isDefault(int addressId) {
        viewModel.isDefault(addressBeans.get(addressId).getAddressID(),ProApplication.SESSIONID());
    }
}
