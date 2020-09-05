package com.lzyyd.hsq.viewmodel;

import android.app.Application;

import com.lzyyd.hsq.activity.RegisterActivity;
import com.lzyyd.hsq.data.DataRepository;

import androidx.annotation.NonNull;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * Create by liguo on 2020/9/5
 * Describe:
 */
public class ChooseWxLoginViewModel extends BaseViewModel<DataRepository> {

    public ChooseWxLoginViewModel(@NonNull Application application, DataRepository model) {
        super(application, model);
    }


}
