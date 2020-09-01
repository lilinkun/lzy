package com.lzyyd.hsq.base;

import android.annotation.SuppressLint;
import android.app.Application;

import com.lzyyd.hsq.activity.ChuangkeActivity;
import com.lzyyd.hsq.activity.ModifyPayActivity;
import com.lzyyd.hsq.data.DataRepository;
import com.lzyyd.hsq.viewmodel.AddAddressViewModel;
import com.lzyyd.hsq.viewmodel.AddressListViewModel;
import com.lzyyd.hsq.viewmodel.BalanceTransferoutViewModel;
import com.lzyyd.hsq.viewmodel.BindCardViewModel;
import com.lzyyd.hsq.viewmodel.CcqViewModel;
import com.lzyyd.hsq.viewmodel.ChuangkeViewModel;
import com.lzyyd.hsq.viewmodel.CollectViewModel;
import com.lzyyd.hsq.viewmodel.ForgetPasswordViewModel;
import com.lzyyd.hsq.viewmodel.GetCashViewModel;
import com.lzyyd.hsq.viewmodel.GoodsDetailViewModel;
import com.lzyyd.hsq.viewmodel.GoodsListViewModel;
import com.lzyyd.hsq.viewmodel.HomeFragmentViewModel;
import com.lzyyd.hsq.viewmodel.IntegralViewModel;
import com.lzyyd.hsq.viewmodel.LoginViewModel;
import com.lzyyd.hsq.viewmodel.MVVMViewModel;
import com.lzyyd.hsq.viewmodel.MeViewModel;
import com.lzyyd.hsq.viewmodel.ModifyPayViewModel;
import com.lzyyd.hsq.viewmodel.MyQrcodeViewModel;
import com.lzyyd.hsq.viewmodel.OrderDetailViewModel;
import com.lzyyd.hsq.viewmodel.OrderListViewModel;
import com.lzyyd.hsq.viewmodel.PayViewModel;
import com.lzyyd.hsq.viewmodel.PersonalInfoViewModel;
import com.lzyyd.hsq.viewmodel.RechargeViewModel;
import com.lzyyd.hsq.viewmodel.RegisterViewModel;
import com.lzyyd.hsq.viewmodel.ShoppingcartViewModel;
import com.lzyyd.hsq.viewmodel.SureOrderViewModel;
import com.lzyyd.hsq.viewmodel.TianfengCoinViewModel;
import com.lzyyd.hsq.viewmodel.TransferoutViewModel;
import com.lzyyd.hsq.viewmodel.VipViewModel;
import com.lzyyd.hsq.viewmodel.WalletViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class AppViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    @SuppressLint("StaticFieldLeak")
    private static volatile AppViewModelFactory INSTANCE;
    private final Application mApplication;
    private final DataRepository mRepository;

    public static AppViewModelFactory getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (AppViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppViewModelFactory(application, Injection.provideDemoRepository(application.getApplicationContext()));
                }
            }
        }
        return INSTANCE;
    }

    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }

    private AppViewModelFactory(Application application, DataRepository repository) {
        this.mApplication = application;
        this.mRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(SureOrderViewModel.class)){
            return (T) new SureOrderViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(GoodsDetailViewModel.class)){
            return (T) new GoodsDetailViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(RegisterViewModel.class)){
            return (T) new RegisterViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(ForgetPasswordViewModel.class)){
            return (T) new ForgetPasswordViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(IntegralViewModel.class)){
            return (T) new IntegralViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(PersonalInfoViewModel.class)){
            return (T) new PersonalInfoViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(MeViewModel.class)){
            return (T) new MeViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(WalletViewModel.class)){
            return (T) new WalletViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(ShoppingcartViewModel.class)){
            return (T) new ShoppingcartViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(CollectViewModel.class)){
            return (T) new CollectViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(GetCashViewModel.class)){
            return (T) new GetCashViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(RechargeViewModel.class)){
            return (T) new RechargeViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(OrderListViewModel.class)){
            return (T) new OrderListViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(MyQrcodeViewModel.class)){
            return (T) new MyQrcodeViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(HomeFragmentViewModel.class)){
            return (T) new HomeFragmentViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(AddressListViewModel.class)){
            return (T) new AddressListViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(AddAddressViewModel.class)){
            return (T) new AddAddressViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(VipViewModel.class)){
            return (T) new VipViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(ChuangkeViewModel.class)){
            return (T) new ChuangkeViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(PayViewModel.class)){
            return (T) new PayViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(CcqViewModel.class)){
            return (T) new CcqViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(GoodsListViewModel.class)){
            return (T) new GoodsListViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(MVVMViewModel.class)){
            return (T) new MVVMViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(BindCardViewModel.class)){
            return (T) new BindCardViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(TransferoutViewModel.class)){
            return (T) new TransferoutViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(OrderDetailViewModel.class)){
            return (T) new OrderDetailViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(TianfengCoinViewModel.class)){
            return (T) new TianfengCoinViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(BalanceTransferoutViewModel.class)){
            return (T) new BalanceTransferoutViewModel(mApplication, mRepository);
        }else if (modelClass.isAssignableFrom(ModifyPayViewModel.class)){
            return (T) new ModifyPayViewModel(mApplication, mRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
