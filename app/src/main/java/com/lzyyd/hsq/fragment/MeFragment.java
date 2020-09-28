package com.lzyyd.hsq.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseFragment;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.BalanceBean;
import com.lzyyd.hsq.bean.CcqBean;
import com.lzyyd.hsq.bean.CcqListBean;
import com.lzyyd.hsq.bean.LoginBean;
import com.lzyyd.hsq.databinding.FragmentMeBinding;
import com.lzyyd.hsq.qrcode.android.CaptureActivity;
import com.lzyyd.hsq.util.HsqAppUtil;
import com.lzyyd.hsq.viewmodel.MeViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

import static android.app.Activity.RESULT_OK;
import static in.srain.cube.views.ptr.util.PtrLocalDisplay.dp2px;

/**
 * Create by liguo on 2020/8/6
 * Describe:
 */
public class MeFragment extends BaseFragment<FragmentMeBinding, MeViewModel> implements MeViewModel.MeBackCall{

    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    private static final int REQUEST_CODE_SCAN = 0x0000;

    public static final int RESULT_CODE_POINT = 0X2212;

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

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(HsqAppUtil.LOGIN, getActivity().MODE_PRIVATE);

        viewModel.getUserInfo(sharedPreferences.getString(HsqAppUtil.USERNAME, ""),ProApplication.SESSIONID(),getActivity());

        if(ProApplication.CCQTYPE == 0) {

            binding.tvGoScan.setVisibility(View.GONE);
            binding.ccqLayout.rlCcqTihuo.setVisibility(View.GONE);
        }else {
            binding.tvGoScan.setVisibility(View.VISIBLE);
            binding.ccqLayout.rlCcqTihuo.setVisibility(View.VISIBLE);
        }

        initPtrFrame();

    }


    @Override
    public void initViewObservable() {
    }

    @Override
    public void getBalanceSuccess(BalanceBean balanceBean) {
        binding.setBalance(balanceBean);
        if (binding.mPtrframe != null &&binding.mPtrframe.isEnabled()){
            binding.mPtrframe.refreshComplete();
        }
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
    public void getCcqListSuccess(CcqListBean ccqBean) {
        Dialog dialog = new Dialog(getActivity());
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        //设置dialog的宽高为屏幕的宽高
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, height);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_qrcode_result,null);
        binding.setVariable(BR.ccqlistbean_dialog,ccqBean);

        dialog.setContentView(view);
        dialog.show();
    }

    @Override
    public void getCcqListFail(String msg) {

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

    @Override
    public void getUserInfoSuccess(LoginBean loginBean) {

        binding.setUserinfo(loginBean);

        ProApplication.CCQTYPE = loginBean.getCcqType();

        if(loginBean.getCcqType() == 0 ) {

            binding.tvGoScan.setVisibility(View.GONE);
            binding.ccqLayout.rlCcqTihuo.setVisibility(View.GONE);
        }else {
            binding.tvGoScan.setVisibility(View.VISIBLE);
            binding.ccqLayout.rlCcqTihuo.setVisibility(View.VISIBLE);
        }

        if (binding.mPtrframe != null &&binding.mPtrframe.isEnabled()){
            binding.mPtrframe.refreshComplete();
        }

    }

    @Override
    public void getUserInfoFail(String msg) {

    }

    public void goScan(){
        Intent intent = new Intent(getActivity(), CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                //返回的文本内容
                String content = data.getStringExtra(DECODED_CONTENT_KEY);
                //返回的BitMap图像
                Bitmap bitmap = data.getParcelableExtra(DECODED_BITMAP_KEY);

                viewModel.getQrcodeCcqData(content,ProApplication.SESSIONID());
            }
        }else if (requestCode == RESULT_CODE_POINT && resultCode == RESULT_OK){

            BalanceBean balanceBean = (BalanceBean) data.getSerializableExtra("balance");
            binding.setBalance(balanceBean);

        }
    }

    private void initPtrFrame() {
//        final StoreHouseHeader header=new StoreHouseHeader(this);
//        header.setPadding(dp2px(20), dp2px(20), 0, 0);
//        header.initWithString("xiaoma is good");
        final PtrClassicDefaultHeader header=new PtrClassicDefaultHeader(getActivity());
        header.setPadding(dp2px(20), dp2px(20), 0, 0);
        binding.mPtrframe.setHeaderView(header);
        binding.mPtrframe.addPtrUIHandler(header);
        binding.mPtrframe.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

                viewModel.getBalance(ProApplication.SESSIONID());
                viewModel.getCcqUse(ProApplication.SESSIONID());

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(HsqAppUtil.LOGIN, getActivity().MODE_PRIVATE);

                viewModel.getUserInfo(sharedPreferences.getString(HsqAppUtil.USERNAME, ""),ProApplication.SESSIONID(),getActivity());
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
    }

}
