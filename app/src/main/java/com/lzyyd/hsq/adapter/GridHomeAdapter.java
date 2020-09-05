package com.lzyyd.hsq.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.activity.WebViewActivity;
import com.lzyyd.hsq.base.BaseBindingAdapter;
import com.lzyyd.hsq.bean.HomeGridItemBean;
import com.lzyyd.hsq.databinding.AdapterItemHomeBinding;

/**
 * Create by liguo on 2020/7/20
 * Describe:
 */
public class GridHomeAdapter extends BaseBindingAdapter<HomeGridItemBean, AdapterItemHomeBinding>{

    public GridHomeAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.adapter_item_home;
    }

    @Override
    protected void onBindItem(AdapterItemHomeBinding binding, HomeGridItemBean item) {
        binding.setGriditem(item);
    }

    @Override
    protected void onclick(int position) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("url",items.get(position).getUrl());
        intent.putExtras(bundle);
        intent.setClass(context, WebViewActivity.class);
        context.startActivity(intent);

    }
}
