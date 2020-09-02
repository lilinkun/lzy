package com.lzyyd.hsq.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.SearchBean;
import com.lzyyd.hsq.databinding.ActivitySearchBinding;
import com.lzyyd.hsq.db.DBManager;
import com.lzyyd.hsq.interf.IGoodsTypeListener;
import com.lzyyd.hsq.util.Eyes;
import com.lzyyd.hsq.viewmodel.SearchViewModel;

import java.util.List;

import androidx.lifecycle.ViewModelProviders;

/**
 * Create by liguo on 2020/9/2
 * Describe:
 */
public class SearchActivity extends BaseActivity<ActivitySearchBinding, SearchViewModel> implements IGoodsTypeListener {

    private LinearLayout.LayoutParams layoutParams;
    private String PAGE_COUNT = "80";
    private String orderby = "0";
    private String goodsType = "";

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_search;
    }

    @Override
    public int initVariableId() {
        return BR.searchviewmodel;
    }

    @Override
    public SearchViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(SearchViewModel.class);
    }

    @Override
    public void initData() {

        List<SearchBean> searchBean = DBManager.getInstance(this).querySearchBean(ProApplication.APP);

        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 15, 10, 10);
        if (searchBean.size() > 0) {
            //往容器内添加TextView数据
            if (binding.flowSearchHistory != null) {
                binding.flowSearchHistory.removeAllViews();
            }
            for (int i = 0; i < searchBean.size(); i++) {
                TextView tv = new TextView(this);
                tv.setText(searchBean.get(i).getSearchname());
                tv.setTextColor(getResources().getColor(R.color.text_222222));
                tv.setMaxEms(10);
                tv.setTextSize(12);
                tv.setSingleLine();
                tv.setBackgroundResource(R.drawable.shape_search_item_select);
                tv.setLayoutParams(layoutParams);
                binding.flowSearchHistory.addView(tv, layoutParams);

                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.etSearch.setText(((TextView) v).getText());
                        doSearch();
                    }
                });
            }
            binding.flowSearchHistory.specifyLines(6);
        }

        binding.llTop.setListener(this);
    }

    private void doSearch() {

        if (!binding.etSearch.getText().toString().isEmpty()) {
            if (DBManager.getInstance(this).querySearch(binding.etSearch.getText().toString()).size() == 0) {
                SearchBean searchBean = new SearchBean();
                searchBean.setUsername(ProApplication.APP);
                searchBean.setSearchname(binding.etSearch.getText().toString());
                if (DBManager.getInstance(this).querySearch(binding.etSearch.getText().toString()).size() == 0) {
                    DBManager.getInstance(this).insertSearchBean(searchBean);
                }
            }
        }

        Eyes.setStatusBarColor(this, getResources().getColor(R.color.setting_title_color));
        binding.rlSearchTop.setBackgroundColor(getResources().getColor(R.color.setting_title_color));
        binding.tvSearch.setVisibility(View.GONE);
        binding.llSearch.setVisibility(View.VISIBLE);
        binding.tvSearchEt.setText(binding.etSearch.getText().toString());
        binding.etSearch.setVisibility(View.INVISIBLE);
        binding.icSearchIcon.setVisibility(View.GONE);

        binding.llResult.setVisibility(View.VISIBLE);
        binding.llSearchGoodsType.setVisibility(View.VISIBLE);


    }

    @Override
    public void getSortType(int sortType) {
        switch (sortType) {
            case 1://默认排序
                viewModel.getData("1", PAGE_COUNT, goodsType, "", binding.etSearch.getText().toString(), ProApplication.SESSIONID());
                binding.llTop.setText("全部");
                break;

            case 2://选择商城

                orderby = "2";
                viewModel.getData("1", PAGE_COUNT, goodsType, orderby, binding.etSearch.getText().toString(), ProApplication.SESSIONID());

                break;


            case 3://销量上
                orderby = "1";
                viewModel.getData("1", PAGE_COUNT, goodsType, orderby, binding.etSearch.getText().toString(), ProApplication.SESSIONID());

                break;


            case 4://销量下


                break;

            case 5://价格上

                orderby = "3";
//                isGrouponType = false;
                viewModel.getData("1", PAGE_COUNT, goodsType, orderby, binding.etSearch.getText().toString(), ProApplication.SESSIONID());

                break;

            case 6://价格下

                orderby = "4";
                viewModel.getData("1", PAGE_COUNT, goodsType, orderby, binding.etSearch.getText().toString(), ProApplication.SESSIONID());

                break;
        }
    }
}
