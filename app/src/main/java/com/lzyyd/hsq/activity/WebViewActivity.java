package com.lzyyd.hsq.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.databinding.ActivityWebviewBinding;
import com.lzyyd.hsq.viewmodel.WebviewViewModel;

/**
 * Create by liguo on 2020/7/24
 * Describe:
 */
public class WebViewActivity extends BaseActivity<ActivityWebviewBinding, WebviewViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_webview;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public void initData() {
        super.initData();

        binding.wvInput.getSettings().setDomStorageEnabled(true);
        binding.wvInput.getSettings().setJavaScriptEnabled(true);
        binding.wvInput.loadUrl("https://dev-qssq.sqqmall.com/pages/login/login?channelCode=1dbf13d0e18107d6d381dd2a7397118a&userName=sm778952333&cardNo=&cardPass=");
        binding.wvInput.setWebViewClient(new WebViewClient() {
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
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

        binding.wvInput.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }


        });
//        binding.wvInput.loadUrl("https://www.baidu.com");

//        Log.v("asdaaaaaaaaaaaaaaaaaa","asdasdasd");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && binding.wvInput.canGoBack()) {

            if (binding.wvInput.getOriginalUrl().contains("https://dev-qssq.sqqmall.com/pages/main/main?channelCode=1dbf13d0e18107d6d381dd2a7397118a")){
                finish();
            }else {
                binding.wvInput.goBack();// activityBaseWebAddWebView.reload();
                binding.wvInput.removeAllViews();//删除webview中所以进程

            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
