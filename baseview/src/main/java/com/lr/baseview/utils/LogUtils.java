package com.lr.baseview.utils;

import android.util.Log;

/**
 * 日志打印工具
 @author liushubao
  *         Created by admin on 2017/3/20.
 */

public class LogUtils {
    public static int LOG_LEVEL=  6;//6.为开发阶段  0.为发布阶段（隐藏所有的打印日志）
    public static int ERROR=1;
    public static int WARN=2;
    public static int INFO=3;
    public static int DEBUG=4;
    public static int VERBOS=5;

    public static void e(String tag, String msg){
        if(LOG_LEVEL>ERROR){
            Log.e(tag, msg);
        }
    }

    public static void i(String tag, String msg){
        if(LOG_LEVEL>INFO){
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg){
        if(LOG_LEVEL>DEBUG){
            Log.d(tag, msg);
        }
    }

    public static void v(String tag, String msg){
        if(LOG_LEVEL>VERBOS){
            Log.v(tag, msg);
        }
    }
    /**
     * 控制异常信息的显示
     * @param e
     */
    public static void showLogfoException(Exception e) {
        LogUtils.e("Exception", e.toString());
    }
}
