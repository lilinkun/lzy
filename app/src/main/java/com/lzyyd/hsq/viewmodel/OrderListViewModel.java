package com.lzyyd.hsq.viewmodel;

import android.app.Application;

import com.lzyyd.hsq.bean.OrderBean;
import com.lzyyd.hsq.data.DataRepository;

import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * Create by liguo on 2020/8/12
 * Describe:
 */
public class OrderListViewModel extends BaseViewModel<DataRepository> {

    public OrderListViewModel(Application application,DataRepository dataRepository){
        super(application,dataRepository);
    }
}
