package com.wya.uikit.gallery;

import java.io.File;
import java.util.List;

/**
 * author : XuDonglin
 * e-mail : 465715784@qq.com
 * time   : 2018/12/29
 * desc   :
 * version: 1.0
 */
public class GalleryUtils {
    /**
     * 删除文件
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


    public static void removeAllFile(List<String> list) {

        for (String url : list) {
            deleteFile(url);
        }
    }
}
