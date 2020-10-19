package com.lzyyd.hsq.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzyyd.hsq.BR;
import com.lzyyd.hsq.R;
import com.lzyyd.hsq.adapter.CcqAdapter;
import com.lzyyd.hsq.base.AppViewModelFactory;
import com.lzyyd.hsq.base.BaseFragment;
import com.lzyyd.hsq.base.ProApplication;
import com.lzyyd.hsq.bean.CcqListBean;
import com.lzyyd.hsq.databinding.FragmentCcqBinding;
import com.lzyyd.hsq.util.QRCodeUtil;
import com.lzyyd.hsq.viewmodel.CcqViewModel;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Create by liguo on 2020/8/27
 * Describe:
 */
public class CcqFragment extends BaseFragment<FragmentCcqBinding, CcqViewModel> implements CcqViewModel.CcqListCallBack, CcqAdapter.OnItemClick {

    private int status = 0;
    private CcqAdapter ccqAdapter;

    public CcqFragment(int position){
        this.status = position;
    }

    @Override
    public int initVariableId() {
        return BR.ccq;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public CcqViewModel initViewModel() {
        AppViewModelFactory appViewModelFactory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this,appViewModelFactory).get(CcqViewModel.class);
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_ccq;
    }

    @Override
    public void initData() {
        viewModel.setListener(this);
        viewModel.getCcqList("1","80",status+"", ProApplication.SESSIONID());
    }

    @Override
    public void initViewObservable() {

    }

    @Override
    public void getCcqListBeanSuccess(ArrayList<CcqListBean> ccqListBeans) {

        if (binding.refreshLayout != null && binding.refreshLayout.isRefreshing()){
            binding.refreshLayout.setRefreshing(false);
        }

        if (ccqAdapter == null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

            ccqAdapter = new CcqAdapter(getActivity(), this);

            ccqAdapter.getItems().addAll(ccqListBeans);

            binding.ccqRv.setLayoutManager(linearLayoutManager);
            binding.ccqRv.setAdapter(ccqAdapter);
            binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    viewModel.getCcqList("1", "80", status + "", ProApplication.SESSIONID());
                }
            });
        }else {
            ccqAdapter.getItems().clear();
            ccqAdapter.getItems().addAll(ccqListBeans);
        }
    }

    @Override
    public void getCcqListBeanFail(String msg) {
    }

    @Override
    public void getQrcode(String orderId) {
        Dialog dialog = new Dialog(getActivity());
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        //设置dialog的宽高为屏幕的宽高
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, height);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_qrcode,null);
        ImageView imageView = view.findViewById(R.id.iv_qrcode);
        TextView btn_exit = view.findViewById(R.id.btn_exit);

        /*Bitmap mBitmap = null;
        try {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
            mBitmap = CodeCreator.createQRCode(orderId, 400, 400, getBitmap(getActivity(),R.mipmap.ic_launcher));
        } catch (WriterException e) {
            e.printStackTrace();
        }*/
        Bitmap mBitmap = QRCodeUtil.createQRCodeBitmap(orderId, 200, 200, BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher1));
        imageView.setImageBitmap(mBitmap);
        dialog.setContentView(view);
        dialog.show();

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                viewModel.getCcqList("1","80",status+"", ProApplication.SESSIONID());
            }
        });

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }



    private static Bitmap getBitmap(Context context, int vectorDrawableId) {
        Bitmap bitmap=null;
        if (Build.VERSION.SDK_INT> Build.VERSION_CODES.LOLLIPOP){
            Drawable vectorDrawable = context.getDrawable(vectorDrawableId);
            bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                    vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            vectorDrawable.draw(canvas);
        }else {
            bitmap = BitmapFactory.decodeResource(context.getResources(), vectorDrawableId);
        }
        return bitmap;
    }


}
