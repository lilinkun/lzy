package com.lzyyd.hsq.data.source;

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
import com.lzyyd.hsq.bean.OrderDetailAddressBean;
import com.lzyyd.hsq.bean.OrderListBean;
import com.lzyyd.hsq.bean.OrderinfoBean;
import com.lzyyd.hsq.bean.PageBean;
import com.lzyyd.hsq.bean.ResultBean;
import com.lzyyd.hsq.bean.UrlBean;
import com.lzyyd.hsq.bean.UserBankBean;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;

/**
 * Created by goldze on 2019/3/26.
 */
public interface HttpDataSource {
    //模拟登录
    Observable<Object> login();

    //发送验证码
    Observable<ResultBean<String, String>> sendVCode(HashMap<String, String> hashMap);

    //注冊
    Observable<ResultBean<String, String>> register(HashMap<String, String> hashMap);

    //获取自营商品列表数据
    Observable<ResultBean<ArrayList<GoodsListBean>, PageBean>> getGoodsListVip(HashMap<String, String> hashMap);

    //获取自营商品列表数据
    Observable<ResultBean<String, Object>> getCash(HashMap<String, String> hashMap);

    //获取主页数据
    Observable<ResultBean<HomeBean, String>> getHomeData(HashMap<String, String> hashMap);

    //获取自营商品列表数据
    Observable<ResultBean<ArrayList<CategoryBean>, PageBean>> getCategoryListVip(HashMap<String, String> hashMap);

    //获取自营商品数据
    Observable<ResultBean<GoodsDetailInfoBean<ArrayList<GoodsChooseBean>>, String>> getGoodsDetails(HashMap<String, String> hashMap);

    //获取图片地址前缀
    Observable<ResultBean<UrlBean, String>> getUrl(HashMap<String, String> hashMap);

    //加入购物车
    Observable<ResultBean<String, Object>> addCart(HashMap<String, String> hashMap);

    Observable<ResultBean<LoginBean, Object>> login(HashMap<String, String> hashMap);

    Observable<ResultBean<ArrayList<CartListBean<ArrayList<CartBean>>>, String>> getCartList(HashMap<String, String> hashMap);

    Observable<ResultBean<ArrayList<AddressBean>, PageBean>> getAddressListData(HashMap<String, String> hashMap);

    Observable<ResultBean<ArrayList<OrderListBean>, PageBean>> getOrderList(HashMap<String, String> hashMap);

    Observable<ResultBean<ArrayList<LocalBean>, String>> getLocalData(HashMap<String, String> hashMap);

    Observable<ResultBean<AddressBean,String>> getSaveAddress(HashMap<String, String> hashMap);

    Observable<ResultBean<OrderinfoBean,String>> OrderInfoBuyGet(HashMap<String, String> hashMap);

    Observable<ResultBean<String,String>> OrderSaveRedis(HashMap<String, String> hashMap);

    Observable<ResultBean<String,String>> buyVipGoods(HashMap<String, String> hashMap);

    Observable<ResultBean<String,String>> sureReceipt(HashMap<String, String> hashMap);

    Observable<ResultBean<CollectBean,String>> modifyOrder(HashMap<String, String> hashMap);

    Observable<ResultBean<String,Object>> deleteGoods(HashMap<String, String> hashMap);

    Observable<ResultBean<String,Object>> isDefault(HashMap<String, String> hashMap);

    Observable<ResultBean<String,Object>> addGoodCollect(HashMap<String, String> hashMap);

    Observable<ResultBean<String,Object>> isGoodCollect(HashMap<String, String> hashMap);

    Observable<ResultBean<String,Object>> DeleteCollectGood(HashMap<String, String> hashMap);

    Observable<ResultBean<ArrayList<CollectListBean>,Object>> GoodCollectList(HashMap<String, String> hashMap);

    Observable<ResultBean<BalanceBean,Object>> getBalance(HashMap<String, String> hashMap);

    Observable<ResultBean<CcqBean,Object>> getCcqUse(HashMap<String, String> hashMap);

    Observable<ResultBean<ArrayList<CcqListBean>,PageBean>> getCcqList(HashMap<String, String> hashMap);

    Observable<ResultBean<CcqListBean,PageBean>> getQrcodeCcqData(HashMap<String, String> hashMap);

    Observable<ResultBean<LoginBean,Object>> getUserInfo(HashMap<String, String> hashMap);

    Observable<ResultBean<UserBankBean,Object>> getBankCard(HashMap<String, String> hashMap);

    Observable<ResultBean<ArrayList<BankBean>,Object>> getBankInfo(HashMap<String, String> hashMap);

    Observable<ResultBean<ArrayList<BalanceDetailBean>,Object>> getAmountPrice(HashMap<String, String> hashMap);

    Observable<ResultBean<String,Object>> setTransferout(HashMap<String, String> hashMap);

    Observable<ResultBean<Integer,Object>> setTtianfeng(HashMap<String, String> hashMap);

    Observable<ResultBean<OrderDetailAddressBean,Object>> getOrderDetail(HashMap<String, String> hashMap);

    Observable<ResultBean<CollectBean,Object>> wxPay(HashMap<String, String> hashMap);

}
