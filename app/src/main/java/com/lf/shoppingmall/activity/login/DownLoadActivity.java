package com.lf.shoppingmall.activity.login;

import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alipay.security.mobile.module.commonutils.CommonUtils;
import com.lf.shoppingmall.R;
import com.lf.shoppingmall.activity.main.MainActivity;
import com.lf.shoppingmall.base.BaseActivity;
import com.lf.shoppingmall.bean.VersionVo;
import com.lf.shoppingmall.common.Constans;
import com.lf.shoppingmall.utils.LogUtils;
import com.lr.baseview.utils.FileHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 新的下载界面
 * Created by Administrator on 2017/7/12.
 */

public class DownLoadActivity extends BaseActivity  {

//    private TextView mTvUpContent;
//    private ProgressBar pb_update;
//    private TextView progress;

    private DownloadManager downloadManager;
    private DownloadManager.Request request;
    private final String downlaodurl = Constans.DownLoad;//下载地址
    private Timer timer;
    private long id;//
    private TimerTask timerTask;
    private MyThread myThread;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            int pro = bundle.getInt("pro");
//            String name = bundle.getString("name");
//            pb_update.setProgress(pro);
//            progress.setText("当前进度:" + pro + "%");
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_down_load;
    }
    @Override
    protected void initView() {

//        mTvUpContent = (TextView) this.findViewById(R.id.version_content);
//        pb_update = (ProgressBar) this.findViewById(R.id.version_download_progress);
//        progress = (TextView) this.findViewById(R.id.version_download_progress_msg);
        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        request = new DownloadManager.Request(Uri.parse(downlaodurl));

//        mTvUpContent.setText("更新版本中，将迎来更好的购物体验 -=-");
//        pb_update.setMax(100);

        request.setTitle("四季鲜");
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
//        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(true);
        request.setMimeType("application/vnd.android.package-archive");//设置下载的文件类型，为了兼容更多的机型
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //创建目录
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).mkdir();
        //设置文件存放路径
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "apkDownload.apk");
        final DownloadManager.Query query = new DownloadManager.Query();
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                LogUtils.e("下载路径", "开始执行--->");
                Cursor cursor = downloadManager.query(query.setFilterById(id));
                if (cursor != null && cursor.moveToFirst()) {
                    if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                        dissmissLoading();
//                        pb_update.setProgress(100);
                        install(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "");
                        timerTask.cancel();
                    }
                    String title = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TITLE));
                    String address = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    int bytes_downloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
//                    int pro = bytes_downloaded / bytes_total * 100;
                    int pro = bytes_total * 100/ bytes_downloaded ;
                    Message msg = Message.obtain();
                    Bundle bundle = new Bundle();
                    bundle.putInt("pro", pro);
                    bundle.putString("name", title);
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }
                cursor.close();
            }
        };
//        timer.schedule(timerTask, 0, 1000);
//        id = downloadManager.enqueue(request);
//        timerTask.run();
        showLoading("更新版本中...\n即将迎来更好的购物体验 -=-");
        myThread = new MyThread();
        myThread.start();
    }

    @Override
    protected Object getTitleText() {
        return null;
    }

    private class MyThread extends Thread {
        @Override
        public void run() {
            download();
        }
    }

    public void download() {
        LogUtils.e("下载路径", "开始执行--->");
        String path = FileHelper.DOWNLOAD_PATH;
//        File downloadDir = new File(path);
//        if (!downloadDir.exists()) {
//            downloadDir.mkdirs();
//        }

        File downloadFile = new File(path + File.separator + "apkDownload.apk");
        if (downloadFile.exists()) {
            downloadFile.delete();
        }
        try {
            URL url = new URL(downlaodurl);
            LogUtils.e("下载路径", "开始执行222222--->");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Accept-Encoding", "identity");
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            InputStream inputStream = null;
            LogUtils.e("下载路径", "conn.getResponseCode()--->" + connection.getResponseCode());
            if (connection.getResponseCode() == 200) {
                int fileSize = connection.getContentLength() / 1024;
                Log.e("------->", "fileSize" + fileSize);
                int totla = 0;
                inputStream = connection.getInputStream();
                FileOutputStream fos = new FileOutputStream(downloadFile);
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = inputStream.read(bytes)) != -1) {
                    fos.write(bytes, 0, len);
                    totla += (len / 1024);
                    Message msg = Message.obtain();
                    Bundle bundle = new Bundle();
//                    int pro = (fileSize * 100) / totla;
//                    int pro = totla * 100 / fileSize;
//                    LogUtils.e("pro", pro + "s");
//                    bundle.putInt("pro", pro);
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }
                fos.flush();
                fos.close();
                inputStream.close();
                install(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.e("下载路径", "开始执行333333--->" + e.toString());
        }
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
    }

    private void install(String mDlPath) {
        LogUtils.e("下载路径", "mDlPath--->" + mDlPath);
        Intent intent = new Intent();
        intent.addFlags(268435456);
        intent.setAction("android.intent.action.VIEW");
        File downloadFile = new File(mDlPath + File.separator + "apkDownload.apk");
        if (downloadFile.exists()) {
//            CommonUtils.promotePermission(mDlPath);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(this.getApkUri(downloadFile), "application/vnd.android.package-archive");
            this.startActivity(intent);
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    protected Uri getApkUri(File downloadFile) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // 7.0以上的版本，特殊处理
            // 参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            return FileProvider.getUriForFile(this, getString(R.string.file_provider), downloadFile);
        } else {
            return Uri.fromFile(downloadFile);
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(DownLoadActivity.this)
                .setCancelable(false).setTitle("提示")
                .setMessage("正在更新中，是否取消？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myThread.interrupt();
                        setResult(RESULT_OK);
                        DownLoadActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();

    }
}
