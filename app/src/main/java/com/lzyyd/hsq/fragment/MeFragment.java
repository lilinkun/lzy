package com.lzyyd.hsq.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;
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
import com.lzyyd.hsq.qrcode.encode.CodeCreator;
import com.lzyyd.hsq.util.HsqAppUtil;
import com.lzyyd.hsq.util.UToast;
import com.lzyyd.hsq.viewmodel.MeViewModel;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import static android.app.Activity.RESULT_OK;

/**
 * Create by liguo on 2020/8/6
 * Describe:
 */
public class MeFragment extends BaseFragment<FragmentMeBinding, MeViewModel> implements MeViewModel.MeBackCall {

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

        viewModel.getUserInfo(sharedPreferences.getString(HsqAppUtil.USERNAME, ""),ProApplication.SESSIONID());

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


}
