package com.lzyyd.hsq.adapter;

import android.content.Context;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.BaseBindingAdapter;
import com.lzyyd.hsq.bean.CcqListBean;
import com.lzyyd.hsq.databinding.AdapterCcqBinding;

/**
 * Create by liguo on 2020/8/27
 * Describe:
 */
public class CcqAdapter extends BaseBindingAdapter<CcqListBean,AdapterCcqBinding> {

    private OnItemClick onItemClick;

    public CcqAdapter(Context context,OnItemClick onItemClick) {
        super(context);
        this.onItemClick = onItemClick;
    }

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.adapter_ccq;
    }

    @Override
    protected void onBindItem(AdapterCcqBinding binding, CcqListBean item) {
        binding.setCcq(item);
    }

    @Override
    protected void onclick(int position) {
        onItemClick.getQrcode(items.get(position).getCcqOrderSn());
    }

    public interface OnItemClick{
        public void getQrcode(String CcqOrderSn);
    }
}
