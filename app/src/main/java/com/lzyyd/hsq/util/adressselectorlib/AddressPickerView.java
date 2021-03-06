package com.lzyyd.hsq.util.adressselectorlib;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.bean.LocalBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * lilinkun
 * 自定义仿京东地址选择器
 */

public class AddressPickerView extends RelativeLayout implements View.OnClickListener {
    // recyclerView 选中Item 的颜色
    private int defaultSelectedColor = Color.parseColor("#999999");
    // recyclerView 未选中Item 的颜色
    private int defaultUnSelectedColor = Color.parseColor("#999999");
    // 确定字体不可以点击时候的颜色
    private int defaultSureUnClickColor = Color.parseColor("#7F7F7F");
    // 确定字体可以点击时候的颜色
    private int defaultSureCanClickColor = Color.parseColor("#50AA00");

    private Context mContext;
    private int defaultTabCount = 3; //tab 的数量
    private TabLayout mTabLayout; // tabLayout
    private RecyclerView mRvList; // 显示数据的RecyclerView
    private String defaultProvince = "省份"; //显示在上面tab中的省份
    private String defaultCity = "城市"; //显示在上面tab中的城市
    private String defaultDistrict = "区县"; //显示在上面tab中的区县

    private List<LocalBean> mRvData; // 用来在recyclerview显示的数据
    private AddressAdapter mAdapter;   // recyclerview 的 adapter

    private ArrayList<LocalBean> mProAddressBean; // 总数据
    private ArrayList<LocalBean> mCityAddressBean; // 总数据
    private ArrayList<LocalBean> mAreaAddressBean; // 总数据
    private LocalBean mSelectProvice; //选中 省份 bean
    private LocalBean mSelectCity;//选中 城市  bean
    private LocalBean mSelectDistrict;//选中 区县  bean
    private int mSelectProvicePosition = 0; //选中 省份 位置
    private int mSelectCityPosition = 0;//选中 城市  位置
    private int mSelectDistrictPosition = 0;//选中 区县  位置
    public static final int TYPE_PROVINCE = 1;
    public static final int TYPE_CITY = 2;
    public static final int TYPE_AREA = 3;

    private OnAddressPickerSureListener mOnAddressPickerSureListener;
    private ImageView mTvSure; //确定

    public AddressPickerView(Context context) {
        super(context);
        init(context);
    }

    public AddressPickerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AddressPickerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化
     */
    private void init(Context context) {
        mContext = context;
        mRvData = new ArrayList<>();

        // UI
        View rootView = inflate(mContext, R.layout.address_picker_view, this);
        // 确定
        mTvSure = rootView.findViewById(R.id.tvSure);
        mTvSure.setOnClickListener(this);
        // tablayout初始化
        mTabLayout = (TabLayout) rootView.findViewById(R.id.tlTabLayout);
        mTabLayout.addTab(mTabLayout.newTab().setText(defaultProvince));
        mTabLayout.addTab(mTabLayout.newTab().setText(defaultCity));
        mTabLayout.addTab(mTabLayout.newTab().setText(defaultDistrict));

        mTabLayout.addOnTabSelectedListener(tabSelectedListener);

        // recyclerview adapter的绑定
        mRvList = (RecyclerView) rootView.findViewById(R.id.rvList);
        mRvList.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new AddressAdapter();
        mRvList.setAdapter(mAdapter);
        // 初始化默认的本地数据  也提供了方法接收外面数据
        mRvList.post(new Runnable() {
            @Override
            public void run() {
                initData();
            }
        });
    }


