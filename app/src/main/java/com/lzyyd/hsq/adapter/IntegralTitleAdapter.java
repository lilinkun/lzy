package com.lzyyd.hsq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.util.DensityUtil;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Create by liguo on 2020/8/3
 * Describe:
 */
public class IntegralTitleAdapter extends RecyclerView.Adapter<IntegralTitleAdapter.ViewHolder> {

    private Context context;

    public IntegralTitleAdapter(Context context){
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_integral_title,parent,false);


        int w  = DensityUtil.getScreenWidth(context) / 4;

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = w;
        view.setLayoutParams(layoutParams);


        ViewHolder viewHolder = new ViewHolder(view);



        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_integral_grid;
        private TextView tv_integral_grid;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_integral_grid = itemView.findViewById(R.id.iv_integral_grid);
            tv_integral_grid = itemView.findViewById(R.id.tv_integral_grid);
        }
    }
}
