package com.weiyian.android.net.interceptor;

import com.weiyian.android.net.model.HttpHeaders;

import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author :
 */
public class HeadersInterceptor implements Interceptor {
    
    private HttpHeaders headers;
    
    public HeadersInterceptor(HttpHeaders headers) {
        this.headers = headers;
    }
    
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        if (headers.getHeadersMap().isEmpty()) {
            return chain.proceed(builder.build());
        }
        try {
            for (Map.Entry<String, String> entry : headers.getHeadersMap().entrySet()) {
                builder.header(entry.getKey(), entry.getValue()).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chain.proceed(builder.build());
        
    }
}
