package com.lzyyd.hsq.adapter;

import android.content.Context;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.BaseBindingAdapter;
import com.lzyyd.hsq.bean.BalanceDetailBean;
import com.lzyyd.hsq.databinding.AdapterWalletBinding;


/**
 * Create by liguo on 2020/8/28
 * Describe:
 */
public class WalletAdapter extends BaseBindingAdapter<BalanceDetailBean, AdapterWalletBinding> {

    public WalletAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.adapter_wallet;
    }

    @Override
    protected void onBindItem(AdapterWalletBinding binding, BalanceDetailBean item) {
        binding.setWallet(item);
    }

    @Override
    protected void onclick(int position) {

    }
}
