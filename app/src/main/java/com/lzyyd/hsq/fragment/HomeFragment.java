package com.lzyyd.hsq.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.activity.GoodsDetailActivity;
import com.lzyyd.hsq.activity.GoodsListActivity;
import com.lzyyd.hsq.activity.WebViewActivity;
import com.lzyyd.hsq.adapter.GoodsListAdapter;
import com.lzyyd.hsq.adapter.GridHomeAdapter;
import com.lzyyd.hsq.adapter.RecommendAdapter;
import com.lzyyd.hsq.adapter.ViewAdapter;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseFragment;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.FlashBean;
import com.lzyyd.hsq.bean.GoodsListBean;
import com.lzyyd.hsq.bean.HomeBean;
import com.lzyyd.hsq.bean.HomeItemBean;
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
import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

import static in.srain.cube.views.ptr.util.PtrLocalDisplay.dp2px;

public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeFragmentViewModel> implements OnScrollChangedListener, HomeFragmentViewModel.HomeDataCallBack {

    private RecommendAdapter recommendAdapter;
    private HomeBean homeBean;
    private GridHomeAdapter gridHomeAdapter;
    private ArrayList<HomeItemBean> homeItemBeans;

    /**  ScrollView 滚动动态改变标题栏 */
    // 滑动的最小距离（自行定义，you happy jiu ok）
    int minHeight = 10;
    // 滑动的最大距离（自行定义，you happy jiu ok）
    int maxHeight = DensityUtil.dp2px(70);

    int topHeight = DensityUtil.dp2px(30);

    private int homeType = 1;


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

        viewModel.setCallBack(this);

        viewModel.getHomeData(ProApplication.SESSIONID());

        binding.tsvHome.init(this);

        initPtrFrame();
    }

    private void initPtrFrame() {
        final PtrClassicDefaultHeader header=new PtrClassicDefaultHeader(getActivity());
        header.setPadding(dp2px(20), dp2px(20), 0, 0);
        binding.mPtrframe.setHeaderView(header);
        binding.mPtrframe.addPtrUIHandler(header);
        binding.mPtrframe.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

                viewModel.getHomeData(ProApplication.SESSIONID());

            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
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
        }else if (scrollView.getScrollY() < 0){
            Log.i("LG","ScrollY:" + scrollView.getScrollY());
        }

    }

    @Override
    public void loadMore() {

    }

    @Override
    public void getHomeDataSuccess(HomeBean homeBean) {


        if (binding.mPtrframe != null &&binding.mPtrframe.isEnabled()){
            binding.mPtrframe.refreshComplete();
        }

        this.homeBean = homeBean;

        binding.setHomebean(homeBean);

        ArrayList<FlashBean> flashBeans = homeBean.getFlash();
        CustomBannerView.startBanner(flashBeans,binding.bannerView,getActivity());

            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 5);

            gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

            binding.rvHome.setLayoutManager(gridLayoutManager);

            gridHomeAdapter = new GridHomeAdapter(getActivity());

            gridHomeAdapter.getItems().addAll(homeBean.getSqIcon());

            int spanCount = 5; // 3 columns
            int spacing = 20; // 50px
            boolean includeEdge = false;
            binding.rvHome.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

            binding.rvHome.setAdapter(gridHomeAdapter);


            if (homeType == 1){
                homeItemBeans = homeBean.getGoodsList6();
            }else if(homeType == 2){
                homeItemBeans = homeBean.getGoodsList4();
            }else if (homeType == 3){
                homeItemBeans = homeBean.getGoodsList5();
            }
            recommendAdapter = new RecommendAdapter(getActivity());
            recommendAdapter.getItems().addAll(homeItemBeans);

            StaggeredGridLayoutManager gridLayoutManager1 = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            binding.rvHomeGoodsList.addItemDecoration(new GridSpacingItemDecoration(2, 20, false));
//        gridLayoutManager1.setOrientation(GridLayoutManager.VERTICAL);
            binding.rvHomeGoodsList.setLayoutManager(gridLayoutManager1);
            binding.rvHomeGoodsList.setAdapter(recommendAdapter);



        ViewAdapter viewAdapter = new ViewAdapter(homeBean.getNews());
        binding.avTitle.setAdapter(viewAdapter);
        binding.avTitle.start();
        onHomeClick(1);
    }

    @Override
    public void getHomeDataFail(String msg) {
        UToast.show(getActivity(),msg);
    }

    @Override
    public void onHomeClick(int position) {
        switch (position){
            case 1:
                homeType = 1;
                viewModel.recommemdFirst.set(true);
                viewModel.recommemdSecond.set(false);
                viewModel.recommemdThird.set(false);
                recommendAdapter.getItems().clear();
                recommendAdapter.getItems().addAll(homeBean.getGoodsList6());
                recommendAdapter.notifyDataSetChanged();
                break;

            case 2:
                homeType = 2;
                viewModel.recommemdFirst.set(false);
                viewModel.recommemdSecond.set(true);
                viewModel.recommemdThird.set(false);
                recommendAdapter.getItems().clear();
                recommendAdapter.getItems().addAll(homeBean.getGoodsList4());
                recommendAdapter.notifyDataSetChanged();
                break;

            case 3:
                homeType = 3;
                viewModel.recommemdFirst.set(false);
                viewModel.recommemdSecond.set(false);
                viewModel.recommemdThird.set(true);
                recommendAdapter.getItems().clear();
                recommendAdapter.getItems().addAll(homeBean.getGoodsList5());
                recommendAdapter.notifyDataSetChanged();
                break;

            case 4:

                startActivity(GoodsListActivity.class);

                break;
        }
    }

}
