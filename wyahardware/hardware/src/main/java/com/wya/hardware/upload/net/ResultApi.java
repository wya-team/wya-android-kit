package com.wya.hardware.upload.net;

import com.wya.hardware.upload.OssInfo;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author :
 */
public class ResultApi {
    
    public Observable<BaseResult> upload(OssInfo ossInfo, String fileName, String filePath) {
        Map<String, String> headerMap = new HashMap<>(16);
        headerMap.put("header_extend", "upload");
        return RetrofitFactory.getInstance().create(IBaseAPI.class).upload(headerMap, generateRequest(ossInfo));
    }
    
    private static List<MultipartBody.Part> generateRequest(OssInfo ossInfo) {
        if (null == ossInfo) {
            return null;
        }
        MultipartBody.Builder builder = new MultipartBody.Builder();
        
        builder.addFormDataPart("policy", ossInfo.getPolicy());
        builder.addFormDataPart("OSSAccessKeyId", ossInfo.getOSSAccessKeyId());
        builder.addFormDataPart("signature", ossInfo.getSignature());
        builder.addFormDataPart("key", ossInfo.getKey());
        
        File file = new File(ossInfo.getFile());
        RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        builder.addFormDataPart("file", ossInfo.getFile(), fileBody);
        
        return builder.build().parts();
    }
    
}
