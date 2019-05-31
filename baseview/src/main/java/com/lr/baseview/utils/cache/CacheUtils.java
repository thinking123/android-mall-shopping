package com.lr.baseview.utils.cache;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.util.Properties;

/**
 * Created by Administrator on 2017/9/5.
 */

public class CacheUtils {
    private static final String SDCARD_PATH = Environment.getExternalStorageDirectory().getPath();

    /**
     * 计算缓存的大小
     */
    public static String  caculateCacheSize(Context context) {
        long fileSize = 0;
        String cacheSize = "0KB";
        File filesDir = context.getFilesDir();
        File cacheDir = context.getCacheDir();

        fileSize += FileUtil.getDirSize(filesDir);
        fileSize += FileUtil.getDirSize(cacheDir);
        // 2.2版本才有将应用缓存转移到sd卡的功能
        if (isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
            File externalCacheDir = MethodsCompat
                    .getExternalCacheDir(context);
            fileSize += FileUtil.getDirSize(externalCacheDir);
            fileSize += FileUtil.getDirSize(new File(SDCARD_PATH+ File.separator + "hahaliu/cache"));
        }
        if (fileSize > 0)
            cacheSize = FileUtil.formatFileSize(fileSize);
       return cacheSize;
    }

    public static boolean isMethodsCompat(int VersionCode) {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }
    /**
     * 清除app缓存
     */
    public static void myclearaAppCache(Context context) {
        DataCleanManager.cleanApplicationData(context);
        // 清除数据缓存
        // 清除编辑器保存的临时内容
        Properties props = getProperties(context);
        for (Object key : props.keySet()) {
            String _key = key.toString();
            if (_key.startsWith("temp"))
                removeProperty(context, _key);
        }
//        Core.getKJBitmap().cleanCache();
    }

    /**
     * 清除保存的缓存
     */
    public static Properties getProperties(Context context) {
        return AppConfig.getAppConfig(context).get();
    }

    public static void removeProperty(Context context, String... key) {
        AppConfig.getAppConfig(context).remove(key);
    }

}
