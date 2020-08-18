package com.lzyyd.hsq.viewmodel;

import android.app.Application;
import android.content.Context;
import android.text.InputType;
import android.widget.CheckBox;

import com.lzyyd.hsq.databinding.ActivityForgetSettingPsdBinding;
import com.lzyyd.hsq.model.ForgetSettingPsdModel;

import androidx.databinding.BindingAdapter;
import androidx.databinding.Observable;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import me.goldze.mvvmhabit.base.BaseViewModel;

public class ForgetSettingPsdViewModel extends BaseViewModel {

    ActivityForgetSettingPsdBinding binding;
    Context context;
    ForgetSettingPsdModel settingPsdModel;

    public ObservableBoolean observableBoolean = new ObservableBoolean();
    public ObservableInt observableInt = new ObservableInt();

    public ForgetSettingPsdViewModel(Application application, ActivityForgetSettingPsdBinding binding) {
        super(application);
        this.binding = binding;
        this.context = context;
        settingPsdModel = new ForgetSettingPsdModel();
    }

    @BindingAdapter({"setCheckValue"})
    public static void setCheckValue(final CheckBox checkBox, final ObservableField<Boolean> tags){
        tags.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                checkBox.setChecked(tags.get());
            }
        });
    }

    public void setChecked(boolean isCheck){

        binding.etInputNewPsd.setInputType(isCheck ?InputType.TYPE_CLASS_TEXT : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        binding.etSureNewPsd.setInputType(isCheck ? InputType.TYPE_CLASS_TEXT :InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

    }

    public void onBack(){
        onBackPressed();
    }



}
