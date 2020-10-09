package com.lzyyd.hsq.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.CheckBean;
import com.lzyyd.hsq.bean.LoginBean;
import com.lzyyd.hsq.databinding.ActivityPersonalInfoBinding;
import com.lzyyd.hsq.ui.DownloadingDialog;
import com.lzyyd.hsq.util.ActivityUtil;
import com.lzyyd.hsq.util.Eyes;
import com.lzyyd.hsq.util.HsqAppUtil;
import com.lzyyd.hsq.util.UToast;
import com.lzyyd.hsq.util.UpdateManager;
import com.lzyyd.hsq.viewmodel.PersonalInfoViewModel;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import cn.bingoogolapple.update.BGADownloadProgressEvent;
import cn.bingoogolapple.update.BGAUpgradeUtil;
import okhttp3.Call;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by LG on 2018/11/19.
 */

public class PersonalInfoActivity extends BaseActivity<ActivityPersonalInfoBinding, PersonalInfoViewModel> implements PersonalInfoViewModel.OnPersonelInfoCallback {

    private boolean isChangeSuccess = false;

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

    public void initData() {
        ActivityUtil.addHomeActivity(this);
        Eyes.setStatusBarWhiteColor(this, getResources().getColor(R.color.white));
        viewModel.setListener(this);
        viewModel.getVCode(this);

        SharedPreferences sharedPreferences = getSharedPreferences(HsqAppUtil.LOGIN, MODE_PRIVATE);

        viewModel.getUserInfo(sharedPreferences.getString(HsqAppUtil.USERNAME, ""), ProApplication.SESSIONID());

        binding.rlMeCustomerService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(PersonalInfoActivity.this).inflate(R.layout.dialog_phone, null);

                dialog = new Dialog(PersonalInfoActivity.this);

                //设置dialog的宽高为屏幕的宽高
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                dialog.setContentView(view, layoutParams);
                dialog.show();
                TextView tv_dialog_phone = (TextView) view.findViewById(R.id.tv_dialog_phone);
                TextView tv_exit = (TextView) view.findViewById(R.id.tv_exit);
                TextView tv_call = (TextView) view.findViewById(R.id.tv_call);
                tv_dialog_phone.setText(ProApplication.KFMOBILE);

                tv_exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                tv_call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        requestPermission();
                    }
                });
            }
        });


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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isChangeSuccess) {
                setResult(RESULT_OK);
            }
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_personal_info;
    }

    @Override
    public int initVariableId() {
        return BR.personalViewModel;
    }

    @Override
    public PersonalInfoViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(PersonalInfoViewModel.class);
    }

    @Override
    public void getUserInfoSuccess(LoginBean mLoginBean) {
        binding.setPersoninfo(mLoginBean);
        ProApplication.ISUSEQSQ = mLoginBean.getIsUseQsq();

        SharedPreferences sharedPreferences = getSharedPreferences(HsqAppUtil.LOGIN, MODE_PRIVATE);
        sharedPreferences.edit().putString("sessionid", ProApplication.SESSIONID()).putBoolean(HsqAppUtil.LOGIN, true)
                .putString(HsqAppUtil.ACCOUNT, mLoginBean.getNickName()).putString(HsqAppUtil.TELEPHONE, mLoginBean.getMobile())
                .putString(HsqAppUtil.USERNAME, mLoginBean.getUserName()).putString(HsqAppUtil.USERID, mLoginBean.getUserId())
                .putString(HsqAppUtil.VIPVALIDITY, mLoginBean.getVipValidity())
                .putString(HsqAppUtil.USERLEVEL, mLoginBean.getUserLevel() + "")
                .putString(HsqAppUtil.CCQTYPE,mLoginBean.getCcqType()+"")
                .putString(HsqAppUtil.LEVEL,mLoginBean.getUserLevel()+"")
                .putString(HsqAppUtil.PROJECT,mLoginBean.getProject()+"")
                .putString(HsqAppUtil.HEADIMGURL,mLoginBean.getPortrait())
                .putString(HsqAppUtil.OTHERUSERNAME,mLoginBean.getOtherUserName())
                .putString(HsqAppUtil.USERLEVELNAME, mLoginBean.getUserLevelName()).commit();
    }

    @Override
    public void getUserInfoFail(String msg) {
        UToast.show(this,msg);
    }
    /**
     * 拨号方法
     */
    private void callPhone() {
        if (dialog != null) {
            dialog.cancel();
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+ProApplication.KFMOBILE));
        startActivity(intent);
    }

    /**
     * 申请权限
     */
    private void requestPermission() {
        //判断Android版本是否大于23
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                        RequestPermissionType.REQUEST_CODE_ASK_CALL_PHONE);
                return;
            } else {
                callPhone();
            }
        } else {
            callPhone();
        }
    }

    /**
     * 注册权限申请回调
     *
     * @param requestCode  申请码
     * @param permissions  申请的权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionType.REQUEST_CODE_ASK_CALL_PHONE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callPhone();
                } else {
                    // Permission Denied
                    UToast.show(this, "CALL_PHONE Denied");
                }
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public interface RequestPermissionType {

        /**
         * 请求打电话的权限码
         */
        int REQUEST_CODE_ASK_CALL_PHONE = 100;
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
                        UToast.show(PersonalInfoActivity.this,"已经是最新版本");
                    }


                    @Override
                    public void onResponse(String response, int id) {

                        Log.d("ok===========", response);

                        Gson gson = new Gson();
                        bean = gson.fromJson(response, CheckBean.class);

                        if (bean.getVersionShort() > code) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(PersonalInfoActivity.this).setMessage("请升级更新app").setPositiveButton("确定", new DialogInterface.OnClickListener() {
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
                            UToast.show(PersonalInfoActivity.this,"已经是最新版本");
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
