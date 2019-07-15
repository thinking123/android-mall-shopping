package com.lf.shoppingmall.base;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.lf.shoppingmall.bean.UserVo;
import com.lr.baseview.utils.FileHelper;
import com.lr.baseview.utils.SaveBeanUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;

/**
 * 自定义Application
 *
 * @author liushubao
 *         Created by admin on 2017/3/21.
 */
public class BaseApplication extends Application {
    private static final String TAG = BaseApplication.class.getSimpleName();
    public static BaseApplication instance = null;
    //登录用户信息
    private UserVo userVo;

    /**
     * 实例化一次
     */
    public static BaseApplication getInstance() {
        if (instance == null) {
            instance = new BaseApplication();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initApplication();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    public UserVo getUserVo() {
        if (userVo == null) {
            userVo = new SaveBeanUtils<UserVo>(this).getObject();
        }
        return userVo;
    }

    public void setUserVo(UserVo userVo) {
        this.userVo = userVo;
        new SaveBeanUtils<UserVo>(this).saveObject(userVo);
    }

    /**
     * App初始化
     */
    @SuppressWarnings("deprecation")
    private void initApplication() {
        instance = this;
        FileHelper.initPath();
        //百度地图初始化
        SDKInitializer.initialize(this);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(50000L, TimeUnit.MILLISECONDS)
                .readTimeout(50000L, TimeUnit.MILLISECONDS)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }
}
