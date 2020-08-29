package com.lzyyd.hsq.http.callback;

import android.util.Log;

import com.google.gson.Gson;
import com.lzyyd.hsq.bean.ResultBean;
import com.lzyyd.hsq.http.factory.ResultException;
import com.lzyyd.hsq.util.HsqAppUtil;

import io.reactivex.observers.DisposableObserver;
import rx.Subscriber;

public abstract class HttpResultCallBack<M, T> extends DisposableObserver<ResultBean<M, T>> {

    /**
     * 请求返回
     */
    public abstract void onResponse(M m, String status, T page);

    public abstract void onErr(String msg, String status);

    /**
     * 请求完成
     */
    public void onComplete() {
    }

    @Override
    public void onError(Throwable e) {
        if (e != null) {
            if (e instanceof ResultException) {
                ResultException err = (ResultException) e;
                onErr(err.getErrMsg(), HsqAppUtil.RESULT_FAIL);
            } else {
                onErr(e.getMessage(), HsqAppUtil.RESULT_FAIL);
                Log.d("HttpResultCallBack", "解析失败==：" + e.getMessage());
            }
        }
        onComplete();
    }

    /**
     * Http请求失败
     */
    private void onHttpFail(String msg, String status) {
        onErr(msg, status);
    }

    @Override
    public void onNext(ResultBean<M, T> result) {
        String jsonResponse = new Gson().toJson(result);
        Log.d("HttpResultCallBack", "返回ok==：" + jsonResponse);
        if (result.getStatus().equals(HsqAppUtil.RESULT_SUCCESS)) {

            onResponse(result.getData(), result.getDesc(), result.getPage());

        } else {
            onHttpFail(result.getDesc(), HsqAppUtil.RESULT_FAIL + result.getCode());
        }
    }
}
