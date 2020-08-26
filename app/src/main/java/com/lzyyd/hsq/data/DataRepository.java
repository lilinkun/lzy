package com.lzyyd.hsq.data;

import com.lzyyd.hsq.bean.AddressBean;
import com.lzyyd.hsq.bean.CartBean;
import com.lzyyd.hsq.bean.CartListBean;
import com.lzyyd.hsq.bean.CategoryBean;
import com.lzyyd.hsq.bean.CollectBean;
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
import com.lzyyd.hsq.data.source.HttpDataSource;
import com.lzyyd.hsq.data.source.LocalDataSource;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.VisibleForTesting;
import io.reactivex.Observable;
import me.goldze.mvvmhabit.base.BaseModel;

public class DataRepository extends BaseModel implements HttpDataSource, LocalDataSource{

    private volatile static DataRepository INSTANCE = null;
    private final HttpDataSource mHttpDataSource;

    private final LocalDataSource mLocalDataSource;

    public DataRepository(HttpDataSource httpDataSource,
                          LocalDataSource localDataSource) {
        this.mHttpDataSource = httpDataSource;
        this.mLocalDataSource = localDataSource;

    }

    public static DataRepository getInstance(HttpDataSource httpDataSource,
                                             LocalDataSource localDataSource){
        if (INSTANCE == null) {
            synchronized (DataRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DataRepository(httpDataSource, localDataSource);
                }
            }
        }
        return INSTANCE;
    }

    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public Observable<Object> login() {
        return mHttpDataSource.login();
    }

    @Override
    public void saveUserName(String userName) {

    }

    @Override
    public void savePassword(String password) {

    }

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }


    //发送验证码
    public Observable<HashMap<String, String>> sendVCode(HashMap<String, String> hashMap){
        return mHttpDataSource.sendVCode(hashMap);
    }

    //注冊
    public Observable<ResultBean<String, String>> register(HashMap<String, String> hashMap){
        return mHttpDataSource.register(hashMap);
    }

    //获取自营商品列表数据
    public Observable<ResultBean<ArrayList<GoodsListBean>, PageBean>> getGoodsListVip(HashMap<String, String> hashMap){
        return mHttpDataSource.getGoodsListVip(hashMap);
    }

    //获取自营商品列表数据
    public Observable<ResultBean<HomeBean, String>> getHomeData(HashMap<String, String> hashMap){
        return mHttpDataSource.getHomeData(hashMap);
    }

    //获取自营商品列表数据
    public Observable<ResultBean<ArrayList<CategoryBean>, PageBean>> getCategoryListVip(HashMap<String, String> hashMap){
        return mHttpDataSource.getCategoryListVip(hashMap);
    }

    //获取自营商品数据
    public Observable<ResultBean<GoodsDetailInfoBean<ArrayList<GoodsChooseBean>>, String>> getGoodsDetails(HashMap<String, String> hashMap){
        return mHttpDataSource.getGoodsDetails(hashMap);
    }

    //获取图片地址前缀
    public Observable<ResultBean<UrlBean, String>> getUrl(HashMap<String, String> hashMap){
        return mHttpDataSource.getUrl(hashMap);
    }

    //加入购物车
    public Observable<ResultBean<String, Object>> addCart(HashMap<String, String> hashMap){
        return mHttpDataSource.addCart(hashMap);
    }

    //登陆
    public Observable<ResultBean<LoginBean, Object>> login(HashMap<String, String> mHashMap) {
        return mHttpDataSource.login(mHashMap);
    }

    //获取购物车列表
    public Observable<ResultBean<ArrayList<CartListBean<ArrayList<CartBean>>>, String>> getCartList(HashMap<String, String> mHashMap) {
        return mHttpDataSource.getCartList(mHashMap);
    }

    /**
     * 修改购物车信息
     */
    public Observable<ResultBean<CollectBean,String>> modifyOrder(HashMap<String,String> mHashMap){
        return mHttpDataSource.modifyOrder(mHashMap);
    }

    /**
     * 删除购物车
     */
    public Observable<ResultBean<String,Object>> deleteGoods(HashMap<String,String> mHashMap){
        return mHttpDataSource.deleteGoods(mHashMap);
    }

    //获取地址列表
    public Observable<ResultBean<ArrayList<AddressBean>, PageBean>> getAddressListData(HashMap<String, String> mHashMap) {
        return mHttpDataSource.getAddressListData(mHashMap);
    }

    //添加地址
    public Observable<ResultBean<ArrayList<LocalBean>, String>> getLocalData(HashMap<String, String> mHashMap) {
        return mHttpDataSource.getLocalData(mHashMap);
    }

    //增加收货地址
    public Observable<ResultBean<AddressBean,String>> getSaveAddress(HashMap<String, String> mHashMap) {
        return mHttpDataSource.getSaveAddress(mHashMap);
    }

    //获取订单详情
    public Observable<ResultBean<OrderinfoBean,String>> OrderInfoBuyGet(HashMap<String, String> mHashMap) {
        return mHttpDataSource.OrderInfoBuyGet(mHashMap);
    }

    //获取订单详情
    public Observable<ResultBean<String,String>> OrderSaveRedis(HashMap<String, String> mHashMap) {
        return mHttpDataSource.OrderSaveRedis(mHashMap);
    }

    //获取订单详情
    public Observable<ResultBean<String,String>> buyVipGoods(HashMap<String, String> mHashMap) {
        return mHttpDataSource.buyVipGoods(mHashMap);
    }

    //确认收货
    public Observable<ResultBean<String,String>> sureReceipt(HashMap<String, String> mHashMap) {
        return mHttpDataSource.sureReceipt(mHashMap);
    }

    public Observable<ResultBean<ArrayList<OrderListBean>,PageBean>> getOrderList(HashMap<String, String> mHashMap) {
        return mHttpDataSource.getOrderList(mHashMap);
    }
}
