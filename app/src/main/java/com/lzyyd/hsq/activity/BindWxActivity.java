package com.lzyyd.hsq.activity;

import android.os.Bundle;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.WxUserInfo;
import com.lzyyd.hsq.databinding.ActivityBindwxBinding;
import com.lzyyd.hsq.util.ActivityUtil;
import com.lzyyd.hsq.util.Eyes;
import com.lzyyd.hsq.util.UToast;
import com.lzyyd.hsq.viewmodel.BindWxViewModel;
import com.lzyyd.hsq.viewmodel.LoginViewModel;

import androidx.lifecycle.ViewModelProviders;
import me.goldze.mvvmhabit.utils.StringUtils;

/**
 * Create by liguo on 2020/9/5
 * Describe:
 */
public class BindWxActivity extends BaseActivity<ActivityBindwxBinding, LoginViewModel> {

    private WxUserInfo wxUserInfo;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_bindwx;
    }

    @Override
    public int initVariableId() {
        return BR.bindwx;
    }

    @Override
    public LoginViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(LoginViewModel.class);
    }


    @Override
    public void initData() {
        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));

        ActivityUtil.addActivity(this);

        wxUserInfo = (WxUserInfo) getIntent().getExtras().getSerializable("wx");
        binding.setBind(this);
    }

    public void setBind(){

        if (StringUtils.isEmpty(viewModel.usernameField.get())){
            UToast.show(this,"请输入用户名");
        }else if (StringUtils.isEmpty(viewModel.passWordField.get())){
            UToast.show(this,"请输入密码");
        }else {
            viewModel.setBingWx(viewModel.usernameField.get(),wxUserInfo.getOpenid(),wxUserInfo.getUnionid(),wxUserInfo.getNickname(),viewModel.passWordField.get(),
                    wxUserInfo.getHeadimgurl(), ProApplication.SESSIONID(),"2");
        }

    }
}
