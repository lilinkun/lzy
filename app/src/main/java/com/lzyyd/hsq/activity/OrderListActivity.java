package com.lzyyd.hsq.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.adapter.SelfOrderAdapter;
import com.lzyyd.hsq.adapter.TabPageAdapter;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.OrderListBean;
import com.lzyyd.hsq.databinding.ActivityOrderlistBinding;
import com.lzyyd.hsq.fragment.OrderAllFragment;
import com.lzyyd.hsq.util.Eyes;
import com.lzyyd.hsq.util.UToast;
import com.lzyyd.hsq.viewmodel.OrderListViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

/**
 * Create by liguo on 2020/8/12
 * Describe:
 */
public class OrderListActivity extends BaseActivity<ActivityOrderlistBinding, OrderListViewModel> {

    private List<String> mTitles;
    private List<Fragment> fragments = new ArrayList<>();
    private OrderAllFragment allOrderFragment;
    public static final int ORDERRESULT = 0x322;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_orderlist;
    }

    @Override
    public int initVariableId() {
        return BR.orderlist;
    }

    @Override
    public OrderListViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(OrderListViewModel.class);
    }

    @Override
    public void initData() {

        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));


        mTitles = new ArrayList<>();
        mTitles.add("全部");
        mTitles.add("待付款");
        mTitles.add("待发货");
        mTitles.add("待收货");
        mTitles.add("交易成功");

        for (int i = 0; i < mTitles.size(); i++){
            allOrderFragment = new OrderAllFragment(i);
            fragments.add(allOrderFragment);
        }


        TabPageAdapter pageAdapter = new TabPageAdapter(getSupportFragmentManager(), fragments, mTitles);
        pageAdapter.setTitles(mTitles);
        binding.orderListVp.setAdapter(pageAdapter);
//        orderListTablayou.setupWithViewPager(orderListVp);
//        orderListTablayou.setSelectedTabIndicatorColor(getResources().getColor(R.color.red));
        binding.orderListTablayou.setViewPager(binding.orderListVp);
        binding.orderListVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((OrderAllFragment)fragments.get(position)).setItemsNoti();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int position = bundle.getInt("position");
        binding.orderListVp.setCurrentItem(position,false);

    }

}
