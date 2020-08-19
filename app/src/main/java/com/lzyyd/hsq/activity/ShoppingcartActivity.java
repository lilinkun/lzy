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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private double mtotalPrice = 0.00;
    private int mtotalCount = 0;

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
        binding.setGopay("结算");
        viewModel.getGoodsCartList(ProApplication.SESSIONID(),this);

        binding.allCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCheckAll();
            }
        });
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

    /**
     * @return 判断组元素是否全选
     */
    private boolean isCheckAll() {
        for (OrderGroupBean<ArrayList<CartBean>> group : orderListBeans) {
            if (!group.isChoosed()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void checkGroup(int groupPosition, boolean isChecked) {
        OrderGroupBean group = orderListBeans.get(groupPosition);
        List<CartChildBean> child = map.get(group.getOrderListBean().getStoreId());
        for (int i = 0; i < child.size(); i++) {
            child.get(i).setChoosed(isChecked);
        }
        myShoppingCarAdapter.setGroupClickId(groupPosition, isChecked);

        if (isCheckAll()) {
            binding.setCheck(true);//全选
        } else {
            binding.setCheck(false);//反选
        }
        myShoppingCarAdapter.notifyDataSetChanged();
        calulate();
    }

    @Override
    public void checkChild(int groupPosition, int childPosition, boolean isChecked) {
        boolean allChildSameState = true; //判断该组下面的所有子元素是否处于同一状态
        OrderGroupBean group = orderListBeans.get(groupPosition);
        List<CartChildBean> child = map.get(group.getOrderListBean().getStoreId());
        for (int i = 0; i < child.size(); i++) {
            //不选全中
            if (child.get(i).isChoosed() != isChecked) {
                allChildSameState = false;
                break;
            }
        }

        if (allChildSameState) {
            group.setChoosed(isChecked);//如果子元素状态相同，那么对应的组元素也设置成这一种的同一状态
        } else {
            group.setChoosed(false);//否则一律视为未选中
        }

        if (isCheckAll()) {
            binding.setCheck(true);//全选
        } else {
            binding.setCheck(false);//反选
        }

        myShoppingCarAdapter.notifyDataSetChanged();
        calulate();

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

    /**
     * 计算商品总价格，操作步骤
     * 1.先清空全局计价,计数
     * 2.遍历所有的子元素，只要是被选中的，就进行相关的计算操作
     * 3.给textView填充数据
     */
    private void calulate() {
        mtotalPrice = 0.00;
        mtotalCount = 0;
        for (int i = 0; i < orderListBeans.size(); i++) {
            OrderGroupBean group = orderListBeans.get(i);
            List<CartChildBean> child = map.get(group.getOrderListBean().getStoreId());
            for (int j = 0; j < child.size(); j++) {
                CartChildBean good = child.get(j);
                if (good.isChoosed()) {
                    mtotalCount += Integer.valueOf(good.getOrderBean().getNum());
                    mtotalPrice += good.getOrderBean().getPrice() * Integer.valueOf(good.getOrderBean().getNum());
                }
            }
        }

        BigDecimal b = new BigDecimal(mtotalPrice);
        mtotalPrice = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        binding.setTotalprice("¥" + mtotalPrice + "");
        if (mtotalCount == 0) {
            //重新设置购物车
            setCartNum();
            binding.setGopay("结算");
        } else {
            binding.setGopay("结算(" + mtotalCount + ")");
//            titlebar.setTileName("购物车(" + mtotalCount + ")");
        }
        if (orderListBeans.size() == 0){
//            linearLayout.setVisibility(View.VISIBLE);
//            rl_cart_bottom.setVisibility(View.GONE);
        }
    }

    /**
     * 设置购物车的数量
     */
    private void setCartNum() {
        int count = 0;
        for (int i = 0; i < orderListBeans.size(); i++) {
            OrderGroupBean group = orderListBeans.get(i);
            group.setChoosed(binding.getCheck());
            List<CartChildBean> child = map.get(group.getOrderListBean().getStoreId());
            for (CartChildBean childs : child) {
                count++;
            }
        }

    }

    /**
     * 全选和反选
     * 错误标记：在这里出现过错误
     */
    private void doCheckAll() {
        for (int i = 0; i < orderListBeans.size(); i++) {
            OrderGroupBean group = orderListBeans.get(i);
            group.setChoosed(binding.getCheck());
            List<CartChildBean> child = map.get(group.getOrderListBean().getStoreId());
            for (int j = 0; j < child.size(); j++) {
                child.get(j).setChoosed(binding.getCheck());//这里出现过错误
            }
        }
        myShoppingCarAdapter.notifyDataSetChanged();
        calulate();
    }

}
