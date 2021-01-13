package com.lzyyd.hsq.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.activity.GoodsListActivity;
import com.lzyyd.hsq.activity.IntegralActivity;
import com.lzyyd.hsq.activity.VipActivity;
import com.lzyyd.hsq.activity.WebViewActivity;
import com.lzyyd.hsq.base.BaseBindingAdapter;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.HomeGridItemBean;
import com.lzyyd.hsq.bean.HomeGridListItemBean;
import com.lzyyd.hsq.databinding.AdapterItemHomeBinding;
import com.lzyyd.hsq.util.HsqAppUtil;

/**
 * Create by liguo on 2020/7/20
 * Describe:
 */
public class GridHomeAdapter extends BaseBindingAdapter<HomeGridListItemBean, AdapterItemHomeBinding>{

    public GridHomeAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.adapter_item_home;
    }

    @Override
    protected void onBindItem(AdapterItemHomeBinding binding, HomeGridListItemBean item) {
        binding.setGriditem(item);
    }

    @Override
    protected void onclick(int position) {

        if (items.get(position).getType() == 0) {

            SharedPreferences sharedPreferences = context.getSharedPreferences(HsqAppUtil.LOGIN,Context.MODE_PRIVATE);
            String username = sharedPreferences.getString(HsqAppUtil.OTHERUSERNAME,"");

            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("url", items.get(position).getUrl());
//            bundle.putString("url", items.get(position).getUrl()+"&userName=" + username);
//            bundle.putString("url", ProApplication.SQ +"&userName=" + username);
            intent.putExtras(bundle);
            intent.setClass(context, WebViewActivity.class);
            context.startActivity(intent);
        }else if (items.get(position).getType() == 1){

            Intent intent = new Intent();
            intent.setClass(context, GoodsListActivity.class);
            context.startActivity(intent);
        }else if (items.get(position).getType() == 2){

            Intent intent = new Intent();
            intent.setClass(context, IntegralActivity.class);
            context.startActivity(intent);
        }else if (items.get(position).getType() == 3){

            Intent intent = new Intent();
            intent.setClass(context, VipActivity.class);
            context.startActivity(intent);
        }

    }
}
