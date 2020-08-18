package com.lzyyd.hsq.viewmodel;

import android.app.Application;

import com.lzyyd.hsq.data.DataRepository;

import androidx.annotation.NonNull;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * Create by liguo on 2020/7/21
 * Describe:
 */
public class SureOrderViewModel extends BaseViewModel<DataRepository> {


    public SureOrderViewModel(@NonNull Application application,DataRepository dataRepository) {
        super(application,dataRepository);
    }
}
