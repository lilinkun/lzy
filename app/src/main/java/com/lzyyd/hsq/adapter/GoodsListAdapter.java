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
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.GoodsListBean;
import com.lzyyd.hsq.bean.HomeItemBean;
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
public class GoodsListAdapter extends RecyclerView.Adapter<GoodsListAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private ArrayList<HomeItemBean> goodsListBeans;
    // mvvm绑定的viewModel引用
    private int mVariableId;
    private OnItemClickListener mItemClickListener;

    public GoodsListAdapter(Context context, ArrayList<HomeItemBean> goodsListBeans, int mVariableId){
       this.context = context;
       this.goodsListBeans = goodsListBeans;
       this.mVariableId = mVariableId;
    }

    @NonNull
    @Override
    public GoodsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.adapter_goodslist,parent,false);

        ViewHolder viewHolder = new ViewHolder(viewDataBinding.getRoot());

        viewHolder.setBinding(viewDataBinding);

        viewDataBinding.getRoot().setOnClickListener(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final GoodsListAdapter.ViewHolder holder, int position) {

//        holder.textView.setText(goodsListBeans.get(position));

        holder.itemView.setTag(position);

        holder.binding.setVariable(mVariableId,goodsListBeans.get(position));
        holder.binding.executePendingBindings();
        holder.binding.setVariable(BR.resImgId,goodsListBeans.get(position).getGoodsImg());

    }

    @Override
    public int getItemCount() {
        return goodsListBeans.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onClick(View v) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClicks((Integer) v.getTag(),goodsListBeans.get((Integer) v.getTag()).getGoodsId());
        }
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }


    public interface OnItemClickListener {
        void onItemClicks(int position,String goodsid);
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
