package com.lzyyd.hsq.ui;

import android.content.Context;
import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.FlashBean;
import com.lzyyd.hsq.transform.BannerTransform;
import com.squareup.picasso.Picasso;
import com.xw.banner.Banner;
import com.xw.banner.BannerConfig;
import com.xw.banner.Transformer;
import com.xw.banner.listener.OnBannerListener;
import com.xw.banner.loader.ImageLoaderInterface;

import java.util.ArrayList;

/**
 * Create by liguo on 2020/7/15
 * Describe:
 */
public class CustomBannerView {

    /**
     * @param banner
     * @param context
     */
    public static void startBanner(final ArrayList<FlashBean> arrayList, Banner banner, final Context context) {
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            strings.add("111111" + i);
        }

        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);

        //设置图片加载器，图片加载器在下方
        banner.setImageLoader(new ImageLoaderInterface() {
            @Override
            public void displayImage(Context context, Object path, View imageView) {
                Picasso.with(context).load(ProApplication.BANNERIMG + ((FlashBean) path).getFlashPic()).error(R.color.gray).into((ImageView) imageView);
            }

            @Override
            public View createImageView(Context context) {
                return null;
            }
        });
        //设置图片网址或地址的集合
        banner.setImages(arrayList);
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        banner.setBannerAnimation(Transformer.RotateDown);

        banner.setPageTransformer(true, new BannerTransform());

        banner.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0,0,view.getWidth(),view.getHeight(),30);
            }
        });

        //设置轮播图的标题集合
        banner.setBannerTitles(strings);
        //设置轮播间隔时间
        banner.setDelayTime(3000);
        //设置是否为自动轮播，默认是“是”。
        banner.isAutoPlay(true);
        //设置指示器的位置，小点点，左中右。
        banner.setIndicatorGravity(BannerConfig.CENTER)
                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {

                    }
                })
                //必须最后调用的方法，启动轮播图。
                .start();

    }


}
