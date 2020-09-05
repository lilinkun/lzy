package com.lzyyd.hsq.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.bean.ArticleDetailBean;
import com.lzyyd.hsq.ui.AdverView;

import java.util.List;

/**
 * Create by liguo on 2020/8/26
 * Describe:
 */
public class ViewAdapter {
    private List<ArticleDetailBean> mDatas;
    public ViewAdapter(List<ArticleDetailBean> mDatas) {
        this.mDatas = mDatas;
        if (mDatas == null || mDatas.isEmpty()) {
            throw new RuntimeException("nothing to show");
        }
    }
    /**
     * 获取数据的条数
     * @return
     */
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    /**
     * 获取摸个数据
     * @param position
     * @return
     */
    public ArticleDetailBean getItem(int position) {
        return mDatas.get(position);
    }
    /**
     * 获取条目布局
     * @param parent
     * @return
     */
    public View getView(AdverView parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.home_article, null);
    }

    /**
     * 条目数据适配
     * @param view
     * @param data
     */
    public void setItem(final View view, final ArticleDetailBean data) {
        TextView tv = (TextView) view.findViewById(R.id.title);
        tv.setText(data.getTitle());
        TextView tag = (TextView) view.findViewById(R.id.tag);
        tag.setText(data.getCategoryName());
            //你可以增加点击事件
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //比如打开url

            }
        });
    }
}