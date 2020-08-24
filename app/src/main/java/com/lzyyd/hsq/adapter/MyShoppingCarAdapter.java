package com.lzyyd.hsq.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzyyd.hsq.R;
import com.lzyyd.hsq.activity.GoodsDetailActivity;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.CartBean;
import com.lzyyd.hsq.bean.CartChildBean;
import com.lzyyd.hsq.bean.OrderBean;
import com.lzyyd.hsq.bean.OrderGroupBean;
import com.lzyyd.hsq.bean.StoreInfo;
import com.lzyyd.hsq.util.UToast;
import com.lzyyd.hsq.util.UtilTool;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;

import androidx.appcompat.app.AlertDialog;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.goldze.mvvmhabit.utils.StringUtils;

/**
 * Create by liguo on 2020/8/15
 * Describe:
 */
public class MyShoppingCarAdapter extends BaseExpandableListAdapter {

    private ArrayList<OrderGroupBean<ArrayList<CartBean>>> groups;
    private Map<String,ArrayList<CartChildBean>> map;
    private ArrayList<CartChildBean> orderChildBeans;
    private Context mcontext;
    private CheckInterface checkInterface;
    private ModifyCountInterface modifyCountInterface;
    private GroupEditorListener groupEditorListener;
    private int count = 0;
    private int groupClickId = -1;
    boolean isCheck = false;
    public static final int child_goods_result = 0x3233;
    Activity activity;

    public void setGroupClickId(int groupid,boolean isCheck){
        this.groupClickId = groupid;
        this.isCheck = isCheck;
    }

