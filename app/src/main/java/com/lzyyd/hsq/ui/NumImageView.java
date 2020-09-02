package com.lzyyd.hsq.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.lzyyd.hsq.R;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * Create by liguo on 2020/8/12
 * Describe:
 */
public class NumImageView extends AppCompatImageView {

    //要显示的数量数量
    private int num = 0;
    //红色圆圈的半径
    private float radius;
    //圆圈内数字的半径
    private float textSize;
    //右边和上边内边距
    private int paddingRight;
    private int paddingTop;

    private Context context;

    public NumImageView(Context context) {
        super(context);
    }

    public NumImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NumImageView);
        num = a.getInteger(R.styleable.NumImageView_num,0);
        setNum(num);
    }

    public NumImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NumImageView);
        num = a.getInteger(R.styleable.NumImageView_num,0);
        setNum(num);
    }

    //设置显示的数量
    public void setNum(int num) {
        this.num = num;
        //重新绘制画布
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (num > 0) {
            //初始化半径
            radius = getWidth() / 8;
            //初始化字体大小
            textSize = num < 10 ? radius + 5 : radius;
            //初始化边距
            paddingRight = getPaddingRight();
            paddingTop = getPaddingTop();
            //初始化画笔
            Paint paint = new Paint();
            //设置抗锯齿
            paint.setAntiAlias(true);
            //设置颜色为红色
            paint.setColor(0x20FE5647);
            //设置填充样式为充满
            paint.setStyle(Paint.Style.FILL);
            //画圆
            canvas.drawCircle(getWidth() - radius - paddingRight/2, radius + paddingTop/2, radius, paint);
            //设置颜色为白色
            paint.setColor(0xffEF3328);
            //设置填充样式为充满
            paint.setStyle(Paint.Style.STROKE);
            //画圆
            canvas.drawCircle(getWidth() - radius - paddingRight/2, radius + paddingTop/2, radius, paint);
            //设置字体大小
            paint.setTextSize(textSize);
            //画数字
            canvas.drawText("" + (num < 10000 ? num : 10000),
                    num < 10 ? getWidth() - radius - textSize / 4 - paddingRight/2
                            : getWidth() - radius - textSize / 2 - paddingRight/2,
                    radius + textSize / 3 + paddingTop/2, paint);
        }
    }

}