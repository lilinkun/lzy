package com.lzyyd.hsq.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.CheckBean;
import com.lzyyd.hsq.bean.UrlBean;
import com.lzyyd.hsq.databinding.ActivitySplashBinding;
import com.lzyyd.hsq.ui.DownloadingDialog;
import com.lzyyd.hsq.util.HsqAppUtil;
import com.lzyyd.hsq.util.UToast;
import com.lzyyd.hsq.util.UpdateManager;
import com.lzyyd.hsq.viewmodel.MVVMViewModel;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;
import cn.bingoogolapple.update.BGADownloadProgressEvent;
import cn.bingoogolapple.update.BGAUpgradeUtil;
import okhttp3.Call;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Create by liguo on 2020/8/28
 * Describe:
 */
public class SplashActivity extends BaseActivity<ActivitySplashBinding, MVVMViewModel> implements MVVMViewModel.LoginCallBack {

    MyCountDownTimer myCountDownTimer = new MyCountDownTimer(1000, 1000);

    private CheckBean bean;
    private double code = 0;
    private Dialog dialog;
    /**
     * 下载文件权限请求码
     */
    private static final int RC_PERMISSION_DOWNLOAD = 1;
    /**
     * 删除文件权限请求码
     */
    private static final int RC_PERMISSION_DELETE = 2;

    private DownloadingDialog mDownloadingDialog;
    private String mNewVersion = "2";
    private String mApkUrl = "";


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_splash;
    }

    @Override
    public int initVariableId() {
        return BR.mvvm;
    }

    @Override
    public void initData() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏

