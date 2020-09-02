package com.lzyyd.hsq.viewmodel;

import android.app.Application;

import com.lzyyd.hsq.data.DataRepository;

import androidx.annotation.NonNull;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * Create by liguo on 2020/9/2
 * Describe:
 */
public class StoreViewModel extends BaseViewModel<DataRepository> {

    public StoreViewModel(@NonNull Application application, DataRepository model) {
        super(application, model);
    }

}
