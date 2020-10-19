package com.lzyyd.hsq.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.databinding.ActivityVipBinding;
import com.lzyyd.hsq.util.Eyes;
import com.lzyyd.hsq.util.HsqAppUtil;
import com.lzyyd.hsq.viewmodel.VipViewModel;
import com.squareup.picasso.Picasso;

import androidx.lifecycle.ViewModelProviders;

/**
 * Create by liguo on 2020/8/24
 * Describe:
 */
public class VipActivity extends BaseActivity<ActivityVipBinding, VipViewModel> {

    private int h = 0;
    private float rawy = 0;
    private int y = 0;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_vip;
    }

    @Override
    public int initVariableId() {
        return BR.vip;
    }

    @Override
    public VipViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(VipViewModel.class);
    }

    @Override
    public void initData() {
        Eyes.setStatusBarWhiteColor(this, getResources().getColor(R.color.white));

        SharedPreferences sharedPreferences = getSharedPreferences(HsqAppUtil.LOGIN,MODE_PRIVATE);
        String Project = sharedPreferences.getString(HsqAppUtil.PROJECT,"");
        String userLevel = sharedPreferences.getString(HsqAppUtil.USERLEVEL,"");
        String userLevelName = sharedPreferences.getString(HsqAppUtil.USERLEVELNAME,"");

        String headImg = sharedPreferences.getString(HsqAppUtil.HEADIMGURL,"");
        Picasso.with(this).load(headImg).into(binding.rivVip);

        binding.vipName.setText(userLevelName);

        viewModel.projectField.set(Integer.valueOf(Project) == 1 ? View.VISIBLE : View.GONE);

        if (Integer.valueOf(userLevel) > 0) {
            binding.rlUpdateVip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewModel.setJumpChuangke(16);
                }
            });
        }else {
            binding.tvVip.setBackground(getResources().getDrawable(R.drawable.bg_update_vip_unclick));
        }

        binding.rlPointVip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(VipActivity.this, IntegralActivity.class);
                startActivity(intent);
            }
        });


        if (Project.equals("1")){
            if (Integer.valueOf(userLevel) >= 120){
                binding.rlVipChuangke.setEnabled(false);
                binding.tvChuangke.setBackground(getResources().getDrawable(R.drawable.bg_update_vip_unclick));
                binding.rlVipServiceCenter.setEnabled(false);
                binding.tvCenter.setBackground(getResources().getDrawable(R.drawable.bg_update_vip_unclick));
            }else if (Integer.valueOf(userLevel) >= 110 && Integer.valueOf(userLevel) < 120){
                binding.rlVipChuangke.setEnabled(false);
                binding.tvChuangke.setBackground(getResources().getDrawable(R.drawable.bg_update_vip_unclick));
            }
        }else if (Project.equals("2")){
            if (Integer.valueOf(userLevel) >= 220){
                binding.rlVipChuangke.setEnabled(false);
                binding.tvChuangke.setBackground(getResources().getDrawable(R.drawable.bg_update_vip_unclick));
            }

        }

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_vip_bg);
        binding.ivVipBg.setImageBitmap(bitmap);

        binding.svVip.setOnTouchListener(new View.OnTouchListener() {

            private int lastY = 0;
            private int touchEventId = -9983761;
            Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    View scroller = (View) msg.obj;

                    if (msg.what == touchEventId) {
                        if (lastY == scroller.getScrollY()) {
                            //停止了，此处你的操作业务
                            y = (int)(y -lastY);
                            binding.svVip.setRY((int)(y));
                        } else {
                            handler.sendMessageDelayed(handler.obtainMessage(touchEventId, scroller), 1);
                            lastY = scroller.getScrollY();
                        }
                    }
                }
            };

            @Override
            public boolean onTouch(View v, MotionEvent ev) {

                if (ev.getAction() == MotionEvent.ACTION_DOWN){
                    rawy = ev.getRawY();
                }
                if (ev.getAction() == MotionEvent.ACTION_MOVE){

                    if (binding.svVip.getChildAt(0).getMeasuredHeight() > v.getScrollY() + v.getHeight()){
                        y = (int)(y + ev.getRawY()-rawy);
                        binding.svVip.setRY((int)(y));
                        Log.v("LGG:", "getRawY:"+ev.getRawY() + "  sc_y:" + y + " rawy:" + rawy);
                        rawy= ev.getRawY();
                    }

                    if (v.getScrollY() == 0){
                        y = binding.rlVip.getTop() + binding.rlVipTitle.getHeight();
                        binding.svVip.setRY(y);
                    }
                }

                if (ev.getAction() == MotionEvent.ACTION_UP){
                    handler.sendMessageDelayed(handler.obtainMessage(touchEventId, v), 5);
                }


                return false;
            }
        });


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        y = binding.rlVip.getTop() + binding.rlVipTitle.getHeight();
        binding.svVip.setRY(y);
    }
}
