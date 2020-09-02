package com.lzyyd.hsq.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.activity.ChuangkeActivity;
import com.lzyyd.hsq.activity.GoodsDetailActivity;
import com.lzyyd.hsq.base.BaseBindingAdapter;
import com.lzyyd.hsq.bean.GoodsListBean;
import com.lzyyd.hsq.bean.VipChooseItemBean;
import com.lzyyd.hsq.databinding.AdapterChuangkeBinding;
import com.lzyyd.hsq.util.HsqAppUtil;

/**
 * Create by liguo on 2020/8/24
 * Describe:
 */
public class ChuangkeAdapter extends BaseBindingAdapter<GoodsListBean, AdapterChuangkeBinding> {

    private ModifyCountInterface modifyCountInterface;

    public ChuangkeAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.adapter_chuangke;
    }

    @Override
    protected void onBindItem(AdapterChuangkeBinding binding,final GoodsListBean item) {

        if (item.getIsPresell() == 1){
            item.setGoodsName("预售" + item.getGoodsName());
        }


        if (item.getQty() != 0){
            binding.reduceGoodsNum.setVisibility(View.GONE);
            binding.tvGoodsCount.setVisibility(View.GONE);
            binding.increaseGoodsNum.setText("选规格");
        }

        binding.goodsSpec1.setText(item.getIsPresell() == 1 ? item.getBeginDate() : (item.getGoodsSpec1() + item.getGoodsSpec2()));

        binding.setGoodslist(item);


        if (ChuangkeActivity.totalGoods != null && ChuangkeActivity.totalGoods.size() > 0){
            if(ChuangkeActivity.totalGoods.contains(item.getGoodsId())){
                for (VipChooseItemBean vipChooseItemBean : ChuangkeActivity.vipChooseItemBeans){
                    if (vipChooseItemBean.getGoodsId().equals(item.getGoodsId())){
                        binding.tvGoodsCount.setText(vipChooseItemBean.getNum()+"");
                    }
                }
            }
        }else {
            binding.tvGoodsCount.setText("0");
        }


        binding.reduceGoodsNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.tvGoodsCount.getText().toString().equals("0")) {
                    int num = Integer.valueOf(binding.tvGoodsCount.getText().toString()) - 1;
                    binding.tvGoodsCount.setText(num + "");
                    modifyCountInterface.doDecrease(item,num);
                }

            }
        });

        binding.increaseGoodsNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getQty() == 0) {
                    int num = Integer.valueOf(binding.tvGoodsCount.getText().toString()) + 1;
                    binding.tvGoodsCount.setText(num + "");
                    modifyCountInterface.doIncrease(item,num);
                }
            }
        });

        binding.tvChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyCountInterface.ChooseGoodsId(item.getGoodsId());
            }
        });

        binding.goodsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString(HsqAppUtil.GOODSID,item.getGoodsId());
                bundle.putInt(HsqAppUtil.TYPE,HsqAppUtil.GOODSTYPE_VIP);
                intent.putExtras(bundle);
                intent.setClass(context, GoodsDetailActivity.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    protected void onclick(int position) {
    }

    public void setModifyCountInterface(ModifyCountInterface modifyCountInterface) {
        this.modifyCountInterface = modifyCountInterface;
    }

    /**
     * 改变数量的接口
     */
    public interface ModifyCountInterface {
        /**
         * 增加操作
         *
         * @param showCountView 用于展示变化后数量的View
         */
        void doIncrease(GoodsListBean goodsListBean, int showCountView);

        void doDecrease(GoodsListBean goodsListBean, int showCountView);

        void doUpdate(GoodsListBean goodsListBean, int showCountView);

        /**
         * 删除子Item
         *
         */
        void childDelete(GoodsListBean goodsListBean);

        void sumGoodsNum(int num);

        void ChooseGoodsId(String goodsid);
    }
}
