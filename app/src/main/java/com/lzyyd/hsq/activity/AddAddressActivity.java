package com.lzyyd.hsq.activity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.AddressBean;
import com.lzyyd.hsq.bean.LocalBean;
import com.lzyyd.hsq.databinding.ActivityAddAddressBinding;
import com.lzyyd.hsq.util.UToast;
import com.lzyyd.hsq.util.adressselectorlib.AddressPickerView;
import com.lzyyd.hsq.viewmodel.AddAddressViewModel;

import java.util.ArrayList;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModelProviders;
import me.goldze.mvvmhabit.utils.StringUtils;


/**
 * Create by liguo on 2020/8/17
 * Describe:
 */
public class AddAddressActivity extends BaseActivity<ActivityAddAddressBinding, AddAddressViewModel> implements AddAddressViewModel.GetLocalData, View.OnClickListener {

    private AddressPickerView addressView;
    private String mProvinceCode;
    private String mCityCode;
    private String mAreaCode;
    private String mZipCode;
    private String isDefault;
    public ObservableField<String> AddressName = new ObservableField<>();
    public ObservableField<String> AddressStr = new ObservableField<>();
    public ObservableField<String> AddressMobile = new ObservableField<>();
    public ObservableField<String> AddressDetail = new ObservableField<>();

    private AddressBean addressBean;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_add_address;
    }

    @Override
    public int initVariableId() {
        return BR.addaddressviewmodel;
    }

    @Override
    public AddAddressViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(AddAddressViewModel.class);
    }

    @Override
    public void initData() {
        viewModel.setLocalCallBack(this);

        binding.llProvince.setOnClickListener(this);

        binding.setVariable(BR.add,this);

        if (getIntent() != null) {
            if (getIntent().getExtras() != null) {
                addressBean = (AddressBean) getIntent().getExtras().getSerializable("addressBean");
            }
        }

        if (addressBean != null) {
            binding.switchTurn.setChecked(addressBean.isDefault());
            AddressMobile.set(addressBean.getMobile());
            AddressDetail.set(addressBean.getAddressName());
            AddressName.set(addressBean.getName());
            AddressStr.set(addressBean.getAddress());
            binding.tvTitle.setText("修改");

            mProvinceCode = addressBean.getProv();
            mCityCode = addressBean.getCity();
            mAreaCode = addressBean.getArea();
            mZipCode = addressBean.getPost();
            isDefault = addressBean.isDefault() ? "1" : "0";
        }
    }

    @Override
    public void getDataSuccess(ArrayList<LocalBean> provinceBeans, int localType) {
        addressView.getDataSuccess(provinceBeans, localType);
    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void getSaveSuccess() {
        UToast.show(this,"保存成功");
        viewModel.finishforresult();
    }

    @Override
    public void getSaveFail(String msg) {
        UToast.show(this,msg);
    }

    @Override
    public void modifySuccess() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void modifyFail(String msg) {
        UToast.show(this,msg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_province:

                final PopupWindow popupWindow = new PopupWindow(this);
                View rootView = LayoutInflater.from(this).inflate(R.layout.pop_address_picker, null, false);
                addressView = rootView.findViewById(R.id.apvAddress);
                addressView.setOnAddressPickerSure(new AddressPickerView.OnAddressPickerSureListener() {
                    @Override
                    public void onSureClick(String address, String provinceCode, String cityCode, String districtCode, String zipCode) {
                        binding.tvLocalAddress.setText(address);
                        mProvinceCode = provinceCode;
                        mCityCode = cityCode;
                        mAreaCode = districtCode;
                        mZipCode = zipCode;

                        popupWindow.dismiss();
                    }

                    @Override
                    public void onExit() {
                        popupWindow.dismiss();
                    }

                    @Override
                    public void onRequstion(String parentId, int localType) {
                        viewModel.getLocalData(parentId,localType);
                    }

                });
                popupWindow.setContentView(rootView);
                popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setFocusable(true);
                popupWindow.setOutsideTouchable(true);
                popupWindow.showAtLocation(binding.rlAddress, Gravity.CENTER | Gravity.CENTER, 0, 0);
                /*popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        if (imageView != null && imageView.isShown()) {
                            imageView.setVisibility(View.GONE);
                        }
                    }
                });*/

                break;
        }
    }


    public void saveClick(){

        if (StringUtils.isEmpty(AddressName.get())){
            UToast.show(this,"请填写收货人");
        }else if (StringUtils.isEmpty(mProvinceCode) || StringUtils.isEmpty(mCityCode) || StringUtils.isEmpty(mAreaCode)){
            UToast.show(this,"请选择所在地区");
        }else if (StringUtils.isEmpty(AddressStr.get())){
            UToast.show(this,"请填写详细地址");
        }else if (StringUtils.isEmpty(AddressMobile.get())){
            UToast.show(this,"请填写手机号码");
        }else {

            if (addressBean == null) {
                viewModel.getSaveAddress(AddressName.get(), mProvinceCode, mCityCode, mAreaCode, AddressStr.get(), mZipCode, AddressMobile.get(),
                        binding.switchTurn.isChecked() ? "1" : "0", ProApplication.SESSIONID());
            }else {
                viewModel.modifyAddress(addressBean.getAddressID(), AddressName.get(), mProvinceCode, mCityCode, mAreaCode, AddressStr.get(), mZipCode, AddressMobile.get(), binding.switchTurn.isChecked() ? "1" : "0", ProApplication.SESSIONID());
            }
        }

    }

}
