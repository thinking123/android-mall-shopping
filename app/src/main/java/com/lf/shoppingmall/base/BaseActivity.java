package com.lf.shoppingmall.base;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lf.shoppingmall.R;
import com.lf.shoppingmall.activity.main.MainActivity;
import com.lr.baseview.utils.ComConstant;

/**
 * 抽象基类Acitivity，从AbstractActivity集成
 *
 * @author liushubao
 *         Created by admin on 2017/3/21.
 */
public abstract class BaseActivity extends AbstractActivity {

    protected Dialog mydialog;

    @Override
    protected void broadcastActivity(Intent intent) {
        if (ComConstant.CLOSE_ACTION.equals(intent.getAction())) {
            finish();
        }
    }

    public BaseActivity getActivity() {
        return this;
    }


    protected void showLoading(String msg) {
//        if (mPdLoading != null && mPdLoading.isShowing()) {
//            return;
//        }
//        mPdLoading = ProgressDialog.show(context, null, msg);

        if (!this.isFinishing()) {
            LayoutInflater inflater = this.getLayoutInflater();
            View layout = inflater.inflate(R.layout.dialog_loading, null);
			TextView tv = (TextView) layout.findViewById(R.id.txt_text);
			ImageView progressBar1 = (ImageView) layout.findViewById(R.id.progressBar1);
//			RelativeLayout dialog_rl = (RelativeLayout) layout.findViewById(com.yunlan.baseview.R.id.dialog_rl);
			if (!TextUtils.isEmpty(msg)) {
				tv.setText(msg);
			}
            Glide.with(this).load(R.drawable.gif_loading).diskCacheStrategy(DiskCacheStrategy.ALL).into(progressBar1);
            if (mydialog == null) {
                mydialog = new Dialog(this, R.style.loadingDialog);
            }
            mydialog.setContentView(layout);
            mydialog.setCanceledOnTouchOutside(false);
            if (!mydialog.isShowing()) {
                mydialog.show();
            }

            // 屏蔽主页加载对话框显示时的返回键
            if (getActivity() instanceof MainActivity) {
                mydialog.setCancelable(false);
            }
            mydialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    // 屏蔽主页加载对话框显示时的返回键
                    if (getActivity() instanceof MainActivity) {
                        return true;
                    }
                    // 点击非主页（ActivityMain）界面的返回键，都结束当前Activity
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        dissmissLoading();
                        getActivity().finish();
                    }
                    return true;
                }
            });
        }
    }

    protected void dissmissLoading() {
        try {
            if (null != mydialog && mydialog.isShowing()) {
                mydialog.dismiss();
                mydialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}