package com.lzyyd.hsq.viewmodel;

import android.app.Application;

import com.lzyyd.hsq.activity.ChuangkeActivity;
import com.lzyyd.hsq.data.DataRepository;

import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * Create by liguo on 2020/8/24
 * Describe:
 */
public class VipViewModel extends BaseViewModel<DataRepository> {

    public VipViewModel(Application application,DataRepository dataRepository){
        super(application,dataRepository);
    }

    public void setJumpChuangke(){
        startActivity(ChuangkeActivity.class);
    }


}
