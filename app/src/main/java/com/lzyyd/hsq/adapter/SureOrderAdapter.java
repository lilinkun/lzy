package com.lzyyd.hsq.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.BaseBindingAdapter;
import com.lzyyd.hsq.bean.OrderInfoBuyListBean;
import com.lzyyd.hsq.databinding.AdapterSureOrderBinding;
import com.lzyyd.hsq.util.UToast;

import java.util.ArrayList;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Create by liguo on 2020/7/23
 * Describe:
 */
public class SureOrderAdapter extends BaseBindingAdapter<OrderInfoBuyListBean, AdapterSureOrderBinding> {

    private ArrayList<OrderInfoBuyListBean> orderBeans;
    private OrderInfoBuyListBean orderInfoBuyListBean;
    protected LayoutInflater mInflater;
    // mvvm绑定的viewModel引用
    private int mVariableId;
    ObservableArrayList<Integer> integers = new ObservableArrayList<>();
    private OnDataGetFare onDataGetFare;

    public ObservableField<String> orderField = new ObservableField<>();
    public ObservableField<Integer> maxpoint = new ObservableField<>();

    public SureOrderAdapter(Context context,int maxpoint,OnDataGetFare onDataGetFare) {
        super(context);
        this.maxpoint.set(maxpoint);
        this.onDataGetFare = onDataGetFare;
    }

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.adapter_sure_order;
    }

    @Override
    protected void onBindItem(AdapterSureOrderBinding binding, OrderInfoBuyListBean item) {
        binding.setOrders(item);
        binding.setSureadapter(this);

        this.orderInfoBuyListBean = item;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        OrderChildrenAdapter orderChildrenAdapter = new OrderChildrenAdapter(context);

        orderChildrenAdapter.getItems().addAll(item.getOrderGoodsBuyList());
        binding.rvGoodsOrders.setLayoutManager(linearLayoutManager);
        binding.rvGoodsOrders.setAdapter(orderChildrenAdapter);

        if (item.getIntegral() == 0){
            binding.rlIntegral.setVisibility(View.GONE);
        }

        if (maxpoint.get() > 0) {

            if (maxpoint.get() > item.getIntegral()) {
                orderField.set(item.getIntegral() + "");
                maxpoint.set(maxpoint.get() - item.getIntegral());
            } else {
                orderField.set(maxpoint + "");
            }
        }else {
            orderField.set("0");
        }

        binding.rlIntegral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(context).inflate(R.layout.layout_edittext_point,null);
                final EditText editText = (EditText) view.findViewById(R.id.edit_num);
                editText.setText(orderField.get());
                new AlertDialog.Builder(context).setMessage("修改使用积分").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!editText.getText().toString().isEmpty()){
                            if (Integer.valueOf(editText.getText().toString()) > orderInfoBuyListBean.getIntegral()){
                                UToast.show(context, "超出可使用积分");
                            }else {
                                if (Integer.valueOf(editText.getText().toString()) > maxpoint.get()) {
                                    UToast.show(context, "超出最大使用积分");
                                } else {

                                    int point = Integer.valueOf(editText.getText().toString()) - Integer.valueOf(binding.orderIntegral.getText().toString());

                                    maxpoint.set(maxpoint.get() - point);
                                    orderField.set(editText.getText().toString());
                                    binding.orderIntegral.setText(editText.getText().toString() + "");

                                    onDataGetFare.onPoint(maxpoint.get());
                                }
                            }
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
            }
        });

        if (item.getStoreId() == items.get(items.size()-1).getStoreId()){
            onDataGetFare.onPoint(maxpoint.get());
        }


    }

    @Override
    protected void onclick(int position) {

    }

    public Typeface setTypeFace(){
        Typeface iconfont = Typeface.createFromAsset(context.getAssets(), "iconfont.ttf");
        return iconfont;
    }


    public interface OnDataGetFare{
        public void onPoint(int point);
    }

}
