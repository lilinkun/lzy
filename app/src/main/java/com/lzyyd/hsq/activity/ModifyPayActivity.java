package com.lzyyd.hsq.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.databinding.ActivityModifypayBinding;
import com.lzyyd.hsq.util.HsqAppUtil;
import com.lzyyd.hsq.util.PhoneFormatCheckUtils;
import com.lzyyd.hsq.util.UToast;
import com.lzyyd.hsq.viewmodel.ModifyPayViewModel;
import com.lzyyd.hsq.viewmodel.MyQrcodeViewModel;

import androidx.lifecycle.ViewModelProviders;

/**
 * Create by liguo on 2020/8/31
 * Describe:
 */
public class ModifyPayActivity extends BaseActivity<ActivityModifypayBinding, ModifyPayViewModel> implements ModifyPayViewModel.ModifyPsdListener {

    MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000, 1000);

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_modifypay;
    }

    @Override
    public int initVariableId() {
        return BR.modify;
    }

    @Override
    public ModifyPayViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(ModifyPayViewModel.class);
    }

    @Override
    public void initData() {
        SharedPreferences sharedPreferences = getSharedPreferences(HsqAppUtil.LOGIN, MODE_PRIVATE);
        String loginInfo = sharedPreferences.getString(HsqAppUtil.TELEPHONE, "");
        binding.tvPhone.setText(PhoneFormatCheckUtils.phoneAddress(loginInfo));

        viewModel.setListener(this);

    }

    @Override
    public void onSendVcodeSuccess() {
        UToast.show(this,"获取验证码成功");
    }

    @Override
    public void onSendVcodeFail(String str) {
        UToast.show(this,str);
    }

    @Override
    public void modifySuccess() {
        UToast.show(this,"修改成功");
        finish();
    }

    @Override
    public void modifyFail(String str) {
        UToast.show(this,str);
    }

    @Override
    public void sendVcode() {
        viewModel.SendSms(ProApplication.SESSIONID());
        myCountDownTimer.start();
    }

    @Override
    public void onSure() {
        if (binding.evVcode.getText().toString().isEmpty()) {
            UToast.show(this,"验证码不能为空");
        } else if (binding.evNewPsd.getText().toString().isEmpty()) {
            UToast.show(this,"新密码不能为空");
        } else if (binding.evSurePsd.getText().toString().isEmpty()) {
            UToast.show(this,"确认密码不能为空");
        } else if (!binding.evNewPsd.getText().toString().equals(binding.evSurePsd.getText().toString())) {
            UToast.show(this,"两次密码不同，请确认");
        } else {

            viewModel.modifyPsd(binding.evVcode.getText().toString(), binding.evNewPsd.getText().toString(), binding.evSurePsd.getText().toString(), ProApplication.SESSIONID());

        }
    }

    /**
     * 获取验证码倒计时
     */
    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程
        @Override
        public void onTick(long l) {
            //防止计时过程中重复点击
            binding.tvSendVcode.setClickable(false);
            binding.tvSendVcode.setText(l / 1000 + "s");
        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            //重新给Button设置文字
            binding.tvSendVcode.setText(R.string.tv_forget_get_vcode);
            //设置可点击
            binding.tvSendVcode.setClickable(true);

            binding.tvSendVcode.setTextColor(getResources().getColor(R.color.register_vcode_bg));
        }
    }


}
