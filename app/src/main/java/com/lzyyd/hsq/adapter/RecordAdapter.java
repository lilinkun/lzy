package com.lzyyd.hsq.adapter;

import android.content.Context;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.BaseBindingAdapter;
import com.lzyyd.hsq.bean.CollectListBean;
import com.lzyyd.hsq.databinding.AdapterCollectBinding;

/**
 * Create by liguo on 2020/8/26
 * Describe:
 */
public class RecordAdapter extends BaseBindingAdapter<CollectListBean, AdapterCollectBinding> {


    public RecordAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.adapter_collect;
    }

    @Override
    protected void onBindItem(AdapterCollectBinding binding, CollectListBean item) {
        binding.setCollect(item);
    }

    @Override
    protected void onclick(int position) {

    }
}
