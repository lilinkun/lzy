package com.lzyyd.hsq.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.adapter.BankAdapter;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.BankBean;
import com.lzyyd.hsq.bean.UserBankBean;
import com.lzyyd.hsq.databinding.ActivityBindCardBinding;
import com.lzyyd.hsq.util.HsqAppUtil;
import com.lzyyd.hsq.util.UToast;
import com.lzyyd.hsq.viewmodel.BindCardViewModel;
import com.lzyyd.hsq.viewmodel.SureOrderViewModel;

import java.util.ArrayList;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Create by liguo on 2020/8/28
 * Describe:
 */
public class BindCardActivity extends BaseActivity<ActivityBindCardBinding, BindCardViewModel> implements BindCardViewModel.OnBankListener, View.OnClickListener, BankAdapter.OnBankClickListener {

    private PopupWindow popupWindow;
    private BankAdapter bankAdapter;
    private BankBean bankBean;

    MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000, 1000);

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_bind_card;
    }

    @Override
    public int initVariableId() {
        return BR.bindCard;
    }

    @Override
    public BindCardViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(BindCardViewModel.class);
    }

    @Override
    public void initData() {
        viewModel.setListener(this);
        viewModel.getBankCard(ProApplication.SESSIONID());

        SharedPreferences sharedPreferences = getSharedPreferences(HsqAppUtil.LOGIN, MODE_PRIVATE);

        binding.tvBankPhone.setText(sharedPreferences.getString(HsqAppUtil.TELEPHONE, ""));
        binding.llBankName.setOnClickListener(this);

        binding.tvCode.setOnClickListener(this);
        binding.tvBindCard.setOnClickListener(this);

    }

    @Override
    public void getBankSuccess(UserBankBean userBankBean) {
        binding.setCardInfo(userBankBean);
    }

    @Override
    public void getBankFail(String msg) {
        UToast.show(this,msg);
    }

    @Override
    public void onSendVcodeSuccess() {
        UToast.show(this,"已发送验证码");
    }

    @Override
    public void onSendVcodeFail(String msg) {
        UToast.show(this,msg);
    }

    @Override
    public void getBankInfoSuccess(ArrayList<BankBean> bankBeans) {
        initPop(bankBeans);
        if (popupWindow != null) {
            popupWindow.showAsDropDown(binding.llBankName);
        }
    }

    @Override
    public void getBankInfoFail(String msg) {
        UToast.show(this,msg);
    }

    @Override
    public void upBankInfoSuccess(String msg) {
        UToast.show(this,"绑定成功");
        finish();
    }

    @Override
    public void upBankInfoFail(String msg) {
        UToast.show(this,msg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_code:


                viewModel.SendSms(ProApplication.SESSIONID());

                myCountDownTimer.start();


                break;

            case R.id.ll_bank_name:

                viewModel.getBankInfo(ProApplication.SESSIONID());

                break;

            case R.id.tv_bind_card:

                if (binding.etBankName.getText().toString().trim().length() == 0) {
                    UToast.show(this,"请填写真实姓名");
                    return;
                }

                if (binding.etCode.getText().toString().trim().length() == 0) {
                    UToast.show(this,"请填写验证码");
                    return;
                }

//                try {
                if (binding.etBankCardId.getText().toString().trim().length() == 15 || binding.etBankCardId.getText().toString().trim().length() == 18) {
                } else {
                    UToast.show(this,R.string.input_bank_card);
                    return;
                }
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }


                if (binding.etBankCardNum.getText().toString().trim().length() == 0) {
//                if (et_bank_card_num.getText().toString().trim().length() == 0 || !PhoneFormatCheckUtils.checkBankCard(et_bank_card_num.getText().toString())){
                    UToast.show(this,"请输入银行卡号码");
                    return;
                }

                if (binding.tvBankName.getText().toString().trim().length() == 0) {
                    UToast.show(this,"请填写开户银行");
                    return;
                }

                viewModel.upBankInfo(bankBean.getId() + "", binding.tvFirstBank.getText().toString(), binding.tvBankName.getText().toString(),
                        binding.etBankCardNum.getText().toString(), binding.etCode.getText().toString(), binding.etBankCardId.getText().toString(), ProApplication.SESSIONID());


                break;
        }
    }

    public void initPop(ArrayList<BankBean> bankBeans) {
        View rootview = LayoutInflater.from(this).inflate(R.layout.pop_layout, null);
        RecyclerView recyclerView = rootview.findViewById(R.id.rv_bind_card);

        bankAdapter = new BankAdapter(this,this);

        bankAdapter.getItems().addAll(bankBeans);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
        });

        recyclerView.setAdapter(bankAdapter);

        popupWindow = new PopupWindow(rootview,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());


        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
            }
        });

    }

    @Override
    public void getItem(BankBean bankBean) {
        this.bankBean = bankBean;
        binding.tvBankName.setText(bankBean.getBankName());
        popupWindow.dismiss();
    }

    /**
     * 获取验证码倒计时
     */
    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程
        @Override
        public void onTick(long l) {
            //防止计时过程中重复点击
            binding.tvCode.setClickable(false);
            binding.tvCode.setText(l / 1000 + "s");
        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            //重新给Button设置文字
            binding.tvCode.setText("发送验证码");
            //设置可点击
            binding.tvCode.setClickable(true);

            binding.tvCode.setTextColor(Color.parseColor("#A47E60"));
        }
    }
}
