package com.lzyyd.hsq.viewmodel;

import android.app.Application;

import com.lzyyd.hsq.data.DataRepository;

import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * Create by liguo on 2020/8/13
 * Describe:
 */
public class MyQrcodeViewModel extends BaseViewModel<DataRepository> {

    public MyQrcodeViewModel(Application application,DataRepository dataRepository){
        super(application,dataRepository);
    }
}
