package com.wya.hardware.upload;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.wya.hardware.upload.net.OssSp;
/**
 * @author :
 */
public class UploadUtil {
    
    public static <T extends IOssInfo> void upload(Context context, T ossInfo, String fileName, String filePath, PostAfterInterface postAfter) {
        if (null == ossInfo) {
            return;
        }
        
        String key = ossInfo.getKey();
        if (TextUtils.isEmpty(key)) {
            key = ossInfo.getDir() + System.currentTimeMillis() + "/" + fileName;
        }
        Log.e("TAG", "[upload] key = " + key);
        
        ossInfo.setKey(key);
        ossInfo.setFile(filePath);
        ossInfo.setSuccessActionStatus("201");
        String url = "https://" + ossInfo.getBucket() + "." + ossInfo.getHost() + "/" + ossInfo.getKey();
        ossInfo.setResultUrl(url);
        
        OssSp.get(context).setBucket(ossInfo.getBucket());
        new Presenter().upload(context, ossInfo, postAfter);
    }
    
    public static void upload(Context context, OssInfo ossInfo, String fileName, String filePath, int index, PostAfterInterface postAfter) {
        if (null == ossInfo) {
            return;
        }
        
        upload(context, ossInfo, fileName + "_" + index, filePath, postAfter);
    }
    
}
