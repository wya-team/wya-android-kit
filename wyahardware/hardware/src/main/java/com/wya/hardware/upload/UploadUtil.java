package com.wya.hardware.upload;

import android.content.Context;
import android.util.Log;

import com.wya.hardware.upload.net.OssSp;
/**
 * @author :
 */
public class UploadUtil {
    
    public static <T extends IOssInfo> void upload(Context context, T iOssInfo, String fileName, String filePath, PostAfterInterface postAfter) {
        if (null == iOssInfo) {
            return;
        }
        
        String key = iOssInfo.getDir() + System.currentTimeMillis() + "/" + fileName;
        Log.e("ZCQ", "[upload] key = " + key);
        
        iOssInfo.setKey(key);
        iOssInfo.setFile(filePath);
        iOssInfo.setSuccessActionStatus("201");
        String url = "https://" + iOssInfo.getBucket() + "." + iOssInfo.getHost() + "/" + iOssInfo.getKey();
        iOssInfo.setResultUrl(url);
        
        OssSp.get(context).setBucket(iOssInfo.getBucket());
        new Presenter().upload(context, iOssInfo, postAfter);
    }
    
    public static void upload(Context context, OssInfo ossInfo, String fileName, String filePath, int index, PostAfterInterface postAfter) {
        if (null == ossInfo) {
            return;
        }
        
        upload(context, ossInfo, fileName + "_" + index, filePath, postAfter);
    }
    
}
