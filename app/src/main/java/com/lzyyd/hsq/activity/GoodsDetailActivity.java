package com.lzyyd.hsq.activity;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.GoodsBean;
import com.lzyyd.hsq.bean.GoodsChooseBean;
import com.lzyyd.hsq.bean.GoodsDetailBean;
import com.lzyyd.hsq.bean.GoodsDetailInfoBean;
import com.lzyyd.hsq.databinding.ActivityGoodsDetailBinding;
import com.lzyyd.hsq.interf.OnScrollChangedListener;
import com.lzyyd.hsq.ui.GoodsPopLayout;
import com.lzyyd.hsq.util.Eyes;
import com.lzyyd.hsq.util.HsqAppUtil;
import com.lzyyd.hsq.util.TextUtil;
import com.lzyyd.hsq.util.UToast;
import com.lzyyd.hsq.viewmodel.GoodsDetailViewModel;
import com.squareup.picasso.Picasso;
import com.xw.banner.BannerConfig;
import com.xw.banner.Transformer;
import com.xw.banner.listener.OnBannerListener;
import com.xw.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import me.tatarka.bindingcollectionadapter2.BR;

public class GoodsDetailActivity extends BaseActivity<ActivityGoodsDetailBinding,GoodsDetailViewModel> implements GoodsPopLayout.OnAddCart, OnScrollChangedListener, GoodsDetailViewModel.GoodsDetailDataCallBack, OnBannerListener {

    private GoodsDetailInfoBean<ArrayList<GoodsChooseBean>> goodsDetailBean;
    private int type = 0;
    private PopupWindow popupWindow;
    private GoodsDetailInfoBean selfGoodsBean;
    private int num = 1;
    private String attr_id = "0";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_goods_detail;
    }

    @Override
    public int initVariableId() {
        return BR.goodsDetailViewModel;
    }

    @Override
    public void initData() {

        Eyes.translucentStatusBar(this);

        String goodsid = getIntent().getExtras().getString("goodsId");

        viewModel.setClickCallBack(this);
        viewModel.getGoodsDetail(goodsid,ProApplication.SESSIONID());

    }

    @Override
    public GoodsDetailViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(GoodsDetailViewModel.class);
    }


    /**
     * 初始化popup
     */
    private void getpopup() {
        GoodsPopLayout goodsPopLayout = new GoodsPopLayout(this);
        popupWindow = new PopupWindow(goodsPopLayout, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT, true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);

        goodsPopLayout.setData(goodsDetailBean, type);
        goodsPopLayout.setListener(this);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                /*if (iv_bg != null) {
                    iv_bg.setVisibility(View.GONE);
                }*/
            }
        });
        goodsPopLayout.setPosition(3);
        popupWindow.showAtLocation(binding.rlGoods, Gravity.CENTER | Gravity.CENTER, 0, 0);
    }

    @Override
    public void addShopCart(GoodsDetailInfoBean<ArrayList<GoodsChooseBean>> goodsDetailBean, GoodsChooseBean goodsChooseBean, int num) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        if (goodsChooseBean != null  && goodsChooseBean.getAttr_id() != 0) {
            attr_id = String.valueOf(goodsChooseBean.getAttr_id());
        }
        this.num = num;
        viewModel.addCartinterf(goodsDetailBean.getGoodsId(), attr_id + "", num + "", ProApplication.SESSIONID());
    }

    @Override
    public void delete() {

    }

    @Override
    public void mRightNowBuy(GoodsDetailInfoBean selfGoodsBean, GoodsChooseBean goodsChooseBean, int num) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }

        this.selfGoodsBean = selfGoodsBean;

        String attr_id = "";
        if (goodsChooseBean != null) {
            attr_id = String.valueOf(goodsChooseBean.getAttr_id());
        }


        viewModel.getKey(goodsDetailBean.getGoodsId(), goodsDetailBean.getAttr() + "",   num+"", ProApplication.SESSIONID());
