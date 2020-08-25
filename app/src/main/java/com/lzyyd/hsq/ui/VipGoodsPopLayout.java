package com.lzyyd.hsq.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.adapter.ChuangkeAdapter;
import com.lzyyd.hsq.adapter.ChuangkePopAdapter;
import com.lzyyd.hsq.bean.GoodsChooseBean;
import com.lzyyd.hsq.bean.GoodsDetailInfoBean;
import com.lzyyd.hsq.bean.VipChooseItemBean;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Create by liguo on 2020/8/25
 * Describe:
 */
public class VipGoodsPopLayout extends RelativeLayout implements View.OnClickListener {
    private Context context;
    private TextView tv_dustbin;
    private RecyclerView rv_pop_vip;
    private RelativeLayout rl_vip_list_layout;
    private LinearLayout ll_dustbin;
    private ChuangkePopAdapter.ModifyCountInterface callback;

    public VipGoodsPopLayout(Context context) {
        super(context);
        init(context);
    }

    public VipGoodsPopLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VipGoodsPopLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context){
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.popup_vip_goodslist, null);

        Typeface iconfont = Typeface.createFromAsset(context.getAssets(), "iconfont.ttf");

        tv_dustbin = (TextView)view.findViewById(R.id.tv_dustbin);
        tv_dustbin.setTypeface(iconfont);
        rv_pop_vip = (RecyclerView)view.findViewById(R.id.rv_pop_vip);
        ll_dustbin = (LinearLayout)view.findViewById(R.id.ll_dustbin);
        rl_vip_list_layout = (RelativeLayout)view.findViewById(R.id.rl_vip_list_layout);

        rl_vip_list_layout.setOnClickListener(this);
        ll_dustbin.setOnClickListener(this);
        addView(view);
    }

    public void setData(ArrayList<VipChooseItemBean> vipChooseItemBeans, ChuangkePopAdapter.ModifyCountInterface handler){
        ChuangkePopAdapter chuangkeAdapter = new ChuangkePopAdapter(context);
        this.callback = handler;
        chuangkeAdapter.setModifyCountInterface(handler);
        chuangkeAdapter.getItems().addAll(vipChooseItemBeans);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rv_pop_vip.setLayoutManager(linearLayoutManager);
        rv_pop_vip.setAdapter(chuangkeAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_vip_list_layout:

                callback.deleteVip();

                break;

            case R.id.ll_dustbin:

                callback.AllDelete();

                break;
        }
    }


}
