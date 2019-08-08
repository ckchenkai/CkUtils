package com.ck.project.utilmodule.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;


import com.ck.project.utilmodule.AppConfig;
import com.ck.project.utilmodule.gson.GsonHelper;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by SilenceDut on 17/04/19.
 * Helper class to do operations on regular files/directories.
 */
public class FileUtil {

    private static String mAppName;

    public static void init(String appName) {
        mAppName = appName;
    }

    /**
     * SDCard root
     */
    public static String sdcardRoot() {
        return Environment.getExternalStorageDirectory().toString();
    }

    public static String getAppFolder() {
        // Create the application workspace
        File cacheDir = new File(sdcardRoot() + File.separator + mAppName + File.separator);
        if (!cacheDir.exists()) {
            makeDir(cacheDir);
        }
        return cacheDir.getPath();
    }

    public static String getAppCacheFolder() {
        File dir = AppConfig.getContext().getExternalCacheDir();
        return dir.getAbsolutePath();
    }

    public static String getLogTrace() {

        File logDir = new File(sdcardRoot() + File.separator + mAppName + File.separator + "log");
        if (!logDir.exists()) {
            makeDir(logDir);
        }
        return logDir.getPath();
    }

    public static boolean makeDir(File dir) {
        if (!dir.exists()) {
            return dir.mkdirs();
        }
        return (dir.exists() && dir.isDirectory());
    }

    /**
     * Build a file, used to be inserted in the disk cache.
     *
     * @param fileId The name build the file.
     * @return A valid file.
     */
    public static File buildFile(String fileId) {
        File file = new File(getAppFolder(), fileId);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 存储在app内部cache目录
     *
     * @param fileId
     * @return
     */
    public static File buildCacheFile(String fileId) {
        File file = new File(getAppCacheFolder(), fileId);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static File getCacheFile(String fileId) {
        return new File(getAppCacheFolder(), fileId);
    }


    /**
     * 同步读取一个文件，will be block
     */
    public static String readFileContent(String fileId) {
        final File file = buildFile(fileId);
        StringBuilder fileContentBuilder = new StringBuilder();
        if (file.exists()) {
            String stringLine;
            try {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                while ((stringLine = bufferedReader.readLine()) != null) {
                    fileContentBuilder.append(stringLine).append("\n");
                }
                bufferedReader.close();
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileContentBuilder.toString();
    }

    /**
     * 同步读取一个文件，根据类型返回
     */
    public static <T> T readFileContent(String fileId, Class<T> ype) {
        String fileContent = readFileContent(fileId);
        return GsonHelper.fromJson(fileContent, ype);
    }


    public static boolean isCached(String fileId) {
        final File entityFile = buildFile(fileId);
        return exists(entityFile);
    }

    public static boolean exists(File file) {
        return file.exists();
    }


    public static String assetFile2String(String fileName, Context context) {
        StringBuilder result = new StringBuilder();
        InputStreamReader inputReader = null;
        BufferedReader bufReader = null;
        try {
            inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            bufReader = new BufferedReader(inputReader);
            String line;

            while ((line = bufReader.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeIO(inputReader, bufReader);

        return result.toString();
    }


    /**
     * 关闭IO
     *
     * @param closeables closeable
     */
    public static void closeIO(Closeable... closeables) {
        if (closeables == null) {
            return;
        }
        try {
            for (Closeable closeable : closeables) {
                if (closeable != null) {
                    closeable.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件
     *
     * @param path
     * @return
     */
    public static boolean delFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return false;
        }
        if (!file.isDirectory()) {
            return false;
        }
        if (file.isFile()) {
            file.delete();
            return true;
        }
        return false;
    }

    /**
     * 删除指定文件夹下所有文件
     *
     * @param path 绝对路径
     * @return
     */
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 删除文件夹
     *
     * @param folderPath
     */
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从assets目录读取文件
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String getFileFromAssets(Context context, String fileName) {
        StringBuilder builder = new StringBuilder();
        AssetManager assetManager = context.getAssets();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(assetManager.open(fileName), "utf-8"));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    /**
     * picSelector压缩的图片保存路径
     *
     * @return
     */
    public static String getPicSelCachePath() {
        File cacheDir = new File(getAppCacheFolder() + File.separator + "compress_pic");
        if (!cacheDir.exists()) {
            cacheDir.mkdir();
        }
        return cacheDir.getAbsolutePath();
    }


    /**
     * 获取网络图片并转换为Bitmap
     * @param url
     * @return
     */
    public static Bitmap getImageFromNet(String url) {
        HttpURLConnection conn = null;
        try {
            URL mURL = new URL(url);
            conn = (HttpURLConnection) mURL.openConnection();
            conn.setRequestMethod("GET"); //设置请求方法
            conn.setConnectTimeout(3000); //设置连接服务器超时时间
            conn.setReadTimeout(3000);  //设置读取数据超时时间

            conn.connect(); //开始连接

            int responseCode = conn.getResponseCode(); //得到服务器的响应码
            if (responseCode == 200) {
                //访问成功
                InputStream is = conn.getInputStream(); //获得服务器返回的流数据
                Bitmap bitmap = BitmapFactory.decodeStream(is); //根据流数据 创建一个bitmap对象
                return bitmap;

            } else {

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect(); //断开连接
            }
        }
        return null;
    }

    /**
     * 获取图片宽高
     * @param filePath
     * @return
     */
    public static int[] getFileImageInfo(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        return new int[]{options.outWidth,options.outHeight};
    }

    /**
     * 保存图片至相册
     * @param context
     * @param mBitmap
     * @param dirPath
     * @return
     */
    public static String saveBitmap(Context context, Bitmap mBitmap,String dirPath) {
        if (mBitmap == null) {
            return "";
        }
        // String savePath;
        File filePic;
        String fileName = "temp" + System.currentTimeMillis();
        File saveDir = new File(Environment.getExternalStorageDirectory(), dirPath);
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }
        try {
            filePic = new File(saveDir , fileName + ".jpg");
            if (!filePic.exists()) {
                filePic.createNewFile();
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            int options = 100;
            while (baos.toByteArray().length / 1024 > 1024 * 1) {  //循环判断如果压缩后图片是否大于1M,大于继续压缩
                baos.reset();//重置baos即清空baos
                options -= 2;//每次都减少2
                mBitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
                long length = baos.toByteArray().length;
            }

            FileOutputStream fos = new FileOutputStream(filePic);
            try {
                fos.write(baos.toByteArray());
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        // 把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    filePic.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + filePic.getAbsolutePath())));

        return filePic.getAbsolutePath();
    }
}
