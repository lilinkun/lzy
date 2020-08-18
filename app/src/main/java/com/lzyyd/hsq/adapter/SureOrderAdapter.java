package com.lzyyd.hsq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.bean.CartBean;
import com.lzyyd.hsq.bean.OrderBean;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Create by liguo on 2020/7/23
 * Describe:
 */
public class SureOrderAdapter extends RecyclerView.Adapter<SureOrderAdapter.ViewHolder> {

    private Context context;
    private ArrayList<CartBean> orderBeans;
    protected LayoutInflater mInflater;
    // mvvm绑定的viewModel引用
    private int mVariableId;

    public SureOrderAdapter(Context context,ArrayList<CartBean> orderBeans,int mVariableId){
        this.context = context;
        this.orderBeans = orderBeans;
        mInflater = LayoutInflater.from(context);
        this.mVariableId = mVariableId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.adapter_sure_order,parent,false);

        ViewHolder viewHolder = new ViewHolder(viewDataBinding.getRoot());
        viewHolder.setBinding(viewDataBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setVariable(mVariableId,orderBeans.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
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
    }
}
