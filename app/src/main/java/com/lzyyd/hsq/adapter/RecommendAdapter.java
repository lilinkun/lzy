package com.lzyyd.hsq.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.activity.GoodsDetailActivity;
import com.lzyyd.hsq.base.BaseBindingAdapter;
import com.lzyyd.hsq.bean.HomeItemBean;
import com.lzyyd.hsq.databinding.AdapterRecommendlistBinding;
import com.lzyyd.hsq.util.HsqAppUtil;

/**
 * Create by liguo on 2020/8/27
 * Describe:
 */
public class RecommendAdapter extends BaseBindingAdapter<HomeItemBean, AdapterRecommendlistBinding> {

    public RecommendAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.adapter_recommendlist;
    }

    @Override
    protected void onBindItem(AdapterRecommendlistBinding binding, HomeItemBean item) {
        binding.setGoodslist(item);
    }

    @Override
    protected void onclick(int position) {

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(HsqAppUtil.GOODSID,items.get(position).getGoodsId());
        bundle.putInt(HsqAppUtil.TYPE,0);
        intent.putExtras(bundle);
        intent.setClass(context,GoodsDetailActivity.class);
        context.startActivity(intent);
    }
}
