package com.lf.shoppingmall.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2017/9/5.
 */

public class FileDownloadThread extends Thread {
    private static final int BUFFER_SIZE = 1024;
    private URL mUrl;
    private File mFile;
    private int mStartPos;
    private int mEndPos;
    private int mCurPos;
    private boolean mFinished = false;
    private int mDownloadedSize = 0;
    BufferedInputStream mInputStream = null;
    RandomAccessFile mOutputStream = null;

    public FileDownloadThread(URL url, File file, int startPosition, int endPosition) {
        this.mUrl = url;
        this.mFile = file;
        this.mStartPos = startPosition;
        this.mEndPos = endPosition;
        this.mCurPos = startPosition;
    }

    public void run() {
        byte[] buf = new byte[1024];
        URLConnection con = null;

        try {
            con = this.mUrl.openConnection();
            con.setAllowUserInteraction(true);
            con.setRequestProperty("Range", "bytes=" + this.mStartPos + "-" + this.mEndPos);
            this.mOutputStream = new RandomAccessFile(this.mFile, "rw");
            this.mOutputStream.seek((long)this.mStartPos);
            this.mInputStream = new BufferedInputStream(con.getInputStream());

            while(this.mCurPos < this.mEndPos) {
                int e = this.mInputStream.read(buf, 0, 1024);
                if(e == -1) {
                    break;
                }

                this.mOutputStream.write(buf, 0, e);
                this.mCurPos += e;
                if(this.mCurPos > this.mEndPos) {
                    this.mDownloadedSize += e - (this.mCurPos - this.mEndPos) + 1;
                } else {
                    this.mDownloadedSize += e;
                }
            }

            this.mFinished = true;
        } catch (IOException var7) {
            var7.printStackTrace();
        } finally {
            this.closeDownloadIOStream();
        }

    }

    public boolean isFinished() {
        return this.mFinished;
    }

    public int getDownloadSize() {
        return this.mDownloadedSize;
    }

    public void closeDownloadIOStream() {
        try {
            if(null != this.mInputStream) {
                this.mInputStream.close();
                this.mInputStream = null;
            }

            if(null != this.mOutputStream) {
                this.mOutputStream.close();
                this.mOutputStream = null;
            }
        } catch (IOException var2) {
            var2.printStackTrace();
        }
    }
}
