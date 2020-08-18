package com.lzyyd.hsq.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.adapter.MyShoppingCarAdapter;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.CartBean;
import com.lzyyd.hsq.bean.CartChildBean;
import com.lzyyd.hsq.bean.CartListBean;
import com.lzyyd.hsq.bean.OrderGroupBean;
import com.lzyyd.hsq.databinding.FragmentGoodsCartBinding;
import com.lzyyd.hsq.viewmodel.ShoppingcartViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.lifecycle.ViewModelProviders;

/**
 * Create by liguo on 2020/7/28
 * Describe:
 */
public class ShoppingcartActivity extends BaseActivity<FragmentGoodsCartBinding, ShoppingcartViewModel> implements ShoppingcartViewModel.CartCallback, MyShoppingCarAdapter.CheckInterface, MyShoppingCarAdapter.ModifyCountInterface {

    private MyShoppingCarAdapter myShoppingCarAdapter;
    Map<String,ArrayList<CartChildBean>> map = new HashMap<>();
    private ArrayList<OrderGroupBean<ArrayList<CartBean>>> orderListBeans;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.fragment_goods_cart;
    }

    @Override
    public int initVariableId() {
        return BR.shoppingcart;
    }

    @Override
    public ShoppingcartViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(ShoppingcartViewModel.class);
    }

    @Override
    public void initData() {
        viewModel.getGoodsCartList(ProApplication.SESSIONID(),this);
    }

    @Override
    public void getCartListSuccess(ArrayList<CartListBean<ArrayList<CartBean>>> storeCartBeans) {

//        MyShoppingCarAdapter myShoppingCarAdapter = new MyShoppingCarAdapter(getActivity(),storeCartBeans,BR.storeinfo);
        ArrayList<OrderGroupBean<ArrayList<CartBean>>> orderGroupBeans = new ArrayList<>();

        if (storeCartBeans.size() > 0) {
            binding.listView.setVisibility(View.VISIBLE);
            for (CartListBean<ArrayList<CartBean>> orderListBean : storeCartBeans) {
                OrderGroupBean<ArrayList<CartBean>> orderGroupBean = new OrderGroupBean<>();
                orderGroupBean.setOrderListBean(orderListBean);
                ArrayList<CartChildBean> orderChildBeans = new ArrayList<>();
                for (CartBean orderBean : orderListBean.getListGoods()) {
                    CartChildBean orderChildBean = new CartChildBean();
                    orderChildBean.setOrderBean(orderBean);
                    orderChildBean.setChoosed(false);
                    orderChildBean.setParentId(orderListBean.getStoreId());
                    orderChildBeans.add(orderChildBean);
                    map.put(orderListBean.getStoreId(), orderChildBeans);
                }
                orderGroupBeans.add(orderGroupBean);
                this.orderListBeans = orderGroupBeans;
            }
            myShoppingCarAdapter = new MyShoppingCarAdapter(orderGroupBeans, map, this, this);
            binding.listView.setAdapter(myShoppingCarAdapter);
            myShoppingCarAdapter.setCheckInterface(this);
            myShoppingCarAdapter.setModifyCountInterface(this); //关键步骤2:设置增删减的接口
            binding.listView.setGroupIndicator(null); //设置属性 GroupIndicator 去掉向下箭头
            for (int i = 0; i < myShoppingCarAdapter.getGroupCount(); i++) {
                binding.listView.expandGroup(i); //关键步骤4:初始化，将ExpandableListView以展开的方式显示
            }

            binding.listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {

                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    int firstVisiablePostion = view.getFirstVisiblePosition();
                    int top = -1;
                    View firstView = view.getChildAt(firstVisibleItem);
                    if (firstView != null) {
                        top = firstView.getTop();
                    }

                    if (firstVisibleItem == 0 && top == 0) {
                        binding.mPtrframe.setEnabled(true);
                    } else {
                        binding.mPtrframe.setEnabled(false);
                    }
                }
            });
        }
    }

    @Override
    public void getCartListFail(String msg) {

    }

    @Override
    public void checkGroup(int groupPosition, boolean isChecked) {

    }

    @Override
    public void checkChild(int groupPosition, int childPosition, boolean isChecked) {

    }

    @Override
    public void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {

    }

    @Override
    public void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {

    }

    @Override
    public void doUpdate(int groupPosition, int childPosition, View showCountView, boolean isChecked) {

    }

    @Override
    public void childDelete(int groupPosition, int childPosition) {

    }
}
