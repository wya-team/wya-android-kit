package com.wya.example.module.utils.fliedownload;

import android.os.Environment;

/**
 * @author : XuDonglin
 * @time : 2019/01/11
 * @describe :
 */
public class FlieConfig {
    public static final String FILE_IMG_DIR = Environment.getExternalStorageDirectory().getPath()
            + "/WYADownLoad/IMG";
    public static final String FILE_VIDEO_DIR = Environment.getExternalStorageDirectory().getPath
            () + "/WYADownLoad/Video";
}
