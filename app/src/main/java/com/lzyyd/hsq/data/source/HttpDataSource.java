package com.lzyyd.hsq.data.source;

import com.lzyyd.hsq.bean.AddressBean;
import com.lzyyd.hsq.bean.CartBean;
import com.lzyyd.hsq.bean.CartListBean;
import com.lzyyd.hsq.bean.CategoryBean;
import com.lzyyd.hsq.bean.CollectBean;
import com.lzyyd.hsq.bean.GoodsChooseBean;
import com.lzyyd.hsq.bean.GoodsDetailInfoBean;
import com.lzyyd.hsq.bean.GoodsListBean;
import com.lzyyd.hsq.bean.LocalBean;
import com.lzyyd.hsq.bean.LoginBean;
import com.lzyyd.hsq.bean.OrderinfoBean;
import com.lzyyd.hsq.bean.PageBean;
import com.lzyyd.hsq.bean.ResultBean;
import com.lzyyd.hsq.bean.UrlBean;

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
    Observable<HashMap<String, String>> sendVCode(HashMap<String, String> hashMap);

    //注冊
    Observable<ResultBean<String, String>> register(HashMap<String, String> hashMap);

    //获取自营商品列表数据
    Observable<ResultBean<ArrayList<GoodsListBean>, PageBean>> getGoodsListVip(HashMap<String, String> hashMap);

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

    Observable<ResultBean<ArrayList<LocalBean>, String>> getLocalData(HashMap<String, String> hashMap);

    Observable<ResultBean<AddressBean,String>> getSaveAddress(HashMap<String, String> hashMap);

    Observable<ResultBean<OrderinfoBean,String>> OrderInfoBuyGet(HashMap<String, String> hashMap);

    Observable<ResultBean<String,String>> OrderSaveRedis(HashMap<String, String> hashMap);

    Observable<ResultBean<String,String>> buyVipGoods(HashMap<String, String> hashMap);

    Observable<ResultBean<CollectBean,String>> modifyOrder(HashMap<String, String> hashMap);

    Observable<ResultBean<String,Object>> deleteGoods(HashMap<String, String> hashMap);
}
