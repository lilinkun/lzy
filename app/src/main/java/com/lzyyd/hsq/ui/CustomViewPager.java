package com.lzyyd.hsq.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

public class CustomViewPager extends ViewPager {
    private boolean isCanScroll = true;

    public CustomViewPager(Context paramContext) {
        super(paramContext);
    }

    public CustomViewPager(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
    }

    public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent) {
        if (this.isCanScroll) {
            return super.onInterceptTouchEvent(paramMotionEvent);
        }
        return false;
    }

    public void setScanScroll(boolean paramBoolean) {
        this.isCanScroll = paramBoolean;
    }
}