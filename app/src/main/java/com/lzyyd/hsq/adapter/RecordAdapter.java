package com.lzyyd.hsq.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.activity.GoodsDetailActivity;
import com.lzyyd.hsq.base.BaseBindingAdapter;
import com.lzyyd.hsq.bean.CollectListBean;
import com.lzyyd.hsq.databinding.AdapterCollectBinding;
import com.lzyyd.hsq.util.HsqAppUtil;

/**
 * Create by liguo on 2020/8/26
 * Describe:
 */
public class RecordAdapter extends BaseBindingAdapter<CollectListBean, AdapterCollectBinding> {

    private Handler handler;

    public RecordAdapter(Context context, Handler handler) {
        super(context);
        this.handler = handler;
    }

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.adapter_collect;
    }

    @Override
    protected void onBindItem(AdapterCollectBinding binding, CollectListBean item) {
        binding.setCollect(item);
        binding.tvMacketprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        binding.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message();

                Bundle bundle = new Bundle();
                bundle.putString("collectid",item.getCollectId());

                message.setData(bundle);

                message.what = 0x11;
                handler.sendMessage(message);
            }
        });

    }

    @Override
    protected void onclick(int position) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(HsqAppUtil.TYPE,HsqAppUtil.GOODSTYPE_COMMON+"");
        bundle.putString(HsqAppUtil.GOODSID,items.get(position).getGoodsId());
        intent.putExtras(bundle);
        intent.setClass(context, GoodsDetailActivity.class);
        context.startActivity(intent);
    }





}
