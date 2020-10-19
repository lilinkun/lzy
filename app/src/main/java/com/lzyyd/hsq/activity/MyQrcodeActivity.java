package com.lzyyd.hsq.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.databinding.ActivityMyqrcodeBinding;
import com.lzyyd.hsq.util.Eyes;
import com.lzyyd.hsq.util.HsqAppUtil;
import com.lzyyd.hsq.util.QRCodeUtil;
import com.lzyyd.hsq.util.UToast;
import com.lzyyd.hsq.util.WxShareUtils;
import com.lzyyd.hsq.viewmodel.MyQrcodeViewModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Create by liguo on 2020/8/13
 * Describe:
 */
public class MyQrcodeActivity extends BaseActivity<ActivityMyqrcodeBinding, MyQrcodeViewModel> {

    private static final int REQUEST_CODE_CONTACT = 101;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_myqrcode;
    }

    @Override
    public int initVariableId() {
        return BR.myqrcode;
    }

    @Override
    public MyQrcodeViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(MyQrcodeViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));

        String qrStr = "https://wx.lzyyd.com/account/Index/" + ProApplication.USERNAME;
        Bitmap mBitmap = QRCodeUtil.createQRCodeBitmap(qrStr, 200, 200, WxShareUtils.getBitmap(this,R.drawable.ic_launcher1));
        binding.icQrcode.setImageBitmap(mBitmap);

        binding.llWx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = WxShareUtils.getBitmap(MyQrcodeActivity.this,R.drawable.ic_launcher1);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                WxShareUtils.shareWeb(MyQrcodeActivity.this, HsqAppUtil.APP_ID,qrStr,"壕省钱一款省钱的APP","朋友，别再原价付款了！试试领券再下单，最高省钱90%啊~",baos.toByteArray(),0);
            }
        });

        binding.llFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = WxShareUtils.getBitmap(MyQrcodeActivity.this,R.drawable.ic_launcher1);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                WxShareUtils.shareWeb(MyQrcodeActivity.this, HsqAppUtil.APP_ID,qrStr,"壕省钱一款省钱的APP","朋友，别再原价付款了！试试领券再下单，最高省钱90%啊~",baos.toByteArray(),1);
            }
        });

        binding.llSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    if (EasyPermissions.hasPermissions(MyQrcodeActivity.this, perms)) {
                        saveBitmap(binding.llPic, "pic1_");
                    }else {
                        EasyPermissions.requestPermissions(MyQrcodeActivity.this, "使用需要授权读写外部存储权限!", REQUEST_CODE_CONTACT, perms);
                    }

                }else {
                    saveBitmap(binding.llPic, "pic1_");
                }
            }
        });

    }

    public void saveBitmap(View v, String name) {

        String fileName = name + "_"+(new Date()).getTime()+".png";


        Bitmap bm = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        v.draw(canvas);
        String TAG = "LG";
        Log.e(TAG, "保存图片");
        String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
        File f = new File(dir, fileName);
//        File f = new File("/sdcard/DCIM/Screenshots/", fileName);
        if (f.exists()) {
            f.delete();
        }
        try {

            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Log.i(TAG, "已经保存");
            UToast.show(this,"保存成功");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(f);
        intent.setData(uri);
        sendBroadcast(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE_CONTACT:

                saveBitmap(binding.llPic, "pic1_");

                break;
        }
    }

}
