package com.lzyyd.hsq.http;

import com.lzyyd.hsq.bean.AddressBean;
import com.lzyyd.hsq.bean.BalanceBean;
import com.lzyyd.hsq.bean.BalanceDetailBean;
import com.lzyyd.hsq.bean.BankBean;
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
import com.lzyyd.hsq.bean.OrderBean;
import com.lzyyd.hsq.bean.OrderGoodsBuyListBean;
import com.lzyyd.hsq.bean.OrderListBean;
import com.lzyyd.hsq.bean.OrderinfoBean;
import com.lzyyd.hsq.bean.PageBean;
import com.lzyyd.hsq.bean.ResultBean;
import com.lzyyd.hsq.bean.UrlBean;
import com.lzyyd.hsq.bean.UserBankBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by LG on 2018/11/7.
 */

public interface RetrofitService {

    @FormUrlEncoded
    @POST("Api/")
    Observable<HashMap<String,String>> setTest(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<String,String>> register(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<ArrayList<GoodsListBean>, PageBean>> getGoodsListVip(@FieldMap Map<String, String> params);


    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<ArrayList<CategoryBean>, PageBean>> getCategoryListVip(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<GoodsDetailInfoBean<ArrayList<GoodsChooseBean>>, String>> getGoodsDetail(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<UrlBean, String>> getUrl(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<String, Object>> addCart(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<LoginBean, Object>> login(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<ArrayList<CartListBean<ArrayList<CartBean>>>, String>> getCartList(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<ArrayList<AddressBean>, PageBean>> getAddressListData(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<ArrayList<LocalBean>, String>> getLocalData(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<AddressBean,String>> getSaveAddress(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<OrderinfoBean,String>> OrderInfoBuyGet(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<CollectBean,String>> modifyOrder(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<String,Object>> deleteGoods(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<String,Object>> isDefault(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<String,String>> OrderSaveRedis(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<String,String>> buyVipGoods(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<String,String>> sureReceipt(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<HomeBean,String>> getHomeData(@FieldMap Map<String, String> params);


    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<ArrayList<OrderListBean>,PageBean>> getOrderList(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<String, Object>> addGoodCollect(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<String, Object>> isGoodCollect(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<ArrayList<CollectListBean>, Object>> GoodCollectList(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<String, Object>> DeleteCollectGood(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<String, Object>> getCash(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<BalanceBean, Object>> getBalance(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<CcqBean, Object>> getCcqUse(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<ArrayList<CcqListBean>, PageBean>> getCcqList(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<CcqListBean, PageBean>> getQrcodeCcqData(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<LoginBean, Object>> getUserInfo(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<UserBankBean, Object>> getBankCard(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<ArrayList<BalanceDetailBean>, Object>> getAmountPrice(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<ArrayList<BankBean>, Object>> getBankInfo(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<String, Object>> setTransferout(@FieldMap Map<String, String> params);

}
