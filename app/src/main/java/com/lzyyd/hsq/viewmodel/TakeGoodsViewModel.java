package com.lzyyd.hsq.viewmodel;

import android.app.Application;

import com.lzyyd.hsq.data.DataRepository;

import androidx.annotation.NonNull;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * Create by liguo on 2020/9/1
 * Describe:
 */
public class TakeGoodsViewModel extends BaseViewModel<DataRepository> {


    public TakeGoodsViewModel(@NonNull Application application, DataRepository model) {
        super(application, model);
    }


}
