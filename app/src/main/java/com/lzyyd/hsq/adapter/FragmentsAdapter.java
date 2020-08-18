package com.lzyyd.hsq.adapter;

import android.util.SparseArray;

import com.lzyyd.hsq.base.BaseFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Create by liguo on &{DATE}
 * Describe:
 */
public class FragmentsAdapter extends FragmentPagerAdapter {

    private SparseArray<BaseFragment> fragmentSparseArr;

    public FragmentsAdapter(FragmentManager fm) {
        super(fm);
    }


    public void setFragments(SparseArray<BaseFragment> fragmentSparseArr) {
        this.fragmentSparseArr = fragmentSparseArr;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        if (null != fragmentSparseArr) return fragmentSparseArr.get(position);
        return null;
    }

    @Override
    public int getCount() {
        if (null != fragmentSparseArr) return fragmentSparseArr.size();
        return 0;
    }
}
