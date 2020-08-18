package com.lzyyd.hsq.viewmodel;

import android.app.Application;

import com.lzyyd.hsq.databinding.ActivityMainBinding;
import com.lzyyd.hsq.model.MVVMModel;

import me.goldze.mvvmhabit.base.BaseViewModel;

public class MVVMViewModel extends BaseViewModel {


    private MVVMModel mvvmModel;
    private ActivityMainBinding binding;

    public MVVMViewModel(Application application, ActivityMainBinding binding) {
        super(application);
        mvvmModel = new MVVMModel();
        this.binding = binding;
    }

    public void setViewPager(int current){
        binding.topVp.setCurrentItem(current);
    }
}
