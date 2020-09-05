package com.lzyyd.hsq.activity;

import android.os.Bundle;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.bean.WxUserInfo;
import com.lzyyd.hsq.databinding.ActivityChooseBindwxBinding;
import com.lzyyd.hsq.util.ActivityUtil;
import com.lzyyd.hsq.viewmodel.ChooseWxLoginViewModel;

import androidx.lifecycle.ViewModelProviders;

/**
 * Create by liguo on 2020/9/5
 * Describe:
 */
public class ChooseWxLoginActivity extends BaseActivity<ActivityChooseBindwxBinding, ChooseWxLoginViewModel> {

    private WxUserInfo wxUserInfo;


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_choose_bindwx;
    }

    @Override
    public int initVariableId() {
        return BR.choosewx;
    }

    @Override
    public ChooseWxLoginViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(ChooseWxLoginViewModel.class);
    }

    @Override
    public void initData() {
        ActivityUtil.addActivity(this);
        wxUserInfo = (WxUserInfo) getIntent().getExtras().getSerializable("wx");
        binding.setChoose(this);
    }

    public void setJumpRegister(){
        Bundle bundle = new Bundle();
        bundle.putSerializable("wx", wxUserInfo);
        startActivity(RegisterActivity.class,bundle,0x1234);
    }

    public void setJumpBindWx(){
        Bundle bundle = new Bundle();
        bundle.putSerializable("wx", wxUserInfo);
        startActivity(BindWxActivity.class,bundle,0x1234);
    }
}