    public MyShoppingCarAdapter(ArrayList<OrderGroupBean<ArrayList<CartBean>>> groups, Map<String,ArrayList<CartChildBean>> map, Context mcontext, Activity activity) {
        this.groups = groups;
        this.mcontext = mcontext;
        this.map = map;
        this.activity = activity;
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        orderChildBeans = map.get(groups.get(groupPosition).getOrderListBean().getStoreId());
        return orderChildBeans.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        orderChildBeans = map.get(groups.get(groupPosition).getOrderListBean().getStoreId());
        return orderChildBeans.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = View.inflate(mcontext, R.layout.adapter_store_cart, null);
            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
//        final StoreInfo group = (StoreInfo) getGroup(groupPosition);
        final OrderGroupBean<ArrayList<OrderBean>> group = (OrderGroupBean<ArrayList<OrderBean>>)getGroup(groupPosition);
        groupViewHolder.storeName.setText(group.getOrderListBean().getStoreName());
        groupViewHolder.storeCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                group.setChoosed(((CheckBox) v).isChecked());
                checkInterface.checkGroup(groupPosition, ((CheckBox) v).isChecked());
            }
        });
        groupViewHolder.storeCheckBox.setChecked(group.isChoosed());

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = View.inflate(mcontext, R.layout.item_shopcat_product, null);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

        if (childPosition == groups.get(groupPosition).getOrderListBean().getListGoods().size()){
            childViewHolder.itemview.setBackgroundDrawable(mcontext.getResources().getDrawable(R.drawable.shape_cart_bottom));
        }

        final CartChildBean child = (CartChildBean) getChild(groupPosition, childPosition);
        if (child != null) {
            childViewHolder.goodsName.setText(child.getOrderBean().getGoodsName());
            childViewHolder.goodsPrice.setText("¥" + child.getOrderBean().getPrice() + "");
            childViewHolder.goodsNum.setText(String.valueOf(child.getOrderBean().getNum()));
            if (child.getOrderBean().getSpec1() != null && !StringUtils.isEmpty(child.getOrderBean().getSpec1())) {
                childViewHolder.goods_size1.setText(child.getOrderBean().getGoodsSpec1() + ":" + child.getOrderBean().getSpec1());
            }
            if (child.getOrderBean().getSpec2() != null && !StringUtils.isEmpty(child.getOrderBean().getSpec2())) {
                childViewHolder.goods_size2.setText( "," + child.getOrderBean().getGoodsSpec2() + ":" + child.getOrderBean().getSpec2());
            }

            Picasso.with(mcontext).load(ProApplication.HEADIMG + child.getOrderBean().getGoodsImg()).into(childViewHolder.goodsImage);

            /*if (groupClickId != -1){
                if(groupPosition == groupClickId){
                    childViewHolder.singleCheckBox.setChecked(isCheck);
                }
            }*/
            childViewHolder.singleCheckBox.setChecked(child.isChoosed());
            childViewHolder.singleCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    child.setChoosed(((CheckBox) v).isChecked());
                    childViewHolder.singleCheckBox.setChecked(((CheckBox) v).isChecked());
                    checkInterface.checkChild(groupPosition, childPosition, ((CheckBox) v).isChecked());
                }
            });
            childViewHolder.increaseGoodsNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    modifyCountInterface.doIncrease(groupPosition, childPosition, childViewHolder.goodsNum, childViewHolder.singleCheckBox.isChecked());
                }
            });
            childViewHolder.reduceGoodsNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    modifyCountInterface.doDecrease(groupPosition, childPosition, childViewHolder.goodsNum, childViewHolder.singleCheckBox.isChecked());
                }
            });
            childViewHolder.goodsNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(groupPosition,childPosition,childViewHolder.goodsNum,childViewHolder.singleCheckBox.isChecked(),child);
                }
            });

        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                    if (ActivityUtil.activityList.size() > 3) {
//                        ActivityUtil.removeOldActivity();
//                    }

                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("goodsid", child.getOrderBean().getGoodsId());
                    bundle.putString("type", "cart");
                    intent.putExtra("TYPEID",bundle);
                    intent.setClass(activity,GoodsDetailActivity.class);
                    mcontext.startActivity(intent);

            }
        });
        return convertView;
    }

    /**
     * @param groupPosition
     * @param childPosition
     * @param showCountView
     * @param isChecked
     */
    private void showDialog(final int groupPosition, final int childPosition, final View showCountView, final  boolean isChecked,final  CartChildBean child) {
        final AlertDialog.Builder alertDialog_Builder=new AlertDialog.Builder(mcontext);
        View view= LayoutInflater.from(mcontext).inflate(R.layout.dialog_change_num,null);
        final AlertDialog dialog=alertDialog_Builder.create();
        dialog.setView(view);//errored,这里是dialog，不是alertDialog_Buidler
        count=Integer.valueOf(child.getOrderBean().getNum());
        final EditText num= (EditText) view.findViewById(R.id.dialog_num);
        num.setText(count+"");
        //自动弹出键盘
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                UtilTool.showKeyboard(mcontext,showCountView);
            }
        });
        final TextView increase= (TextView) view.findViewById(R.id.dialog_increaseNum);
        final TextView DeIncrease=(TextView)view.findViewById(R.id.dialog_reduceNum);
        final TextView pButton= (TextView) view.findViewById(R.id.dialog_Pbutton);
        final TextView nButton= (TextView) view.findViewById(R.id.dialog_Nbutton);
        nButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        pButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number= Integer.parseInt(num.getText().toString().trim());
                if(number==0){
                    dialog.dismiss();
                }else{
                    if (number > child.getOrderBean().getGoodsNumber()){
                        UToast.show(mcontext,"库存不足");
                    }else {
                        num.setText(String.valueOf(number));
                        child.getOrderBean().setNum(number);
                        modifyCountInterface.doUpdate(groupPosition, childPosition, showCountView, isChecked,count);
                        dialog.dismiss();
                    }
                }
            }
        });
        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                num.setText(String.valueOf(count));
            }
        });
        DeIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count>1){
                    count--;
                    num.setText(String.valueOf(count));
                }
            }
        });
        dialog.show();
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    public GroupEditorListener getGroupEditorListener() {
        return groupEditorListener;
    }

    public void setGroupEditorListener(GroupEditorListener groupEditorListener) {
        this.groupEditorListener = groupEditorListener;
    }

    public CheckInterface getCheckInterface() {
        return checkInterface;
    }

    public void setCheckInterface(CheckInterface checkInterface) {
        this.checkInterface = checkInterface;
    }

    public ModifyCountInterface getModifyCountInterface() {
        return modifyCountInterface;
    }

    public void setModifyCountInterface(ModifyCountInterface modifyCountInterface) {
        this.modifyCountInterface = modifyCountInterface;
    }


    static class GroupViewHolder {
        @BindView(R.id.store_checkBox)
        CheckBox storeCheckBox;
        @BindView(R.id.store_name)
        TextView storeName;

        public GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }

    /**
     * 店铺的复选框
     */
    public interface CheckInterface {
        /**
         * 组选框状态改变触发的事件
         *
         * @param groupPosition 组元素的位置
         * @param isChecked     组元素的选中与否
         */
        void checkGroup(int groupPosition, boolean isChecked);

        /**
         * 子选框状态改变触发的事件
         *
         * @param groupPosition 组元素的位置
         * @param childPosition 子元素的位置
         * @param isChecked     子元素的选中与否
         */
        void checkChild(int groupPosition, int childPosition, boolean isChecked);
    }


    /**
     * 改变数量的接口
     */
    public interface ModifyCountInterface {
        /**
         * 增加操作
         *
         * @param groupPosition 组元素的位置
         * @param childPosition 子元素的位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked);

        void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked);

        void doUpdate(int groupPosition, int childPosition, View showCountView, boolean isChecked,int num);

        /**
         * 删除子Item
         *
         * @param groupPosition
         * @param childPosition
         */
        void childDelete(int groupPosition, int childPosition);
    }

    /**
     * 监听编辑状态
     */
    public interface GroupEditorListener {
        void groupEditor(int groupPosition);
    }

    /**
     * 使某个小组处于编辑状态
     */
    private class GroupViewClick implements View.OnClickListener {
        private StoreInfo group;
        private int groupPosition;
        private TextView editor;

        public GroupViewClick(StoreInfo group, int groupPosition, TextView editor) {
            this.group = group;
            this.groupPosition = groupPosition;
            this.editor = editor;
        }

        @Override
        public void onClick(View v) {
            if (editor.getId() == v.getId()) {
                groupEditorListener.groupEditor(groupPosition);
                if (group.isEditor()) {
                    group.setEditor(false);
                } else {
                    group.setEditor(true);
                }
                notifyDataSetChanged();
            }
        }
    }


    static class ChildViewHolder {
        @BindView(R.id.single_checkBox)
        CheckBox singleCheckBox;
        @BindView(R.id.goods_image)
        ImageView goodsImage;
        @BindView(R.id.goods_name)
        TextView goodsName;
        @BindView(R.id.goods_spec1)
        TextView goods_size1;
        @BindView(R.id.goods_spec2)
        TextView goods_size2;
        @BindView(R.id.goods_price)
        TextView goodsPrice;
        @BindView(R.id.goods_data)
        RelativeLayout goodsData;
        @BindView(R.id.reduce_goodsNum)
        TextView reduceGoodsNum;
        @BindView(R.id.tv_goods_count)
        TextView goodsNum;
        @BindView(R.id.increase_goods_Num)
        TextView increaseGoodsNum;
        @BindView(R.id.itemview)
        LinearLayout itemview;

        public ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}