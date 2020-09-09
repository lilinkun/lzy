package com.lzyyd.hsq.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.AddressBean;
import com.lzyyd.hsq.bean.BalanceBean;
import com.lzyyd.hsq.bean.GoodsChooseBean;
import com.lzyyd.hsq.bean.GoodsDetailInfoBean;
import com.lzyyd.hsq.databinding.ActivityTakeGoodsBinding;
import com.lzyyd.hsq.util.Eyes;
import com.lzyyd.hsq.util.HsqAppUtil;
import com.lzyyd.hsq.util.UToast;
import com.lzyyd.hsq.viewmodel.TakeGoodsViewModel;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;
import me.goldze.mvvmhabit.utils.StringUtils;

/**
 * Create by liguo on 2020/9/1
 * Describe:
 */
public class TakeGoodsActivity extends BaseActivity<ActivityTakeGoodsBinding, TakeGoodsViewModel> implements TakeGoodsViewModel.OnTakeGoodsListener {

    private AddressBean addressBean;
    private BalanceBean balanceBean;
    private int numInt = 0;
    private int reducenum = 0;
    private int count = 0;
    private GoodsDetailInfoBean goodsDetailInfoBean;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_take_goods;
    }

    @Override
    public int initVariableId() {
        return BR.takegoods;
    }

    @Override
    public TakeGoodsViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(TakeGoodsViewModel.class);
    }

    @Override
    public void initData() {

        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));

        viewModel.setListener(this);

        balanceBean = (BalanceBean) getIntent().getSerializableExtra("balance");

        reducenum = balanceBean.getMoney7Balance();
        viewModel.reduceField.set(reducenum);

        binding.tvAddress.setVisibility(View.GONE);

        String ccqgoodsid = ProApplication.CCQGOODSID;

        SharedPreferences sharedPreferences = getSharedPreferences(HsqAppUtil.LOGIN,MODE_PRIVATE);
        String username = sharedPreferences.getString(HsqAppUtil.USERNAME,"");
        viewModel.usernameField.set(username);
        viewModel.getGoodsDetail(ccqgoodsid,ProApplication.SESSIONID());

        binding.reduceGoodsNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.tvGoodsCount.getText().toString().equals("0")){
                    binding.reduceGoodsNum.setEnabled(false);
                }else {
                    numInt = Integer.valueOf(binding.tvGoodsCount.getText().toString()) - 1;
                    binding.tvGoodsCount.setText(numInt+"");
                    viewModel.numField.set(numInt);

                    reducenum = balanceBean.getMoney7Balance() - numInt;
                    viewModel.reduceField.set(reducenum);
                }
            }
        });

        binding.increaseGoodsNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.tvGoodsCount.getText().toString().equals(balanceBean.getMoney7Balance() + "")){
                    UToast.show(TakeGoodsActivity.this,"不能超过提货数");
                }else {
                    numInt = Integer.valueOf(binding.tvGoodsCount.getText().toString()) + 1;
                    binding.tvGoodsCount.setText(numInt+"");
                    viewModel.numField.set(numInt);
                    reducenum = balanceBean.getMoney7Balance() - numInt;
                    viewModel.reduceField.set(reducenum);

                }
            }
        });

        binding.tvGoodsCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

    }

    /**
     */
    private void showDialog() {
        final AlertDialog.Builder alertDialog_Builder=new AlertDialog.Builder(this);
        View view= LayoutInflater.from(this).inflate(R.layout.dialog_change_num,null);
        final AlertDialog dialog=alertDialog_Builder.create();
        dialog.setView(view);//errored,这里是dialog，不是alertDialog_Buidler
        final EditText num= (EditText) view.findViewById(R.id.dialog_num);
        num.setText(binding.tvGoodsCount.getText().toString()+"");

        final TextView increase= (TextView) view.findViewById(R.id.dialog_increaseNum);
        final TextView DeIncrease=(TextView)view.findViewById(R.id.dialog_reduceNum);
        final TextView pButton= (TextView) view.findViewById(R.id.dialog_Pbutton);
        final TextView nButton= (TextView) view.findViewById(R.id.dialog_Nbutton);
        nButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        pButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number= Integer.parseInt(num.getText().toString().trim());
                if(number==0){
                    dialog.dismiss();
                }else{
                    if (number > balanceBean.getMoney7Balance()){
                        UToast.show(TakeGoodsActivity.this,"库存不足");
                    }else {
                        num.setText(String.valueOf(number));
                        numInt = number;
                        binding.tvGoodsCount.setText(numInt+"");
                        viewModel.numField.set(numInt);
                        reducenum = balanceBean.getMoney7Balance() - numInt;
                        viewModel.reduceField.set(reducenum);
                        dialog.dismiss();
                    }
                }
            }
        });
        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                num.setText(String.valueOf(count));
            }
        });
        DeIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count>1){
                    count--;
                    num.setText(String.valueOf(count));
                }
            }
        });
        dialog.show();
    }


    @Override
    public void onTakeGoods() {
        if (binding.tvGoodsCount.getText().toString().equals("0")){
            UToast.show(this,"请选择提货数");
        }else if (addressBean == null){
            UToast.show(this,"请选择地址");
        }else if (StringUtils.isEmpty(viewModel.psdField.get())){
            UToast.show(this,"请输入密码");
        }else {
            double price = numInt * goodsDetailInfoBean.getPrice();
            viewModel.getTakeGoods(viewModel.psdField.get(),ProApplication.CCQGOODSID,"0",price+"",numInt+"",addressBean.getAddressID(),ProApplication.SESSIONID());
        }
    }

    @Override
    public void getTakeGoodsSuccess(String msg) {
        Bundle bundle = new Bundle();
        bundle.putString("ordersn",msg);
        startActivity(OrderDetailActivity.class,bundle);
        finish();
    }

    @Override
    public void getTakeGoodsFail(String msg) {
        UToast.show(this,msg);
    }

    @Override
    public void getDataSuccess(GoodsDetailInfoBean<ArrayList<GoodsChooseBean>> goodsListBeans) {
        binding.setGoodsdetail(goodsListBeans);
        this.goodsDetailInfoBean = goodsListBeans;
    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == RESULT_OK){
            if (requestCode == 0x212){
                addressBean = (AddressBean) data.getSerializableExtra(HsqAppUtil.ADDRESS);
                binding.setAddress(addressBean);
                binding.tvAddress.setVisibility(View.VISIBLE);
                if (addressBean != null){
                    binding.tvAddAddress.setVisibility(View.GONE);
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
