package com.lzyyd.hsq.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseFragment;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.BalanceBean;
import com.lzyyd.hsq.bean.CcqBean;
import com.lzyyd.hsq.databinding.FragmentMeBinding;
import com.lzyyd.hsq.util.UToast;
import com.lzyyd.hsq.viewmodel.MeViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

/**
 * Create by liguo on 2020/8/6
 * Describe:
 */
public class MeFragment extends BaseFragment<FragmentMeBinding, MeViewModel> implements MeViewModel.MeBackCall {

    @Override
    public int initVariableId() {
        return BR.me;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_me;
    }

    @Override
    public MeViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(MeViewModel.class);
    }

    @Override
    public void initData() {
//        binding.orderLayout.nimAllOrder.setNum(4);

        viewModel.setListener(this);
        viewModel.getBalance(ProApplication.SESSIONID());
        viewModel.getCcqUse(ProApplication.SESSIONID());

    }


    @Override
    public void initViewObservable() {

    }

    @Override
    public void getBalanceSuccess(BalanceBean balanceBean) {
        binding.setBalance(balanceBean);
    }

    @Override
    public void getBalanceFail(String msg) {

    }

    @Override
    public void getCcqSuccess(CcqBean ccqBean) {
        binding.setCcq(ccqBean);
    }

    @Override
    public void getCcqFail(String msg) {

    }

    @Override
    public void clickQrcode() {
        //动态权限申请
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            this.requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
        } else {
            //扫码
            goScan();
        }
    }

    public void goScan(){
        UToast.show(getActivity(),"asdasdasda");
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //扫码
                    goScan();
                } else {
                    Toast.makeText(getActivity(), "你拒绝了权限申请，无法打开相机扫码哟！", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

}
