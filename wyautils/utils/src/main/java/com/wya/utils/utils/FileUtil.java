package com.wya.utils.utils;

import android.graphics.Bitmap;
import android.media.MediaRecorder;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @date: 2018/12/5 14:04
 * @author: Chunjiang Mao
 * @classname: FileUtil
 * @describe: 文件工具类
 */

public class FileUtil {
    private static final File PARENT_PATH = Environment.getExternalStorageDirectory();
    private static String storagePath = "";
    private static String DST_FOLDER_NAME = "wya";
    
    /**
     * 初始化路径,创建文件
     *
     * @return
     */
    private static String initPath(String dir) {
        DST_FOLDER_NAME = dir;
        if ("".equals(storagePath)) {
            storagePath = PARENT_PATH.getAbsolutePath() + File.separator + DST_FOLDER_NAME;
            File f = new File(storagePath);
            if (!f.exists()) {
                f.mkdir();
            }
        }
        return storagePath;
    }
    
    /**
     * 保存图片
     *
     * @param dir
     * @param b
     * @return
     */
    public static String saveBitmap(String dir, Bitmap b) {
        String path = initPath(dir);
        long dataTake = System.currentTimeMillis();
        String jpegName = path + File.separator + "picture_" + dataTake + ".jpg";
        try {
            FileOutputStream fout = new FileOutputStream(jpegName);
            BufferedOutputStream bos = new BufferedOutputStream(fout);
            b.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            return jpegName;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
    
    /**
     * 删除文件
     *
     * @param url
     * @return
     */
    public static boolean deleteFile(String url) {
        boolean result = false;
        File file = new File(url);
        if (file.exists()) {
            result = file.delete();
        }
        return result;
    }
    
    /**
     * 判断是否存在SD卡
     *
     * @return
     */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    private static MediaRecorder mRecorder;

    /**
     * 开始录音
     *
     * @param path 录音文件地址 如: xxx/xxx/record.amr
     */
    public static void startRecord(String path) {

        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        //设置封装格式
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(path);
        //设置编码格式
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
        }
        //录音
        mRecorder.start();
    }

    /**
     * 停止录音
     */
    public static void stopRecord() {
        if (mRecorder == null) {
            return;
        }
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }
}
