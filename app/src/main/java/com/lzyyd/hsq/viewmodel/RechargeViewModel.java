package com.lzyyd.hsq.viewmodel;

import android.app.Application;
import android.view.View;
import android.widget.TextView;

import com.lzyyd.hsq.data.DataRepository;

import androidx.databinding.ObservableField;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * Create by liguo on 2020/8/11
 * Describe:
 */
public class RechargeViewModel extends BaseViewModel<DataRepository> {

    private View oldView;
    public ObservableField<String> rechargeStr = new ObservableField<>();

    public RechargeViewModel(Application application,DataRepository dataRepository){
        super(application,dataRepository);
    }


    public void setClick(View view){
        view.setSelected(true);
        if (oldView != null){
            oldView.setSelected(false);
        }
        this.oldView = view;

        String priceStr =  ((TextView) view).getText().toString();
        rechargeStr.set(priceStr.substring(0,priceStr.length()-1));
    }

}
