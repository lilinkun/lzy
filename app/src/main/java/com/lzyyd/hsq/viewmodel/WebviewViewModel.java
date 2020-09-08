package com.lzyyd.hsq.viewmodel;

import android.app.Application;

import com.lzyyd.hsq.data.DataRepository;

import androidx.annotation.NonNull;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * Create by liguo on 2020/7/24
 * Describe:
 */
public class WebviewViewModel extends BaseViewModel<DataRepository> {


    public WebviewViewModel(@NonNull Application application,DataRepository dataRepository) {
        super(application,dataRepository);
    }

}
