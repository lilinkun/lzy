package com.lzyyd.hsq.adapter;

import android.content.Context;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.BaseBindingAdapter;
import com.lzyyd.hsq.bean.OrderGoodsBuyListBean;
import com.lzyyd.hsq.databinding.OrderChildItemBinding;

/**
 * Create by liguo on 2020/8/19
 * Describe:
 */
public class OrderChildrenAdapter extends BaseBindingAdapter<OrderGoodsBuyListBean, OrderChildItemBinding> {


    public OrderChildrenAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.order_child_item;
    }

    @Override
    protected void onBindItem(OrderChildItemBinding binding, OrderGoodsBuyListBean item) {
        binding.setOrdergoodsbuylistbean(item);
    }

    @Override
    protected void onclick(int position) {

    }
}
