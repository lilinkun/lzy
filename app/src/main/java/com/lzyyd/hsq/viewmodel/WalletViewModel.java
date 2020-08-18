package com.lzyyd.hsq.viewmodel;

import android.app.Application;

import com.lzyyd.hsq.data.DataRepository;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * Create by liguo on 2020/8/10
 * Describe:
 */
public class WalletViewModel extends BaseViewModel<DataRepository> {

    public WalletViewModel(@NonNull Application application,  DataRepository model) {
        super(application,model);
    }
}
