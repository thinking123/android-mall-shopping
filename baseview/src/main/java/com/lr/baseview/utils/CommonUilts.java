package com.lr.baseview.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.lr.baseview.widget.AlertDialog;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.ContentValues.TAG;

/**
 * 常用工具类
 * @author liushubao
 * Created by admin on 2017/3/13.
 */

public class CommonUilts {

    /**
     * 当前网络是否可用
     *
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        boolean isAvailable = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            isAvailable = true;
            Log.v(TAG, "当前使用网络名称：" + info.getTypeName());
        } else {
            Log.v(TAG, "当前没有可用网络");
        }
        return isAvailable;
    }

    /**
     * MD5加密
     *
     * @param str
     *            待加密字符串
     * @return
     */
    @SuppressLint("DefaultLocale")
    public static String md5(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] bytes = messageDigest.digest();
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < bytes.length; i++) {
            if (Integer.toHexString(0xFF & bytes[i]).length() == 1) {
                sb.append("0").append(Integer.toHexString(0xFF & bytes[i]));
            } else {
                sb.append(Integer.toHexString(0xFF & bytes[i]));
            }
        }
        // 32位加密，从第0位到31位
        return sb.substring(0, 32).toString().toLowerCase();
    }

    /**
     * 检测Sdcard是否存在
     *
     * @return
     */
    public static boolean isExitsSdcard() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }

    /**
     * 两位小数的显示格式
     *
     * @param number
     *            小数类型的参数
     * @return
     */
    public static String getDoubleTwo(double number) {
        DecimalFormat df = new DecimalFormat("0.00");
        return String.valueOf(df.format(number));
    }

    /**
     * 拨打电话弹窗
     */
    public static void showTellDialog(final Activity activity, final String tellNumber) {
//        if (!TextUtils.isEmpty(tellNumber)) {/*if (!isPhone(tellNumber)) {*/
//            ToastUtils.showToast(activity, "电话号码格式不正确");
//            return;
//        }
//        new AlertDialog(activity).builder().setTitle("提示").setMsg("是否拨打" + tellNumber + "?")
//                .setNegativeButton("", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                })
//                .setPositiveButton("", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                            ToastUtils.showToast(activity, "请打开通话权限，否则无法直接拨打该号码");
//                            return;
//                        }
//                        activity.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tellNumber)));
//                    }
//                }).show();

        if (TextUtils.isEmpty(tellNumber)){
            ToastUtils.showToast(activity, "电话号码不可以为空!");
            return;
        }
        Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+tellNumber));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    public static final String IDCARD = "((11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62|63|64|65)[0-9]{4})" + "(([1|2][0-9]{3}[0|1][0-9][0-3][0-9][0-9]{3}"
            + "[Xx0-9])|([0-9]{2}[0|1][0-9][0-3][0-9][0-9]{3}))";
    public static final String PHONE = "1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}";

    /**
     * 验证手机号
     *
     * @param str
     * @return
     */
    public static boolean isPhone(String str) {
        if (TextUtils.isEmpty(str))
            return false;
        if (str.trim().length() > 7 && str.trim().length() < 16) {
            return Regular(str, PHONE);
        } else {
            return false;
        }
    }
    /**
     * 验证身份证号码
     *
     * @param str
     * @return
     */
    public static boolean isIdCard(String str) {
        if (TextUtils.isEmpty(str))
            return false;
        if (str.trim().length() == 15 || str.trim().length() == 18) {
            return Regular(str, IDCARD);
        } else {
            return false;
        }
    }

    private static boolean Regular(String str, String pattern) {
        if (null == str || str.trim().length() <= 0)
            return false;
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);
        return m.matches();
    }
}
