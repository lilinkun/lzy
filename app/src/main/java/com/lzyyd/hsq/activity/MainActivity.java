package com.lzyyd.hsq.activity;

import android.os.Bundle;
import android.util.SparseArray;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.adapter.FragmentsAdapter;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.base.BaseFragment;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.UrlBean;
import com.lzyyd.hsq.databinding.ActivityMainBinding;
import com.lzyyd.hsq.fragment.HomeFragment;
import com.lzyyd.hsq.fragment.MeFragment;
import com.lzyyd.hsq.fragment.ShoppingCartFragment;
import com.lzyyd.hsq.fragment.ZunXiangFragment;
import com.lzyyd.hsq.interf.IGetSqlListener;
import com.lzyyd.hsq.util.ActivityUtil;
import com.lzyyd.hsq.util.Eyes;
import com.lzyyd.hsq.util.HsqAppUtil;
import com.lzyyd.hsq.util.UToast;
import com.lzyyd.hsq.viewmodel.MVVMViewModel;

import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends BaseActivity<ActivityMainBinding, MVVMViewModel> implements MVVMViewModel.LoginCallBack, IGetSqlListener {

    private final SparseArray<BaseFragment> sparseArray = new SparseArray<>();

    private ShoppingCartFragment shoppingCartFragment = new ShoppingCartFragment();
    private ZunXiangFragment zunXiangFragment = new ZunXiangFragment();
    private MeFragment meFragment = new MeFragment();

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData(){
        ActivityUtil.addHomeActivity(this);
        FragmentsAdapter adapter = new FragmentsAdapter(getSupportFragmentManager());
        getMenusFragments();
        adapter.setFragments(sparseArray);
        binding.topVp.setAdapter(adapter);
        binding.topVp.setOffscreenPageLimit(4);
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
                        binding.topVp.setCurrentItem(3,false);
                        shoppingCartFragment.setUpdate();
                        break;

                    case R.id.rbAddress:
                        binding.topVp.setCurrentItem(2,false);
                        break;

                    case R.id.zunxiang:
                        if (ProApplication.ISUSEQSQ == 1) {
                            zunXiangFragment.initUrl();
                        }
                        binding.topVp.setCurrentItem(1,false);
                        break;
                }
            }
        });

    }


    @Override
    public MVVMViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(MVVMViewModel.class);
    }

    private void getMenusFragments() {
        sparseArray.put(HsqAppUtil.PAGE_HOMEPAGE, new HomeFragment());
        sparseArray.put(HsqAppUtil.PAGE_FIND, zunXiangFragment);
        sparseArray.put(HsqAppUtil.PAGE_MALL, shoppingCartFragment);
        sparseArray.put(HsqAppUtil.PAGE_ME,meFragment);
        meFragment.initListener(this);
    }

    @Override
    public void getUrlSuccess(UrlBean urlBean) {

    }

    @Override
    public void getUrlFail(String msg) {
        UToast.show(this,msg);
    }

    @Override
    public void getDataStr() {
        zunXiangFragment.initUrl();
    }
}
