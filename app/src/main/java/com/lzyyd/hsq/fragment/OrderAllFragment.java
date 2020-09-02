package com.lzyyd.hsq.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.activity.OrderDetailActivity;
import com.lzyyd.hsq.activity.OrderListActivity;
import com.lzyyd.hsq.adapter.SelfOrderAdapter;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseFragment;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.OrderListBean;
import com.lzyyd.hsq.bean.PageBean;
import com.lzyyd.hsq.databinding.FragmentAllOrderBinding;
import com.lzyyd.hsq.ui.GridSpacingItemDecoration;
import com.lzyyd.hsq.ui.SpacesItemDecoration;
import com.lzyyd.hsq.util.UToast;
import com.lzyyd.hsq.viewmodel.MeViewModel;
import com.lzyyd.hsq.viewmodel.OrderListViewModel;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import me.tatarka.bindingcollectionadapter2.BR;

/**
 * Create by liguo on 2020/8/12
 * Describe:
 */
public class OrderAllFragment extends BaseFragment<FragmentAllOrderBinding, OrderListViewModel> implements OrderListViewModel.OnGetDataCallback, SelfOrderAdapter.OnItemClickListener, SelfOrderAdapter.OnItemClick {

    private String orderStatus = "";
    private SelfOrderAdapter selfOrderAdapter;
    private ArrayList<OrderListBean> orderListBeans;

    public OrderAllFragment(int position){
        if (position == 0){
            orderStatus = "";
        }else {
            orderStatus = position - 1 + "";
        }
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

        int spacing = 20; // 50px
        binding.allOrderRv.addItemDecoration(new SpacesItemDecoration(spacing));

        binding.allOrderRv.setLayoutManager(linearLayoutManager);

        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.getOrderData("1","20",orderStatus, ProApplication.SESSIONID());
            }
        });
    }

    public void setItemsNoti(){
        if (viewModel != null) {
            viewModel.getOrderData("1", "20", orderStatus, ProApplication.SESSIONID());
        }
    }

    @Override
    public void initViewObservable() {

    }

    @Override
    public void getDataSuccess(ArrayList<OrderListBean> orderListBeans, PageBean pageBean) {

        this.orderListBeans = orderListBeans;

        if (binding.refreshLayout != null && binding.refreshLayout.isRefreshing()){
            binding.refreshLayout.setRefreshing(false);
        }

        if (selfOrderAdapter == null) {

            selfOrderAdapter = new SelfOrderAdapter(getActivity(), orderListBeans, this);

            selfOrderAdapter.setItemClickListener(this);
            binding.allOrderRv.setAdapter(selfOrderAdapter);
        }else {
            selfOrderAdapter.setData(orderListBeans);
        }

    }

    @Override
    public void getDataFail(String msg) {
    }

    @Override
    public void sureReceiptSuccess(String msg) {
        viewModel.getOrderData("1","20",orderStatus, ProApplication.SESSIONID());
    }

    @Override
    public void sureReceiptFail(String msg) {
        UToast.show(getActivity(),msg);
    }

    @Override
    public void exitOrderSuccess(String s) {
        viewModel.getOrderData("1","20",orderStatus, ProApplication.SESSIONID());
    }

    @Override
    public void exitOrderFail(String msg) {
        UToast.show(getActivity(),msg);
    }

    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("ordersn",orderListBeans.get(position).getOrderSn());
        startActivity(OrderDetailActivity.class,bundle, OrderListActivity.ORDERRESULT);
    }

    @Override
    public void exit_order(String orderId) {
        viewModel.exitOrder(orderId,ProApplication.SESSIONID());
    }

    @Override
    public void go_pay(OrderListBean orderId) {

    }

    @Override
    public void sureReceipt(String orderId) {

        new AlertDialog.Builder(getActivity()).setMessage("是否确定收货").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                viewModel.sureReceipt(orderId, ProApplication.SESSIONID());
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();

    }

    @Override
    public void cancelOrder(String orderId) {

    }

    @Override
    public void getQrcode(String orderId) {

    }


    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == Activity.RESULT_OK){

            if (requestCode == OrderListActivity.ORDERRESULT){

                viewModel.getOrderData("1","20",orderStatus, ProApplication.SESSIONID());
            }


        }


        super.onActivityResult(requestCode, resultCode, data);
    }
}
