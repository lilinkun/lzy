package com.lzyyd.hsq.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.adapter.WalletAdapter;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.BalanceBean;
import com.lzyyd.hsq.bean.BalanceDetailBean;
import com.lzyyd.hsq.databinding.ActivityPointBinding;
import com.lzyyd.hsq.ui.SpacesItemDecoration;
import com.lzyyd.hsq.util.UToast;
import com.lzyyd.hsq.viewmodel.WalletViewModel;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Create by liguo on 2020/8/28
 * Describe:
 */
public class PointActivity extends BaseActivity<ActivityPointBinding, WalletViewModel> implements WalletViewModel.OnWalletListener {

    public static int foresult = 0x212;
    public static int RECHARGEFORESULT = 0x213;

    private WalletAdapter walletAdapter;
    private BalanceBean balanceBean;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_point;
    }

    @Override
    public int initVariableId() {
        return BR.point;
    }

    @Override
    public WalletViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(WalletViewModel.class);
    }

    @Override
    public void initData() {

        viewModel.setListener(this);
        viewModel.getBalance(ProApplication.SESSIONID());
        viewModel.getPriceData("1","20","2", ProApplication.SESSIONID());

    }

    @Override
    public void getDataSuccess(ArrayList<BalanceDetailBean> balanceDetailBeans) {

        if (walletAdapter == null) {

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

            walletAdapter = new WalletAdapter(this);
            binding.rvPoint.setLayoutManager(linearLayoutManager);
            binding.rvPoint.setAdapter(walletAdapter);
            walletAdapter.getItems().addAll(balanceDetailBeans);

        }else {
            walletAdapter.getItems().clear();
            walletAdapter.getItems().addAll(balanceDetailBeans);
        }
    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void getBalanceSuccess(BalanceBean balanceBean) {
        this.balanceBean = balanceBean;
        binding.setBalance(balanceBean);
    }

    @Override
    public void getBalanceFail(String msg) {
        UToast.show(this,msg);
    }

    @Override
    public void finishForResult() {
        Intent intent = new Intent();
        intent.putExtra("balance",balanceBean);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent();
            intent.putExtra("balance",balanceBean);
            setResult(RESULT_OK,intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == foresult){
                viewModel.getBalance(ProApplication.SESSIONID());
                viewModel.getPriceData("1","20","2", ProApplication.SESSIONID());
            }
        }
    }
}
