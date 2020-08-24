package com.lzyyd.hsq.activity;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.adapter.ChuangkeAdapter;
import com.lzyyd.hsq.adapter.GoodsListAdapter;
import com.lzyyd.hsq.adapter.GridHomeAdapter;
import com.lzyyd.hsq.adapter.TabPageAdapter;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.bean.CategoryBean;
import com.lzyyd.hsq.bean.GoodsListBean;
import com.lzyyd.hsq.bean.PageBean;
import com.lzyyd.hsq.databinding.ActivityChuangkeBinding;
import com.lzyyd.hsq.fragment.ChuangkeFragment;
import com.lzyyd.hsq.ui.GridSpacingItemDecoration;
import com.lzyyd.hsq.viewmodel.ChuangkeViewModel;
import com.lzyyd.hsq.viewmodel.PersonalInfoViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

/**
 * Create by liguo on 2020/8/24
 * Describe:
 */
public class ChuangkeActivity extends BaseActivity<ActivityChuangkeBinding, ChuangkeViewModel> implements ChuangkeViewModel.ChuangkeCategoryDateCallBack {

    private List<Fragment> fragments = new ArrayList<>();

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_chuangke;
    }

    @Override
    public int initVariableId() {
        return BR.chuangke;
    }

    @Override
    public ChuangkeViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(ChuangkeViewModel.class);
    }

    @Override
    public void initData() {

        viewModel.setChuangkeCategoryDateCallBack(this);
        viewModel.getCategoryData(1,20);

    }

    @Override
    public void getCategorySuccess(ArrayList<CategoryBean> categoryBeans, PageBean page) {
        ArrayList<String> mTitles = new ArrayList<>();
        ChuangkeFragment chuangkeFragment1 = new ChuangkeFragment("");
        mTitles.add("全部");
        fragments.add(chuangkeFragment1);
        for (CategoryBean categoryBean : categoryBeans){
            ChuangkeFragment chuangkeFragment = new ChuangkeFragment(categoryBean.getCategoryID());
            mTitles.add(categoryBean.getCategoryName());
            fragments.add(chuangkeFragment);
        }

        TabPageAdapter pageAdapter = new TabPageAdapter(getSupportFragmentManager(), fragments, mTitles);
        pageAdapter.setTitles(mTitles);
        binding.chuangkeListVp.setAdapter(pageAdapter);
//        orderListTablayou.setupWithViewPager(orderListVp);
//        orderListTablayou.setSelectedTabIndicatorColor(getResources().getColor(R.color.red));
        binding.orderListTablayou.setViewPager(binding.chuangkeListVp);
        binding.chuangkeListVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

    @Override
    public void getCategoryFail(String msg) {

    }


}
