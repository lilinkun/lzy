package com.lzyyd.hsq.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Scroller;

/**
 * Create by liguo on 2020/10/9
 * Describe:
 */
public class DragView extends RelativeLayout {
        private int mLastX;
        private int mLastY;
        //声明Scroller变量
        private Scroller mScroller;
        public DragView(Context context) {
            this(context, null);
        }

        public DragView(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public DragView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            //在构造方法中初始化Scroller变量
            mScroller = new Scroller(context);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            int x = (int) event.getRawX();
            int y = (int) event.getRawY();
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    mLastX = x;
                    mLastY = y;
                    break;
                case MotionEvent.ACTION_MOVE:
                    int offsetX = x - mLastX;
                    int offsetY = y - mLastY;
                    //实现View跟随手指移动的效果
                    ((View)getParent()).scrollBy(-offsetX, -offsetY);
                    //重新设置初始坐标
                    mLastX = x;
                    mLastY = y;
                    break;
                case MotionEvent.ACTION_UP:
                    //当手指抬起时执行滑动过程
                    View view = (View) getParent();
                    mScroller.startScroll(view.getScrollX(), view.getScrollY(),
                            view.getScrollX(), view.getScrollY(), 5000);
                    //调用重绘来间接调用computeScroll()方法
                    invalidate();
                    break;
            }
            return true;
        }

        @Override
        public void computeScroll() {
            super.computeScroll();
            //判断滑动过程是否完成
            if (mScroller.computeScrollOffset()){
                ((View)getParent()).scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
                //通过重绘来不断调用computeScroll()方法
                invalidate();
            }
        }

}
