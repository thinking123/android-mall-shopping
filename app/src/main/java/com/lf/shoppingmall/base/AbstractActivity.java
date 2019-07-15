package com.lf.shoppingmall.base;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunlan.baseview.R;
import com.lr.baseview.utils.ComConstant;
import com.lf.shoppingmall.utils.LogUtils;
import com.lr.baseview.utils.ToastUtils;

/**
 * 基类Activity，主界面Activity直接从该类派生
 *
 * @author liushubao
 *         Created by admin on 2017/3/20.
 *         <p>
 *         TODO 加载框
 */
public abstract class AbstractActivity extends FragmentActivity implements View.OnClickListener {

    protected String TAG = AbstractActivity.class.getSimpleName();
    protected Context context = this;
    private CustomReceiver customReceiver = null;
//    protected ProgressDialog mPdLoading;//加载框

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customReceiver = new CustomReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ComConstant.CLOSE_ACTION);
        context.registerReceiver(customReceiver, filter);
        setContentView(getLayoutId());
        initTitleBar();
        initView();
    }

    /**
     * 布局id
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化view操作
     */
    protected abstract void initView();

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(customReceiver);
    }

    protected void broadcastActivity(Intent intent) {

    }

    /**
     * 顶部工具栏实现接口
     */
    protected void initTitleBar() {
        ImageView iv_back = (ImageView) this.findViewById(R.id.iv_back);
        TextView tv_title = (TextView) this.findViewById(R.id.tv_title);
        TextView tv_right = (TextView) this.findViewById(R.id.tv_right);
        ImageView iv_right = (ImageView) this.findViewById(R.id.iv_right);
        if (iv_back != null) {
            iv_back.setOnClickListener(this);
        }
        if (tv_title != null) {
            tv_title.setText(getCharSequenceText());
        }
//        if (tv_right != null) {
//            tv_right.setOnClickListener(this);
//        }
//        if (iv_right != null) {
//            iv_right.setOnClickListener(this);
//        }
        setTitleBarStatus(iv_back, tv_title, tv_right, iv_right);
    }

    protected void setTitleBarStatus(ImageView iv_back, TextView tv_title, TextView tv_right, ImageView iv_right) {
    }


    private CharSequence getCharSequenceText() {
        String title = "";
        Object obj = getTitleText();
        if (obj instanceof String) {
            title = (String) obj;
        } else if (obj instanceof Integer) {
            title = getResources().getString((Integer) obj);
        }
        return title;
    }


    /**
     * 设置标题 ，有标题栏的情况下使用
     *
     * @return
     */
    protected abstract Object getTitleText();

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            back();
        }
    }

    /**
     * 返回上一页方法
     */
    protected void back() {
        finish();
    }

    /**
     * 显示提示信息
     *
     * @param msg
     */
    public void showToast(int msg) {
        ToastUtils.showToastResId(context, msg);
    }

    public void showToast(String msg) {
        ToastUtils.showToastResId(context, msg);
    }

    /**
     * 判断是否需要隐藏键盘
     *
     * @param v
     * @param event
     * @return
     */
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 点击非获取焦点EditText隐藏键盘
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    protected boolean isValidContext(Context context) {
        Activity a = (Activity) context;
        if (a.isFinishing()) {
            LogUtils.d(TAG, "Activity is invalid. isFinishing-->" + a.isFinishing());
            return false;
        } else {
            return true;
        }
    }

    private class CustomReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            broadcastActivity(intent);
        }
    }

    /**
     * APP字体大小不跟随系统更改
     *
     * @return
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

}
