package com.lzyyd.hsq.adapter;

import android.content.Context;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.BaseBindingAdapter;
import com.lzyyd.hsq.bean.OrderGoodsBuyListBean;
import com.lzyyd.hsq.databinding.AdapterOrderdetailBinding;

/**
 * Create by liguo on 2020/9/3
 * Describe:
 */
public class OrderDetailAdapter extends BaseBindingAdapter<OrderGoodsBuyListBean, AdapterOrderdetailBinding> {

    public OrderDetailAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.adapter_orderdetail;
    }

    @Override
    protected void onBindItem(AdapterOrderdetailBinding binding, OrderGoodsBuyListBean item) {
        binding.setOrderdetail(item);
    }

    @Override
    protected void onclick(int position) {
    }
}
