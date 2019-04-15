package com.wya.hardware.upload;

import android.content.Context;

import com.wya.hardware.upload.net.OssSp;
/**
 * @author :
 */
public class UploadUtil {
    
    public static void upload(Context context, OssInfo ossInfo, String fileName, String filePath, PostAfterInterface postAfter) {
        if (null == ossInfo) {
            return;
        }
        
        String key = ossInfo.getDir() + System.currentTimeMillis() + "/" + fileName;
        
        ossInfo.setKey(key);
        ossInfo.setFile(filePath);
        ossInfo.setSuccess_action_status("201");
        String url = "https://" + ossInfo.getBucket() + "." + ossInfo.getHost() + "/" + ossInfo.getKey();
        ossInfo.setResultUrl(url);
        
        OssSp.get(context).setBucket(ossInfo.getBucket());
        new Presenter().upload(context, ossInfo, postAfter);
    }
    
}
