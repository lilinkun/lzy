package com.lzyyd.hsq.adapter;

import android.content.Context;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.BaseBindingAdapter;
import com.lzyyd.hsq.bean.HomeGridItemBean;
import com.lzyyd.hsq.databinding.AdapterItemHomeBinding;

/**
 * Create by liguo on 2020/7/20
 * Describe:
 */
public class GridHomeAdapter extends BaseBindingAdapter<HomeGridItemBean, AdapterItemHomeBinding>{

    public GridHomeAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.adapter_item_home;
    }

    @Override
    protected void onBindItem(AdapterItemHomeBinding binding, HomeGridItemBean item) {
        binding.setGriditem(item);
    }

    @Override
    protected void onclick(int position) {

    }
}

        /*RecyclerView.Adapter<GridHomeAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private OnItemClickListener mItemClickListener;

    public GridHomeAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_item_home, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.itemView.setTag(position);


        HomeGridRvEnum homeGridRvEnum = HomeGridRvEnum.values()[position];

//        holder.iv_home_grid.setImageResource(homeGridRvEnum.getSrcmsg());

        Picasso.with(context).load(homeGridRvEnum.getMsgAddress()).error(R.mipmap.ic_haosheng).into(holder.iv_home_grid);

        holder.tv_home_grid.setText(homeGridRvEnum.getStatusMsg());

    }

    @Override
    public int getItemCount() {
        return HomeGridRvEnum.values().length;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onClick(View v) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick((Integer) v.getTag());
        }
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_home_grid;
        private TextView tv_home_grid;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_home_grid = itemView.findViewById(R.id.iv_home_grid);
            tv_home_grid = itemView.findViewById(R.id.tv_home_grid);
        }
    }
}

*/