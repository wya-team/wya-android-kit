package com.wya.hardware.upload.net;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
/**
 * @author :
 */
public class ExtendInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        List<String> headers = request.headers("header_extend");
        if (headers != null && headers.size() > 0) {
            if ("upload".equals(headers.get(0).toLowerCase())) {
                Log.e("ZCQ", "[MyInterceptor] request = " + request.toString());
                // 替换更新接口根域名
                builder.removeHeader("header_extend");
                
                // 拼接新的请求url，根域名+路径
                
                // wyatest
                String bucket = OssSp.get(BaseApp.getApp()).getBucket();
                Log.e("ZCQ", "[ExtendInterceptor] bucket = " + bucket);
                HttpUrl httpUrl = HttpUrl.parse("https://" + bucket + ".oss-cn-hangzhou.aliyuncs.com/");
                if (null != httpUrl) {
                    builder.url(httpUrl);
                }
            }
        }
        Request requestBuilder = builder.build();
        Log.e("ZCQ", "[MyInterceptor] request = " + requestBuilder.toString());
        return chain.proceed(requestBuilder);
    }
}
