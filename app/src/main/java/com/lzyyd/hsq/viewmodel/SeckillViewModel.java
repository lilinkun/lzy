package com.lzyyd.hsq.viewmodel;

import android.app.Activity;
import android.app.Application;

import com.lzyyd.hsq.databinding.ActivitySeckillBinding;

import me.goldze.mvvmhabit.base.BaseViewModel;

public class SeckillViewModel extends BaseViewModel {

    public ActivitySeckillBinding activitySeckillBinding;
    public Activity activity;

    public SeckillViewModel(Application application,ActivitySeckillBinding activitySeckillBinding) {
        super(application);
        this.activitySeckillBinding = activitySeckillBinding;
        this.activity = activity;
    }

    public void setBack(){
        onBackPressed();
    }

}
