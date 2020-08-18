package com.lzyyd.hsq.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.adapter.FragmentsAdapter;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.base.BaseFragment;
import com.lzyyd.hsq.databinding.ActivityMainBinding;
import com.lzyyd.hsq.fragment.HomeFragment;
import com.lzyyd.hsq.fragment.MeFragment;
import com.lzyyd.hsq.fragment.ShoppingCartFragment;
import com.lzyyd.hsq.util.Eyes;
import com.lzyyd.hsq.util.HsqAppUtil;
import com.lzyyd.hsq.viewmodel.MVVMViewModel;

import androidx.viewpager.widget.ViewPager;

public class MainActivity extends BaseActivity<ActivityMainBinding, MVVMViewModel> {

    private final SparseArray<BaseFragment> sparseArray = new SparseArray<>();

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {

        FragmentsAdapter adapter = new FragmentsAdapter(getSupportFragmentManager());
        getMenusFragments();
        adapter.setFragments(sparseArray);
        binding.topVp.setAdapter(adapter);
        Eyes.translucentStatusBar(MainActivity.this);

        binding.topVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                    RadioButton radioButton = (RadioButton)binding.rgMain.getChildAt(position);
                    radioButton.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        binding.rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rbChat:

                        binding.topVp.setCurrentItem(0,false);
                        break;

                    case R.id.rbMe:
                        binding.topVp.setCurrentItem(2,false);
                        break;

                    case R.id.rbAddress:
                        binding.topVp.setCurrentItem(1,false);
                        break;
                }
            }
        });

    }

    @Override
    public MVVMViewModel initViewModel() {
        MVVMViewModel mvvmViewModel = new MVVMViewModel(getApplication(),binding);
        return mvvmViewModel;
    }

    private void getMenusFragments() {
        sparseArray.put(HsqAppUtil.PAGE_HOMEPAGE, new HomeFragment());
        sparseArray.put(HsqAppUtil.PAGE_FIND, new ShoppingCartFragment());
        sparseArray.put(HsqAppUtil.PAGE_MALL,new MeFragment());
    }
}
