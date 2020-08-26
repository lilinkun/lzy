package com.lzyyd.hsq.viewmodel;

import android.app.Application;

import com.lzyyd.hsq.data.DataRepository;

import androidx.annotation.NonNull;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * Create by liguo on 2020/8/26
 * Describe:
 */
public class PayViewModel extends BaseViewModel<DataRepository> {


    public PayViewModel(@NonNull Application application,DataRepository dataRepository) {
        super(application,dataRepository);
    }
}
