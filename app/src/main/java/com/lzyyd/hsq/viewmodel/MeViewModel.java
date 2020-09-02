package com.lzyyd.hsq.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.lzyyd.hsq.activity.CcqActivity;
import com.lzyyd.hsq.activity.CollectGoodsActivity;
import com.lzyyd.hsq.activity.IntegralActivity;
import com.lzyyd.hsq.activity.MyQrcodeActivity;
import com.lzyyd.hsq.activity.OrderListActivity;
import com.lzyyd.hsq.activity.PersonalInfoActivity;
import com.lzyyd.hsq.activity.PointActivity;
import com.lzyyd.hsq.activity.TakeGoodsActivity;
import com.lzyyd.hsq.activity.VipActivity;
import com.lzyyd.hsq.activity.WalletActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.BalanceBean;
import com.lzyyd.hsq.bean.CcqBean;
import com.lzyyd.hsq.bean.CcqListBean;
import com.lzyyd.hsq.bean.LoginBean;
import com.lzyyd.hsq.data.DataRepository;
import com.lzyyd.hsq.fragment.MeFragment;
import com.lzyyd.hsq.http.callback.HttpResultCallBack;
import com.lzyyd.hsq.util.HsqAppUtil;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.RxUtils;

import static android.content.Context.MODE_PRIVATE;

/**
 * Create by liguo on 2020/8/6
 * Describe:
 */
public class MeViewModel extends BaseViewModel<DataRepository> {

    private MeBackCall meBackCall;
    public ObservableField<Integer> OrderAllNum = new ObservableField<>();
    public ObservableField<Integer> ccqField = new ObservableField<>();
    private BalanceBean balanceBeans;

    public MeViewModel(@NonNull Application application, DataRepository model) {
        super(application, model);

    }

    public void setListener(MeBackCall meBackCall){
        this.meBackCall = meBackCall;
    }

    public void setJumpPersonal(){
        startActivity(PersonalInfoActivity.class);
    }


    public void setJumpWallet(){
        startActivity(WalletActivity.class,null, MeFragment.RESULT_CODE_POINT);
    }

    public void setJumpCollect(){
        startActivity(CollectGoodsActivity.class);
    }

    public void setJumpIntegral(){
        Bundle bundle = new Bundle();
        bundle.putInt("integral",(int) balanceBeans.getMoney3Balance());
        startActivity(IntegralActivity.class,bundle);
    }

    public void setJumpPoint(){
        startActivity(PointActivity.class,null, MeFragment.RESULT_CODE_POINT);
    }

    public void setJumpVip(){
        startActivity(VipActivity.class);
    }

    public void setJumpCcq(){
        startActivity(CcqActivity.class);
    }

    public void setJumpTihuo(){
        if (balanceBeans.getMoney7Balance() > 0){
            Bundle bundle = new Bundle();
            bundle.putSerializable("balance",balanceBeans);
            startActivity(TakeGoodsActivity.class,bundle, MeFragment.RESULT_CODE_POINT);
        }

    }

    public void setJumpOrderlist(int position){
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        startActivity(OrderListActivity.class,bundle);
    }

    public void setJumpQrcode(){
        startActivity(MyQrcodeActivity.class);
    }



    public void setQrcode(){
        meBackCall.clickQrcode();
    }


