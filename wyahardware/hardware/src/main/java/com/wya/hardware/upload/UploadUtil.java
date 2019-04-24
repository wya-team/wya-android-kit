package com.wya.hardware.upload;

import android.content.Context;
import android.text.TextUtils;

import com.wya.hardware.upload.net.OssSp;
/**
 * @author :
 */
public class UploadUtil {
    
    /**
     * upload
     *
     * @param context   :
     * @param ossInfo   :
     * @param fileName  :
     * @param filePath  :
     * @param index     : 批量操作时以防文件路径一致被覆盖
     * @param postAfter :
     */
    public static void upload(Context context, OssInfo ossInfo, String fileName, String filePath, int index, PostAfterInterface postAfter) {
        if (null == ossInfo) {
            return;
        }
        
        String key = ossInfo.getDir() + System.currentTimeMillis() + index + "/" + fileName;
        ossInfo.setKey(key);
        upload(context, ossInfo, fileName, filePath, postAfter);
    }
    
    public static void upload(Context context, PostBeforeInterface postBefore, String fileName, String filePath, int index, PostAfterInterface postAfter) {
        IOssInfo ossInfo = null;
        if (null != postBefore) {
            ossInfo = postBefore.onPostBefore();
        }
        if (null == ossInfo) {
            return;
        }
        
        String key = ossInfo.getDir() + System.currentTimeMillis() + index + "/" + fileName;
        ossInfo.setKey(key);
        upload(context, ossInfo, fileName, filePath, postAfter);
    }
    
    public static <T extends IOssInfo> void upload(Context context, T ossInfo, String fileName, String filePath, PostAfterInterface postAfter) {
        if (null == ossInfo) {
            return;
        }
        
        String key = ossInfo.getKey();
        if (TextUtils.isEmpty(key)) {
            key = ossInfo.getDir() + System.currentTimeMillis() + "/" + fileName;
        }
        ossInfo.setKey(key);
        ossInfo.setFile(filePath);
        ossInfo.setSuccessActionStatus("201");
        
        if (TextUtils.isEmpty(ossInfo.getResultUrl())) {
            String url = "https://" + ossInfo.getBucket() + "." + ossInfo.getHost() + "/" + ossInfo.getKey();
            ossInfo.setResultUrl(url);
        }
        
        OssSp.get(context).setBucket(ossInfo.getBucket());
        new Presenter().upload(context, ossInfo, postAfter);
    }
    
}
