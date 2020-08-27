package com.lzyyd.hsq.adapter;

import android.content.Context;
import android.graphics.PostProcessor;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.BaseBindingAdapter;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.GoodsListBean;
import com.lzyyd.hsq.bean.HomeItemBean;
import com.lzyyd.hsq.databinding.AdapterGoodslistBinding;
import com.lzyyd.hsq.viewmodel.GoodsListViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Create by liguo on 2020/7/20
 * Describe:
 */
public class GoodsListAdapter extends BaseBindingAdapter<GoodsListBean, AdapterGoodslistBinding> {

    private Context context;

    public GoodsListAdapter(Context context){
        super(context);
       this.context = context;
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
    }


}
