package com.lzyyd.hsq.activity;

import android.graphics.Color;
import android.os.Bundle;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.databinding.ActivityGoodslistBinding;
import com.lzyyd.hsq.util.Eyes;
import com.lzyyd.hsq.viewmodel.GoodsListViewModel;

import androidx.annotation.Nullable;

public class GoodsListActivity extends BaseActivity<ActivityGoodslistBinding, GoodsListViewModel> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Eyes.setStatusBarLightMode(this, Color.parseColor("#FFFFFF"));

    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_goodslist;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public GoodsListViewModel initViewModel() {
        GoodsListViewModel goodsListViewModel = new GoodsListViewModel(this.getApplication());
        return goodsListViewModel;
    }
}
