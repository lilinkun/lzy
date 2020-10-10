package com.lzyyd.hsq.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.activity.LoginActivity;
import com.lzyyd.hsq.activity.SureOrderActivity;
import com.lzyyd.hsq.adapter.MyShoppingCarAdapter;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseFragment;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.CartBean;
import com.lzyyd.hsq.bean.CartChildBean;
import com.lzyyd.hsq.bean.CartListBean;
import com.lzyyd.hsq.bean.CollectBean;
import com.lzyyd.hsq.bean.OrderGroupBean;
import com.lzyyd.hsq.databinding.FragmentGoodsCartBinding;
import com.lzyyd.hsq.util.HsqAppUtil;
import com.lzyyd.hsq.util.UToast;
import com.lzyyd.hsq.viewmodel.ShoppingcartViewModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;
import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

import static in.srain.cube.views.ptr.util.PtrLocalDisplay.dp2px;

/**
 * Create by liguo on 2020/7/21
 * Describe:
 */
public class ShoppingCartFragment extends BaseFragment<FragmentGoodsCartBinding, ShoppingcartViewModel> implements ShoppingcartViewModel.CartCallback, MyShoppingCarAdapter.CheckInterface, MyShoppingCarAdapter.ModifyCountInterface {

    private MyShoppingCarAdapter myShoppingCarAdapter;
    Map<String,ArrayList<CartChildBean>> map = new HashMap<>();
    private ArrayList<OrderGroupBean<ArrayList<CartBean>>> orderListBeans;
    private double mtotalPrice = 0.00;
    private int mtotalCount = 0;
    private int totalgoods = 0;
    private CartChildBean orderBean;
    //false就是编辑，ture就是管理
    private boolean flag = false;

    @Override
    public int initVariableId() {
        return BR.shoppingcart;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public ShoppingcartViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(ShoppingcartViewModel.class);

    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_goods_cart;
    }

    @Override
    public void initData() {
        binding.setGopay("结算");
        viewModel.setCallBack(this);
        viewModel.getGoodsCartList(ProApplication.SESSIONID());

        binding.vBottom.setVisibility(View.VISIBLE);

        initPtrFrame();
    }

    @Override
    public void initViewObservable() {

    }

    public void setUpdate(){
        viewModel.getGoodsCartList(ProApplication.SESSIONID());
    }

    private void initPtrFrame() {
        final PtrClassicDefaultHeader header=new PtrClassicDefaultHeader(getActivity());
        header.setPadding(dp2px(20), dp2px(20), 0, 0);
        binding.mPtrframe.setHeaderView(header);
        binding.mPtrframe.addPtrUIHandler(header);
        binding.mPtrframe.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
               /* mPtrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtrFrame.refreshComplete();
                    }
                },2000);*/
                viewModel.getGoodsCartList(ProApplication.SESSIONID());
                updataData();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
    }

    private void updataData(){
        if (orderListBeans != null && orderListBeans.size() > 0) {
            for (int i = 0; i < orderListBeans.size(); i++) {
                OrderGroupBean group = orderListBeans.get(i);
                group.setChoosed(false);
                List<CartChildBean> child = map.get(group.getOrderListBean().getStoreId());
                for (int j = 0; j < child.size(); j++) {
                    child.get(j).setChoosed(false);//这里出现过错误
                }
            }
            myShoppingCarAdapter.notifyDataSetChanged();
            calulate();
            binding.allCheckBox.setChecked(false);
        }
        binding.mPtrframe.refreshComplete();
    }

