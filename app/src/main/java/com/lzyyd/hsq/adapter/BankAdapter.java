package com.lzyyd.hsq.adapter;

import android.content.Context;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.BaseBindingAdapter;
import com.lzyyd.hsq.bean.BankBean;
import com.lzyyd.hsq.databinding.AdapterBankBinding;

/**
 * Create by liguo on 2020/8/29
 * Describe:
 */
public class BankAdapter extends BaseBindingAdapter<BankBean,AdapterBankBinding> {

    OnBankClickListener onBankClickListener;

    public BankAdapter(Context context,OnBankClickListener onBankClickListener) {
        super(context);
        this.onBankClickListener = onBankClickListener;
    }

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.adapter_bank;
    }

    @Override
    protected void onBindItem(AdapterBankBinding binding, BankBean item) {
        binding.setBank(item);
    }

    @Override
    protected void onclick(int position) {
        onBankClickListener.getItem(items.get(position));
    }

    public interface OnBankClickListener{
        public void getItem(BankBean bankBean);
    }
}
