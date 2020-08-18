package com.lzyyd.hsq.http.callback;

import io.reactivex.observers.DisposableObserver;

/**
 * Create by liguo on 2020/7/28
 * Describe:
 */
public abstract class HttpCallBack<T> extends DisposableObserver<T> {

    /**
     * 请求返回
     */
    public abstract void onResponse(T page);

    public abstract void onErr(String msg);

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onError(Throwable e) {

        onErr(e.getMessage());

    }

    @Override
    public void onNext(T t) {
        onResponse(t);
    }
}