//        myCountDownTimer.start();

        viewModel.getUrl(this);


        // 监听下载进度
        BGAUpgradeUtil.getDownloadProgressEventObservable()
                .subscribe(new Action1<BGADownloadProgressEvent>() {
                    @Override
                    public void call(BGADownloadProgressEvent downloadProgressEvent) {
                        if (mDownloadingDialog != null && mDownloadingDialog.isShowing() && downloadProgressEvent.isNotDownloadFinished()) {
                            mDownloadingDialog.setProgress(downloadProgressEvent.getProgress(), downloadProgressEvent.getTotal());
                        }
                    }
                });

    }

    @Override
    public void getUrlSuccess(UrlBean urlBean) {
        ProApplication.HEADIMG = urlBean.getImgUrl() + ProApplication.IMG_SMALL;
        ProApplication.BANNERIMG = urlBean.getImgUrl() + ProApplication.IMG_BIG;
        ProApplication.HOMEADDRESS = urlBean.getImgUrl() + ProApplication.IMG_HOME_ADDRESS;
        ProApplication.CUSTOMERIMG = urlBean.getServiesUrl();
        ProApplication.SHAREDIMG = urlBean.getSharedWebUrl();
        ProApplication.REGISTERREQUIREMENTS = urlBean.getRegisterRequirements();
        ProApplication.LOGISTICSURL = urlBean.getLogisticsUrl();
        ProApplication.UPGRADEURL = urlBean.getUpgradeUrl();
        ProApplication.UPGRADETOKEN = urlBean.getUpgradeToken();
        ProApplication.PHONE = urlBean.getKFMobile();
        ProApplication.SERVIESVIP = urlBean.getServiesVip();
        ProApplication.SHAREDMEIMG = urlBean.getShareImg();
        ProApplication.USERLEVELPRICE10 = urlBean.getUserLevelPrice10();
        ProApplication.USERLEVELPRICE20 = urlBean.getUserLevelPrice20();
        ProApplication.CCQGOODSID = urlBean.getCcqGoodsId();
        ProApplication.KFMOBILE = urlBean.getKFMobile();
        ProApplication.SQURL = urlBean.getSqZsj();
        SharedPreferences sharedPreferences = getSharedPreferences(HsqAppUtil.LOGIN, MODE_PRIVATE);
        sharedPreferences.edit().putString(HsqAppUtil.IMG, ProApplication.HEADIMG).putString(HsqAppUtil.BANNERIMG, ProApplication.BANNERIMG)
                .putString(HsqAppUtil.CUSTOMER, ProApplication.CUSTOMERIMG).putString(HsqAppUtil.SHAREDIMG, ProApplication.SHAREDIMG)
                .putString(HsqAppUtil.SHAREDMEIMG,"").putString(HsqAppUtil.SQURL,urlBean.getSqZsj()).commit();
        UpdateApp();
//        turnHome();
    }

    @Override
    public MVVMViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(MVVMViewModel.class);
    }

    @Override
    public void getUrlFail(String msg) {
        UToast.show(this,msg);
    }

    /**
     * 获取验证码倒计时
     */
    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程
        @Override
        public void onTick(long l) {

        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            turnHome();

        }
    }

    private void turnHome() {
        Intent intent = null;
        SharedPreferences sharedPreferences = getSharedPreferences(HsqAppUtil.LOGIN, MODE_PRIVATE);
        if (sharedPreferences.getBoolean(HsqAppUtil.LOGIN, false) == true) {
            intent = new Intent(getBaseContext(), MainActivity.class);
        } else {
            intent = new Intent(getBaseContext(), LoginActivity.class);
        }
        //启动MainActivity
        startActivity(intent);
        finish();
    }

    @Override
    public void onPermission() {
        super.onPermission();

        UpdateApp();

    }

    public void UpdateApp(){
        myPermission();

        String url = ProApplication.UPGRADEURL;
        final double code = UpdateManager.getInstance().getVersionName(this);
        OkHttpUtils.get()
                .url(url)
                .addParams("api_token", ProApplication.UPGRADETOKEN)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        turnHome();
                    }


                    @Override
                    public void onResponse(String response, int id) {

                        Log.d("ok===========", response);

                        Gson gson = new Gson();
                        bean = gson.fromJson(response, CheckBean.class);

                        if (bean.getVersionShort() > code) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this).setMessage("请升级更新app").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    mApkUrl = bean.getInstall_url();
                                    deleteApkFile();
                                    downloadApkFile();
                                }
                            });
                            builder.create().setCanceledOnTouchOutside(false);
                            //  builder.setCancelable(false);
                            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    finish();
                                }
                            });
                            builder.show();

                        } else {
                            turnHome();
                        }
                    }
                });
    }

    /**
     * 删除之前升级时下载的老的 apk 文件
     */
    @AfterPermissionGranted(RC_PERMISSION_DELETE)
    public void deleteApkFile() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // 删除之前升级时下载的老的 apk 文件
            BGAUpgradeUtil.deleteOldApk();
        } else {
            EasyPermissions.requestPermissions(this, "使用 BGAUpdateDemo 需要授权读写外部存储权限!", RC_PERMISSION_DELETE, perms);
        }
    }

    /**
     * 下载新版 apk 文件
     */
    @AfterPermissionGranted(RC_PERMISSION_DOWNLOAD)
    public void downloadApkFile() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // 如果新版 apk 文件已经下载过了，直接 return，此时不需要开发者调用安装 apk 文件的方法，在 isApkFileDownloaded 里已经调用了安装」
            if (BGAUpgradeUtil.isApkFileDownloaded(mNewVersion)) {
                return;
            }

            // 下载新版 apk 文件
            BGAUpgradeUtil.downloadApkFile(mApkUrl, mNewVersion)
                    .subscribe(new Subscriber<File>() {
                        @Override
                        public void onStart() {
                            showDownloadingDialog();
                        }

                        @Override
                        public void onCompleted() {
                            dismissDownloadingDialog();
                        }

                        @Override
                        public void onError(Throwable e) {
                            dismissDownloadingDialog();
                        }

                        @Override
                        public void onNext(File apkFile) {
                            if (apkFile != null) {
                                BGAUpgradeUtil.installApk(apkFile);
                            }
                        }
                    });
        } else {
            EasyPermissions.requestPermissions(this, "使用 BGAUpdateDemo 需要授权读写外部存储权限!", RC_PERMISSION_DOWNLOAD, perms);
        }
    }

    /**
     * 显示下载对话框
     */
    private void showDownloadingDialog() {
        if (mDownloadingDialog == null) {
            mDownloadingDialog = new DownloadingDialog(this);
        }
        mDownloadingDialog.setUpdateMessage(bean.getChangelog() + "");
        mDownloadingDialog.show();
    }

    /**
     * 隐藏下载对话框
     */
    private void dismissDownloadingDialog() {
        if (mDownloadingDialog != null) {
            mDownloadingDialog.dismiss();
        }
    }


}
