package com.lzyyd.hsq.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.activity.GoodsDetailActivity;
import com.lzyyd.hsq.base.BaseBindingAdapter;
import com.lzyyd.hsq.bean.GoodsListBean;
import com.lzyyd.hsq.databinding.AdapterGoodslistBinding;
import com.lzyyd.hsq.util.HsqAppUtil;

/**
 * Create by liguo on 2020/7/20
 * Describe:
 */
public class GoodsListAdapter extends BaseBindingAdapter<GoodsListBean, AdapterGoodslistBinding> {

    private Context context;
    private int type;

    public GoodsListAdapter(Context context,int type){
        super(context);
       this.context = context;
       this.type = type;
    }


    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.adapter_goodslist;
    }

    @Override
    protected void onBindItem(AdapterGoodslistBinding binding, GoodsListBean item) {
        binding.setGoodslist(item);
    }

    @Override
    protected void onclick(int position) {

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt(HsqAppUtil.TYPE,type);
        bundle.putString(HsqAppUtil.GOODSID,items.get(position).getGoodsId());
        intent.putExtras(bundle);
        intent.setClass(context, GoodsDetailActivity.class);
        context.startActivity(intent);

    }


}
