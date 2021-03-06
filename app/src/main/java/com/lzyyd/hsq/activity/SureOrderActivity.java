package com.lzyyd.hsq.activity;

import android.content.Intent;
import android.os.Bundle;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.adapter.SureOrderAdapter;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.AddressBean;
import com.lzyyd.hsq.bean.CollectBean;
import com.lzyyd.hsq.bean.OrderGoodsBuyListBean;
import com.lzyyd.hsq.bean.OrderInfoBuyListBean;
import com.lzyyd.hsq.bean.OrderinfoBean;
import com.lzyyd.hsq.databinding.ActivitySureOrderBinding;
import com.lzyyd.hsq.interf.IWxPayListener;
import com.lzyyd.hsq.interf.IWxResultListener;
import com.lzyyd.hsq.util.HsqAppUtil;
import com.lzyyd.hsq.util.UToast;
import com.lzyyd.hsq.util.WxPayUtil;
import com.lzyyd.hsq.viewmodel.SureOrderViewModel;
import com.lzyyd.hsq.wxapi.WXEntryActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import me.tatarka.bindingcollectionadapter2.BR;

/**
 * Create by liguo on 2020/7/21
 * Describe:
 */
public class SureOrderActivity extends BaseActivity<ActivitySureOrderBinding, SureOrderViewModel> implements SureOrderViewModel.SureOrderCallBack, SureOrderAdapter.OnDataGetFare, IWxResultListener, IWxPayListener {

