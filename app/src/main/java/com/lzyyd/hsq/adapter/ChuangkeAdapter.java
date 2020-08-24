package com.lzyyd.hsq.adapter;

import android.content.Context;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.BaseBindingAdapter;
import com.lzyyd.hsq.bean.GoodsListBean;
import com.lzyyd.hsq.databinding.AdapterChuangkeBinding;

/**
 * Create by liguo on 2020/8/24
 * Describe:
 */
public class ChuangkeAdapter extends BaseBindingAdapter<GoodsListBean, AdapterChuangkeBinding> {


    public ChuangkeAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.adapter_chuangke;
    }

    @Override
    protected void onBindItem(AdapterChuangkeBinding binding, GoodsListBean item) {
        binding.setGoodslist(item);
    }

    @Override
    protected void onclick(int position) {

    }
}
