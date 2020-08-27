package com.lzyyd.hsq.activity;

import android.os.Bundle;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.adapter.TabPageAdapter;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.databinding.ActivityCcqBinding;
import com.lzyyd.hsq.fragment.CcqFragment;
import com.lzyyd.hsq.util.Eyes;
import com.lzyyd.hsq.viewmodel.CcqViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

/**
 * Create by liguo on 2020/8/27
 * Describe:
 */
public class CcqActivity extends BaseActivity<ActivityCcqBinding, CcqViewModel> {

    private List<String> mTitles;
    private CcqFragment ccqFragment;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_ccq;
    }

    @Override
    public int initVariableId() {
        return BR.ccq;
    }

    @Override
    public CcqViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(CcqViewModel.class);
    }

    @Override
    public void initData() {
        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));

        mTitles = new ArrayList<>();
        mTitles.add("未使用");
        mTitles.add("已使用");
        mTitles.add("已失效");

        for (int i = 0; i < mTitles.size(); i++){
            ccqFragment = new CcqFragment(i);
            fragments.add(ccqFragment);
        }


        TabPageAdapter pageAdapter = new TabPageAdapter(getSupportFragmentManager(), fragments, mTitles);
        pageAdapter.setTitles(mTitles);
        binding.ccqListVp.setAdapter(pageAdapter);
//        orderListTablayou.setupWithViewPager(orderListVp);
//        orderListTablayou.setSelectedTabIndicatorColor(getResources().getColor(R.color.red));
        binding.ccqListTablayou.setViewPager(binding.ccqListVp);
        binding.ccqListVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
