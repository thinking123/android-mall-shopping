package com.lr.baseview.utils;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/8/23.
 */

public class FileUtils {
    public FileUtils() {
    }

    public static void mkDirs(String dirStr) {
        File dir = new File(dirStr);
        if(!dir.exists()) {
            dir.mkdirs();
        }

    }

    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if(null != file && file.exists()) {
            file.delete();
        }

    }

    public static void copyRawFile(Context context, File outFile, int resRawId) {
        InputStream inputStream = context.getResources().openRawResource(resRawId);
        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(outFile);
            byte[] e = new byte[1024];
            boolean count = false;

            int count1;
            while(-1 != (count1 = inputStream.read(e))) {
                outputStream.write(e, 0, count1);
            }

            outputStream.flush();
        } catch (IOException var19) {
            var19.printStackTrace();
        } finally {
            try {
                if(null != inputStream) {
                    inputStream.close();
                }
            } catch (IOException var18) {
                var18.printStackTrace();
            }

            try {
                if(null != outputStream) {
                    outputStream.close();
                }
            } catch (IOException var17) {
                var17.printStackTrace();
            }
        }
    }
}
