package com.wya.uikit.gallery;

import java.io.File;
import java.util.List;

/**
 * @author : XuDonglin
 * @time : 2019-01-10
 * @description : 图片浏览工具
 */
public class GalleryUtils {
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
    
    public static void removeAllFile(List<String> list) {
        
        for (String url : list) {
            deleteFile(url);
        }
    }
}
