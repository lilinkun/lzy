package com.lzyyd.hsq.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

public class TextUtil {

    public static void setText(Context context, String content, String lableStr,TextView tvLable){
        int width = context.getResources().getDisplayMetrics().widthPixels;
        int height = context.getResources().getDisplayMetrics().heightPixels;
        height = height / 38;
        width = width / 25;
        Drawable drawable = TextDrawable.builder()
                .beginConfig()
                .width(lableStr.length() * width)  // width in px
                .height(height) // height in px
                .textColor(Color.parseColor("#ffffff"))
                .fontSize((int) (height * 0.7))
                .endConfig()
                .buildRoundRect(lableStr, Color.parseColor("#ff3c38"), 4);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        setTextStyle(content, drawable, tvLable); //添加一个标签
    }

    /**
     * 利用SpannableString实现添加Drawable
     *
     * @param content
     * @param drawable
     * @param tv
     */
    private static void setTextStyle(String content, Drawable drawable, TextView tv) {
        SpannableString spanText = new SpannableString(" " + " " + " " + content);
        // 替换0,1的字符
        if (drawable != null) {
            spanText.setSpan(new ImageSpan(drawable), 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        } else {
            spanText.setSpan("", 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        tv.setText(spanText);
    }

    private void setTextStyle(String content, Drawable drawable, Drawable drawable2, TextView tv) {
        SpannableString spanText = new SpannableString(" " + " " + " " + " " + content);
        // 替换0,1的字符
        if (drawable != null) {
            spanText.setSpan(new ImageSpan(drawable), 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        } else {
            spanText.setSpan("", 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        if (drawable2 != null) {
            spanText.setSpan(new ImageSpan(drawable2), 2, 3, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        } else {
            spanText.setSpan("", 2, 3, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        tv.setText(spanText);
    }

}
