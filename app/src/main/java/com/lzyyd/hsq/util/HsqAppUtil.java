package com.lzyyd.hsq.util;

import android.widget.ImageView;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.ProApplication;
import com.squareup.picasso.Picasso;

import androidx.databinding.BindingAdapter;

/**
 * Create by liguo on &{DATE}
 * Describe:
 */
public class HsqAppUtil {
    public static final int PAGE_HOMEPAGE = 0;
    public static final int PAGE_FIND = 1;
    public static final int PAGE_MALL = 2;
    public static final int PAGE_ME = 3;

    public static final int WXTYPE_LOGIN = 1;
    public static final int WXTYPE_SHARED = 2;

    public static String RESULT_SUCCESS = "success";
    public static String RESULT_FAIL = "fail";

    public static final String TYPEID = "TYPEID";
    public static final String GOODSTYPE = "GOODSTYPE";
    public static final String USERNAME = "username";
    public static final String OTHERUSERNAME = "OtherUserName";
    public static final String GOODSNAME = "goodsname";
    public static final String USERID = "userid";
    public static final String LOGIN = "login";
    public static final String OPENID = "openid";
    public static final String UNIONID = "unionid";
    public static final String GROUPONGOODS = "groupongoods";
    public static final String INTEGRAL = "integral";
    public static final String MANUFACURE = "Manufacure";
    public static final String VIP = "vip";
    public static final String GOODSID = "goodsid";
    public static final String TYPE = "type";
    public static final String GOODSCHOOSEBEAN = "goodsChooseBean";
    public static final String GOODSDETAILINFOBEAN = "GoodsDetailInfoBean";
    public static final String RIGHTNOWBUYBEAN = "RightNowBuyBean";
    public static final String GOODSNUM = "GoodsNum";
    public static final String ATTRID = "attr_id";
    public static final String KEY = "KEY";
    public static final String ORDERID = "orderid";
    public static final String ORDERAMOUNT = "orderamount";
    public static final String CATID = "catid";
    public static final String ACCOUNT = "account";
    public static final String POINT = "point";
    public static final String HEADIMGURL = "headimgurl";
    public static final String TEAMID = "teamid";
    public static final String TELEPHONE = "telephone";
    public static final String PRICE = "price";
    public static final String BALANCEBEAN = "balanceBean";
    public static final String IMG = "img";
    public static final String BANNERIMG = "bannerimg";
    public static final String WHERE = "where";
    public static final String GOODS = "goods";
    public static final String CUSTOMER = "customer";
    public static final String SHAREDIMG = "shared";
    public static final String VIPVALIDITY = "VipValidity";
    public static final String WLMCOIN = "wlmcoin";
    public static final String USERBANKBEAN = "UserBankBean";
    public static final String USERLEVEL = "UserLevel";
    public static final String USERLEVELNAME = "UserLevelName";
    public static final String SHAREDMEIMG = "Sharedmeimg";
    public static final String ADDRESS = "ADDRESS";
    public static final String CCQTYPE = "CCQTYPE";
    public static final String LEVEL = "LEVEL";
    public static final String PROJECT = "PROJECT";
    public static final String SQURL = "SQURL";
    public static final String SEARCH = "SEARCH";

    public static final int GOODSTYPE_COMMON = 1;//普通商品
    public static final int GOODSTYPE_INTEGRAL = 2;//积分商城
    public static final int GOODSTYPE_VIP = 4;//vip创客礼包
    public static final int GOODSTYPE_SERVICE_CENTER = 8;//服务中心
    public static final int GOODSTYPE_SECKILL = 16;//秒杀
    public static final int GOODSTYPE_CCQ = 64;//常常清

    public static final String APP_ID = "wxd0358b9affd3d81b";
    public static final String SECRET = "f24a148883365f08ab912dac8bc8a7d8";
//    public static final String APP_ID = "wx27fb4ad747521493";
//    public static final String SECRET = "b552b4f33b682c4520315ef3ca50a31d";


    @BindingAdapter({"image"})
    public static void setImage(ImageView view, String url) {
        Picasso.with(view.getContext()).load(ProApplication.BANNERIMG + url).error(R.mipmap.ic_banner).into(view);
    }

    @BindingAdapter({"images"})
    public static void setImages(ImageView view, String url) {
        Picasso.with(view.getContext()).load(url).into(view);
    }

}
