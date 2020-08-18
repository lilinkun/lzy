package com.lzyyd.hsq.viewmodel;

import android.app.Application;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.lzyyd.hsq.data.DataRepository;

import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * Create by liguo on 2020/7/20
 * Describe:
 */
public class IntegralViewModel extends BaseViewModel<DataRepository> {


    public IntegralViewModel(@NonNull Application application, DataRepository repository) {
        super(application,repository);
    }


}
