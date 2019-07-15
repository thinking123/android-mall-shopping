package com.lr.baseview.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yunlan.baseview.R;

/**
 * ToastUtils
 *
 * @author liushubao
 *         Created by admin on 2017/3/13.
 */

public class ToastUtils {

    public static Toast toast;

    /**
     * 显示提示信息
     *
     * @param context
     * @param gravity
     * @param msg
     */
    public static void showToast(Context context, int gravity, int x, int y, String msg) {
        if (context != null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.base_view_toast_layout, null);

            TextView text = (TextView) layout.findViewById(R.id.text);
            text.setText(msg);
            if (toast == null) {
                toast = new Toast(context);
                toast.setGravity(gravity, x, y);
                toast.setDuration(Toast.LENGTH_SHORT);
            }
            toast.setView(layout);
            toast.show();
        }
    }

    /**
     * 显示提示信息
     *
     * @param context
     * @param msg
     */
    public static void showToast(Context context, String msg) {
        if (context != null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.base_view_toast_layout, null);

            TextView text = (TextView) layout.findViewById(R.id.text);
            text.setText(msg);
            if (toast == null) {
                toast = new Toast(context);
            }
            toast.setDuration(Toast.LENGTH_SHORT);

            toast.setView(layout);
            toast.show();
        }
    }

    /**
     * 显示资源文件string.xml里面的字段
     *
     * @param context 上下文
     * @param resId   消息资源
     */
    public static void showToastResId(Context context, int resId) {
        showToast(context, context.getApplicationContext().getResources().getString(resId));
    }

    /**
     * 显示资源文件string.xml里面的字段
     *
     * @param context 上下文
     * @param msg   消息资源
     */
    public static void showToastResId(Context context, String msg) {
        showToast(context, msg);
    }

}
