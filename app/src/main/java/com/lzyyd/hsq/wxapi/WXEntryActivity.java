package com.lzyyd.hsq.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lzyyd.hsq.bean.WxUserInfo;
import com.lzyyd.hsq.interf.IWxLoginListener;
import com.lzyyd.hsq.util.HsqAppUtil;
import com.lzyyd.hsq.util.OkHttpUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    private IWXAPI iwxapi;
    private String unionid;
    private String openid;
    public static int wxType = 0;
    public static IWxLoginListener iWxResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
//        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //接收到分享以及登录的intent传递handleIntent方法，处理结果
        iwxapi = WXAPIFactory.createWXAPI(this, HsqAppUtil.APP_ID, false);
        iwxapi.handleIntent(getIntent(), this);
    }


    @Override
    public void onReq(BaseReq baseReq) {
    }

    private static final int RETURN_MSG_TYPE_SHARE = 2;

    //请求回调结果处理
    @Override
    public void onResp(BaseResp baseResp) {
        //登录回调
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                if (wxType == HsqAppUtil.WXTYPE_LOGIN) {
                    String code = ((SendAuth.Resp) baseResp).code;
                    //获取用户信息
                    getAccessToken(code);
                } else {
                    finish();
                }
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED://用户拒绝授权
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL://用户取消
                finish();
                break;
            case BaseResp.ErrCode.ERR_BAN:
                Toast.makeText(this, "签名错误", Toast.LENGTH_LONG).show();
                break;
            case RETURN_MSG_TYPE_SHARE:
                finish();
                break;

            default:
                break;
        }
    }

    private void getAccessToken(String code) {
        //获取授权
        StringBuffer loginUrl = new StringBuffer();
        loginUrl.append("https://api.weixin.qq.com/sns/oauth2/access_token")
                .append("?appid=")
                .append(HsqAppUtil.APP_ID)
                .append("&secret=")
                .append(HsqAppUtil.SECRET)
                .append("&code=")
                .append(code)
                .append("&grant_type=authorization_code");
        OkHttpUtils.ResultCallback<String> resultCallback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(final String response) {
                String access = null;
                String openId = null;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    access = jsonObject.getString("access_token");
                    openId = jsonObject.getString("openid");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //获取个人信息
                String getUserInfo = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access + "&openid=" + openId;
                OkHttpUtils.ResultCallback<String> reCallback = new OkHttpUtils.ResultCallback<String>() {
                    @Override
                    public void onSuccess(String responses) {

                        String nickName = null;
                        String sex = null;
                        String city = null;
                        String province = null;
                        String country = null;
                        String headimgurl = null;
                        try {
                            JSONObject jsonObject = new JSONObject(responses);

                            Gson gson = new GsonBuilder().create();

                            WxUserInfo wxInfomation = gson.fromJson(responses, WxUserInfo.class);

                            openid = jsonObject.getString("openid");
                            nickName = jsonObject.getString("nickname");
                            sex = jsonObject.getString("sex");
                            city = jsonObject.getString("city");
                            province = jsonObject.getString("province");
                            country = jsonObject.getString("country");
                            headimgurl = jsonObject.getString("headimgurl");
                            unionid = jsonObject.getString("unionid");

//                            DBManager.getInstance(WXEntryActivity.this).insertWxInfo(wxInfomation);
                            iWxResult.setWxLoginSuccess(wxInfomation);
                            Intent intent = new Intent();
                            intent.putExtra("wxinfo", wxInfomation);
                            setResult(RESULT_OK, intent);
                            finish();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Exception e) {
//                        DBManager.getInstance(WXEntryActivity.this).deleteWxInfo();
                        Toast.makeText(WXEntryActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                };
                OkHttpUtils.get(getUserInfo, reCallback);
            }

            @Override
            public void onFailure(Exception e) {
//                DBManager.getInstance(WXEntryActivity.this).deleteWxInfo();
                Toast.makeText(WXEntryActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                finish();
            }
        };
        OkHttpUtils.get(loginUrl.toString(), resultCallback);
    }

    @Override
    protected void onPause() {
        overridePendingTransition(0, 0);
        super.onPause();
    }

    public static void setLoginListener(IWxLoginListener iWxResultListener) {
        iWxResult = iWxResultListener;
    }

    public static void wxType(int type) {
        wxType = type;
    }

}
