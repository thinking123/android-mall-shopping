package com.lf.shoppingmall.activity.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lf.shoppingmall.R;
import com.lf.shoppingmall.activity.main.MainActivity;
import com.lf.shoppingmall.base.BaseActivity;
import com.lf.shoppingmall.bean.VersionVo;
import com.lf.shoppingmall.common.Constans;
import com.lf.shoppingmall.utils.ComUtils;
import com.lf.shoppingmall.utils.FileDownloadThread;
import com.lr.baseview.utils.FileHelper;
import com.lr.baseview.utils.ImmersionStatus;

import java.io.File;
import java.net.URL;
import java.net.URLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/5.
 */

public class VersionActivity extends BaseActivity {
    @BindView(R.id.version_content)
    TextView mTvUpContent;
    @BindView(R.id.version_download_progress)
    ProgressBar mPbDlProgress;
    @BindView(R.id.version_download_progress_msg)
    TextView mTvDlProgressMsg;

    public final String ARG_VERSION_INFO = "version_info";
    private final String SDCARD_PATH = Environment.getExternalStorageDirectory().getPath();
    private final String DEF_DL_PATH = SDCARD_PATH + File.separator + "Downloads";
    private FileDownloadThread mDownloadThred;
    private int mDownloadedSize = 0;
    private int mFileSize = 0;
    protected VersionVo mVers1ionVo = null;
    private String mDlPath = "";
    private String mBaseUrl = "";

    private Handler mUiHandler = new Handler(Looper.myLooper()) {
        public void handleMessage(Message msg) {
            int progress = Double.valueOf((double) VersionActivity.this.mDownloadedSize * 1.0D / (double) VersionActivity.this.mFileSize * 100.0D).intValue();
            VersionActivity.this.mPbDlProgress.setProgress(progress);
            VersionActivity.this.mTvDlProgressMsg.setText("当前进度:" + progress + "%");
            if (progress == 100) {
                VersionActivity.this.installNewApk();
            }

        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.activity_version;
    }

    @Override
    protected void initView() {
        ImmersionStatus.getInstance().setStateColor(this, R.color.blue_color_theme_stand); // 设置图片的沉浸式
        ButterKnife.bind(this);

//        this.mVersionVo = this.getVersionVo();
        this.mDlPath = this.getDownloadPath();
        this.mBaseUrl = this.getDownloadBaseUrl();
        this.mDlPath = TextUtils.isEmpty(this.mDlPath) ? DEF_DL_PATH : this.mDlPath;
        if (TextUtils.isEmpty(this.mBaseUrl)) {
            Log.e(TAG, "version download base url is null!");
        } else {
            this.initViews();
            if (Build.VERSION.SDK_INT > 22) {
                String[] permissions = new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
                if (ContextCompat.checkSelfPermission(this, permissions[0]) == 0 && ContextCompat.checkSelfPermission(this, permissions[1]) == 0) {
                    this.downLoadApkFile();
                } else {
                    ActivityCompat.requestPermissions(this, permissions, 512);
                }
            } else {
                this.downLoadApkFile();
            }
        }
    }

    @Override
    protected Object getTitleText() {
        return null;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (512 == requestCode && grantResults[0] == 0) {
            this.downLoadApkFile();
        }

    }

    protected VersionVo getVersionVo() {
        return (VersionVo) getIntent().getSerializableExtra(ARG_VERSION_INFO);
    }


    protected String getDownloadPath() {
        return FileHelper.DOWNLOAD_PATH;
    }

    protected String getDownloadBaseUrl() {
        return Constans.DownLoad;
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

    private void initViews() {
//        if (null != this.mVersionVo) {
            this.mTvUpContent.setText("更新版本中，将迎来更好的购物体验 -=-\n"/* + this.mVersionVo.getUpdateContent()*/);
//        }
        this.mPbDlProgress.setVisibility(View.VISIBLE);
        this.mPbDlProgress.setMax(100);
        this.mPbDlProgress.setProgress(0);
    }

    private void downLoadApkFile() {
        File downloadDir = new File(this.mDlPath);
        if (!downloadDir.exists()) {
            downloadDir.mkdirs();
        }

        File downloadFile = new File(this.mDlPath + File.separator + "apkDownload.apk");
        if (downloadFile.exists()) {
            downloadFile.delete();
        }

//        String serviceFileName = this.mVersionVo.getVersionId() + ".apk";
        this.mPbDlProgress.setProgress(0);
        (new DownloadTask(this.mBaseUrl, downloadFile)).start();
    }

    public void installNewApk() {
//        this.onInstallApkStart(this.mVersionVo);
        Intent intent = new Intent();
        intent.addFlags(268435456);
        intent.setAction("android.intent.action.VIEW");
        File downloadFile = new File(this.mDlPath + File.separator + "apkDownload.apk");
        if (downloadFile.exists()) {
            ComUtils.promotePermission(this.mDlPath);
            intent.addFlags(1);
            intent.setDataAndType(this.getApkUri(downloadFile), "application/vnd.android.package-archive");
            this.startActivity(intent);
            startActivity(new Intent(this, MainActivity.class)
                    .putExtra("update", true));
            finish();
        }

    }

    protected void onInstallApkStart(VersionVo versionInfo) {
    }

    protected void onDestroy() {
        super.onDestroy();
        this.mDownloadThred.closeDownloadIOStream();
        this.mDownloadThred.interrupt();
    }

    public class DownloadTask extends Thread {
        private int blockSize;
        private int downloadSizeMore;
        private String urlStr;
        private File downloadFile;

        public DownloadTask(String urlStr, File downloadFile) {
            this.urlStr = urlStr;
            this.downloadFile = downloadFile;
        }

        public void run() {
            try {
                URL e = new URL(this.urlStr);
                URLConnection conn = e.openConnection();
                VersionActivity.this.mFileSize = conn.getContentLength();
                this.blockSize = VersionActivity.this.mFileSize / 1;
                this.downloadSizeMore = VersionActivity.this.mFileSize % 1;
                VersionActivity.this.mDownloadThred = new FileDownloadThread(e, this.downloadFile, 0, this.blockSize - 1);
                VersionActivity.this.mDownloadThred.setName("Thread0");
                VersionActivity.this.mDownloadThred.start();
                boolean finished = false;
                while (!finished) {
                    VersionActivity.this.mDownloadedSize = this.downloadSizeMore;
                    finished = true;
                    VersionActivity.this.mDownloadedSize = VersionActivity.this.mDownloadedSize + VersionActivity.this.mDownloadThred.getDownloadSize();
                    if (!VersionActivity.this.mDownloadThred.isFinished()) {
                        finished = false;
                    }

                    VersionActivity.this.mUiHandler.sendEmptyMessage(0);
                    sleep(1000L);
                }
            } catch (Exception var4) {
                var4.printStackTrace();
            }
        }
    }
}
