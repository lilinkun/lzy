package com.lzyyd.hsq.http;

import com.lzyyd.hsq.bean.AddressBean;
import com.lzyyd.hsq.bean.BalanceBean;
import com.lzyyd.hsq.bean.BalanceDetailBean;
import com.lzyyd.hsq.bean.CartBean;
import com.lzyyd.hsq.bean.CartListBean;
import com.lzyyd.hsq.bean.CategoryBean;
import com.lzyyd.hsq.bean.CcqBean;
import com.lzyyd.hsq.bean.CcqListBean;
import com.lzyyd.hsq.bean.CollectBean;
import com.lzyyd.hsq.bean.CollectListBean;
import com.lzyyd.hsq.bean.GoodsChooseBean;
import com.lzyyd.hsq.bean.GoodsDetailInfoBean;
import com.lzyyd.hsq.bean.GoodsListBean;
import com.lzyyd.hsq.bean.HomeBean;
import com.lzyyd.hsq.bean.LocalBean;
import com.lzyyd.hsq.bean.LoginBean;
import com.lzyyd.hsq.bean.OrderListBean;
import com.lzyyd.hsq.bean.OrderinfoBean;
import com.lzyyd.hsq.bean.PageBean;
import com.lzyyd.hsq.bean.ResultBean;
import com.lzyyd.hsq.bean.UrlBean;
import com.lzyyd.hsq.bean.UserBankBean;
import com.lzyyd.hsq.data.source.HttpDataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;


public class HttpDataSourceImpl implements HttpDataSource {
    private RetrofitService apiService;
    private volatile static HttpDataSourceImpl INSTANCE = null;

