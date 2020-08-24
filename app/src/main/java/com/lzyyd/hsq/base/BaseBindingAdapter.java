package com.lzyyd.hsq.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzyyd.hsq.bean.OrderGoodsBuyListBean;

import androidx.annotation.LayoutRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import me.goldze.mvvmhabit.utils.StringUtils;

public abstract class BaseBindingAdapter<M, B extends ViewDataBinding> extends RecyclerView.Adapter {
    protected Context context;
    protected ObservableArrayList<M> items;


    public BaseBindingAdapter(Context context) {
        this.context = context;
        this.items = new ObservableArrayList<>();
    }

    public ObservableArrayList<M> getItems() {
        return items;
    }

    public class BaseBindingViewHolder extends RecyclerView.ViewHolder {
        public BaseBindingViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        B binding = DataBindingUtil.inflate(LayoutInflater.from(this.context), this.getLayoutResId(viewType), parent, false);
        return new BaseBindingViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        B binding = DataBindingUtil.getBinding(holder.itemView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick(position);
            }
        });

        this.onBindItem(binding, this.items.get(position));
    }

    @LayoutRes
    protected abstract int getLayoutResId(int viewType);

    protected abstract void onBindItem(B binding, M item);

    protected abstract void onclick(int position);

}
