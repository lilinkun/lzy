package com.lzyyd.hsq.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.BalanceBean;
import com.lzyyd.hsq.bean.UserBankBean;
import com.lzyyd.hsq.databinding.ActivityGetcashBinding;
import com.lzyyd.hsq.util.Eyes;
import com.lzyyd.hsq.util.UToast;
import com.lzyyd.hsq.viewmodel.GetCashViewModel;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;
import me.goldze.mvvmhabit.utils.StringUtils;

/**
 * Create by liguo on 2020/8/11
 * Describe:
 */
public class GetCashActivity extends BaseActivity<ActivityGetcashBinding, GetCashViewModel> implements GetCashViewModel.OnCashListener {

    private BalanceBean balanceBean;
    private AlertDialog dialog;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_getcash;
    }

    @Override
    public int initVariableId() {
        return BR.getcash;
    }

    @Override
    public GetCashViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(GetCashViewModel.class);
    }

    @Override
    public void initData() {
        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));

        balanceBean = (BalanceBean) getIntent().getExtras().getSerializable("balance");
        binding.setBalance(balanceBean);

        UserBankBean bankBean = (UserBankBean) getIntent().getExtras().getSerializable("bankBean");

        String bankStr = bankBean.getBankNo().substring(bankBean.getBankNo().length() - 4, bankBean.getBankNo().length());
        binding.tvBankName.setText(bankBean.getBankNameDesc() + "(" + bankStr + ")");

        viewModel.setListener(this);

    }

    @Override
    public void getCashSuccess() {
        UToast.show(this,"提现成功");
        dialog.dismiss();
    }

    @Override
    public void getCashFail(String msg) {
        UToast.show(this,msg);
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
    public void onClickBtn() {
        if (Double.valueOf(binding.etHsqCoin.getText().toString()) > balanceBean.getMoney5Balance()) {
            UToast.show(this,"提现金额大于余额，不能提现");
            return;
        }

        if (Double.valueOf(binding.etHsqCoin.getText().toString()) <= 0) {
            UToast.show(this,"提现金额必须大于0");
            return;
        }

        AlertDialog.Builder alertDialog_Builder = new AlertDialog.Builder(this);
        View viewDialog = LayoutInflater.from(this).inflate(R.layout.dialog_pay, null);
        dialog = alertDialog_Builder.create();

        dialog.setView(viewDialog);

        dialog.show();

        EditText dialog_pay = (EditText) viewDialog.findViewById(R.id.et_password);

        TextView exitBtn = (TextView) viewDialog.findViewById(R.id.tv_exit);

        TextView sureBtn = (TextView) viewDialog.findViewById(R.id.tv_sure);


        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isEmpty(dialog_pay.getText().toString())){
                    UToast.show(GetCashActivity.this,"请输入密码");
                }else {
                    viewModel.getCash(binding.etHsqCoin.getText().toString(),"0",dialog_pay.getText().toString(), ProApplication.SESSIONID());
                }
            }
        });





    }
}
