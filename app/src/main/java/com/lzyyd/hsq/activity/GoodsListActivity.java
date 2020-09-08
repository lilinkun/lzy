package com.lzyyd.hsq.activity;

import android.graphics.Color;
import android.os.Bundle;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.adapter.GoodsListAdapter;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.GoodsListBean;
import com.lzyyd.hsq.bean.PageBean;
import com.lzyyd.hsq.databinding.ActivityGoodslistBinding;
import com.lzyyd.hsq.ui.GridSpacingItemDecoration;
import com.lzyyd.hsq.util.Eyes;
import com.lzyyd.hsq.util.UToast;
import com.lzyyd.hsq.viewmodel.GoodsListViewModel;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class GoodsListActivity extends BaseActivity<ActivityGoodslistBinding, GoodsListViewModel> implements GoodsListViewModel.GetGoodsListCallBack {

    private GoodsListAdapter goodsListAdapter;
    private boolean isPrice = false;

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
        return BR.goodslist;
    }

    @Override
    public GoodsListViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(GoodsListViewModel.class);
    }

    @Override
    public void initData() {
        binding.setGoodslistactivity(this);
        viewModel.setListener(this);
        viewModel.getGoodsListData(1,20,1,"0", ProApplication.SESSIONID());

    }

    @Override
    public void getDataSuccess(ArrayList<GoodsListBean> goodsListBeans, PageBean page) {

        if (goodsListAdapter == null) {
            goodsListAdapter = new GoodsListAdapter(this,1);
            goodsListAdapter.getItems().addAll(goodsListBeans);
            StaggeredGridLayoutManager gridLayoutManager1 = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            binding.rvGoodsList.addItemDecoration(new GridSpacingItemDecoration(2, 20, false));
//        gridLayoutManager1.setOrientation(GridLayoutManager.VERTICAL);
            binding.rvGoodsList.setLayoutManager(gridLayoutManager1);
            binding.rvGoodsList.setAdapter(goodsListAdapter);
        }else {
            goodsListAdapter.getItems().clear();
            goodsListAdapter.getItems().addAll(goodsListBeans);
            goodsListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getDataFail(String msg) {
        UToast.show(this,msg);
    }


    public void clickSort(int position){
        switch (position){
            case 1:

                viewModel.getGoodsListData(1,20,1,"0", ProApplication.SESSIONID());

                binding.txPople.setTextColor(Color.parseColor("#E43A3C"));
                binding.txTop.setTextColor(Color.parseColor("#232323"));
                binding.txPrice.setTextColor(Color.parseColor("#232323"));
                binding.txNewest.setTextColor(Color.parseColor("#232323"));

                isPrice = false;

                binding.img1.setImageResource(R.mipmap.j_1);
                binding.img2.setImageResource(R.mipmap.j_2);
                break;


            case 2:

                viewModel.getGoodsListData(1,20,1,"2", ProApplication.SESSIONID());

                binding.txTop.setTextColor(Color.parseColor("#E43A3C"));
                binding.txPople.setTextColor(Color.parseColor("#232323"));
                binding.txPrice.setTextColor(Color.parseColor("#232323"));
                binding.txNewest.setTextColor(Color.parseColor("#232323"));

                binding.img1.setImageResource(R.mipmap.j_1);
                binding.img2.setImageResource(R.mipmap.j_2);
                isPrice = false;
                break;

            case 3:

                isPrice = !isPrice;
                if (isPrice) {
                    viewModel.getGoodsListData(1, 20, 1, "3", ProApplication.SESSIONID());
                    binding.img1.setImageResource(R.mipmap.j_1_1);
                    binding.img2.setImageResource(R.mipmap.j_2);

                }else {
                    viewModel.getGoodsListData(1, 20, 1, "4", ProApplication.SESSIONID());
                    binding.img1.setImageResource(R.mipmap.j_1);
                    binding.img2.setImageResource(R.mipmap.j_2_1);
                }

                binding.txPrice.setTextColor(Color.parseColor("#E43A3C"));
                binding.txTop.setTextColor(Color.parseColor("#232323"));
                binding.txPople.setTextColor(Color.parseColor("#232323"));
                binding.txNewest.setTextColor(Color.parseColor("#232323"));

                break;

            case 4:

                viewModel.getGoodsListData(1,20,1,"5", ProApplication.SESSIONID());

                binding.txNewest.setTextColor(Color.parseColor("#E43A3C"));
                binding.txTop.setTextColor(Color.parseColor("#232323"));
                binding.txPrice.setTextColor(Color.parseColor("#232323"));
                binding.txPople.setTextColor(Color.parseColor("#232323"));

                binding.img1.setImageResource(R.mipmap.j_1);
                binding.img2.setImageResource(R.mipmap.j_2);
                isPrice = false;

                break;
        }
    }

}
