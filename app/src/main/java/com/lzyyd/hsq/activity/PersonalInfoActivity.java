package com.lzyyd.hsq.activity;

import android.os.Bundle;
import android.view.KeyEvent;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.bean.CheckBean;
import com.lzyyd.hsq.databinding.ActivityPersonalInfoBinding;
import com.lzyyd.hsq.util.Eyes;
import com.lzyyd.hsq.viewmodel.PersonalInfoViewModel;

import androidx.lifecycle.ViewModelProviders;

/**
 * Created by LG on 2018/11/19.
 */

public class PersonalInfoActivity extends BaseActivity<ActivityPersonalInfoBinding, PersonalInfoViewModel>{

    private boolean isChangeSuccess = false;

    private CheckBean bean;
    private double code = 0;

    public void initData() {
        Eyes.setStatusBarWhiteColor(this, getResources().getColor(R.color.white));

        viewModel.getVCode(this);



    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isChangeSuccess) {
                setResult(RESULT_OK);
            }
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_personal_info;
    }

    @Override
    public int initVariableId() {
        return BR.personalViewModel;
    }

    @Override
    public PersonalInfoViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(PersonalInfoViewModel.class);
    }
}