    private String key;
    private OrderinfoBean orderinfoBean;
    private AddressBean addressBean;
    private SureOrderAdapter sureOrderAdapter;
    public static final int ADDRESS_RESULT = 0x032;
    private boolean isPay = false;
    private String orderSn = "";
    private ArrayList<String> strings = new ArrayList<>();

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_sure_order;
    }

    @Override
    public int initVariableId() {
        return BR.sureorder;
    }

    @Override
    public SureOrderViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(SureOrderViewModel.class);
    }

    @Override
    public void initData() {

        Bundle bundle = getIntent().getExtras();

        key = bundle.getString(HsqAppUtil.KEY);

        viewModel.setOnClick(this);

        viewModel.getData(key,"0", ProApplication.SESSIONID());

        WXEntryActivity.setPayListener(this);
    }

    @Override
    public void getOrderInfoSuccess(OrderinfoBean orderinfoBean) {

        this.orderinfoBean = orderinfoBean;

        binding.setReturnInt(orderinfoBean.getReturnIntegral()+"");

        if (sureOrderAdapter == null) {
            sureOrderAdapter = new SureOrderAdapter(this, (int)orderinfoBean.getMoney3Balance(),this);

            sureOrderAdapter.getItems().addAll(orderinfoBean.getOrderInfoBuyList());

            addressBean = orderinfoBean.getAddress();

            binding.setAddress(orderinfoBean.getAddress());

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
            binding.rvSureOrder.setLayoutManager(linearLayoutManager);
            binding.rvSureOrder.setAdapter(sureOrderAdapter);

            binding.setOrderPrice(orderinfoBean.getOrderAmount() + "");
        }
    }

    @Override
    public void getOrderInfoFail(String msg) {
        UToast.show(this,msg);
    }

    @Override
    public void getOrderSuccess(String msg) {
        orderSn = msg;
        viewModel.waitpay(msg,ProApplication.SESSIONID());

    }

    @Override
    public void getOrderFail(String msg) {
        UToast.show(this,msg);
    }

    @Override
    public void orderClick() {

        if (addressBean == null){
            UToast.show(this,"请选择地址");
            return;
        }


        try{
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            jsonObject.put("Integral",orderinfoBean.getIntegral());
            jsonObject.put("GoodsAmount",orderinfoBean.getGoodsAmount());
            jsonObject.put("OrderAmount",orderinfoBean.getOrderAmount());
            jsonObject.put("ReturnIntegral",orderinfoBean.getReturnIntegral());
            jsonObject.put("ShippingFree",orderinfoBean.getShippingFree());
            jsonObject.put("AddressId",orderinfoBean.getAddress().getAddressID());

            int i = 0;
            for (OrderInfoBuyListBean orderInfoBuyListBean : orderinfoBean.getOrderInfoBuyList()){
                JSONObject jsonObject1 = new JSONObject();
                JSONArray jsonArray1 = new JSONArray();
                jsonObject1.put("Integral",orderInfoBuyListBean.getIntegral());
                jsonObject1.put("GoodsAmount",orderInfoBuyListBean.getGoodsAmount());
                jsonObject1.put("ReturnIntegral",orderInfoBuyListBean.getReturnIntegral());
                jsonObject1.put("OrderAmount",orderInfoBuyListBean.getOrderAmount());
                jsonObject1.put("ShippingFree",orderInfoBuyListBean.getShippingFree());
                jsonObject1.put("Money3",strings.get(i));
                jsonObject1.put("StoreId",orderInfoBuyListBean.getStoreId());
                jsonObject1.put("PostScript","");
                i++;
                for (OrderGoodsBuyListBean orderGoodsBuyListBean : orderInfoBuyListBean.getOrderGoodsBuyList()){
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("GoodsId",orderGoodsBuyListBean.getGoodsId());
                    jsonObject2.put("AttrId",orderGoodsBuyListBean.getAttrId());
                    jsonObject2.put("Num",orderGoodsBuyListBean.getNum());
                    jsonObject2.put("Price",orderGoodsBuyListBean.getPrice());
                    jsonObject2.put("Integral",orderGoodsBuyListBean.getIntegral());
                    jsonArray1.put(jsonObject2);
                }
                jsonObject1.put("OrderGoodsBuyList",jsonArray1);
                jsonArray.put(jsonObject1);
            }
            jsonObject.put("OrderInfoBuyList",jsonArray);
            viewModel.sureOrder(key,jsonObject.toString(), ProApplication.SESSIONID());
        }catch (Exception e){

        }


    }

    @Override
    public void getOrderPaySuccess(String msg) {
        isPay = true;
        WxPayUtil.wxProgramPay(HsqAppUtil.APP_ID,this,"pages/Payment/Paymentall?key="+msg + "&SessionId=" + ProApplication.SESSIONID() + "&send=234");

//        viewModel.setWxPay("",orderinfoBean.getOrderAmount()+"","30","0","Android","com.lzyyd.lyb", ProApplication.SESSIONID());
    }

    @Override
    public void getOrderPayFail(String msg) {
        UToast.show(this,msg);
    }

    @Override
    public void getWxSuccess(CollectBean collectBean) {
        String orderSn = collectBean.getMessage();
        WxPayUtil.wxProgramPay(HsqAppUtil.APP_ID,this,"pages/Payment/Paymentall?key="+orderSn + "&SessionId=" + ProApplication.SESSIONID() + "&send=234");
    }

    @Override
    public void getWxFail(String msg) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == RESULT_OK){
            if (requestCode == ADDRESS_RESULT){
                addressBean = (AddressBean) data.getSerializableExtra(HsqAppUtil.ADDRESS);
                binding.setAddress(addressBean);
            }
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPoint(int point) {
        double orderPrice = orderinfoBean.getOrderAmount() - ((int)orderinfoBean.getMoney3Balance() - point);

        if (orderinfoBean.getOrderInfoBuyList().get(0).getOrderType() == 64){
            orderPrice = orderPrice - orderinfoBean.getOrderInfoBuyList().get(0).getOrderGoodsBuyList().get(0).getNum() * 1000;
            binding.setOrderPrice(orderPrice + "(包含" + ((int) (orderinfoBean.getOrderInfoBuyList().get(0).getOrderGoodsBuyList().get(0).getNum() * 1000)) + "优惠券)");
        }else {

            if (orderinfoBean.getMoney3Balance() == point) {
                binding.setOrderPrice(orderPrice + "");
            } else {
                binding.setOrderPrice(orderPrice + "(包含" + ((int) orderinfoBean.getMoney3Balance() - point) + "积分)");
            }
        }

    }

    @Override
    public void onIntegral(ArrayList<String> arrayList) {
        strings = arrayList;
    }

    @Override
    public void setWxSuccess() {

        Bundle bundle = new Bundle();
        bundle.putString("price",orderinfoBean.getOrderAmount() +"");
        startActivity(PayActivity.class,bundle);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void setWxFail() {
        UToast.show(this,"fail");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (isPay){
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("ordersn",orderSn);
            intent.putExtras(bundle);
            if (orderSn.contains(",")){
                intent.setClass(this,OrderListActivity.class);
            }else {
                intent.setClass(this, OrderDetailActivity.class);
            }
            startActivity(intent);
            finish();
            isPay = !isPay;
        }

    }

    @Override
    public void setWxPaySuccess(String msg) {
        if (isPay){
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("ordersn",orderSn);
            intent.putExtras(bundle);
            if (orderSn.contains(",")){
                intent.setClass(this,OrderListActivity.class);
            }else {
                intent.setClass(this, OrderDetailActivity.class);
            }
            startActivity(intent);
            finish();
            isPay = !isPay;
        }
    }

    @Override
    public void setWxPayFail(String msg) {
        UToast.show(this,msg);
        if (isPay){
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("ordersn",orderSn);
            intent.putExtras(bundle);
            if (orderSn.contains(",")){
                intent.setClass(this,OrderListActivity.class);
            }else {
                intent.setClass(this, OrderDetailActivity.class);
            }
            startActivity(intent);
            finish();
            isPay = !isPay;
        }
    }
}
