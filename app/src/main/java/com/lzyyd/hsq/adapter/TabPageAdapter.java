package com.lzyyd.hsq.adapter;

import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Create by liguo on 2020/8/12
 * Describe:
 */
public class TabPageAdapter extends FragmentPagerAdapter {
    List<Fragment> fragments = new ArrayList<>();
    private List<String> titles;
    FragmentManager fm;

    public TabPageAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
        this.fm = fm;
//        fragments.add(allOrderFragment);
//        fragments.add(waitPayFragment);
//        fragments.add(waitReceiveFragment);
//        fragments.add(completedOrderFragment);
//        fragments.add(overOrderFragment);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // 将实例化的fragment进行显示即可。
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        fm.beginTransaction().show(fragment).commit();
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//            Fragment fragment = fragments.get(position);// 获取要销毁的fragment
//            getSupportFragmentManager().beginTransaction().hide(fragment).commit();// 将其隐藏即可，并不需要真正销毁，这样fragment状态就得到了保存
    }

}