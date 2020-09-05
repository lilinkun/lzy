package com.lzyyd.hsq.ui;

import android.content.Context;
import android.widget.TextView;

import com.lzyyd.hsq.R;

import androidx.appcompat.app.AppCompatDialog;
import cn.bingoogolapple.progressbar.BGAProgressBar;

/**
 * Create by liguo on 2020/9/5
 * Describe:
 */
public class DownloadingDialog extends AppCompatDialog {
    private BGAProgressBar mProgressBar;
    private TextView textView;

    public DownloadingDialog(Context context) {
        super(context, R.style.AppDialogTheme);
        setContentView(R.layout.dialog_downloading);
        mProgressBar = (BGAProgressBar) findViewById(R.id.pb_downloading_content);
        setCancelable(false);
        textView = (TextView) findViewById(R.id.tv_update_message);
    }

    public void setProgress(long progress, long maxProgress) {
        mProgressBar.setMax((int) maxProgress / 100);
        mProgressBar.setProgress((int) progress / 100);
    }

    public void setUpdateMessage(String message) {
        textView.setText(message);
    }

    @Override
    public void show() {
        super.show();
        mProgressBar.setMax(100);
        mProgressBar.setProgress(0);
    }
}