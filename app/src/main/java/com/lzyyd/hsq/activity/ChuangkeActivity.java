package com.lzyyd.hsq.activity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.adapter.ChuangkeAdapter;
import com.lzyyd.hsq.adapter.ChuangkePopAdapter;
import com.lzyyd.hsq.adapter.TabPageAdapter;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.CategoryBean;
import com.lzyyd.hsq.bean.GoodsChooseBean;
import com.lzyyd.hsq.bean.GoodsDetailInfoBean;
import com.lzyyd.hsq.bean.GoodsListBean;
import com.lzyyd.hsq.bean.PageBean;
import com.lzyyd.hsq.bean.VipChooseItemBean;
import com.lzyyd.hsq.databinding.ActivityChuangkeBinding;
import com.lzyyd.hsq.fragment.ChuangkeFragment;
import com.lzyyd.hsq.ui.GoodsPopLayout;
import com.lzyyd.hsq.ui.VipGoodsPopLayout;
import com.lzyyd.hsq.util.Eyes;
import com.lzyyd.hsq.util.HsqAppUtil;
import com.lzyyd.hsq.util.UToast;
import com.lzyyd.hsq.viewmodel.ChuangkeViewModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

/**
 * Create by liguo on 2020/8/24
 * Describe:
 */
public class ChuangkeActivity extends BaseActivity<ActivityChuangkeBinding, ChuangkeViewModel> implements ChuangkeViewModel.ChuangkeCategoryDateCallBack, ChuangkeAdapter.ModifyCountInterface, GoodsPopLayout.OnVipGoods, ChuangkePopAdapter.ModifyCountInterface {