//        this.goodsChooseBean = goodsChooseBean;
//        this.num = num + "";


    }


    @Override
    public void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy) {
        //Y轴偏移量
        float scrollY = scrollView.getScrollY();

        //变化率
        float headerBarOffsetY = 400;//Toolbar与header高度的差值
        float offset = Math.max((headerBarOffsetY - scrollY) / headerBarOffsetY, 0f);

        if (offset > 0.5){
            binding.ivBackWhite.setAlpha(offset);
            binding.ivBackBlack.setAlpha(0f);
        }else {
            binding.ivBackWhite.setAlpha(0f);
            binding.ivBackBlack.setAlpha(1-offset);
        }

        binding.rlGoodsDetailTitle.setAlpha(1-offset);
    }

    @Override
    public void loadMore() {

    }


    @Override
    public void getDataSuccess(GoodsDetailInfoBean<ArrayList<GoodsChooseBean>> goodsListBeans) {

        this.goodsDetailBean = goodsListBeans;
        type = goodsDetailBean.getGoodsType();

        binding.setVariable(BR.goodsDetail,goodsListBeans);

        TextUtil.setText(this,goodsListBeans.getGoodsName(),"秒杀",binding.tvGoodsContent);

        binding.tvOldPrice.setText("￥" + goodsListBeans.getMarketPrice());

        binding.tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        binding.tvRushTime.start("2020-07-28 07:00:00");
        binding.tsvDetail.init(this);

        List<String> list_path = new ArrayList<>();
        String[] strings;
        if (goodsListBeans.getGoodsImgList().contains(",")) {
            strings = goodsListBeans.getGoodsImgList().split(",");
        } else {
            strings = new String[1];
            strings[0] = goodsListBeans.getGoodsImgList();
        }

        for (int i = 0; i < strings.length; i++) {
            list_path.add(strings[i]);
        }

        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        binding.bannerGoodPic.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器，图片加载器在下方
        binding.bannerGoodPic.setImageLoader(new MyLoader());
        //设置图片网址或地址的集合
        binding.bannerGoodPic.setImages(list_path);
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        binding.bannerGoodPic.setBannerAnimation(Transformer.Default);
        //设置轮播图的标题集合
        binding.bannerGoodPic.setBannerTitles(list_path);
        //设置轮播间隔时间
        binding.bannerGoodPic.setDelayTime(3000);
        //设置是否为自动轮播，默认是“是”。
        binding.bannerGoodPic.isAutoPlay(false);

        //设置指示器的位置，小点点，左中右。
        binding.bannerGoodPic.setIndicatorGravity(BannerConfig.CENTER)
                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                .setOnBannerListener(this)
                //必须最后调用的方法，启动轮播图。
                .start();
    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void getClickPop() {
        getpopup();
    }

    @Override
    public void addCartSuccess(String msg) {
        UToast.show(this,"加入购物车成功");
    }

    @Override
    public void addCartFail(String msg) {
        UToast.show(this,msg);
    }

    @Override
    public void SureOrderSuccess(String msg) {
        Bundle bundle = new Bundle();
        bundle.putString(HsqAppUtil.GOODSID, selfGoodsBean.getGoodsId());
        bundle.putInt(HsqAppUtil.GOODSNUM, Integer.valueOf(num));
        bundle.putString(HsqAppUtil.ATTRID, attr_id);
        bundle.putString(HsqAppUtil.KEY, msg);
        bundle.putInt(HsqAppUtil.TYPE,goodsDetailBean.getGoodsType());
        startActivity(SureOrderActivity.class,bundle);
    }

    @Override
    public void SureOrderFail(String msg) {
        UToast.show(this,msg);
    }

    @Override
    public void sureOrder() {
        if (goodsDetailBean != null && Integer.valueOf(goodsDetailBean.getGoodsNumber()) == 0) {
            UToast.show(this,"库存不足，无法下单");
        } else {
            if (type == HsqAppUtil.GOODSTYPE_VIP || type == HsqAppUtil.GOODSTYPE_SECKILL || type == HsqAppUtil.GOODSTYPE_CROWDFUNDING) {
                if (goodsDetailBean != null) {
                    mRightNowBuy(goodsDetailBean, null, 1);
                }
            } else {
                if (goodsDetailBean.getQty() == 0) {
                    getpopup();
                }
            }
        }
    }

    @Override
    public void addCart() {
        if (goodsDetailBean.getQty() == 0) {
            viewModel.addCartinterf(goodsDetailBean.getGoodsId(), attr_id + "", num + "", ProApplication.SESSIONID());
        }else {
            getpopup();
        }
    }

    @Override
    public void OnBannerClick(int position) {

    }

    //自定义的图片加载器
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {

            Picasso.with(context).load(ProApplication.BANNERIMG + (String) path).error(R.mipmap.ic_banner).into(imageView);
        }
    }
}
