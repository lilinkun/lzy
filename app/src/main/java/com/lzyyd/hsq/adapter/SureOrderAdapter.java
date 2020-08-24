package com.lzyyd.hsq.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.activity.MainActivity;
import com.lzyyd.hsq.base.BaseBindingAdapter;
import com.lzyyd.hsq.bean.CartBean;
import com.lzyyd.hsq.bean.OrderBean;
import com.lzyyd.hsq.bean.OrderInfoBuyListBean;
import com.lzyyd.hsq.databinding.AdapterSureOrderBinding;
import com.lzyyd.hsq.util.UToast;

import java.util.ArrayList;
import java.util.Observable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableChar;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Create by liguo on 2020/7/23
 * Describe:
 */
public class SureOrderAdapter extends BaseBindingAdapter<OrderInfoBuyListBean, AdapterSureOrderBinding> {

    private ArrayList<OrderInfoBuyListBean> orderBeans;
    protected LayoutInflater mInflater;
    // mvvm绑定的viewModel引用
    private int mVariableId;
    ObservableArrayList<Integer> integers = new ObservableArrayList<>();

    public ObservableField<String> orderField = new ObservableField<>();
    public ObservableField<Double> maxpoint = new ObservableField<>();

    public SureOrderAdapter(Context context,double maxpoint) {
        super(context);
        this.maxpoint.set(maxpoint);
    }

    /*public SureOrderAdapter(Context context, ArrayList<OrderInfoBuyListBean> orderBeans, int mVariableId){
        this.context = context;
        this.orderBeans = orderBeans;
        mInflater = LayoutInflater.from(context);
        this.mVariableId = mVariableId;
    }*/

    /*@NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.adapter_sure_order,parent,false);

        ViewHolder viewHolder = new ViewHolder(viewDataBinding.getRoot());
        viewHolder.setBinding(viewDataBinding);
        return viewHolder;
    }*/

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.adapter_sure_order;
    }

    @Override
    protected void onBindItem(AdapterSureOrderBinding binding, OrderInfoBuyListBean item) {
        binding.setOrders(item);
        binding.setSureadapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        OrderChildrenAdapter orderChildrenAdapter = new OrderChildrenAdapter(context);

        orderChildrenAdapter.getItems().addAll(item.getOrderGoodsBuyList());
        binding.rvGoodsOrders.setLayoutManager(linearLayoutManager);
        binding.rvGoodsOrders.setAdapter(orderChildrenAdapter);

        if (maxpoint.get() > item.getIntegral()){
            orderField.set(item.getIntegral()+"");
            maxpoint.set(maxpoint.get() - item.getIntegral());
        }else {
            orderField.set(maxpoint+"");
        }


    }

    @Override
    protected void onclick(int position) {

    }

    public void setItemPoint(){
        /*View view = LayoutInflater.from(context).inflate(R.layout.layout_edittext_point,null);
        final EditText editText = (EditText) view.findViewById(R.id.edit_num);
        editText.setText(orderField.get());
        new AlertDialog.Builder(context).setMessage("修改使用积分").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!editText.getText().toString().isEmpty()){
                    if (Integer.valueOf(editText.getText().toString()) > buyBeans.getUsermodel().getPoint()){
                        UToast.show(context, "超出可使用积分");
                    }else {
                        if (Integer.valueOf(editText.getText().toString()) > buyBeans.getStoremodel().get(position).getMax_use_Point()) {
                            UToast.show(context, "超出最大使用积分");
                        } else {
                            point += Integer.valueOf(editText.getText().toString()) - (int) (Double.parseDouble(holder.mIntegral.getText().toString()));
                            holder.mIntegral.setText(editText.getText().toString() + "");
                            onDataGetFare.onPoint(point);
                        }
                    }
                }
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();*/
    }

    public Typeface setTypeFace(){
        Typeface iconfont = Typeface.createFromAsset(context.getAssets(), "iconfont.ttf");
        return iconfont;
    }

    /*public void onPointClick(){
        View view = LayoutInflater.from(context).inflate(R.layout.layout_edittext_point,null);
        final EditText editText = (EditText) view.findViewById(R.id.edit_num);
        editText.setText(holder.mIntegral.getText().toString());
        new AlertDialog.Builder(context).setMessage("修改使用积分").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!editText.getText().toString().isEmpty()) {
                    if (Integer.valueOf(editText.getText().toString()) > buyBeans.getUsermodel().getPoint()) {
                        UToast.show(context, "超出可使用积分");
                    }else {
                        if (Integer.valueOf(editText.getText().toString()) > buyBeans.getStoremodel().get(position).getMax_use_Point()) {
                            UToast.show(context, "超出此商品所用最大积分");
                            return;
                        } else {
                            point += (int) (Double.valueOf(editText.getText().toString()) - Double.valueOf(holder.mIntegral.getText().toString()));
                            holder.mIntegral.setText(editText.getText().toString() + "");
                            onDataGetFare.onPoint(point, position, Integer.valueOf(editText.getText().toString()));
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
    }*/

    /*@Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setVariable(mVariableId,orderBeans.get(position));
        holder.binding.executePendingBindings();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    }*/

    /*@Override
    public int getItemCount() {
        return null == orderBeans ? 0 : orderBeans.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ViewDataBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }

        public void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }
    }*/
}