    @Override
    public void getCartListSuccess(ArrayList<CartListBean<ArrayList<CartBean>>> storeCartBeans) {
        totalgoods = 0;
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

                    totalgoods = totalgoods + orderBean.getNum();

                }
                orderGroupBeans.add(orderGroupBean);
                this.orderListBeans = orderGroupBeans;
            }

            binding.setTotalgoods("总共" + totalgoods + "件宝贝");

            myShoppingCarAdapter = new MyShoppingCarAdapter(orderGroupBeans, map, getActivity(), getActivity());
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
        if(msg.contains("登录已失效") || msg.contains("用户不存在")){
            startActivity(LoginActivity.class);
        }
        UToast.show(getActivity(),msg);
    }

    @Override
    public void SureOrderSuccess(String msg) {

        Bundle bundle = new Bundle();
        bundle.putString(HsqAppUtil.KEY,msg);
        startActivity(SureOrderActivity.class,bundle);
    }

    @Override
    public void SureOrderFail(String msg) {
        UToast.show(getActivity(),msg);
    }

    @Override
    public void deleteGoodsSuccess(String msg) {
        UToast.show(getActivity(),"删除成功");
        calulate();
    }

    @Override
    public void deleteGoodsFail(String msg) {
        UToast.show(getActivity(),"删除失败");
    }

    @Override
    public void allClick() {
        doCheckAll();
    }

    @Override
    public void editClick() {
        flag = !flag;
        if (flag) {
            binding.orderInfo.setVisibility(View.GONE);
            binding.shareInfo.setVisibility(View.VISIBLE);
            binding.tvCartEdit.setText("完成");
            binding.goPay.setVisibility(View.GONE);
        } else {
            binding.orderInfo.setVisibility(View.VISIBLE);
            binding.shareInfo.setVisibility(View.GONE);
            binding.goPay.setVisibility(View.VISIBLE);
            binding.tvCartEdit.setText("管理");
        }
    }

    @Override
    public void modifyOrderSuccess(CollectBean collectBean, int num, View view,int total) {
        if (collectBean.getStatus() != 0){
            UToast.show(getActivity(),collectBean.getMessage());
        }else {
            orderBean.getOrderBean().setNum(num);
            ((TextView) view).setText(String.valueOf(num));
            myShoppingCarAdapter.notifyDataSetChanged();
            calulate();
        }
    }

    @Override
    public void cartPay() {

        if (mtotalCount == 0) {
            UToast.show(getActivity(), "请选择要支付的商品");
            return;
        }

        viewModel.getKey(getOrderList(),ProApplication.SESSIONID());

    }

    @Override
    public void cartDel() {
        if (mtotalCount == 0) {
            UToast.show(getActivity(), "请选择要删除的商品");
            return;
        }
        AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
        dialog.setMessage("确认要删除该商品吗?");
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doDelete();
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        dialog.show();
    }

    /**
     * 删除操作
     * 1.不要边遍历边删除,容易出现数组越界的情况
     * 2.把将要删除的对象放进相应的容器中，待遍历完，用removeAll的方式进行删除
     */
    private void doDelete() {
        String deleteStr = "";
        List<OrderGroupBean> toBeDeleteGroups = new ArrayList<OrderGroupBean>(); //待删除的组元素
        for (int i = 0; i < orderListBeans.size(); i++) {
            OrderGroupBean group = orderListBeans.get(i);
            if (group.isChoosed()) {
                toBeDeleteGroups.add(group);
            }
            List<CartChildBean> toBeDeleteChilds = new ArrayList<CartChildBean>();//待删除的子元素
            List<CartChildBean> child = map.get(group.getOrderListBean().getStoreId());
            for (int j = 0; j < child.size(); j++) {
                if (child.get(j).isChoosed()) {
                    toBeDeleteChilds.add(child.get(j));
                    if (deleteStr.equals("")){
                        deleteStr = child.get(j).getOrderBean().getCartId();
                    }else {
                        deleteStr = deleteStr + "," + child.get(j).getOrderBean().getCartId();
                    }

                }
            }
            child.removeAll(toBeDeleteChilds);
        }
        orderListBeans.removeAll(toBeDeleteGroups);
        myShoppingCarAdapter.notifyDataSetChanged();

        viewModel.deleteOrder(deleteStr,ProApplication.SESSIONID());
    }

    public String getOrderList(){
        String OrderStr = "";
        for (int i = 0; i < orderListBeans.size(); i++) {
            OrderGroupBean group = orderListBeans.get(i);
            List<CartChildBean> child = map.get(group.getOrderListBean().getStoreId());
            for (int j = 0; j < child.size(); j++) {
                if (child.get(j).isChoosed()) {
                    if (OrderStr.equals("")){
                        OrderStr = child.get(j).getOrderBean().getCartId();
                    }else {
                        OrderStr = OrderStr + "," + child.get(j).getOrderBean().getCartId();
                    }
                }
            }
        }
        return OrderStr;
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
        orderBean = (CartChildBean)myShoppingCarAdapter.getChild(groupPosition,childPosition);
        int count = Integer.valueOf(orderBean.getOrderBean().getNum());
        count++;
        viewModel.modifyOrder(count,orderBean.getOrderBean().getCartId(),showCountView,ProApplication.SESSIONID(),totalgoods+1);

    }

    @Override
    public void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        orderBean = (CartChildBean)myShoppingCarAdapter.getChild(groupPosition,childPosition);
        int count = Integer.valueOf(orderBean.getOrderBean().getNum());
        if (count == 1) {
            return;
        }
        count--;
        viewModel.modifyOrder(count,orderBean.getOrderBean().getCartId(),showCountView,ProApplication.SESSIONID(),totalgoods-1);

    }

    @Override
    public void doUpdate(int groupPosition, int childPosition, View showCountView, boolean isChecked,int num) {
        orderBean = (CartChildBean) myShoppingCarAdapter.getChild(groupPosition, childPosition);
        int count = Integer.valueOf(orderBean.getOrderBean().getNum());
        viewModel.modifyOrder(count,orderBean.getOrderBean().getCartId(),showCountView,ProApplication.SESSIONID(),totalgoods + count - num);
    }

    @Override
    public void childDelete(int groupPosition, int childPosition) {
        OrderGroupBean group = orderListBeans.get(groupPosition);
        List<CartChildBean> child = map.get(group.getOrderListBean().getStoreId());
        child.remove(childPosition);
        if (child.size() == 0) {
            orderListBeans.remove(groupPosition);
        }
        myShoppingCarAdapter.notifyDataSetChanged();
        calulate();
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
        totalgoods = 0;
        for (int i = 0; i < orderListBeans.size(); i++) {
            OrderGroupBean group = orderListBeans.get(i);
            List<CartChildBean> child = map.get(group.getOrderListBean().getStoreId());
            for (int j = 0; j < child.size(); j++) {
                CartChildBean good = child.get(j);
                if (good.isChoosed()) {
                    mtotalCount += Integer.valueOf(good.getOrderBean().getNum());
                    mtotalPrice += good.getOrderBean().getPrice() * Integer.valueOf(good.getOrderBean().getNum());
                }
                totalgoods += Integer.valueOf(good.getOrderBean().getNum());
            }
        }
        binding.setTotalgoods("总共" + totalgoods + "件宝贝");

        BigDecimal b = new BigDecimal(mtotalPrice);
        mtotalPrice = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        binding.setTotalprice("¥" + mtotalPrice + "");
        if (mtotalCount == 0) {
            //重新设置购物车
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
