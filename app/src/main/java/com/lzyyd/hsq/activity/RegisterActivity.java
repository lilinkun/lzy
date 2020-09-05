package com.lzyyd.hsq.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.bean.WxUserInfo;
import com.lzyyd.hsq.databinding.ActivityRegisterBinding;
import com.lzyyd.hsq.util.Eyes;
import com.lzyyd.hsq.viewmodel.RegisterViewModel;

import androidx.lifecycle.ViewModelProviders;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding,RegisterViewModel> {


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_register;
    }

    @Override
    public int initVariableId() {
        return BR.register;
    }

    @Override
    public RegisterViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(RegisterViewModel.class);
    }

    @Override
    public void initData() {

        Eyes.translucentStatusBar(this);
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,height,0,0);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        binding.ivWelcome.setLayoutParams(layoutParams);

        viewModel.setBinding(binding);

        WxUserInfo wxUserInfo = (WxUserInfo) getIntent().getExtras().getSerializable("wx");
        viewModel.setWxInfo(wxUserInfo);
    }

    @Override
    public void initViewObservable() {

    }


}
