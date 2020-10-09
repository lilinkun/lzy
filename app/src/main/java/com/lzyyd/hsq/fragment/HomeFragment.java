package com.lzyyd.hsq.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ScrollView;
import android.widget.Toast;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.activity.GoodsListActivity;
import com.lzyyd.hsq.activity.WebViewActivity;
import com.lzyyd.hsq.adapter.GridHomeAdapter;
import com.lzyyd.hsq.adapter.RecommendAdapter;
import com.lzyyd.hsq.adapter.ViewAdapter;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseFragment;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.FlashBean;
import com.lzyyd.hsq.bean.HomeBean;
import com.lzyyd.hsq.bean.HomeItemBean;
import com.lzyyd.hsq.databinding.FragmentHomeBinding;
import com.lzyyd.hsq.interf.IGetSqlListener;
import com.lzyyd.hsq.interf.OnScrollChangedListener;
import com.lzyyd.hsq.ui.CustomBannerView;
import com.lzyyd.hsq.ui.GridSpacingItemDecoration;
import com.lzyyd.hsq.util.DensityUtil;
import com.lzyyd.hsq.util.HsqAppUtil;
import com.lzyyd.hsq.util.UToast;
import com.lzyyd.hsq.viewmodel.HomeFragmentViewModel;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
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

    int topHeight = DensityUtil.dp2px(35);

    private int homeType = 1;

    private boolean isFresh = false;
    private String homeUrl = "";
    private String homeUrl2 = "";


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

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 5);

        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        binding.rvHome.setLayoutManager(gridLayoutManager);
    }

    private void initPtrFrame() {
        final PtrClassicDefaultHeader header=new PtrClassicDefaultHeader(getActivity());
        header.setPadding(dp2px(20), dp2px(20), 0, 0);

        binding.mPtrframe.setHeaderView(header);
        binding.mPtrframe.addPtrUIHandler(header);
        binding.mPtrframe.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                isFresh = true;
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
    @SuppressLint("SetJavaScriptEnabled")
    public void getHomeDataSuccess(HomeBean homeBean) {


        if (binding.mPtrframe != null &&binding.mPtrframe.isEnabled()){
            binding.mPtrframe.refreshComplete();
        }

        this.homeBean = homeBean;

        binding.setHomebean(homeBean);

        ArrayList<FlashBean> flashBeans = homeBean.getFlash();
        CustomBannerView.startBanner(flashBeans,binding.bannerView,getActivity());

        if (gridHomeAdapter == null) {

            gridHomeAdapter = new GridHomeAdapter(getActivity());

            gridHomeAdapter.getItems().addAll(homeBean.getSqIcon());

            int spanCount = 5; // 3 columns
            int spacing = 20; // 50px
            boolean includeEdge = false;
            binding.rvHome.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

            binding.rvHome.setAdapter(gridHomeAdapter);
        }else {
            gridHomeAdapter.getItems().clear();
            gridHomeAdapter.getItems().addAll(homeBean.getSqIcon());
            gridHomeAdapter.notifyDataSetChanged();
        }

        ProApplication.SQ = homeBean.getSq().getUrl();

        if (homeType == 1){
            homeItemBeans = homeBean.getGoodsList4();
        }else if(homeType == 2){
            homeItemBeans = homeBean.getGoodsList5();
        }else if (homeType == 3){
            homeItemBeans = homeBean.getGoodsList6();
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

//        UToast.show(getActivity(),binding.llHome.getHeight()/2+"");

//        viewModel.onHome(binding.wvHome);

        SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences(HsqAppUtil.LOGIN, Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(HsqAppUtil.OTHERUSERNAME,"");
        String url = ProApplication.SQ +"&userName=" + username;

        int h = binding.llHome.getMeasuredHeight();
        int h1 = homeBean.getTopHeight();

        initWebview(url,(int)(-h1*1.6));
    }

    public void initWebview(String url,int topHeight){

        //启用应用缓存
        binding.wvHome.getSettings().setAppCacheEnabled(false);
        binding.wvHome.getSettings().setDomStorageEnabled(true);
        binding.wvHome.getSettings().setJavaScriptEnabled(true);
        binding.wvHome.getSettings().setBlockNetworkImage(false);
        //适应屏幕
        binding.wvHome.getSettings().setLoadWithOverviewMode(true);
        binding.wvHome.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        binding.wvHome.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {

            binding.wvHome.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        }
        binding.wvHome.getSettings().setDatabaseEnabled(true);
        String dir = this.getActivity().getDir("database", Context.MODE_PRIVATE).getPath();
        //启用地理定位
        binding.wvHome.getSettings().setGeolocationEnabled(true);
        //设置定位的数据库路径
        binding.wvHome.getSettings().setGeolocationDatabasePath(dir);
        //最重要的方法，一定要设置，这就是出不来的主要原因
        binding.wvHome.getSettings().setDomStorageEnabled(true);


        Log.v("LG",url);

        ViewGroup.LayoutParams layoutParams = binding.wvHome.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        marginLayoutParams.setMargins(0,topHeight,0,0);
        binding.wvHome.setLayoutParams(marginLayoutParams);

        binding.wvHome.loadUrl(url);
//        binding.wvHome.loadUrl(ProApplication.SQ +"&userName=" + username);
        binding.wvHome.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                /*WebView.HitTestResult hitTestResult = view.getHitTestResult();

                if( url.startsWith("http:") || url.startsWith("https:") ) {
                    //hitTestResult==null解决重定向问题
                    if (!TextUtils.isEmpty(url) && hitTestResult == null) {
                        view.loadUrl(url);
                        return true;
                    }
                }else {
                    try {
                        Uri uri = Uri.parse(url);
                        Intent intent =new Intent(Intent.ACTION_VIEW, uri);
                        view.getContext().startActivity(intent);
                    }catch (Exception e){
                        e.printStackTrace();
                        if (url.startsWith("alipay")){
                            Toast.makeText(view.getContext(), "请确认是否安装支付宝", Toast.LENGTH_SHORT).show();
                        }else if (url.startsWith("mqqwpa")){
                            Toast.makeText(view.getContext(), "请确认是否安装QQ",Toast.LENGTH_SHORT).show();
                        }
                    }
                    return true;

                }
                return super.shouldOverrideUrlLoading(view, url);*/


                return false;
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                if (!binding.wvHome.canGoBack()) {
                    view.setVisibility(View.GONE);
                }

//                view.loadUrl("https://qssq-common.sqqmall.com/pages/card/newpower");
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (!binding.wvHome.canGoBack()) {
                    binding.wvHome.scrollTo(0,0);
                    view.setVisibility(View.VISIBLE);
                }

            }
        });

        binding.wvHome.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {


                if (!view.getUrl().equals(homeUrl) && !view.getUrl().equals(homeUrl2)) {
                    if (view.canGoBack()) {
                        view.stopLoading();
                        view.goBack();
                        Bundle bundle = new Bundle();
                        bundle.putString("url", view.getUrl());
                        startActivity(WebViewActivity.class, bundle);
                        return;
                    }
                }


                if (newProgress == 100){

                    if (homeUrl.equals("")) {
                        homeUrl = view.getUrl();
                    }else {
                        if (!homeUrl.equals(view.getUrl())){
                            if (homeUrl2.equals("")) {
                                homeUrl2 = view.getUrl();
                            }
                        }
                    }
                }

                super.onProgressChanged(view,newProgress);

            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }
        });

        binding.wvHome.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){

                }
            }
        });

        binding.wvHome.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (!binding.wvHome.canGoBack()){
                    /*if (scrollY < 1000){
//                        ivPage.setVisibility(View.VISIBLE);
                        binding.wvHome.scrollTo(0,1000);

                        binding.wvHome.setFocusable(false);
                    }*/
                }
            }
        });

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
                viewModel.recommemdFirst.set(false);
                viewModel.recommemdSecond.set(true);
                viewModel.recommemdThird.set(false);
                recommendAdapter.getItems().clear();
                recommendAdapter.getItems().addAll(homeBean.getGoodsList4());
                recommendAdapter.notifyDataSetChanged();
                break;

            case 2:
                homeType = 2;
                viewModel.recommemdFirst.set(false);
                viewModel.recommemdSecond.set(false);
                viewModel.recommemdThird.set(true);
                recommendAdapter.getItems().clear();
                recommendAdapter.getItems().addAll(homeBean.getGoodsList5());
                recommendAdapter.notifyDataSetChanged();
                break;

            case 3:
                homeType = 3;
                viewModel.recommemdFirst.set(true);
                viewModel.recommemdSecond.set(false);
                viewModel.recommemdThird.set(false);
                recommendAdapter.getItems().clear();
                recommendAdapter.getItems().addAll(homeBean.getGoodsList6());
                recommendAdapter.notifyDataSetChanged();
                break;

            case 4:

                startActivity(GoodsListActivity.class);

                break;
        }
    }

}