    public void getBalance(String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "BankBase_GetBalance");
        params.put("SessionId", SessionId);
        model.getBalance(params)
                .compose(RxUtils.schedulersTransformer())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>(){
                    @Override
                    public void accept(Disposable disposable){
                        showDialog();
                    }
                })
                .subscribe(new HttpResultCallBack<BalanceBean, Object>() {
                    @Override
                    public void onResponse(BalanceBean balanceBean, String status, Object page) {
                        dismissDialog();
                        meBackCall.getBalanceSuccess(balanceBean);
                        balanceBeans = balanceBean;
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        dismissDialog();
                        meBackCall.getBalanceFail(msg);
                    }
                });
    }



    public void getCcqUse(String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserCcq");
        params.put("fun", "UserCcqUse");
        params.put("SessionId", SessionId);
        model.getCcqUse(params)
                .compose(RxUtils.schedulersTransformer())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>(){
                    @Override
                    public void accept(Disposable disposable){
                        showDialog();
                    }
                })
                .subscribe(new HttpResultCallBack<CcqBean, Object>() {
                    @Override
                    public void onResponse(CcqBean ccqBean, String status, Object page) {
                        dismissDialog();
                        meBackCall.getCcqSuccess(ccqBean);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        dismissDialog();
                        meBackCall.getCcqFail(msg);
                    }
                });
    }


    public void getUserInfo(String UserName, String SessionId,final Context context) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "UserBaseVipGet");
        params.put("UserName",UserName);
        params.put("SessionId", SessionId);
        model.getUserInfo(params)
                .compose(RxUtils.schedulersTransformer())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>(){
                    @Override
                    public void accept(Disposable disposable){
                        showDialog();
                    }
                })
                .subscribe(new HttpResultCallBack<LoginBean, Object>() {
                    @Override
                    public void onResponse(LoginBean mLoginBean, String status, Object page) {
                        dismissDialog();
                        meBackCall.getUserInfoSuccess(mLoginBean);

                        SharedPreferences sharedPreferences = context.getSharedPreferences(HsqAppUtil.LOGIN, MODE_PRIVATE);
                        sharedPreferences.edit().putString("sessionid", ProApplication.SESSIONID()).putBoolean(HsqAppUtil.LOGIN, true)
                                .putString(HsqAppUtil.ACCOUNT, mLoginBean.getNickName()).putString(HsqAppUtil.TELEPHONE, mLoginBean.getMobile())
                                .putString(HsqAppUtil.USERNAME, mLoginBean.getUserName()).putString(HsqAppUtil.USERID, mLoginBean.getUserId())
                                .putString(HsqAppUtil.VIPVALIDITY, mLoginBean.getVipValidity())
                                .putString(HsqAppUtil.USERLEVEL, mLoginBean.getUserLevel() + "")
                                .putString(HsqAppUtil.CCQTYPE,mLoginBean.getCcqType()+"")
                                .putString(HsqAppUtil.LEVEL,mLoginBean.getUserLevel()+"")
                                .putString(HsqAppUtil.PROJECT,mLoginBean.getProject()+"")
                                .putString(HsqAppUtil.USERLEVELNAME, mLoginBean.getUserLevelName()).commit();

                    }

                    @Override
                    public void onErr(String msg, String status) {
                        dismissDialog();
                        meBackCall.getUserInfoFail(msg);
                    }
                });
    }


    public void getQrcodeCcqData(String CcqOrderSn , String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserCcq");
        params.put("fun", "UserCcqVipGet");
        params.put("CcqOrderSn",CcqOrderSn);
        params.put("SessionId", SessionId);
        model.getQrcodeCcqData(params)
                .compose(RxUtils.schedulersTransformer())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>(){
                    @Override
                    public void accept(Disposable disposable){
                        showDialog();
                    }
                })
                .subscribe(new HttpResultCallBack<CcqListBean, Object>() {
                    @Override
                    public void onResponse(CcqListBean ccqListBean, String status, Object page) {
                        dismissDialog();
                        meBackCall.getCcqListSuccess(ccqListBean);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        dismissDialog();
                        meBackCall.getCcqListFail(msg);
                    }
                });
    }


    public interface MeBackCall{
        public void getBalanceSuccess(BalanceBean balanceBean);
        public void getBalanceFail(String msg);

        public void getCcqSuccess(CcqBean ccqBean);
        public void getCcqFail(String msg);

        public void getCcqListSuccess(CcqListBean ccqBean);
        public void getCcqListFail(String msg);

        public void clickQrcode();

        public void getUserInfoSuccess(LoginBean loginBean);
        public void getUserInfoFail(String msg);

    }
}
