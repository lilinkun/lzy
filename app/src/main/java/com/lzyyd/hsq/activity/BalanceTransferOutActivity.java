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
import com.lzyyd.hsq.databinding.ActivityBalancetransferoutBinding;
import com.lzyyd.hsq.util.HsqAppUtil;
import com.lzyyd.hsq.util.UToast;
import com.lzyyd.hsq.viewmodel.BalanceTransferoutViewModel;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;
import me.goldze.mvvmhabit.utils.StringUtils;

/**
 * Create by liguo on 2020/8/31
 * Describe:
 */
public class BalanceTransferOutActivity extends BaseActivity<ActivityBalancetransferoutBinding, BalanceTransferoutViewModel> implements BalanceTransferoutViewModel.OnTransferoutListener {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_balancetransferout;
    }

    @Override
    public int initVariableId() {
        return BR.balance_transferout;
    }


    @Override
    public BalanceTransferoutViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(BalanceTransferoutViewModel.class);
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
    public void clickTransferout() {
        if (StringUtils.isEmpty(viewModel.transferAmount.get())){
            UToast.show(this,"请输入转出金额");
        }else if (StringUtils.isEmpty(viewModel.payPassword.get())){
            UToast.show(this,"请输入密码");
        }else {

            new AlertDialog.Builder(this).setMessage("您确认转" + viewModel.transferAmount.get() + "余额到电子币").setNegativeButton("取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    viewModel.setBalanceTransferout(viewModel.transferAmount.get(), viewModel.payPassword.get(),"0","1",  ProApplication.SESSIONID());
                }
            }).create().show();
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

    @Override
    public void initData() {

        double balance = getIntent().getExtras().getDouble("balance");
        viewModel.amountPoint.set(balance+"");
        SharedPreferences sharedPreferences = getSharedPreferences(HsqAppUtil.LOGIN, MODE_PRIVATE);
        binding.setEleName(sharedPreferences.getString(HsqAppUtil.USERNAME, ""));
        viewModel.setListener(this);
    }
}
