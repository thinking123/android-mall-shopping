/**
 * Project Name: PostalManage
 * File Name: FileHelper.java
 * Date: 2017-05-18 16:21
 * Copyright (c) 2017, 陕西中爆安全网科技有限公司 All Rights Reserved.
 */
package com.lr.baseview.utils;

import android.os.Environment;

import java.io.File;

/**
 * Function : 文件操作帮助类
 * Date:      2017-05-18 16:21
 * @author ReiChin_
 * @version
 */
public class FileHelper {

    private static final String SDCARD_PATH = Environment.getExternalStorageDirectory().getPath();

    // mnt/sdcard/wltlib
    public static final String IDCARD_DRIVE_PATH = SDCARD_PATH + File.separator + "wltlib";

    // mnt/sdcard/hahaliu
    public static final String ROOT_PATH = SDCARD_PATH + File.separator + "hahaliu";

    // mnt/sdcard/hahaliu/download
    public static final String DOWNLOAD_PATH = ROOT_PATH + File.separator + "download";

    // mnt/sdcard/hahaliu/log
    public static final String LOG_PATH = ROOT_PATH + File.separator + "log";

    // mnt/sdcard/hahaliu/photo
    public static final String PHOTO_PATH = ROOT_PATH + File.separator + "photo";

    public static final String CACHE_PATH = ROOT_PATH + File.separator + "cache";
    public static final String PHOTO_CACHE_PATH = CACHE_PATH + File.separator + "photo";

    /**
     * 初始化应用路径
     */
    public static void initPath() {
        FileUtils.mkDirs(IDCARD_DRIVE_PATH);
        FileUtils.mkDirs(ROOT_PATH);
        FileUtils.mkDirs(DOWNLOAD_PATH);
        FileUtils.mkDirs(LOG_PATH);
        FileUtils.mkDirs(PHOTO_PATH);
        FileUtils.mkDirs(CACHE_PATH);
        FileUtils.mkDirs(PHOTO_CACHE_PATH);
    }
}
