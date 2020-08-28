package com.lzyyd.hsq.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.adapter.RecordAdapter;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.CollectListBean;
import com.lzyyd.hsq.databinding.ActivityCollectBinding;
import com.lzyyd.hsq.ui.GridSpacingItemDecoration;
import com.lzyyd.hsq.ui.SpacesItemDecoration;
import com.lzyyd.hsq.util.Eyes;
import com.lzyyd.hsq.util.UToast;
import com.lzyyd.hsq.viewmodel.CollectViewModel;
import com.lzyyd.hsq.viewmodel.ForgetPasswordViewModel;
import com.lzyyd.hsq.viewmodel.GoodsListViewModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
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

    private RecordAdapter recordAdapter;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_collect;
    }

    @Override
    public int initVariableId() {
        return BR.collect;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0x11:

                    String collectid = msg.getData().getString("collectid");

                    viewModel.deleteCollectGoods(collectid,ProApplication.SESSIONID());

                    break;
            }
        }
    };

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

            if(recordAdapter == null ) {
                recordAdapter = new RecordAdapter(this, handler);

                recordAdapter.getItems().addAll(msg);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

                int spacing = 30; // 50px
                binding.rvCollectlist.addItemDecoration(new SpacesItemDecoration(spacing));
                binding.rvCollectlist.setLayoutManager(linearLayoutManager);
                binding.rvCollectlist.setAdapter(recordAdapter);
            }else {
                recordAdapter.getItems().clear();
                recordAdapter.getItems().addAll(msg);
                recordAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void getCollectFail(String msg) {
        UToast.show(this,msg);
    }

    @Override
    public void getDeleteCollectGoodsSuccess(String msg) {
        viewModel.getCollectDataList(PageIndex + "", PAGE_COUNT + "", "1", ProApplication.SESSIONID());
    }

    @Override
    public void getDeleteCollectGoodsFail(String msg) {
        UToast.show(this,msg);
    }
}
