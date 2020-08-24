package com.lzyyd.hsq.viewmodel;

import android.app.Application;
import android.os.Bundle;

import com.lzyyd.hsq.activity.AddressListActivity;
import com.lzyyd.hsq.activity.CollectGoodsActivity;
import com.lzyyd.hsq.activity.GetCashActivity;
import com.lzyyd.hsq.activity.MyQrcodeActivity;
import com.lzyyd.hsq.activity.OrderListActivity;
import com.lzyyd.hsq.activity.PersonalInfoActivity;
import com.lzyyd.hsq.activity.RechargeActivity;
import com.lzyyd.hsq.activity.VipActivity;
import com.lzyyd.hsq.activity.WalletActivity;
import com.lzyyd.hsq.data.DataRepository;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * Create by liguo on 2020/8/6
 * Describe:
 */
public class MeViewModel extends BaseViewModel<DataRepository> {

    public ObservableField<Integer> OrderAllNum = new ObservableField<>();

    public MeViewModel(@NonNull Application application) {
        super(application);
    }

    public MeViewModel(@NonNull Application application, DataRepository model) {
        super(application, model);

    }


    public void setJumpPersonal(){
        startActivity(PersonalInfoActivity.class);
    }


    public void setJumpWallet(){
        startActivity(WalletActivity.class);
    }

    public void setJumpCollect(){
        startActivity(CollectGoodsActivity.class);
    }

    public void setJumpGetCash(){
        startActivity(GetCashActivity.class);
    }

    public void setJumpReCharge(){
        startActivity(RechargeActivity.class);
    }

    public void setJumpVip(){
        startActivity(VipActivity.class);
    }

    public void setJumpOrderlist(int position){
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        startActivity(OrderListActivity.class,bundle);
    }

    public void setJumpQrcode(){
        startActivity(MyQrcodeActivity.class);
    }


    public void setJumpAddress(){
        startActivity(AddressListActivity.class);
    }
}
