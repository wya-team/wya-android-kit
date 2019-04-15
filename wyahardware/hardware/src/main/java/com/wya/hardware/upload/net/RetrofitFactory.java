package com.wya.hardware.upload.net;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author :
 */
public class RetrofitFactory {
    
    private final Retrofit retrofit;
    
    private static class InstanceHolder {
        private static final RetrofitFactory INSTANCE = new RetrofitFactory();
    }
    
    public static RetrofitFactory getInstance() {
        return InstanceHolder.INSTANCE;
    }
    
    public RetrofitFactory() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://wyatest.oss-cn-hangzhou.aliyuncs.com/")
                .client(initClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
    
    private OkHttpClient initClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .method(original.method(), original.body())
                            .build();
                    return chain.proceed(request);
                })
                .addInterceptor(initLogInterceptor())
                .addInterceptor(new ExtendInterceptor())
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build();
        return client;
    }
    
    private Interceptor initLogInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }
    
    public <T> T create(Class<T> cls) {
        return retrofit.create(cls);
    }
    
}
