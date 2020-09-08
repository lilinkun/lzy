package com.lzyyd.hsq.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

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
        Eyes.setStatusBarColor1(this, Color.parseColor("#1C1714"));

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

    }
}
