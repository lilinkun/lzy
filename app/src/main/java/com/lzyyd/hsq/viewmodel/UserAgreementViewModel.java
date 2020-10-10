package com.lzyyd.hsq.viewmodel;

import android.app.Application;

import com.lzyyd.hsq.data.DataRepository;

import androidx.annotation.NonNull;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * Create by liguo on 2020/10/10
 * Describe:
 */
public class UserAgreementViewModel extends BaseViewModel<DataRepository> {

    public UserAgreementViewModel(@NonNull Application application, DataRepository model) {
        super(application, model);
    }

}
