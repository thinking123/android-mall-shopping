package com.lf.shoppingmall.base;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lf.shoppingmall.R;
import com.lf.shoppingmall.activity.main.MainActivity;
import com.lr.baseview.utils.ToastUtils;

/**
 * fragment的基类
 * @author liushubao
 *   Created by admin on 2017/3/21.
 *   TODO 加载框
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    protected long lastClickTime = 0;
    protected final int SUCCESS = 0;
    protected final int TIME_INTERVAL = 500;
    protected boolean isVisible;
    /** 标志位，标志已经初始化完成 */
    protected boolean isPrepared;
    /** 是否已被加载过一次，第二次就不再去请求数据了 */
    protected boolean isLoaded;
    protected Context context;
//    protected ProgressDialog mPdLoading;//加载框
protected Dialog mydialog;

    /**
     * 显示提示信息
     *
     * @param msg
     */
    public void showToast(int msg) {
        ToastUtils.showToastResId(getActivity(), msg);
    }
    /**
     * 显示提示信息
     *
     * @param msg
     */
    public void showToast(String msg) {
        ToastUtils.showToast(getActivity(), msg);
    }

    /**
     * 判断上下文是否有效
     *
     * @param context
     * @return
     */
    protected boolean isValidContext(Context context) {
        FragmentActivity a = (FragmentActivity) context;
        if (a.isFinishing()) {
            return false;
        } else {
            return true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 可见
     */
    protected void onVisible() {
        lazyLoad();
    }

    /**
     * 不可见
     */
    protected void onInvisible() {

    }

    /**
     * 延迟加载 子类必须重写此方法
     * （一般对isLoaded 、 isPrepared进行判断）
     */
    protected void lazyLoad() {
    }

    @Override
    public void onClick(View v) {

    }

    public boolean isLoaded() {
        return isLoaded;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        init();
        View view = inflater.inflate(getLayoutId(), container, false);
        initView(view);
        isPrepared = true;
        return view;
    }

    /**
     * 加载界面前的初始化操作
     */
    protected abstract void init();

    /**
     * 获取布局id
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化控件等操作
     * @param view
     */
    protected abstract void initView(View view);

    protected void showLoading(String msg){
//        mPdLoading = ProgressDialog.show(context, TextUtils.isEmpty(msg) ? "请稍候..." : msg, msg);
        if (getActivity() == null || !isAdded()) {
            return;
        }
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog_loading, null);
        TextView tv = (TextView) layout.findViewById(R.id.txt_text);
        ImageView progressBar1 = (ImageView) layout.findViewById(R.id.progressBar1);
        RelativeLayout dialog_rl = (RelativeLayout) layout.findViewById(R.id.dialog_rl);
        if (!TextUtils.isEmpty(msg)) {
            tv.setText(msg);
        }
        Glide.with(this).load(R.drawable.gif_loading).diskCacheStrategy(DiskCacheStrategy.ALL).into(progressBar1);
        if (mydialog == null) {
            mydialog = new Dialog(getActivity(), R.style.loadingDialog);
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

    protected void dissmissLoading(){
//        if (mPdLoading != null){
//            mPdLoading.dismiss();
//        }
        if (null != mydialog) {
            mydialog.dismiss();
        }
    }
}
