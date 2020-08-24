package com.lzyyd.hsq.adapter;

import android.content.Context;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.BaseBindingAdapter;
import com.lzyyd.hsq.bean.OrderGoodsBuyListBean;
import com.lzyyd.hsq.databinding.OrderChildItemBinding;

import me.goldze.mvvmhabit.utils.StringUtils;

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

        String spec = "";

        if (item.getAttrOne() != null && !StringUtils.isEmpty(item.getAttrOne())){

            if (item.getAttrTwo() != null && !StringUtils.isEmpty(item.getAttrTwo())) {
                spec = item.getAttrOneName() + ":" + item.getAttrOne()+","+ item.getAttrTwoName() + ":" + item.getAttrTwo();
            }else {
                spec = item.getAttrOneName() + ":" + item.getAttrOne();
            }
        }

        binding.setRere(spec);
        binding.setOrdergoodsbuylistbean(item);
    }

    @Override
    protected void onclick(int position) {

    }
}
