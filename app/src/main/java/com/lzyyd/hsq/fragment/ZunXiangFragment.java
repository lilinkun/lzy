package com.lzyyd.hsq.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.activity.WebViewActivity;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseFragment;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.databinding.FragmentZunxiangBinding;
import com.lzyyd.hsq.util.Eyes;
import com.lzyyd.hsq.viewmodel.WebviewViewModel;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

/**
 * Create by liguo on 2020/9/28
 * Describe:
 */
public class ZunXiangFragment extends BaseFragment<FragmentZunxiangBinding, WebviewViewModel> {


    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_zunxiang;
    }

    @Override
    public void initData() {
        if(!ProApplication.SQURL.isEmpty()) {
            initWebview(ProApplication.SQURL);
        }
    }

    public void initUrl(String url){
        initWebview(url);
    }


    @Override
    public WebviewViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(WebviewViewModel.class);
    }

    @Override
    public void initViewObservable() {

    }

    public void initWebview(String url){

        //启用应用缓存
        binding.wvInput.getSettings().setAppCacheEnabled(false);
        binding.wvInput.getSettings().setDomStorageEnabled(true);
        binding.wvInput.getSettings().setJavaScriptEnabled(true);
        binding.wvInput.getSettings().setBlockNetworkImage(false);
        //适应屏幕
        binding.wvInput.getSettings().setLoadWithOverviewMode(true);
        binding.wvInput.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        binding.wvInput.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {

            binding.wvInput.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        }
        binding.wvInput.getSettings().setDatabaseEnabled(true);
        String dir = this.getActivity().getDir("database", Context.MODE_PRIVATE).getPath();
        //启用地理定位
        binding.wvInput.getSettings().setGeolocationEnabled(true);
        //设置定位的数据库路径
        binding.wvInput.getSettings().setGeolocationDatabasePath(dir);
        //最重要的方法，一定要设置，这就是出不来的主要原因
        binding.wvInput.getSettings().setDomStorageEnabled(true);


        binding.wvInput.loadUrl(url);
//        binding.wvHome.loadUrl(ProApplication.SQ +"&userName=" + username);
        binding.wvInput.setWebViewClient(new WebViewClient() {
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

                if (!binding.wvInput.canGoBack()) {
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
                if (!binding.wvInput.canGoBack()) {
                    binding.wvInput.scrollTo(0,0);
                    view.setVisibility(View.VISIBLE);
                }

            }
        });

        binding.wvInput.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {


                if (!view.getUrl().equals(url)) {
                    if (view.canGoBack()) {
                        view.stopLoading();
                        view.goBack();
                        Bundle bundle = new Bundle();
                        bundle.putString("url", view.getUrl());
                        startActivity(WebViewActivity.class, bundle);
                        return;
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

        binding.wvInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){

                }
            }
        });

        binding.wvInput.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (!binding.wvInput.canGoBack()){
                    /*if (scrollY < 1000){
//                        ivPage.setVisibility(View.VISIBLE);
                        binding.wvHome.scrollTo(0,1000);

                        binding.wvHome.setFocusable(false);
                    }*/
                }
            }
        });

    }
}
