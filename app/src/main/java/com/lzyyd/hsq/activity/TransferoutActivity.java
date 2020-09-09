package com.lzyyd.hsq.activity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.LoginBean;
import com.lzyyd.hsq.databinding.ActivityTransferoutBinding;
import com.lzyyd.hsq.util.Eyes;
import com.lzyyd.hsq.util.HsqAppUtil;
import com.lzyyd.hsq.util.UToast;
import com.lzyyd.hsq.viewmodel.TransferoutViewModel;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;
import me.goldze.mvvmhabit.utils.StringUtils;

/**
 * Create by liguo on 2020/8/29
 * Describe:
 */
public class TransferoutActivity extends BaseActivity<ActivityTransferoutBinding, TransferoutViewModel> implements TransferoutViewModel.OnTransferoutListener {

    Handler handler = new Handler();
    Runnable runnable;


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_transferout;
    }

    @Override
    public int initVariableId() {
        return BR.transferout;
    }

    @Override
    public void initData() {
        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));

        String balance = getIntent().getExtras().getString("balance");

        viewModel.amountPoint.set(balance);

        SharedPreferences sharedPreferences = getSharedPreferences(HsqAppUtil.LOGIN, MODE_PRIVATE);

        binding.setEleName(sharedPreferences.getString(HsqAppUtil.USERNAME, ""));

        viewModel.setListener(this);

        binding.etReceiver.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!StringUtils.isEmpty(binding.etReceiver.getText().toString())) {
                        viewModel.setFindUsername(binding.etReceiver.getText().toString(), ProApplication.SESSIONID());
                    }
                }
            }
        });

    }

    @Override
    public TransferoutViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(TransferoutViewModel.class);
    }

    @Override
    public void getTransferoutSuccess(String msg) {
        UToast.show(this,msg);
        double oldBalance = Double.valueOf(viewModel.amountPoint.get());
        double transferoutPrice = Double.valueOf(viewModel.transferAmount.get());
        viewModel.amountPoint.set(String.valueOf(oldBalance - transferoutPrice));
    }

    @Override
    public void getTransferoutFail(String msg) {
        UToast.show(this,msg);
    }

    @Override
    public void getUserInfoSuccess(LoginBean msg) {
        binding.tvTransfername.setText(msg.getNickName());
    }

    @Override
    public void getUserInfoFail(String msg) {
        binding.tvTransfername.setText("");
        UToast.show(this,msg);
    }

    @Override
    public void clickTransferout() {
        if (StringUtils.isEmpty(viewModel.ReceiverName.get())){
            UToast.show(this,"请输入接收人");
        }else if (StringUtils.isEmpty(viewModel.transferAmount.get())){
            UToast.show(this,"请输入转出金额");
        }else if (StringUtils.isEmpty(viewModel.payPassword.get())){
            UToast.show(this,"请输入密码");
        }else {

            new AlertDialog.Builder(this).setMessage("您确认向" + viewModel.ReceiverName.get() + "转出" + viewModel.transferAmount.get() + "电子币").setNegativeButton("取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    viewModel.setTransferout(viewModel.transferAmount.get(), viewModel.payPassword.get(), viewModel.ReceiverName.get(), "2", ProApplication.SESSIONID());
                }
            }).create().show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            setResult(RESULT_OK);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
