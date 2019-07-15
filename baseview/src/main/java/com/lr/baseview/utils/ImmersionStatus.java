package com.lr.baseview.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.lang.reflect.Field;

/**
 * @author liushubao
 *         Created by admin on 2017/3/20.
 * 描述：获取状态栏高度，设置沉浸式布局
 */
public class ImmersionStatus {

    private static ImmersionStatus instance = null;

    public static ImmersionStatus getInstance() {
        if (instance == null) {
            instance = new ImmersionStatus();
        }
        return instance;
    }

    /**
     * android 4.4(API19过后就是沉浸式的了)
     * 沉浸式： true; 否则： false
     *
     * @TargetApi(19)
     */
    public boolean isImmerseLayout(Context mContext) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            ((Activity) mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            return true;
        }
        return false;
    }

//    /**
//     * android 6.0后可设置状态栏字体颜色
//     *
//     * @param darkMode 状态栏背景是否为深色
//     * @TargetApi(23)
//     */
//    public void setStateTextColor(Context mContext, boolean darkMode) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //透明状态栏
//            ((Activity) mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                int flag = ((Activity) mContext).getWindow().getDecorView().getSystemUiVisibility();
//                if (!darkMode) {
//                    flag |= (WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
//                            | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//                } else {
//                    flag |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//                }
//                //View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//                ((Activity) mContext).getWindow().getDecorView().setSystemUiVisibility(flag);
//            }
//        }
//    }

    /**
     * 获取状态栏高度
     *
     * @param mContext 上下文
     * @return 状态栏高度
     */
    public int getStatusHeight(Context mContext) {
        //获取状态栏的高度
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int size = 0, statusHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            size = Integer.parseInt(field.get(obj).toString());
            statusHeight = mContext.getResources().getDimensionPixelSize(size);
            Log.i("onCreate", "---statusHeight" + statusHeight);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 设置图片状态栏，使图片和状态栏融为一体
     * 设置头布局的padding
     *
     * @param mContext
     * @param view
     */
    public void setHeadPadding(Context mContext, View view) {
        if (instance.isImmerseLayout(mContext)) {
            int statusHeight = instance.getStatusHeight(mContext);
            view.setPadding(0, statusHeight, 0, 0);
        } else {
            view.setPadding(0, 0, 0, 0);
        }
    }

    /**
     * 设置顶部为文字状的态栏颜色，使用此方法时，需在布局文件的根控件里面设置 fitsSystemWindow="true" 属性
     *
     * @param mActivity
     * @param colorId
     */
    public void setStateColor(Activity mActivity, int colorId) {
        if (instance.isImmerseLayout(mActivity)) {
            SystemBarTintManagerHelper tintManager = new SystemBarTintManagerHelper(mActivity);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(colorId);
        }
    }

    /**
     * 设置顶部为文字状的态栏颜色，使用此方法时，需在布局文件的根控件里面设置 fitsSystemWindow="true" 属性
     *
     * @param mActivity
     * @param color
     */
    public void setStateColorValue(Activity mActivity, int color,float alpha) {
        if (instance.isImmerseLayout(mActivity)) {
            SystemBarTintManagerHelper tintManager = new SystemBarTintManagerHelper(mActivity);
            tintManager.setStatusBarTintEnabled(true);
//            tintManager.setStatusBarAlpha(alpha);
            tintManager.setStatusBarTintColor(color);
        }
    }

    /**
     * android 4.4(API19过后就是沉浸式的了)
     * 设置顶部为图片的状态栏沉浸式
     *
     * @param resId 顶部控件的id
     * @TargetApi(19)
     */
    public void setImmerseLayout(Activity mContext, int resId) {
        View view = mContext.findViewById(resId);
        ImmersionStatus.getInstance().setHeadPadding(mContext, view);
    }

}