    private List<Fragment> fragments = new ArrayList<>();
    public static ArrayList<VipChooseItemBean> vipChooseItemBeans = new ArrayList<>();
    public static ArrayList<String> totalGoods = new ArrayList<>();
    private PopupWindow popupWindow;
    private PopupWindow vipGoodsPopupWindow;
    private int positionFragment = 0;
    private int goodstype = 0;
    private double price = 0;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_chuangke;
    }

    @Override
    public int initVariableId() {
        return BR.chuangke;
    }

    @Override
    public ChuangkeViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(ChuangkeViewModel.class);
    }

    @Override
    public void initData() {

        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));

        goodstype = getIntent().getExtras().getInt(HsqAppUtil.GOODSTYPE);

        totalGoods.clear();
        vipChooseItemBeans.clear();

        if (goodstype == 4){
            binding.tvTitle.setText("创客礼包");
        }else if (goodstype == 8){
            binding.tvTitle.setText("服务中心");
        }else if (goodstype == 16){
            binding.tvTitle.setText("二次进货");
        }

        viewModel.setChuangkeCategoryDateCallBack(this);
        viewModel.getCategoryData(1,20);

    }

    @Override
    public void getCategorySuccess(ArrayList<CategoryBean> categoryBeans, PageBean page) {
        ArrayList<String> mTitles = new ArrayList<>();
        ChuangkeFragment chuangkeFragment1 = new ChuangkeFragment("",goodstype,this);
        mTitles.add("全部");
        fragments.add(chuangkeFragment1);
        for (CategoryBean categoryBean : categoryBeans){
            ChuangkeFragment chuangkeFragment = new ChuangkeFragment(categoryBean.getCategoryID(),goodstype,this);
            mTitles.add(categoryBean.getCategoryName());
            fragments.add(chuangkeFragment);
        }

        TabPageAdapter pageAdapter = new TabPageAdapter(getSupportFragmentManager(), fragments, mTitles);
        pageAdapter.setTitles(mTitles);
        binding.chuangkeListVp.setAdapter(pageAdapter);
//        orderListTablayou.setupWithViewPager(orderListVp);
//        orderListTablayou.setSelectedTabIndicatorColor(getResources().getColor(R.color.red));
        binding.orderListTablayou.setViewPager(binding.chuangkeListVp);
        binding.chuangkeListVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((ChuangkeFragment)fragments.get(position)).setFresh();
                positionFragment = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void getCategoryFail(String msg) {
        UToast.show(this,msg);
    }

    @Override
    public void getDetailSucccess(GoodsDetailInfoBean<ArrayList<GoodsChooseBean>> s) {
        getpopup(s);
    }

    @Override
    public void getDetailFail(String msg) {

    }

    @Override
    public void visibleLayout() {
        if (totalGoods.size()!=0){
            VipGoodsPopLayout vipGoodsPopLayout = new VipGoodsPopLayout(this);
            vipGoodsPopupWindow = new PopupWindow(vipGoodsPopLayout, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
            vipGoodsPopupWindow.setBackgroundDrawable(new BitmapDrawable());
            vipGoodsPopupWindow.setFocusable(true);
            vipGoodsPopupWindow.setOutsideTouchable(true);
            vipGoodsPopupWindow.setAnimationStyle(R.style.popwin_anim_style);

            vipGoodsPopLayout.setData(vipChooseItemBeans,this);

            vipGoodsPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                /*if (iv_bg != null) {
                    iv_bg.setVisibility(View.GONE);
                }*/
                }
            });
            vipGoodsPopupWindow.showAtLocation(binding.rlGoodsBottom, Gravity.TOP, 0, 50);
        }
    }

    @Override
    public void sureorder() {

        if (price <= ProApplication.USERLEVELPRICE10){
            UToast.show(this,"所选金额不足");
            return;
        }

        try{
            JSONArray jsonArray = new JSONArray();

            for (VipChooseItemBean vipChooseItemBean : vipChooseItemBeans) {

                JSONObject jsonObject = new JSONObject();
                String goodsid = vipChooseItemBean.getGoodsId();
                int AttrId = 0;
                if (vipChooseItemBean.getGoodsChooseBean() != null) {
                    AttrId = vipChooseItemBean.getGoodsChooseBean().getAttr_id();
                }
                if (goodsid.contains(",")){
                    goodsid = goodsid.substring(0,goodsid.indexOf(","));
                }

                jsonObject.put("GoodsId", goodsid);
                jsonObject.put("Num",vipChooseItemBean.getNum());
                jsonObject.put("AttrId",AttrId);
                jsonArray.put(jsonObject);
            }

            viewModel.buyVipGoods(jsonArray.toString(),HsqAppUtil.GOODSTYPE_VIP+"",ProApplication.SESSIONID());
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public void sureVipGoodsSuccess(String msg) {
        Bundle bundle = new Bundle();
        bundle.putString(HsqAppUtil.KEY, msg);
        startActivity(SureOrderActivity.class,bundle);
    }

    @Override
    public void sureVipGoodsFail(String msg) {
        UToast.show(this,"fail" + msg);
    }

    /**
     * 初始化popup
     */
    private void getpopup(GoodsDetailInfoBean<ArrayList<GoodsChooseBean>> goodsDetailBean) {
        GoodsPopLayout goodsPopLayout = new GoodsPopLayout(this);
        popupWindow = new PopupWindow(goodsPopLayout, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT, true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);

        goodsPopLayout.setData(goodsDetailBean, HsqAppUtil.GOODSTYPE_VIP);
        goodsPopLayout.setVipListener(this);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                /*if (iv_bg != null) {
                    iv_bg.setVisibility(View.GONE);
                }*/
            }
        });
        goodsPopLayout.setPosition(2);
        popupWindow.showAtLocation(binding.rlGoods, Gravity.CENTER | Gravity.CENTER, 0, 0);
    }


    @Override
    public void doIncrease(GoodsListBean goodsListBean, int showCountView) {

        if (totalGoods == null || totalGoods.size() == 0){
            setTotalGoods(goodsListBean,showCountView);
        }else {
            if (totalGoods.contains(goodsListBean.getGoodsId())){
                for (int i = 0; i < vipChooseItemBeans.size(); i++) {
                    if (vipChooseItemBeans.get(i).getGoodsId().equals(goodsListBean.getGoodsId())){
                        vipChooseItemBeans.get(i).setNum(showCountView);
                    }
                }
            }else {
                setTotalGoods(goodsListBean,showCountView);
            }
        }

        setNum();

    }

    @Override
    public void doDecrease(GoodsListBean goodsListBean, int showCountView) {

        if (showCountView == 0){
            if (totalGoods.contains(goodsListBean.getGoodsId())){
                totalGoods.remove(goodsListBean.getGoodsId());
            }
            for (int i =0 ; i < vipChooseItemBeans.size();i++){
                if (vipChooseItemBeans.get(i).getGoodsId().equals(goodsListBean.getGoodsId())){
                    vipChooseItemBeans.remove(i);
                }
            }
        }else {

            for (int i =0 ; i < vipChooseItemBeans.size();i++){
                if (vipChooseItemBeans.get(i).getGoodsId().equals(goodsListBean.getGoodsId())){
                    vipChooseItemBeans.get(i).setNum(showCountView);
                }
            }
        }
        setNum();
    }

    @Override
    public void doUpdate(GoodsListBean goodsListBean, int showCountView) {

    }

    @Override
    public void childDelete(GoodsListBean goodsListBean) {

    }

    @Override
    public void doIncrease(VipChooseItemBean vipChooseItemBean, int showCountView) {

        if (totalGoods.contains(vipChooseItemBean.getGoodsId())){
            for (int i = 0; i < vipChooseItemBeans.size(); i++) {
                if (vipChooseItemBeans.get(i).getGoodsId().equals(vipChooseItemBean.getGoodsId())){
                    vipChooseItemBeans.get(i).setNum(showCountView);
                }
            }
        }

        setNum();
        ((ChuangkeFragment)fragments.get(positionFragment)).setFresh();
    }

    @Override
    public void doDecrease(VipChooseItemBean goodsListBean, int showCountView) {

        if (showCountView == 0){
            if (totalGoods.contains(goodsListBean.getGoodsId())){
                totalGoods.remove(goodsListBean.getGoodsId());
            }
            for (VipChooseItemBean vipChooseItemBean : vipChooseItemBeans){
                if (vipChooseItemBean.getGoodsId().equals(goodsListBean.getGoodsId())){
                    vipChooseItemBeans.remove(vipChooseItemBean);
                }
            }
        }else {

            for (VipChooseItemBean vipChooseItemBean : vipChooseItemBeans){
                if (vipChooseItemBean.getGoodsId().equals(goodsListBean.getGoodsId())){
                    vipChooseItemBean.setNum(showCountView);
                }
            }
        }
        setNum();
        ((ChuangkeFragment)fragments.get(positionFragment)).setFresh();
    }

    @Override
    public void doUpdate(VipChooseItemBean goodsListBean, int showCountView) {

    }

    @Override
    public void AllDelete() {
        if (vipGoodsPopupWindow != null && vipGoodsPopupWindow.isShowing()){
            vipGoodsPopupWindow.dismiss();
        }
        vipChooseItemBeans.clear();
        totalGoods.clear();
        setNum();
        ((ChuangkeFragment)fragments.get(positionFragment)).setFresh();
    }

    @Override
    public void sumGoodsNum(int num) {

    }

    @Override
    public void ChooseGoodsId(String goodsid) {
        viewModel.getGoodsDetail(goodsid, ProApplication.SESSIONID());
    }

    @Override
    public void deleteVip() {
        if (vipGoodsPopupWindow != null && vipGoodsPopupWindow.isShowing()){
            vipGoodsPopupWindow.dismiss();
        }
    }

    public void setTotalGoods(GoodsListBean goodsListBean,int showCountView){
        totalGoods.add(goodsListBean.getGoodsId());
        VipChooseItemBean vipChooseItemBean = new VipChooseItemBean();
        vipChooseItemBean.setGoodsId(goodsListBean.getGoodsId());
        vipChooseItemBean.setGoodsSn(goodsListBean.getGoodsSn());
        vipChooseItemBean.setNum(showCountView);
        vipChooseItemBean.setPrice(goodsListBean.getPrice());
        vipChooseItemBean.setQty(goodsListBean.getQty());
        vipChooseItemBean.setGoodsImg(goodsListBean.getGoodsImg());
        vipChooseItemBean.setGoodsName(goodsListBean.getGoodsName());
        vipChooseItemBeans.add(vipChooseItemBean);
    }

    public void setNum(){
        int num = 0;
        price = 0;

        if (vipChooseItemBeans == null || vipChooseItemBeans.size() == 0){

            viewModel.goodsCount.set(0);
            String s = String.valueOf(price);
            viewModel.price.set("");
        }else {

            for (int i = 0; i < vipChooseItemBeans.size(); i++) {
                num += vipChooseItemBeans.get(i).getNum();
                price += vipChooseItemBeans.get(i).getNum() * vipChooseItemBeans.get(i).getPrice();
            }

            viewModel.goodsCount.set(num);
            viewModel.price.set("￥" + price);
        }
    }

    @Override
    public void mRightNowBuy(GoodsDetailInfoBean selfGoodsBean, GoodsChooseBean goodsChooseBean, int num) {

        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }

        if (totalGoods.contains(goodsChooseBean.getGoodsId()+","+goodsChooseBean.getAttr_id())){

            for (VipChooseItemBean vipChooseItemBean : vipChooseItemBeans){
                if (vipChooseItemBean.getGoodsId().equals(goodsChooseBean.getGoodsId()+","+goodsChooseBean.getAttr_id())){
                    vipChooseItemBean.setNum(vipChooseItemBean.getNum()+num);
                }
            }

        }else {

            VipChooseItemBean vipChooseItemBean = new VipChooseItemBean();
            vipChooseItemBean.setGoodsId(goodsChooseBean.getGoodsId() + "," + goodsChooseBean.getAttr_id());
            vipChooseItemBean.setGoodsSn(selfGoodsBean.getGoodsSn());
            vipChooseItemBean.setNum(num);
            vipChooseItemBean.setPrice(goodsChooseBean.getPrice());
            vipChooseItemBean.setGoodsChooseBean(goodsChooseBean);
            vipChooseItemBean.setQty(selfGoodsBean.getQty());
            vipChooseItemBean.setGoodsImg(selfGoodsBean.getGoodsImg());
            vipChooseItemBean.setGoodsName(selfGoodsBean.getGoodsName());
            vipChooseItemBean.setGoodsSpec1(selfGoodsBean.getGoodsSpec1());
            vipChooseItemBean.setGoodsSpec2(selfGoodsBean.getGoodsSpec2());

            vipChooseItemBeans.add(vipChooseItemBean);
            totalGoods.add(goodsChooseBean.getGoodsId() + "," + goodsChooseBean.getAttr_id());
        }
        setNum();
    }

    @Override
    public void delete() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }
}
