package com.lzyyd.hsq.viewmodel;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.activity.CcqActivity;
import com.lzyyd.hsq.activity.ChuangkeActivity;
import com.lzyyd.hsq.activity.GoodsDetailActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.data.DataRepository;
import com.lzyyd.hsq.util.HsqAppUtil;
import com.lzyyd.hsq.util.WxShareUtils;

import java.io.ByteArrayOutputStream;

import androidx.databinding.ObservableField;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * Create by liguo on 2020/8/24
 * Describe:
 */
public class VipViewModel extends BaseViewModel<DataRepository> {

    public ObservableField<Integer> projectField = new ObservableField<>();
    public ObservableField<Boolean> userLevelField = new ObservableField<>();

    private Application application;

    public VipViewModel(Application application,DataRepository dataRepository){
        super(application,dataRepository);
        this.application = application;
    }

    public void setJumpChuangke(int goodstype){
        Bundle bundle = new Bundle();
        bundle.putInt(HsqAppUtil.GOODSTYPE,goodstype);
        startActivity(ChuangkeActivity.class,bundle);
    }

    public void setJumpCCQ(){
        Bundle bundle = new Bundle();
        bundle.putString(HsqAppUtil.GOODSID, ProApplication.CCQGOODSID);
        bundle.putInt(HsqAppUtil.TYPE,64);
        startActivity(GoodsDetailActivity.class,bundle);
    }


    public void setWxShared(){
        Context context = application;
        String qrStr = "https://wx.lzyyd.com/account/Index/" + ProApplication.USERNAME;
        Bitmap bitmap = WxShareUtils.getBitmap(context, R.drawable.ic_launcher1);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        WxShareUtils.shareWeb(context, HsqAppUtil.APP_ID,qrStr,"壕省钱一款省钱的APP","朋友，别再原价付款了！试试领券再下单，最高省钱90%啊~",baos.toByteArray(),0);

    }
}



