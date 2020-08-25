package com.lzyyd.hsq.adapter;

import android.content.Context;
import android.view.View;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.activity.ChuangkeActivity;
import com.lzyyd.hsq.base.BaseBindingAdapter;
import com.lzyyd.hsq.bean.VipChooseItemBean;
import com.lzyyd.hsq.databinding.AdapterChuangkeBinding;
import com.lzyyd.hsq.databinding.AdapterChuangkePopBinding;

/**
 * Create by liguo on 2020/8/25
 * Describe:
 */
public class ChuangkePopAdapter extends BaseBindingAdapter<VipChooseItemBean, AdapterChuangkePopBinding> {

    private ModifyCountInterface modifyCountInterface;

    public ChuangkePopAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.adapter_chuangke_pop;
    }

    @Override
    protected void onBindItem(AdapterChuangkePopBinding binding,final VipChooseItemBean item) {

        if (item.getIsPresell() == 1){
            item.setGoodsName("预售" + item.getGoodsName());
        }

        String spec = "";

        if (item.getIsPresell() == 1){
            spec = item.getBeginDate();
        }else {
            if (item.getQty() == 1){
                spec = item.getGoodsSpec1() + ":" + item.getGoodsChooseBean().getSpec1();
            }else if (item.getQty() == 2){
                spec = item.getGoodsSpec1() + ":" + item.getGoodsChooseBean().getSpec1() + ", " + item.getGoodsSpec2() + ":" + item.getGoodsChooseBean().getSpec2();
            }
        }

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


        binding.goodsSpec1.setText(spec);

        binding.setVipchoosebean(item);


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
                    int num = Integer.valueOf(binding.tvGoodsCount.getText().toString()) + 1;
                    binding.tvGoodsCount.setText(num + "");
                    modifyCountInterface.doIncrease(item,num);
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
        void doIncrease(VipChooseItemBean goodsListBean, int showCountView);

        void doDecrease(VipChooseItemBean goodsListBean, int showCountView);

        void doUpdate(VipChooseItemBean goodsListBean, int showCountView);


        void AllDelete();

        void sumGoodsNum(int num);

        void ChooseGoodsId(String goodsid);

        void deleteVip();
    }
}
