package com.lzyyd.hsq.adapter;

import android.content.Context;
import android.content.Intent;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.activity.GoodsDetailActivity;
import com.lzyyd.hsq.base.BaseBindingAdapter;
import com.lzyyd.hsq.bean.SeckillGoodsBean;
import com.lzyyd.hsq.databinding.AdapterSeckillBinding;
import com.squareup.picasso.Picasso;

public class SeckillAdapter extends BaseBindingAdapter<SeckillGoodsBean, AdapterSeckillBinding>{

    private Context context;

    public SeckillAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.adapter_seckill;
    }

    @Override
    protected void onBindItem(AdapterSeckillBinding binding, SeckillGoodsBean item) {

        binding.setSeckill(item);

        binding.tvRushTime.start(item.getSeckilltime().get());

        Picasso.with(context).load(item.getGoodsImg().get()).error(R.mipmap.ic_back).into(binding.ivFace);

    }

    @Override
    protected void onclick(int position) {
        Intent intent = new Intent(context, GoodsDetailActivity.class);
        context.startActivity(intent);
    }

    /*@BindingAdapter({"app:imgurl"})
    public static void getTransImageView(ImageView imageView,int res){

        Picasso.with(imageView.getContext()).load(res).into(imageView);

    }*/

}