package com.lzyyd.hsq.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupWindow;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.AddressBean;
import com.lzyyd.hsq.bean.OrderDetailAddressBean;
import com.lzyyd.hsq.bean.OrderGoodsBuyListBean;
import com.lzyyd.hsq.databinding.ActivityOrderdetailBinding;
import com.lzyyd.hsq.util.UToast;
import com.lzyyd.hsq.viewmodel.OrderDetailViewModel;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;
import me.tatarka.bindingcollectionadapter2.BR;

/**
 * Create by liguo on 2020/8/31
 * Describe:
 */
public class OrderDetailActivity extends BaseActivity<ActivityOrderdetailBinding, OrderDetailViewModel> implements OrderDetailViewModel.OrderDetailListener {

    private OrderGoodsBuyListBean orderDetailBean;
    private ArrayList<OrderGoodsBuyListBean> orderDetailBeans;
    private PopupWindow payPopupWindow;
    private String orderId = "";
    private String orderSn = "";
    private int status = 0;
    private Dialog payDialog ;
    double payid = 0;
    double useIntegral = 0;
    double shipping_fee = 0;
    double order_amount = 0;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_orderdetail;
    }

    @Override
    public int initVariableId() {
        return BR.orderdetail;
    }

    @Override
    public OrderDetailViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(OrderDetailViewModel.class);
    }

    @Override
    public void initData() {

        String ordersn = getIntent().getExtras().getString("ordersn");

        viewModel.setLisener(this);
        viewModel.cartBuy(ordersn, ProApplication.SESSIONID());

    }

    @Override
    public void setDataSuccess(OrderDetailAddressBean orderDetailBeans) {
        binding.setOrderdetailbean(orderDetailBeans);

        AddressBean addressBean = new AddressBean();
        addressBean.setAddressName(orderDetailBeans.getAddressName());
        addressBean.setAddress(orderDetailBeans.getAddress());
        addressBean.setMobile(orderDetailBeans.getMobile());
        addressBean.setName(orderDetailBeans.getConsignee());

        binding.setAddress(addressBean);

        status = orderDetailBeans.getOrderStatus();

        if (status == 1 ){
            binding.tvPayMessage.setText("买家已付款，等待发货");
            binding.rlBottom.setVisibility(View.GONE);
            binding.ivOrderStatus.setImageDrawable(getResources().getDrawable(R.drawable.ic_me_wait_get_goods));
        }else if (status == 2){
            binding.tvExitOrder.setVisibility(View.GONE);
            binding.tvPayMessage.setText("您的商品正在运输中");
            binding.ivOrderStatus.setImageDrawable(getResources().getDrawable(R.drawable.ic_me_wait_goods));
        }else if (status == 0){
            binding.tvExitOrder.setText("取消订单");
            binding.tvPayOrder.setText("立即付款");
            binding.tvPayMessage.setText("您的订单已提交，请尽快完成支付，确保宝贝早日 到达您的身边。");
            binding.ivOrderStatus.setImageDrawable(getResources().getDrawable(R.drawable.ic_me_wait_getcash));
        } else if(status == 4){
            binding.tvPayMessage.setText("您的交易已经完成");
            binding.tvPayOrder.setText("删除订单");
            binding.llPriceStatus.setVisibility(View.GONE);
            binding.tvExitOrder.setVisibility(View.GONE);
            binding.ivOrderStatus.setImageDrawable(getResources().getDrawable(R.drawable.ic_me_successful_trade));
        } else if(status == 5){
            binding.tvPayMessage.setText("");
            binding.rlBottom.setVisibility(View.GONE);
            binding.ivOrderStatus.setImageDrawable(getResources().getDrawable(R.drawable.ic_me_all_order));
        }

        binding.tvExitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (status == 1 || status == 2){
                        new AlertDialog.Builder(OrderDetailActivity.this).setMessage("您确定要申请退款？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                viewModel.cancelOrder(orderDetailBeans.getOrderSn(), ProApplication.SESSIONID());
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                    }else {
                        new AlertDialog.Builder(OrderDetailActivity.this).setTitle("温馨提示").setMessage("您确定要取消订单？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                viewModel.exitOrder(orderDetailBeans.getOrderSn(), ProApplication.SESSIONID());
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                    }

            }
        });
        binding.tvPayOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    binding.tvPayOrder.setClickable(false);
                    if (status == 0) {

//                        allOrderPresenter.getOrderData(ProApplication.SESSIONID(AllOrderActivity.this));
                    } else if (status == 2) {

                        new AlertDialog.Builder(OrderDetailActivity.this).setMessage("是否确定收货").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                viewModel.sureReceipt(orderDetailBeans.getOrderSn() + "", ProApplication.SESSIONID());
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                    } else if (status == 4) {
//                        allOrderPresenter.deleteOrder(orderDetailBeans.getOrderId()+"", ProApplication.SESSIONID(AllOrderActivity.this));
                    }
                }
        });


    }

    @Override
    public void setDataFail(String msg) {
        UToast.show(this,msg);
    }

    @Override
    public void exitOrderSuccess(String collectDeleteBean) {
        if (!binding.tvPayOrder.isClickable()) {
            binding.tvPayOrder.setClickable(true);
        }
        setResult(RESULT_OK);
        finish();

    }

    @Override
    public void exitOrderFail(String msg) {
        if (!binding.tvPayOrder.isClickable()) {
            binding.tvPayOrder.setClickable(true);
        }
        UToast.show(this,msg);
    }

    @Override
    public void cancelOrderSuccess(String collectDeleteBean) {
        viewModel.cartBuy(orderSn, ProApplication.SESSIONID());
    }

    @Override
    public void cancelOrderFail(String msg) {
        UToast.show(this,msg);
    }

    @Override
    public void sureReceiptSuccess(String collectDeleteBean) {
        if (!binding.tvPayOrder.isClickable()) {
            binding.tvPayOrder.setClickable(true);
        }
        UToast.show(this,"收货成功");
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void sureReceiptFail(String msg) {
        if (!binding.tvPayOrder.isClickable()) {
            binding.tvPayOrder.setClickable(true);
        }
        UToast.show(this,msg);
    }
}
