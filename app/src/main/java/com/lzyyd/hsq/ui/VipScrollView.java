package com.lzyyd.hsq.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * Create by liguo on 2020/9/30
 * Describe:
 */
public class VipScrollView extends ScrollView {

    private int sc_y = 0;

    public VipScrollView(Context context) {
        super(context);
    }

    public VipScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VipScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setRY(int y){
        this.sc_y = y;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (ev.getRawY() > sc_y) {

            return super.onTouchEvent(ev);
        }else {
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
