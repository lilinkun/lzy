package com.lzyyd.hsq.viewmodel;

import android.app.Application;

import com.lzyyd.hsq.data.DataRepository;

import androidx.annotation.NonNull;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * Create by liguo on 2020/8/31
 * Describe:
 */
public class IntegralListViewModel extends BaseViewModel<DataRepository> {

    public IntegralListViewModel(@NonNull Application application, DataRepository model) {
        super(application, model);
    }


}
