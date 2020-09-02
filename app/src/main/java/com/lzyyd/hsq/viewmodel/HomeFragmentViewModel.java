package com.lzyyd.hsq.viewmodel;

import android.app.Application;
import android.os.Bundle;

import com.lzyyd.hsq.activity.GoodsDetailActivity;
import com.lzyyd.hsq.activity.SearchActivity;
import com.lzyyd.hsq.activity.VipActivity;
import com.lzyyd.hsq.bean.HomeBean;
import com.lzyyd.hsq.data.DataRepository;
import com.lzyyd.hsq.http.callback.HttpResultCallBack;
import com.lzyyd.hsq.util.HsqAppUtil;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.RxUtils;

/**
 * Create by liguo on &{DATE}
 * Describe:
 */
public class HomeFragmentViewModel extends BaseViewModel<DataRepository> {

    private Application application;
    private HomeDataCallBack homeDataCallBack;
    public ObservableField<Boolean> recommemdFirst = new ObservableField<>();
    public ObservableField<Boolean> recommemdSecond = new ObservableField<>();
    public ObservableField<Boolean> recommemdThird = new ObservableField<>();

    public HomeFragmentViewModel(@NonNull Application application,DataRepository dataRepository) {
        super(application,dataRepository);
        this.application = application;
    }

    public void setCallBack(HomeDataCallBack homeDataCallBack){
        this.homeDataCallBack = homeDataCallBack;
    }

    public void setImageClick(){
        startActivity(VipActivity.class);
    }

    public void ccqEntrance(String goodsid){
        Bundle bundle = new Bundle();
        bundle.putString(HsqAppUtil.GOODSID,goodsid);
        bundle.putInt(HsqAppUtil.TYPE,64);
        startActivity(GoodsDetailActivity.class,bundle);
    }

    public void onClickGoodsItem(String goodsid){
        Bundle bundle = new Bundle();
        bundle.putString(HsqAppUtil.GOODSID,goodsid);
        bundle.putInt(HsqAppUtil.TYPE,1);
        startActivity(GoodsDetailActivity.class,bundle);
    }

    public void setJumpSearch(){
        startActivity(SearchActivity.class);
    }

    public void getHomeData(String SessionId){

        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Home");
        params.put("fun", "Mobile");
        params.put("SessionId",SessionId);
        model.getHomeData(params)
                .compose(RxUtils.schedulersTransformer())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>(){
                    @Override
                    public void accept(Disposable disposable){
                        showDialog();
                    }
                })
                .subscribe(new HttpResultCallBack<HomeBean, String>() {
                    @Override
                    public void onResponse(HomeBean homeBean, String status, String page) {
                        homeDataCallBack.getHomeDataSuccess(homeBean);
                        dismissDialog();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        homeDataCallBack.getHomeDataFail(msg);
                        dismissDialog();
                    }

                });
    }

    public void onClick(int position){
        homeDataCallBack.onHomeClick(position);
    }


    public interface HomeDataCallBack{

        public void getHomeDataSuccess(HomeBean homeBean);
        public void getHomeDataFail(String msg);

        public void onHomeClick(int position);
    }

}