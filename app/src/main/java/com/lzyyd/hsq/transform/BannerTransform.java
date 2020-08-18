package com.lzyyd.hsq.transform;

import android.view.View;

import com.xw.banner.transformer.ABaseTransformer;

/**
 * Create by liguo on 2020/7/15
 * Describe:
 */
public class BannerTransform extends ABaseTransformer {
    @Override
    protected void onTransform(View page, float position) {

    }

    @Override
    protected boolean isPagingEnabled() {
        return true;
    }
}
