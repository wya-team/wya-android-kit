package com.wya.hardware.upload.net;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * @author :
 */
public interface IBaseAPI {
    
    /**
     * upload
     *
     * @param headerMap :
     * @param list      :
     * @return :
     */
    @Multipart
    @Headers({
            "header_extend:upload"
    })
    @POST("https://wyatest.oss-cn-hangzhou.aliyuncs.com/")
    Call<Void> upload(@HeaderMap Map<String, String> headerMap, @Part List<MultipartBody.Part> list);
    
}
