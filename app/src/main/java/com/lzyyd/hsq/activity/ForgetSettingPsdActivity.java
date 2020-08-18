package com.lzyyd.hsq.activity;

import android.os.Bundle;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.databinding.ActivityForgetSettingPsdBinding;
import com.lzyyd.hsq.util.Eyes;
import com.lzyyd.hsq.viewmodel.ForgetSettingPsdViewModel;

import com.lzyyd.hsq.base.BaseActivity;


public class ForgetSettingPsdActivity extends BaseActivity<ActivityForgetSettingPsdBinding,ForgetSettingPsdViewModel> {


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_forget_setting_psd;
    }

    @Override
    public int initVariableId() {
        return BR.forgetpsd;
    }

    @Override
    public void initData() {
        super.initData();
        Eyes.setStatusBarColor(this,getResources().getColor(R.color.white));
    }

    @Override
    public ForgetSettingPsdViewModel initViewModel() {
        ForgetSettingPsdViewModel settingPsdViewModel = new ForgetSettingPsdViewModel(this.getApplication(),binding);
        return settingPsdViewModel;
    }
}
