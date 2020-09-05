package com.lzyyd.hsq.util;

import android.content.Context;

import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Create by liguo on 2020/9/3
 * Describe:
 */
public class WxPayUtil {

    public static void wxProgramPay(String appid, Context context, String page){
        IWXAPI api = WXAPIFactory.createWXAPI(context, appid);
        WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
        req.userName = "gh_236e7c5bd9d8";
        req.path = page;
        req.miniprogramType = WXLaunchMiniProgram.Req.MINIPROGRAM_TYPE_TEST;
        api.sendReq(req);

    }
}
