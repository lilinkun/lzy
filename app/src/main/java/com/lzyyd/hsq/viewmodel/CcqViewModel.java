package com.lzyyd.hsq.viewmodel;

import android.app.Application;

import com.lzyyd.hsq.bean.CcqListBean;
import com.lzyyd.hsq.bean.PageBean;
import com.lzyyd.hsq.data.DataRepository;
import com.lzyyd.hsq.http.callback.HttpResultCallBack;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.RxUtils;

/**
 * Create by liguo on 2020/8/27
 * Describe:
 */
public class CcqViewModel extends BaseViewModel<DataRepository> {

    private CcqListCallBack ccqListCallBack;

    public CcqViewModel(@NonNull Application application,DataRepository dataRepository) {
        super(application,dataRepository);
    }

    public void setListener(CcqListCallBack ccqListCallBack){
        this.ccqListCallBack = ccqListCallBack;
    }

    public void getCcqList(String PageIndex,String PageCount,String CcqStatus,String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserCcq");
        params.put("fun", "UserCcqVipList");
        params.put("PageIndex",PageIndex);
        params.put("PageCount",PageCount);
        params.put("CcqStatus",CcqStatus);
        params.put("SessionId", SessionId);
        model.getCcqList(params)
                .compose(RxUtils.schedulersTransformer())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>(){
                    @Override
                    public void accept(Disposable disposable){
                        showDialog();
                    }
                })
                .subscribe(new HttpResultCallBack<ArrayList<CcqListBean>, PageBean>() {
                    @Override
                    public void onResponse(ArrayList<CcqListBean> ccqListBeans, String status, PageBean page) {
                        dismissDialog();
                        ccqListCallBack.getCcqListBeanSuccess(ccqListBeans);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        dismissDialog();
                        ccqListCallBack.getCcqListBeanFail(msg);
                    }
                });
    }

    public interface CcqListCallBack{
        public void getCcqListBeanSuccess(ArrayList<CcqListBean> ccqListBeans);
        public void getCcqListBeanFail(String msg);
    }
}
