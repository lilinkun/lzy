package com.lzyyd.hsq.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Create by liguo on 2020/8/3
 * Describe:
 */
public class RecyclerViewSlidingBar extends View {

    private Paint allPaint, inPaint;
    private float mRoundRadius = 0;
    private RectF mViewRect, mInRect;
    private int viewWidth;
    private int viewHeight;
    private boolean canShow = false;

    public RecyclerViewSlidingBar(Context context) {
        this(context, null);
    }

    public RecyclerViewSlidingBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerViewSlidingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        allPaint = new Paint();
        allPaint.setAntiAlias(true); // 抗锯齿
        allPaint.setDither(true); // 防抖动
        allPaint.setColor(Color.parseColor("#E6E6E6"));
        allPaint.setStyle(Paint.Style.FILL);
        inPaint = new Paint();
        inPaint.setAntiAlias(true); // 抗锯齿
        inPaint.setDither(true); // 防抖动
        inPaint.setColor(Color.parseColor("#FF3C38"));
        inPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        viewWidth = getWidth();
        viewHeight = getHeight();
        mViewRect = new RectF(0, 0, viewWidth, viewHeight);
        mRoundRadius = viewHeight / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (canShow) {
            canvas.drawRoundRect(mViewRect, mRoundRadius, mRoundRadius, allPaint);
            canvas.drawRoundRect(mInRect, mRoundRadius, mRoundRadius, inPaint);
        }
    }

    private float rvWidth;
    private float rvViewWidth;
    private float pointWidth;
    private float scroll;
    private float coefficient;

    public void bindRecyclerView(final RecyclerView mRv) {
        mRv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.e("onGlobalLayout", "onGlobalLayout");
                rvWidth = mRv.computeHorizontalScrollRange();
                rvViewWidth = mRv.getWidth();
                canShow = rvWidth > rvViewWidth;
                if (!canShow) return;
                coefficient = viewWidth / rvWidth;
                pointWidth = rvViewWidth / rvWidth * viewWidth;
                mInRect = new RectF(0, 0, pointWidth, viewHeight);
                invalidate();
                mRv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        Log.e("onScrolled", dx + ":" + rvWidth);
                        if (dx != 0) {
                            scroll = coefficient * dx;
                            mInRect.left += scroll;
                            mInRect.right += scroll;
                            invalidate();
                        }
                    }
                });
            }
        });
    }
}
