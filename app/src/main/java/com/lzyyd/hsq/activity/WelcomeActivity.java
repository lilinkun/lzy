package com.lzyyd.hsq.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.util.PrefManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * Create by liguo on 2020/8/24
 * Describe:
 */
public class WelcomeActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnNext;
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //在setContentView()前检查是否第一次运行
        prefManager = new PrefManager(this);
        if(!prefManager.isFirstTimeLaunch()){
        launchHomeScreen();
        finish();
    }

    //让状态栏透明
        if(Build.VERSION.SDK_INT >= 21){
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    setContentView(R.layout.activity_welcome);

    viewPager = (ViewPager)findViewById(R.id.view_pager);
    dotsLayout = (LinearLayout)findViewById(R.id.layoutDots);
    btnNext = (Button) findViewById(R.id.btn_next);

    View view = LayoutInflater.from(this).inflate(R.layout.welcome_dialog,null);

    TextView tv_welcome = (TextView) view.findViewById(R.id.tv_welcome);

    tv_welcome.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(WelcomeActivity.this,UserAgreementActivity.class);
            startActivity(intent);
        }
    });

        AlertDialog.Builder builder = new AlertDialog.Builder(this).setView(view).setNeutralButton("暂不使用", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            finish();
        }
    }).setPositiveButton("同意", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    });
        builder.setCancelable(false);
        builder.show();


    //添加欢迎页面
    layouts = new int[]{
        R.layout.welcome_slide1,
        R.layout.welcome_slide2,
        R.layout.welcome_slide3,
        R.layout.welcome_slide4
    };
    //添加点
    addBottomDots(0);

    //让状态栏透明
    changeStatusBarColor();

    myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btnNext.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int current = getItem(+1);
            if(current < layouts.length){
                viewPager.setCurrentItem(current);
            }else{
                launchHomeScreen();
            }
        }
    });

    }


    public Typeface setTypeFace(){
        Typeface iconfont = Typeface.createFromAsset(this.getAssets(), "iconfont.ttf");
        return iconfont;
    }

    private void addBottomDots(int currentPage){
        dots = new TextView[layouts.length];

        dotsLayout.removeAllViews();
        for(int i = 0; i < dots.length; i++){
            dots[i] = new TextView(this);
            dots[i].setText(getResources().getString(R.string.tv_coin));//圆点
            dots[i].setTextSize(20);
            dots[i].setTextColor(Color.parseColor("#33E61E16"));
            dots[i].setTypeface(setTypeFace());
            dots[i].setPadding(10,0,10,0);
//            dots[i].getBackground().setAlpha(20);
            dotsLayout.addView(dots[i]);
        }

        if(dots.length > 0){
            dots[currentPage].setTextColor(Color.parseColor("#ffE61E16"));
//            dots[currentPage].getBackground().mutate().setAlpha(255);
        }
    }

    private int getItem(int i){
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen(){
        prefManager.setFirstTimeLaunch(false);
        Intent intent = null;
        intent = new Intent(getBaseContext(), SplashActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 让状态栏变透明
     */
    private void changeStatusBarColor(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener(){

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            //改变下一步按钮text  “NEXT”或“GOT IT”
            if(position == layouts.length - 1){
                btnNext.setText("立即体验");
                btnNext.setVisibility(View.VISIBLE);
            }else {
                btnNext.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

public class MyViewPagerAdapter extends PagerAdapter {

    private LayoutInflater layoutInflater;

    public MyViewPagerAdapter(){}

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(layouts[position],container,false);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return layouts.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View)object;
        container.removeView(view);
    }
}
}