    public static HttpDataSourceImpl getInstance(RetrofitService apiService) {
        if (INSTANCE == null) {
            synchronized (HttpDataSourceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpDataSourceImpl(apiService);
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private HttpDataSourceImpl(RetrofitService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Observable<Object> login() {
        return Observable.just(new Object()).delay(3, TimeUnit.SECONDS); //延迟3秒
    }

    @Override
    public Observable<HashMap<String, String>> sendVCode(HashMap<String, String> hashMap) {
        return apiService.setTest(hashMap); //延迟3秒
    }

    @Override
    public Observable<ResultBean<String, String>> register(HashMap<String, String> hashMap) {
        return apiService.register(hashMap);
    }

    @Override
    public Observable<ResultBean<ArrayList<GoodsListBean>, PageBean>> getGoodsListVip(HashMap<String, String> hashMap) {
        return apiService.getGoodsListVip(hashMap);
    }

    @Override
    public Observable<ResultBean<ArrayList<CategoryBean>, PageBean>> getCategoryListVip(HashMap<String, String> hashMap) {
        return apiService.getCategoryListVip(hashMap);
    }

    @Override
    public Observable<ResultBean<GoodsDetailInfoBean<ArrayList<GoodsChooseBean>>, String>> getGoodsDetails(HashMap<String, String> hashMap) {
        return apiService.getGoodsDetail(hashMap);
    }

    @Override
    public Observable<ResultBean<UrlBean, String>> getUrl(HashMap<String, String> hashMap) {
        return apiService.getUrl(hashMap);
    }

    @Override
    public Observable<ResultBean<String, Object>> addCart(HashMap<String, String> hashMap) {
        return apiService.addCart(hashMap);
    }

    @Override
    public Observable<ResultBean<LoginBean, Object>> login(HashMap<String, String> hashMap) {
        return apiService.login(hashMap);
    }

    @Override
    public Observable<ResultBean<ArrayList<CartListBean<ArrayList<CartBean>>>, String>> getCartList(HashMap<String, String> hashMap) {
        return apiService.getCartList(hashMap);
    }

    @Override
    public Observable<ResultBean<ArrayList<AddressBean>, PageBean>> getAddressListData(HashMap<String, String> hashMap) {
        return apiService.getAddressListData(hashMap);
    }

    @Override
    public Observable<ResultBean<ArrayList<LocalBean>, String>> getLocalData(HashMap<String, String> hashMap) {
        return apiService.getLocalData(hashMap);
    }

    @Override
    public Observable<ResultBean<AddressBean,String>> getSaveAddress(HashMap<String, String> hashMap) {
        return apiService.getSaveAddress(hashMap);
    }

    @Override
    public Observable<ResultBean<OrderinfoBean,String>> OrderInfoBuyGet(HashMap<String, String> hashMap) {
        return apiService.OrderInfoBuyGet(hashMap);
    }

    @Override
    public Observable<ResultBean<CollectBean, String>> modifyOrder(HashMap<String, String> hashMap) {
        return apiService.modifyOrder(hashMap);
    }

    @Override
    public Observable<ResultBean<String, Object>> deleteGoods(HashMap<String, String> hashMap) {
        return apiService.deleteGoods(hashMap);
    }

    @Override
    public Observable<ResultBean<String, Object>> isDefault(HashMap<String, String> hashMap) {
        return apiService.isDefault(hashMap);
    }

    @Override
    public Observable<ResultBean<String, String>> OrderSaveRedis(HashMap<String, String> hashMap) {
        return apiService.OrderSaveRedis(hashMap);
    }

    @Override
    public Observable<ResultBean<String, String>> buyVipGoods(HashMap<String, String> hashMap) {
        return apiService.buyVipGoods(hashMap);
    }

    @Override
    public Observable<ResultBean<String, String>> sureReceipt(HashMap<String, String> hashMap) {
        return apiService.sureReceipt(hashMap);
    }

    @Override
    public Observable<ResultBean<HomeBean, String>> getHomeData(HashMap<String, String> hashMap) {
        return apiService.getHomeData(hashMap);
    }
    @Override
    public Observable<ResultBean<ArrayList<OrderListBean>, PageBean>> getOrderList(HashMap<String, String> hashMap) {
        return apiService.getOrderList(hashMap);
    }

    public Observable<ResultBean<ArrayList<CollectListBean>, Object>> GoodCollectList(HashMap<String, String> mHashMap) {
        return apiService.GoodCollectList(mHashMap);
    }

    public Observable<ResultBean<String, Object>> DeleteCollectGood(HashMap<String, String> mHashMap) {
        return apiService.DeleteCollectGood(mHashMap);
    }

    public Observable<ResultBean<String, Object>> isGoodCollect(HashMap<String, String> mHashMap) {
        return apiService.isGoodCollect(mHashMap);
    }


    public Observable<ResultBean<String, Object>> addGoodCollect(HashMap<String, String> mHashMap) {
        return apiService.addGoodCollect(mHashMap);
    }


    public Observable<ResultBean<BalanceBean, Object>> getBalance(HashMap<String, String> mHashMap) {
        return apiService.getBalance(mHashMap);
    }

    public Observable<ResultBean<CcqBean, Object>> getCcqUse(HashMap<String, String> mHashMap) {
        return apiService.getCcqUse(mHashMap);
    }

    public Observable<ResultBean<ArrayList<CcqListBean>, PageBean>> getCcqList(HashMap<String, String> mHashMap) {
        return apiService.getCcqList(mHashMap);
    }

    public Observable<ResultBean<CcqListBean, PageBean>> getQrcodeCcqData(HashMap<String, String> mHashMap) {
        return apiService.getQrcodeCcqData(mHashMap);
    }

    public Observable<ResultBean<LoginBean, Object>> getUserInfo(HashMap<String, String> mHashMap) {
        return apiService.getUserInfo(mHashMap);
    }

    public Observable<ResultBean<UserBankBean, Object>> getBankCard(HashMap<String, String> mHashMap) {
        return apiService.getBankCard(mHashMap);
    }

    public Observable<ResultBean<ArrayList<BalanceDetailBean>, Object>> getAmountPrice(HashMap<String, String> mHashMap) {
        return apiService.getAmountPrice(mHashMap);
    }


}
