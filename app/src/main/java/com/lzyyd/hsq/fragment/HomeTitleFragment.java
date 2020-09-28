package com.lzyyd.hsq.fragment;

import android.content.Context;
import android.content.Intent;
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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ScrollView;
import android.widget.Toast;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseFragment;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.HomeBean;
import com.lzyyd.hsq.databinding.FragmentHomeBinding;
import com.lzyyd.hsq.databinding.FragmentHomeTitleBinding;
import com.lzyyd.hsq.interf.OnScrollChangedListener;
import com.lzyyd.hsq.viewmodel.HomeFragmentViewModel;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

import static in.srain.cube.views.ptr.util.PtrLocalDisplay.dp2px;

/**
 * Create by liguo on 2020/9/21
 * Describe:
 */
public class HomeTitleFragment extends BaseFragment<FragmentHomeTitleBinding, HomeFragmentViewModel> implements HomeFragmentViewModel.HomeDataCallBack, OnScrollChangedListener {

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
        return R.layout.fragment_home_title;
    }

    @Override
    public void initData() {
        viewModel.setCallBack(this);

        viewModel.getHomeData(ProApplication.SESSIONID());

        binding.tsvHome.init(this);

        initPtrFrame();

        initWebview("https://www.baidu.com/");
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

    public void initWebview(String url){


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


        binding.wvHome.loadUrl(url);
//        binding.wvHome.loadUrl(ProApplication.SQ +"&userName=" + username);
        binding.wvHome.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                WebView.HitTestResult hitTestResult = view.getHitTestResult();

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
                return super.shouldOverrideUrlLoading(view, url);
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                if (!binding.wvHome.canGoBack()) {
                    view.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (!binding.wvHome.canGoBack()) {
                    binding.wvHome.scrollTo(0,1000);
                    view.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.wvHome.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
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
                    if (scrollY < 1000){
//                        ivPage.setVisibility(View.VISIBLE);
                        binding.wvHome.scrollTo(0,1000);

                        binding.wvHome.setFocusable(false);
                    }
                }
            }
        });

    }

    @Override
    public void initViewObservable() {

    }

    @Override
    public void getHomeDataSuccess(HomeBean homeBean) {

    }

    @Override
    public void getHomeDataFail(String msg) {

    }

    @Override
    public void onHomeClick(int position) {

    }

    @Override
    public void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy) {

    }

    @Override
    public void loadMore() {

    }
}
