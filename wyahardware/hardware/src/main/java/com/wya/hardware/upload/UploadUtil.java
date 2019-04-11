package com.wya.hardware.upload;

import android.content.Context;
import android.util.Log;
/**
 * @author :
 */
public class UploadUtil {
    
    public static void upload(Context context, PostBeforeInterface before, String fileName, String filePath) {
        OssInfo ossInfo = null;
        if (null != before) {
            ossInfo = before.onPostBefore();
        }
        if (null == ossInfo) {
            return;
        }
        upload(context, ossInfo, fileName, filePath);
    }
    
    public static void upload(Context context, OssInfo ossInfo, String fileName, String filePath) {
        if (null == ossInfo) {
            return;
        }
        upload(context, ossInfo, fileName, filePath, (status, msg, data) -> Log.e("TAG", "[onPostAfter] status = " + status + " , msg = " + msg + " , data = " + data));
    }
    
    public static void upload(Context context, OssInfo ossInfo, String fileName, String filePath, PostAfterInterface postAfter) {
        if (null == ossInfo) {
            return;
        }
        String accessKeyId = ossInfo.getOSSAccessKeyId();
        String accessKeySecret = ossInfo.getAccessKeySecret();
        String endpoint = ossInfo.getHost();
        String bucketName = ossInfo.getBucket();
        
        OssService ossService = new OssService(context, accessKeyId, accessKeySecret, endpoint, bucketName);
        ossService.initOSSClient();
        String resultUrl = "https://" + ossInfo.getBucket() + "." + ossInfo.getHost() + "/" + fileName;
        ossService.startUpload(context, fileName, filePath, resultUrl, postAfter);
    }
    
}
