package com.lzyyd.hsq.activity;

import android.graphics.Color;
import android.os.Bundle;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.adapter.SeckillAdapter;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.bean.SeckillGoodsBean;
import com.lzyyd.hsq.databinding.ActivitySeckillBinding;
import com.lzyyd.hsq.util.Eyes;
import com.lzyyd.hsq.viewmodel.SeckillViewModel;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SeckillActivity extends BaseActivity<ActivitySeckillBinding,SeckillViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_seckill;
    }

    @Override
    public int initVariableId() {
        return BR.seckillviewmodel;
    }

    @Override
    public SeckillViewModel initViewModel() {
        SeckillViewModel seckillViewModel = new SeckillViewModel(this.getApplication(),binding);
        return seckillViewModel;
    }

    public void initData(){

        Eyes.setStatusBarColor1(this, Color.parseColor("#FF3C38"));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        SeckillAdapter seckillAdapter = new SeckillAdapter(this);
        seckillAdapter.getItems().add(new SeckillGoodsBean("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/" +
                "it/u=1338127215,2214734562&fm=26&gp=0.jpg","adsadsasdads","2020-07-08 11:11:11",60));
        seckillAdapter.getItems().add(new SeckillGoodsBean("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/" +
                "it/u=1338127215,2214734562&fm=26&gp=0.jpg","adsadsasdads","2020-07-08 11:11:11",60));
        seckillAdapter.getItems().add(new SeckillGoodsBean("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/" +
                "it/u=1338127215,2214734562&fm=26&gp=0.jpg","adsadsasdads","2020-07-08 11:11:11",60));


        RecyclerView recyclerView = binding.recyclerSeckill;

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(seckillAdapter);

    }

}
