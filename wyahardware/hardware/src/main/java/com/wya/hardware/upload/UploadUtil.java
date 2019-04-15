package com.wya.hardware.upload;

import android.content.Context;

import com.wya.hardware.upload.net.OssSp;
/**
 * @author :
 */
public class UploadUtil {
    
    //    public static void upload(Context context, PostBeforeInterface before, String fileName, String filePath) {
    //        OssInfo ossInfo = null;
    //        if (null != before) {
    //            ossInfo = before.onPostBefore();
    //        }
    //        if (null == ossInfo) {
    //            return;
    //        }
    //        upload(context, ossInfo, fileName, filePath);
    //    }
    
    // TODO: 2019/4/15 ZCQ SDK
    //    public static void upload(Context context, OssInfo ossInfo, String fileName, String filePath) {
    //        if (null == ossInfo) {
    //            return;
    //        }
    //        upload(context, ossInfo, fileName, filePath, (status, msg, data) -> Log.e("TAG", "[onPostAfter] status = " + status + " , msg = " + msg + " , data = " + data));
    //    }
    
    public static void upload(Context context, OssInfo ossInfo, String fileName, String filePath, PostAfterInterface postAfter) {
        if (null == ossInfo) {
            return;
        }
        OssSp.get(context).setBucket(ossInfo.getBucket());
        new Presenter().upload(context, ossInfo, fileName, filePath, postAfter);
    }
    
    //    public static void upload(Context context, OssInfo ossInfo, String fileName, String filePath, PostAfterInterface postAfter) {
    //        if (null == ossInfo) {
    //            return;
    //        }
    //        String accessKeyId = ossInfo.getOSSAccessKeyId();
    //        String accessKeySecret = ossInfo.getAccessKeySecret();
    //        String endpoint = ossInfo.getHost();
    //        String bucketName = ossInfo.getBucket();
    //
    //        String policy = ossInfo.getPolicy();
    //        String signature = ossInfo.getSignature();
    //        OssService ossService = new OssService(context, accessKeyId, accessKeySecret, endpoint, bucketName, policy, signature);
    //        ossService.initOSSClient();
    //        String resultUrl = "https://" + ossInfo.getBucket() + "." + ossInfo.getHost() + "/" + fileName;
    //        ossService.startUpload(context, fileName, filePath, resultUrl, postAfter);
    //    }
    
}
