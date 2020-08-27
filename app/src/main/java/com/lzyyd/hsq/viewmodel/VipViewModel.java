package com.lzyyd.hsq.viewmodel;

import android.app.Application;
import android.os.Bundle;

import com.lzyyd.hsq.activity.ChuangkeActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.data.DataRepository;
import com.lzyyd.hsq.util.HsqAppUtil;

import androidx.databinding.ObservableField;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * Create by liguo on 2020/8/24
 * Describe:
 */
public class VipViewModel extends BaseViewModel<DataRepository> {

    public ObservableField<Integer> projectField = new ObservableField<>(ProApplication.PROJECT);
    public ObservableField<Integer> userLevelField = new ObservableField<>(ProApplication.USERLEVEL);

    public VipViewModel(Application application,DataRepository dataRepository){
        super(application,dataRepository);
    }

    public void setJumpChuangke(int goodstype){
        Bundle bundle = new Bundle();
        bundle.putInt(HsqAppUtil.GOODSTYPE,goodstype);
        startActivity(ChuangkeActivity.class,bundle);
    }


}