    /**
     * 初始化数据
     * 拿assets下的json文件
     */
    private void initData() {
        StringBuilder jsonSB = new StringBuilder();
        try {
            BufferedReader addressJsonStream = new BufferedReader(new InputStreamReader(mContext.getAssets().open("address.json")));
            String line;
            while ((line = addressJsonStream.readLine()) != null) {
                jsonSB.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 将数据转换为对象
        /*mYwpAddressBean = new Gson().fromJson(jsonSB.toString(), ConsigneeAddressBean.class);
        if (mYwpAddressBean != null) {
            mRvData.clear();
            mRvData.addAll(mYwpAddressBean.getProvince());
            mAdapter.notifyDataSetChanged();
        }*/
    }

    /**
     * 开放给外部传入数据
     * 暂时就用这个Bean模型，如果数据不一致就需要各自根据数据来生成这个bean了
     */
    public void initData(ArrayList<LocalBean> bean) {
        if (bean != null) {
            mSelectDistrict = null;
            mSelectCity = null;
            mSelectProvice = null;
            mTabLayout.getTabAt(0).select();

            mProAddressBean = bean;
            mRvData.clear();
            mRvData.addAll(bean);
            mAdapter.notifyDataSetChanged();

        }
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tvSure) {
            if (mOnAddressPickerSureListener != null) {
                mOnAddressPickerSureListener.onExit();
            }
        }
    }

    //点确定
    private void sure() {
        if (mSelectProvice != null &&
                mSelectCity != null &&
                mSelectDistrict != null) {
            //   回调接口
            if (mOnAddressPickerSureListener != null) {
                mOnAddressPickerSureListener.onSureClick(mSelectProvice.getRegionName() + " " + mSelectCity.getRegionName() + " " + mSelectDistrict.getRegionName() + " ",
                        mSelectProvice.getRegionId(), mSelectCity.getRegionId(), mSelectDistrict.getRegionId(), mSelectDistrict.getRegionId());
            }
        } else {
            Toast.makeText(mContext, "地址还没有选完整哦", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mProAddressBean = null;
        mAreaAddressBean = null;
        mCityAddressBean = null;
    }

    /**
     * TabLayout 切换事件
     */
    TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            mRvData.clear();
            switch (tab.getPosition()) {
                case 0:
                    mRvData.addAll(mProAddressBean);
                    mAdapter.notifyDataSetChanged();
                    // 滚动到这个位置
                    mRvList.smoothScrollToPosition(mSelectProvicePosition);
                    break;
                case 1:
                    // 点到城市的时候要判断有没有选择省份
                    /*if (mSelectProvice != null) {
                        for (LocalBean itemBean : mYwpAddressBean.getCity()) {
                            if (itemBean.getP().equals(mSelectProvice.getI()))
                                mRvData.add(itemBean);
                        }
                    } else {
                        Toast.makeText(mContext, "请您先选择省份", Toast.LENGTH_SHORT).show();
                    }*/
                    int oldContentSize = mProAddressBean.size();//获取刷新前数据长度
                    mRvData.clear();
                    mAdapter.notifyItemRangeRemoved(0, oldContentSize);
                    if (mCityAddressBean != null && mCityAddressBean.size() > 0) {
                        mRvData.addAll(mCityAddressBean);
                        mAdapter.notifyDataSetChanged();
                        // 滚动到这个位置
                        mRvList.smoothScrollToPosition(mSelectProvicePosition);
                        mAdapter.notifyDataSetChanged();
                    }
                    // 滚动到这个位置
                    mRvList.smoothScrollToPosition(mSelectCityPosition);
                    break;
                case 2:
                    mRvData.clear();
                    mAdapter.notifyDataSetChanged();
                    // 点到区的时候要判断有没有选择省份与城市
                    if (mAreaAddressBean != null && mAreaAddressBean.size() > 0) {
                        mRvData.addAll(mAreaAddressBean);
                        mAdapter.notifyDataSetChanged();
                        // 滚动到这个位置
                        mRvList.smoothScrollToPosition(mSelectProvicePosition);
                        mAdapter.notifyDataSetChanged();
                    }
//                    // 滚动到这个位置
                    mRvList.smoothScrollToPosition(mSelectDistrictPosition);
                    break;
            }


        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
        }
    };


    public void getDataSuccess(ArrayList<LocalBean> provinceBeans, int id) {
        if (id == TYPE_PROVINCE) {
            initData(provinceBeans);
        } else if (id == TYPE_CITY) {
            this.mCityAddressBean = provinceBeans;
            mRvData.clear();
            mRvData.addAll(provinceBeans);
            mAdapter.notifyDataSetChanged();
        } else if (id == TYPE_AREA) {
            this.mAreaAddressBean = provinceBeans;
            mRvData.clear();
            mRvData.addAll(provinceBeans);
            mAdapter.notifyDataSetChanged();
        }
    }



    /**
     * 下面显示数据的adapter
     */
    class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_address_text, parent, false));
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            final int tabSelectPosition = mTabLayout.getSelectedTabPosition();
            holder.mTitle.setText(mRvData.get(position).getRegionName());
            holder.mTitle.setTextColor(defaultUnSelectedColor);
            holder.mTitle.setTextSize(14);
            // 设置选中效果的颜色
            switch (tabSelectPosition) {
                case 0:
                    if (mRvData.get(position) != null &&
                            mSelectProvice != null &&
                            mRvData.get(position).getRegionName().equals(mSelectProvice.getRegionId())) {
                        holder.mTitle.setTextColor(defaultSelectedColor);
                    }
                    break;
                case 1:
                    if (mRvData.get(position) != null &&
                            mSelectCity != null &&
                            mRvData.get(position).getRegionName().equals(mSelectCity.getRegionId())) {
                        holder.mTitle.setTextColor(defaultSelectedColor);
                    }
                    break;
                case 2:
                    if (mRvData.get(position) != null &&
                            mSelectDistrict != null &&
                            mRvData.get(position).getRegionName().equals(mSelectDistrict.getRegionId())) {
                        holder.mTitle.setTextColor(defaultSelectedColor);
                    }
                    break;
            }
            // 设置点击之后的事件
            holder.mTitle.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 点击 分类别
                    switch (tabSelectPosition) {
                        case 0:
                            mSelectProvice = mRvData.get(position);

                            mOnAddressPickerSureListener.onRequstion(mSelectProvice.getRegionId(), TYPE_CITY);

                            // 清空后面两个的数据
                            mSelectCity = null;
                            mSelectDistrict = null;
                            mSelectCityPosition = 0;
                            mSelectDistrictPosition = 0;
                            mTabLayout.getTabAt(1).setText(defaultCity);
                            mTabLayout.getTabAt(2).setText(defaultDistrict);
                            // 设置这个对应的标题
                            mTabLayout.getTabAt(0).setText(mSelectProvice.getRegionName());
                            // 跳到下一个选择
                            mTabLayout.getTabAt(1).select();
                            mSelectProvicePosition = position;
                            break;
                        case 1:
                            mSelectCity = mRvData.get(position);

                            mOnAddressPickerSureListener.onRequstion(mSelectCity.getRegionId(), TYPE_AREA);
                            // 清空后面一个的数据
                            mSelectDistrict = null;
                            mSelectDistrictPosition = 0;
                            mTabLayout.getTabAt(2).setText(defaultDistrict);
                            // 设置这个对应的标题
                            mTabLayout.getTabAt(1).setText(mSelectCity.getRegionName());
                            // 跳到下一个选择
                            mTabLayout.getTabAt(2).select();
                            mSelectCityPosition = position;
                            break;
                        case 2:
                            mSelectDistrict = mRvData.get(position);
                            // 没了，选完了，这个时候可以点确定了
                            mTabLayout.getTabAt(2).setText(mSelectDistrict.getRegionName());
                            notifyDataSetChanged();
                            mSelectDistrictPosition = position;

                            sure();
                            break;
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mRvData.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView mTitle;

            ViewHolder(View itemView) {
                super(itemView);
                mTitle = (TextView) itemView.findViewById(R.id.itemTvTitle);
            }

        }
    }


    /**
     * 点确定回调这个接口
     */
    public interface OnAddressPickerSureListener {
        void onSureClick(String address, String provinceCode, String cityCode, String districtCode, String zipCode);

        void onExit();

        void onRequstion(String parentId, int localType);
    }

    public void setOnAddressPickerSure(OnAddressPickerSureListener listener) {
        this.mOnAddressPickerSureListener = listener;
        mOnAddressPickerSureListener.onRequstion("1",TYPE_PROVINCE);
    }


}
