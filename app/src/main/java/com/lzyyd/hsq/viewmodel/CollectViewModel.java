package com.lzyyd.hsq.viewmodel;

import android.app.Application;

import com.lzyyd.hsq.data.DataRepository;

import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * Create by liguo on 2020/8/11
 * Describe:
 */
public class CollectViewModel extends BaseViewModel<DataRepository> {

    public CollectViewModel(Application application,DataRepository dataRepository){
        super(application,dataRepository);
    }
}
