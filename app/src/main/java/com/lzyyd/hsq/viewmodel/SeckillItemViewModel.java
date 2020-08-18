package com.lzyyd.hsq.viewmodel;

import android.app.Application;

import com.lzyyd.hsq.bean.SeckillGoodsBean;

import androidx.databinding.ObservableField;
import me.goldze.mvvmhabit.base.BaseViewModel;

public class SeckillItemViewModel extends BaseViewModel {

    public ObservableField<SeckillGoodsBean> observableField = new ObservableField<>();




    SeckillItemViewModel(Application application, SeckillGoodsBean demoEntity){
        super(application);
        observableField.set(demoEntity);
    }

}
