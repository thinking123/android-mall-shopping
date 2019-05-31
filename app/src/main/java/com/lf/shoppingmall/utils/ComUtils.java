package com.lf.shoppingmall.utils;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.lf.shoppingmall.activity.login.LoginActivity;
import com.lf.shoppingmall.base.BaseActivity;
import com.lf.shoppingmall.base.BaseApplication;
import com.lf.shoppingmall.bean.UserVo;
import com.lf.shoppingmall.common.ComParams;
import com.lr.baseview.common.LoginSucListener;

import java.io.IOException;
import java.util.UUID;

/**
 *
 * Created by Administrator on 2017/8/3.
 */

public class ComUtils {

    /**
     * 检测登录状态
     *
     * @param activity
     */
    public static void checkLoginStatus(BaseActivity activity, LoginSucListener sucListener, int result) {
        if (ComParams.login_status == -1) {
            UserVo userVo = ((BaseApplication) activity.getApplication()).getUserVo();
            ComParams.login_status = userVo == null ? 0 : 1;
        }
        if (ComParams.login_status == 0) {//未登录
            Intent intent = new Intent();
            intent.setClass(activity, LoginActivity.class);
            if (result >= 0) {
                activity.startActivityForResult(intent, result);
            } else {
                activity.startActivity(intent);
                activity.finish();
            }

        } else if (sucListener != null) {//已登录后可以的操作
            sucListener.onLoginSucClick();
        }
    }

    /**
     * 获取设别UUID
     */
    public static String getUUID(Context context) {
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, tmPhone, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        return uniqueId;
    }

    /**
     * 隐藏软件盘
     *
     * @param context : Context实例
     * @param view    : view控件
     */
    public static void hideSoftKeyboard(Context context, View view) {
        try {
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            IBinder windowToken = view.getWindowToken();
            if (null != inputManager && null != windowToken) {
                inputManager.hideSoftInputFromWindow(windowToken, 0);
            }
        } catch (Exception e) {
        }
    }


    public static String getOrderStatus(int status) {
        switch (status) {
            case 0:
                return "待配货";

            case 1:
                return "待发货";

            case 2:
                return "待收货";

            case 3:
                return "已完成";

            case 4:
                return "已取消";
        }
        return "待配货";
//        订单状态 0 待配货 1 待发货，2待收货，3已完成，4已取消
    }

    public static void promotePermission(String filePath) {
        String command = "chmod 777 " + filePath;
        Runtime runtime = Runtime.getRuntime();

        try {
            runtime.exec(command);
        } catch (IOException var4) {
            var4.printStackTrace();
        }
    }
}