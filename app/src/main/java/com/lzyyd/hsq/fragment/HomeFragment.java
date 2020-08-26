package com.lzyyd.hsq.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.activity.GoodsDetailActivity;
import com.lzyyd.hsq.activity.WebViewActivity;
import com.lzyyd.hsq.adapter.GoodsListAdapter;
import com.lzyyd.hsq.adapter.GridHomeAdapter;
import com.lzyyd.hsq.adapter.ViewAdapter;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseFragment;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.FlashBean;
import com.lzyyd.hsq.bean.GoodsListBean;
import com.lzyyd.hsq.bean.HomeBean;
import com.lzyyd.hsq.bean.PageBean;
import com.lzyyd.hsq.databinding.FragmentHomeBinding;
import com.lzyyd.hsq.interf.OnScrollChangedListener;
import com.lzyyd.hsq.ui.CustomBannerView;
import com.lzyyd.hsq.ui.GridSpacingItemDecoration;
import com.lzyyd.hsq.util.DensityUtil;
import com.lzyyd.hsq.util.HsqAppUtil;
import com.lzyyd.hsq.util.UToast;
import com.lzyyd.hsq.viewmodel.HomeFragmentViewModel;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.databinding.BindingMethods;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeFragmentViewModel> implements OnScrollChangedListener, HomeFragmentViewModel.HomeDataCallBack, GoodsListAdapter.OnItemClickListener {

    /**  ScrollView 滚动动态改变标题栏 */
    // 滑动的最小距离（自行定义，you happy jiu ok）
    int minHeight = 10;
    // 滑动的最大距离（自行定义，you happy jiu ok）
    int maxHeight = DensityUtil.dp2px(70);

    int topHeight = DensityUtil.dp2px(30);


    @Override
    public int initVariableId() {
        return BR.home;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public HomeFragmentViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(HomeFragmentViewModel.class);
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_home;
    }

    @Override
    public void initData() {

//        Resources resources = getResources();
//        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
//        int height = resources.getDimensionPixelSize(resourceId);
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        layoutParams.setMargins(0,height,0,0);
//        binding.titleLayoutSearch.setLayoutParams(layoutParams);
        viewModel.setCallBack(this);
        viewModel.getHomeData(ProApplication.SESSIONID());


        binding.tsvHome.init(this);



    }

    @Override
    public void initViewObservable() {
    }

    @Override
    public void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy) {


        if (scrollView.getScrollY() > minHeight  && scrollView.getScrollY() < maxHeight){
//            params.(10,40,scrollView.getScrollY(),0);
            binding.llSearch.setPadding(scrollView.getScrollY(),topHeight,scrollView.getScrollY(),0);
        }else if (scrollView.getScrollY() > maxHeight && scrollView.getScrollY() <= maxHeight + topHeight){
            binding.llSearch.setPadding(maxHeight,topHeight - scrollView.getScrollY() + maxHeight,maxHeight,0);
        }

        if (scrollView.getScrollY() == 0){
            binding.llSearch.setPadding(0,topHeight,0,0);
        }else if (scrollView.getScrollY() >= maxHeight + topHeight){
            binding.llSearch.setPadding(maxHeight, minHeight,maxHeight,0);
        }

    }

    @Override
    public void loadMore() {

    }

    @Override
    public void getHomeDataSuccess(HomeBean homeBean) {

        binding.setHomebean(homeBean);

        ArrayList<FlashBean> flashBeans = homeBean.getFlash();
        CustomBannerView.startBanner(flashBeans,binding.bannerView,getActivity());


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 5);

        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        binding.rvHome.setLayoutManager(gridLayoutManager);

        GridHomeAdapter gridHomeAdapter = new GridHomeAdapter(getActivity());

        gridHomeAdapter.getItems().addAll(homeBean.getSqIcon());

        int spanCount = 5; // 3 columns
        int spacing = 20; // 50px
        boolean includeEdge = false;
        binding.rvHome.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        binding.rvHome.setAdapter(gridHomeAdapter);



        GoodsListAdapter goodsListAdapter = new GoodsListAdapter(getActivity(),homeBean.getGoodsList6(),BR.goodslist);

        StaggeredGridLayoutManager gridLayoutManager1 = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        binding.rvHomeGoodsList.addItemDecoration(new GridSpacingItemDecoration(2, 20, false));
//        gridLayoutManager1.setOrientation(GridLayoutManager.VERTICAL);
        binding.rvHomeGoodsList.setLayoutManager(gridLayoutManager1);
        binding.rvHomeGoodsList.setAdapter(goodsListAdapter);

        goodsListAdapter.setItemClickListener(this);

        ViewAdapter viewAdapter = new ViewAdapter(homeBean.getNews());
        binding.avTitle.setAdapter(viewAdapter);
        binding.avTitle.start();
    }

    @Override
    public void getHomeDataFail(String msg) {
        UToast.show(getActivity(),msg);
    }

    @Override
    public void onHomeClick(int position) {
        switch (position){
            case 1:



                break;

            case 2:

                break;

            case 3:

                break;
        }
    }

    @Override
    public void onItemClicks(int position,String goodsid) {
        Bundle bundle = new Bundle();
        bundle.putString(HsqAppUtil.GOODSID,goodsid);
        bundle.putInt(HsqAppUtil.TYPE,0);
        startActivity(GoodsDetailActivity.class,bundle);
    }


}
