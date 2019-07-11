package com.weiyian.android.net.interceptor;

import android.content.Context;
import android.text.TextUtils;

import com.weiyian.android.net.utils.HttpLog;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * <p>描述：设置缓存功能</p>
 * 作者： zhouyou<br>
 * 日期： 2016/12/19 16:35<br>
 * 版本： v2.0<br>
 *
 * @author :
 */
public class CacheInterceptor implements Interceptor {
    
    protected Context context;
    protected String cacheControlValue_Offline;
    protected String cacheControlValue_Online;
    protected static final int maxStale = 60 * 60 * 24 * 3;
    protected static final int maxStaleOnline = 60;
    
    public CacheInterceptor(Context context) {
        this(context, String.format("max-age=%d", maxStaleOnline));
    }
    
    public CacheInterceptor(Context context, String cacheControlValue) {
        this(context, cacheControlValue, String.format("max-age=%d", maxStale));
    }
    
    public CacheInterceptor(Context context, String cacheControlValueOffline, String cacheControlValueOnline) {
        this.context = context;
        this.cacheControlValue_Offline = cacheControlValueOffline;
        this.cacheControlValue_Online = cacheControlValueOnline;
    }
    
    @Override
    public Response intercept(Chain chain) throws IOException {
        okhttp3.Response originalResponse = chain.proceed(chain.request());
        String cacheControl = originalResponse.header("Cache-Control");
        HttpLog.e(maxStaleOnline + "s load cache:" + cacheControl);
        
        boolean isOrigin = null != cacheControl && TextUtils.isEmpty(cacheControl) || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                cacheControl.contains("must-revalidate") || cacheControl.contains("max-age") || cacheControl.contains("max-stale");
        
        return isOrigin ? originalResponse : originalResponse.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                .header("Cache-Control", "public, max-age=" + maxStale)
                .build();
    }
}
