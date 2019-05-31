package com.lr.baseview.utils;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class FileUtil {
    public static File updateDir = null;
    public static File updateFile = null;
    static final String PIC_PATH = "/hahaliu/pic/";
    static final String PROJECT_PATH = "/hahaliu";

    private static String SDPATH;//用于存sd card 的文件的路径;
    private Bitmap returnBitmap = null;
    private String picpath = PIC_PATH;

    /**
     * * 构造方法
     * 获取sd卡路径
     ***/
    public FileUtil() {
        //获得当前外部存储设备的路径
        SDPATH = Environment.getExternalStorageDirectory() + "/";
    }


    /**
     * 创建文件夹
     *
     * @param name
     * @return
     */
    public static File createFile(String path, String name) {
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            updateDir = new File(Environment.getExternalStorageDirectory()
                    + "/" + path);
            updateFile = new File(updateDir + "/" + name + ".apk");

            if (!updateDir.exists()) {
                updateDir.mkdirs();
            }
            if (!updateFile.exists()) {
                try {
                    updateFile.createNewFile();
                } catch (IOException e) {
                    LogUtils.showLogfoException(e);
                }
            }
        }
        return updateFile;
    }


    /**
     * 在SD卡上面创建文件
     *
     * @throws java.io.IOException
     */
    public File CreateSDFile(String fileName) throws IOException {
        System.out.println("filename:" + fileName);
        File file = new File(SDPATH + fileName);
        return file;
    }

    /**
     * 在SD卡上创建目录
     * 判断SD card是否存在
     * String  Environment.getExternalStorageState();  //外部存储设备的状态
     * 若结果是 Environment.MEDIA_MOUNTED则说明存在可读写
     * 获得SD card目录
     * Environment.getExternalStorageDirectory()
     */
    public File createSDDir(String dirName) {
        File dir = new File(SDPATH + dirName);
        System.out.println("storage device's state :" + Environment.getExternalStorageState());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            System.out.println("the result of making directory:" + dir.mkdirs());
        }
        return dir;
    }

    /**
     * 删除文件
     **/
    public boolean removeFile(String filename) {
        File file = new File(SDPATH + filename);
        if (file.exists()) {
            return file.delete();
        } else {
            return false;
        }
    }

    /**
     * 删除文件夹所有内容包括文件夹（清空文件夹并删除文件夹）
     * @param file 文件夹
     */
    public static void deleteFile(File file){
        if (file.exists()) { // 判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                file.delete(); // delete()方法 你应该知道 是删除的意思;
            } else if (file.isDirectory()) { // 否则如果它是一个目录
                File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                    deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
                }
            }
            file.delete();
        } else {
        }
    }

    /**
     * 判断SD卡上的文件夹是否存在
     **/
    public static boolean isFileExist(String fileName) {
        File file = new File(SDPATH + fileName);
        return file.exists();
    }

    /**
     * 保存图片
     *
     * @param bitmap
     * @param fileName
     * @throws java.io.IOException
     */
    public File writeBitmapToSD(String fileName, Bitmap bitmap) {
        if (bitmap == null) return null;
        File file = null;
        File tempf;
        try {
            //创建SD卡上的目录
            tempf = createSDDir(picpath);
            synchronized (tempf) {
                System.out.println("directory in the sd card:" + tempf.exists());
                String nameString = picpath + fileName;
                //	removeFile(nameString);
                RenameFile(fileName); //添加标记
                file = CreateSDFile(nameString);
                System.out.println("file in the sd card:" + file.exists());
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                String name = fileName.substring(fileName.lastIndexOf("."));
                if (name.equals(".jpg")) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos);
                } else if (name.equals(".png")) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 50, bos); //PNG格式是无损的，因此质量的设置将被忽略，无法进行压缩
                }
                bos.flush();
                bos.close();
                DeletemarkFile();//删除掉标记
            }

            //	removeFile(path+marks);
        } catch (FileNotFoundException e) {
            LogUtils.showLogfoException(e);
        } catch (IOException e) {
            LogUtils.showLogfoException(e);
        }
        return file;
    }


    /**
     * 修改以前的图片为标记文件
     */
    public void RenameFile(String filename) {
        File f = new File(SDPATH + picpath);
        if (f.exists()) {
            if (f.isDirectory()) {
                File[] files = f.listFiles();
                if (files == null) return;
                for (File file : files) {
                    String oldname = file.getName();
                    if (oldname.contains(filename)) {//只能锁定相关图片，不相关的则不需要lock
                        String newname = oldname.substring(0, oldname.length());
                        newname = newname + ".lock";
                        if (!oldname.equals(newname)) {
                            String path = file.getParent();
                            File newfile = new File(path + "/" + newname);
                            if (!newfile.exists()) {
                                file.renameTo(newfile);
                            }
                        }
                    }

                }
            }
        }

    }

    /**
     * 删除标记文件
     */
    public void DeletemarkFile() {
        File f = new File(SDPATH + picpath);
        if (f.exists()) {
            if (f.isDirectory()) {
                File[] files = f.listFiles();
                File file;
                if (files == null) return;
                for (File file1 : files) {
                    file = file1;
                    String oldname = file.getName();
                    if (oldname.contains(".lock")) {
                        file.delete();
                    }
                }
            }
        }

    }

    /**
     * 判断是否存在标记文件
     */
    public boolean IsmarkFile() {
        File f = new File(SDPATH + picpath);
        if (f.exists()) {
            if (f.isDirectory()) {
                File[] files = f.listFiles();
                if (files == null) return false;
                File file;
                for (File file1 : files) {
                    file = file1;
                    String oldname = file.getName();
                    if (oldname.contains(".lock")) {
                        return true;
                    }
                }
            }
        }
        return false;

    }


    /**
     * 保存信息到文件中
     *
     * @param filename
     * @param content
     */
    public static void writeFile(String filename, String content) {
        try {
            FileUtil fileUtil = new FileUtil();
            filename = PROJECT_PATH + filename + ".txt";
            File file = fileUtil.CreateSDFile(filename);
            FileOutputStream outStream = new FileOutputStream(file);
            outStream.write(content.getBytes());
            outStream.close();
        } catch (FileNotFoundException e) {
            LogUtils.showLogfoException(e);
        } catch (IOException e) {
            LogUtils.showLogfoException(e);
        }
    }


    /**
     * 读取文件内容
     * 文件名称
     *
     * @return 文件内容
     */
    public String read(Context context, String filename) {
        try {
            File temp = context.getFileStreamPath(filename);
            if (temp.exists()) {
                FileInputStream inStream = context.openFileInput(filename);
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while ((len = inStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                byte[] data = outStream.toByteArray();
                outStream.close();
                inStream.close();
                return new String(data);
            } else {
                return "";
            }

        } catch (Exception e) {
            LogUtils.showLogfoException(e);
            return "";
        }
    }

		/*
         * public String readFromRaw() { String result = ""; try { InputStream in =
		 * context.getResources().openRawResource(R.raw.init); int length =
		 * in.available(); byte [] buffer = new byte[length]; in.read(buffer);
		 * result = EncodingUtils.getString(buffer, "UTF-8"); } catch (IOException
		 * e) { e.printStackTrace(); return result; } return result; }
		 */

    /**
     * 读取文件内容
     *
     * @param filename 文件名称
     * @return 文件内容
     */
    public byte[] readFromSD(String filename) {
        try {
            File file = new File(filename);
            FileInputStream inStream = new FileInputStream(file);
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            byte[] data = outStream.toByteArray();
            return data;
        } catch (Exception e) {
            LogUtils.showLogfoException(e);
            return null;
        }
    }


    public static boolean deleteLocalFile(String imgPath) {
        boolean flag = false;
        File file = new File(imgPath);
        if (file.exists()) {
            flag = file.delete();
        }
        return flag;

    }


    /**
     * 读取文件
     **/
    public Bitmap getBitmap(String filename) {
        String path = picpath;
        returnBitmap = null;
        if (filename != null) {
            final String name = path + filename;
            Thread pictureThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String filename1 = name;
                    File imageFile = new File(SDPATH + filename1);
                    FileInputStream inputStream = null;
                    BitmapFactory.Options opt = new BitmapFactory.Options();
                    opt.inPreferredConfig = Bitmap.Config.RGB_565;
                    opt.inPurgeable = true;
                    opt.inInputShareable = true;
                    if (imageFile.exists()) {
                        try {
                            inputStream = new FileInputStream(imageFile);
                            returnBitmap = BitmapFactory.decodeStream(inputStream, null, opt);
                            if (inputStream != null) {
                                inputStream.close();
                            }
                        } catch (FileNotFoundException e) {
                            LogUtils.showLogfoException(e);
                            returnBitmap = null;
                        } catch (IOException e) {
                            LogUtils.showLogfoException(e);
                            returnBitmap = null;
                        } catch (OutOfMemoryError e) {
                            LogUtils.e("OutofMemoryError", e.toString());
                            returnBitmap = null;
                        }
                    }
                }
            });
            pictureThread.start();
            try {
                pictureThread.join();
            } catch (InterruptedException e) {
                LogUtils.showLogfoException(e);
            }
        }
        return returnBitmap;
    }

    /**
     * 获得路径Media的路径，如图片等
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getMediaPath(Activity context, Uri uri) {
        String path = "";
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            path = cursor.getString(column_index);
            return path;
        } else {
            return "";
        }

    }

    /**
     * 递归删除 某个文件夹下 最后修改时间小于time的文件 filePath 文件名 time 时间
     *
     * @param filePath
     * @param time     // 删除一个星期前的备份文件 long time = System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000L;
     */
    public static void deleteFileByModifyTime(String filePath, long time) {
        if (!TextUtils.isEmpty(filePath)) {
            File root = new File(filePath);
            if (root != null) {
                if (root.isDirectory()) {
                    File[] files = root.listFiles();
                    for (File file : files) {
                        if (file.isDirectory()) {
                            deleteFileByModifyTime(file.getAbsolutePath(), time);
                        }
                    }
                } else {
                    if (isModifyThanTime(root, time)) {
                        root.delete();
                    }
                }
            }
        }
    }

    /**
     * 文件的最后修改时间是否小于给定的时间
     *
     * @param file 文件
     * @param time 对比时间
     * @return
     */
    public static boolean isModifyThanTime(File file, long time) {
        if (file != null && file.exists()) {
            if (file.lastModified() < time) {
                return true;
            }
        }
        return false;
    }

    /**
     * 复制单个文件 覆盖
     *
     * @param srcFileName  待复制的文件名
     * @return 如果复制成功返回true，否则返回false
     */
    public static boolean copyFile(String srcFileName, String destFileName) {
        return copyFile(srcFileName, destFileName, true);
    }

    /**
     * 复制单个文件
     *
     * @param srcFileName  待复制的文件名
     * @return 如果复制成功返回true，否则返回false
     */
    public static boolean copyFile(String srcFileName, String destFileName, boolean isOverlay) {
        File srcFile = new File(srcFileName);
        // 判断源文件是否存在
        if (!srcFile.exists()) {
            return false;
        } else if (!srcFile.isFile()) {
            return false;
        }
        // 判断目标文件是否存在
        File destFile = new File(destFileName);
        if (destFile.exists()) {
            // 如果目标文件存在并允许覆盖
            if (isOverlay) {
                // 删除已经存在的目标文件，无论目标文件是目录还是单个文件
                new File(destFileName).delete();
            } else {
                return true;
            }
        } else {
            // 如果目标文件所在目录不存在，则创建目录
            if (!destFile.getParentFile().exists()) {
                // 目标文件所在目录不存在
                if (!destFile.getParentFile().mkdirs()) {
                    // 复制文件失败：创建目标文件所在目录失败
                    return false;
                }
            }
        }
        // 复制文件
        int byteread = 0; // 读取的字节数
        FileInputStream in = null;
        FileOutputStream out = null;
        try {
            in = new FileInputStream(srcFile);
            out = new FileOutputStream(destFile);
            FileDescriptor fd = out.getFD();
            byte[] buffer = new byte[2048];
            while ((byteread = in.read(buffer)) != -1) {
                out.write(buffer, 0, byteread);
            }
            out.flush();
            // 确保文件已复制到磁盘中
            fd.sync();
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (out != null)
                    out.close();
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 得到以时间戳的字符串
     *
     * @return String yyyyMMdd yyyyMMddHHmmssSSS 20150102123956891
     */
    public static String getPrimarykey() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.CHINA);
        return formatter.format(new Date());
    }
}
