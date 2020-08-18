package com.lzyyd.hsq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.bean.AddressBean;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Create by liguo on 2020/8/17
 * Describe:
 */
public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    private Context context;
    private ArrayList<AddressBean> addressBeans;
    private int mVariableId;

    public AddressAdapter(Context context,ArrayList<AddressBean> addressBeans,int mVariableId){
        this.context = context;
        this.addressBeans = addressBeans;
        this.mVariableId = mVariableId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_addresslist,parent,false);

        ViewHolder viewHolder = new ViewHolder(viewDataBinding.getRoot());

        viewHolder.setBinding(viewDataBinding);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.binding.setVariable(mVariableId,addressBeans.get(position));
        holder.binding.executePendingBindings();

    }

    @Override
    public int getItemCount() {
        return addressBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ViewDataBinding binding;

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
