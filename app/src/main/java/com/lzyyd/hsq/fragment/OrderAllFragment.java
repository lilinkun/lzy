package com.lzyyd.hsq.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.adapter.SelfOrderAdapter;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseFragment;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.OrderListBean;
import com.lzyyd.hsq.bean.PageBean;
import com.lzyyd.hsq.databinding.FragmentAllOrderBinding;
import com.lzyyd.hsq.ui.GridSpacingItemDecoration;
import com.lzyyd.hsq.util.UToast;
import com.lzyyd.hsq.viewmodel.MeViewModel;
import com.lzyyd.hsq.viewmodel.OrderListViewModel;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.tatarka.bindingcollectionadapter2.BR;

/**
 * Create by liguo on 2020/8/12
 * Describe:
 */
public class OrderAllFragment extends BaseFragment<FragmentAllOrderBinding, OrderListViewModel> implements OrderListViewModel.OnGetDataCallback {

    private String orderStatus = "";
    private SelfOrderAdapter.OnItemClick itemClick;

    public OrderAllFragment(int position,SelfOrderAdapter.OnItemClick onItemClick){
        if (position == 0){
            orderStatus = "";
        }else {
            orderStatus = position - 1 + "";
        }
        this.itemClick = onItemClick;
    }

    @Override
    public int initVariableId() {
        return BR.orderlist;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_all_order;
    }

    @Override
    public OrderListViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(OrderListViewModel.class);
    }

    @Override
    public void initData() {

        viewModel.setListener(this);
        viewModel.getOrderData("1","20",orderStatus, ProApplication.SESSIONID());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        int spanCount = 5; // 3 columns
        int spacing = 20; // 50px
        boolean includeEdge = false;
        binding.allOrderRv.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        binding.allOrderRv.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void initViewObservable() {

    }

    @Override
    public void getDataSuccess(ArrayList<OrderListBean> orderListBeans, PageBean pageBean) {
        SelfOrderAdapter selfOrderAdapter = new SelfOrderAdapter(getActivity(),orderListBeans,itemClick);


        binding.allOrderRv.setAdapter(selfOrderAdapter);
    }

    @Override
    public void getDataFail(String msg) {
        UToast.show(getActivity(),msg);
    }

    @Override
    public void sureReceiptSuccess(String msg) {

    }

    @Override
    public void sureReceiptFail(String msg) {

    }

}
