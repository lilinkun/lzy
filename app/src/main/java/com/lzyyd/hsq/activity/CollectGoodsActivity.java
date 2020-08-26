package com.lzyyd.hsq.activity;

import android.os.Bundle;
import android.view.View;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.adapter.RecordAdapter;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.CollectListBean;
import com.lzyyd.hsq.databinding.ActivityCollectBinding;
import com.lzyyd.hsq.ui.GridSpacingItemDecoration;
import com.lzyyd.hsq.util.Eyes;
import com.lzyyd.hsq.viewmodel.CollectViewModel;
import com.lzyyd.hsq.viewmodel.ForgetPasswordViewModel;
import com.lzyyd.hsq.viewmodel.GoodsListViewModel;

import java.util.ArrayList;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Create by liguo on 2020/8/11
 * Describe:
 */
public class CollectGoodsActivity extends BaseActivity<ActivityCollectBinding, CollectViewModel> implements CollectViewModel.CollectGoodsListener {

    private int PageIndex = 1;
    private int PAGE_COUNT = 20;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_collect;
    }

    @Override
    public int initVariableId() {
        return BR.collect;
    }

    @Override
    public CollectViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(CollectViewModel.class);
    }

    @Override
    public void initData() {
        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));

        viewModel.setListener(this);
        viewModel.getCollectDataList(PageIndex + "", PAGE_COUNT + "", "1", ProApplication.SESSIONID());
    }

    @Override
    public void getCollectDataSuccess(ArrayList<CollectListBean> msg) {
        if (msg != null && msg.size() > 0){
            binding.llNoCollect.setVisibility(View.GONE);

            RecordAdapter recordAdapter = new RecordAdapter(this);

            recordAdapter.getItems().addAll(msg);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            int spanCount = 3; // 3 columns
            int spacing = 20; // 50px
            boolean includeEdge = false;
            binding.rvCollectlist.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
            binding.rvCollectlist.setLayoutManager(linearLayoutManager);
            binding.rvCollectlist.setAdapter(recordAdapter);
        }
    }

    @Override
    public void getCollectFail(String msg) {

    }
}
