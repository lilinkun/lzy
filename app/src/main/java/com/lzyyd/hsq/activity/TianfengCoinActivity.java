package com.lzyyd.hsq.activity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.databinding.ActivityTianfengBinding;
import com.lzyyd.hsq.util.Eyes;
import com.lzyyd.hsq.util.HsqAppUtil;
import com.lzyyd.hsq.util.UToast;
import com.lzyyd.hsq.viewmodel.TianfengCoinViewModel;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;
import me.goldze.mvvmhabit.utils.StringUtils;

/**
 * Create by liguo on 2020/8/31
 * Describe:
 */
public class TianfengCoinActivity extends BaseActivity<ActivityTianfengBinding, TianfengCoinViewModel> implements TianfengCoinViewModel.OnTransferoutListener {

    private String tianfengBalance ;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_tianfeng;
    }

    @Override
    public int initVariableId() {
        return BR.tianfeng;
    }

    @Override
    public TianfengCoinViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(TianfengCoinViewModel.class);
    }

    @Override
    public void initData() {

        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));

        viewModel.setListener(this);
        viewModel.setTianfengbalance(ProApplication.SESSIONID());

        double balance = getIntent().getExtras().getDouble("balance");
        viewModel.amountPoint.set(balance+"");
        SharedPreferences sharedPreferences = getSharedPreferences(HsqAppUtil.LOGIN, MODE_PRIVATE);
        binding.setEleName(sharedPreferences.getString(HsqAppUtil.USERNAME, ""));
        viewModel.setListener(this);

    }

    @Override
    public void getTransferoutSuccess(String msg) {
        UToast.show(this,msg);
    }

    @Override
    public void getTransferoutFail(String msg) {
        UToast.show(this,msg);
    }

    @Override
    public void getTianfengBalanceSuccess(String msg) {
        tianfengBalance = msg;
        viewModel.tianfengbalance.set(msg);
    }

    @Override
    public void getTianfengBalanceFail(String msg) {
        UToast.show(this,msg);
    }

    @Override
    public void clickTransferout() {

        if (tianfengBalance != null && !StringUtils.isEmpty(tianfengBalance)) {

            if (StringUtils.isEmpty(viewModel.transferAmount.get())) {
                UToast.show(this, "请输入转出金额");
            } else if (StringUtils.isEmpty(viewModel.payPassword.get())) {
                UToast.show(this, "请输入密码");
            } else {

                new AlertDialog.Builder(this).setMessage("您确认向转入" + viewModel.transferAmount.get() + "电子币").setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewModel.setTransferout(viewModel.transferAmount.get(), viewModel.payPassword.get(), "0", "0", ProApplication.SESSIONID());
                    }
                }).create().show();
            }
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(RESULT_OK);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
