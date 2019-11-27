package com.weiyian.android.net;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.weiyian.android.net.interceptor.HttpLoggingInterceptor;
import com.weiyian.android.net.model.HttpHeaders;
import com.weiyian.android.net.model.HttpParams;
import com.weiyian.android.net.request.CustomBodyRequest;
import com.weiyian.android.net.request.CustomRequest;
import com.weiyian.android.net.request.GetRequest;
import com.weiyian.android.net.request.PostRequest;
import com.weiyian.android.net.utils.Utils;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import io.reactivex.disposables.Disposable;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * @author :
 */
public final class HttpClient {
    
    private static Application sContext;
    public static final int DEFAULT_MILLISECONDS = 60000;
    private static final int DEFAULT_RETRY_COUNT = 3;
    private static final int DEFAULT_RETRY_INCREASEDELAY = 0;
    private static final int DEFAULT_RETRY_DELAY = 500;
    private Cache mCache = null;
    private String mBaseUrl;
    private int mRetryCount = DEFAULT_RETRY_COUNT;
    private int mRetryDelay = DEFAULT_RETRY_DELAY;
    private int mRetryIncreaseDelay = DEFAULT_RETRY_INCREASEDELAY;
    private HttpHeaders mCommonHeaders;
    private HttpParams mCommonParams;
    private OkHttpClient.Builder okHttpClientBuilder;
    private Retrofit.Builder retrofitBuilder;
    private volatile static HttpClient singleton = null;
    
    private HttpClient() {
        okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.hostnameVerifier(new DefaultHostnameVerifier());
        okHttpClientBuilder.connectTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        okHttpClientBuilder.readTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        okHttpClientBuilder.writeTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        retrofitBuilder = new Retrofit.Builder();
        retrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }
    
    public static HttpClient getInstance() {
        testInitialize();
        if (singleton == null) {
            synchronized (HttpClient.class) {
                if (singleton == null) {
                    singleton = new HttpClient();
                }
            }
        }
        return singleton;
    }
    
    public static void init(Application app) {
        sContext = app;
    }
    
    /**
     * 获取全局上下文
     */
    public static Context getContext() {
        testInitialize();
        return sContext;
    }
    
    private static void testInitialize() {
        if (sContext == null) {
            throw new ExceptionInInitializerError("请先在全局Application中调用 EasyHttp.init() 初始化！");
        }
    }
    
    public static OkHttpClient getOkHttpClient() {
        return getInstance().okHttpClientBuilder.build();
    }
    
    public static Retrofit getRetrofit() {
        return getInstance().retrofitBuilder.build();
    }
    
    /**
     * 对外暴露 OkHttpClient,方便自定义
     */
    public static OkHttpClient.Builder getOkHttpClientBuilder() {
        return getInstance().okHttpClientBuilder;
    }
    
    /**
     * 对外暴露 Retrofit,方便自定义
     */
    public static Retrofit.Builder getRetrofitBuilder() {
        return getInstance().retrofitBuilder;
    }
    
    /**
     * 调试模式,默认打开所有的异常调试
     */
    public HttpClient debug(String tag) {
        debug(tag, true);
        return this;
    }
    
    /**
     * 调试模式,第二个参数表示所有catch住的log是否需要打印<br>
     * 一般来说,这些异常是由于不标准的数据格式,或者特殊需要主动产生的,
     * 并不是框架错误,如果不想每次打印,这里可以关闭异常显示
     */
    public HttpClient debug(String tag, boolean isPrintException) {
        String tempTag = TextUtils.isEmpty(tag) ? "RxEasyHttp_" : tag;
        if (isPrintException) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(tempTag, isPrintException);
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClientBuilder.addInterceptor(loggingInterceptor);
        }
        return this;
    }
    
    /**
     * 此类是用于主机名验证的基接口。 在握手期间，如果 URL 的主机名和服务器的标识主机名不匹配，
     * 则验证机制可以回调此接口的实现程序来确定是否应该允许此连接。策略可以是基于证书的或依赖于其他验证方案。
     * 当验证 URL 主机名使用的默认规则失败时使用这些回调。如果主机名是可接受的，则返回 true
     */
    public class DefaultHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
    
    /**
     * 全局读取超时时间
     */
    public HttpClient setReadTimeOut(long readTimeOut) {
        okHttpClientBuilder.readTimeout(readTimeOut, TimeUnit.MILLISECONDS);
        return this;
    }
    
    /**
     * 全局写入超时时间
     */
    public HttpClient setWriteTimeOut(long writeTimeout) {
        okHttpClientBuilder.writeTimeout(writeTimeout, TimeUnit.MILLISECONDS);
        return this;
    }
    
    /**
     * 全局连接超时时间
     */
    public HttpClient setConnectTimeout(long connectTimeout) {
        okHttpClientBuilder.connectTimeout(connectTimeout, TimeUnit.MILLISECONDS);
        return this;
    }
    
    /**
     * 超时重试次数
     */
    public HttpClient setRetryCount(int retryCount) {
        if (retryCount < 0) throw new IllegalArgumentException("retryCount must > 0");
        mRetryCount = retryCount;
        return this;
    }
    
    /**
     * 超时重试次数
     */
    public static int getRetryCount() {
        return getInstance().mRetryCount;
    }
    
    /**
     * 超时重试延迟时间
     */
    public HttpClient setRetryDelay(int retryDelay) {
        if (retryDelay < 0) throw new IllegalArgumentException("retryDelay must > 0");
        mRetryDelay = retryDelay;
        return this;
    }
    
    /**
     * 超时重试延迟时间
     */
    public static int getRetryDelay() {
        return getInstance().mRetryDelay;
    }
    
    /**
     * 超时重试延迟叠加时间
     */
    public HttpClient setRetryIncreaseDelay(int retryIncreaseDelay) {
        if (retryIncreaseDelay < 0) {
            throw new IllegalArgumentException("retryIncreaseDelay must > 0");
        }
        mRetryIncreaseDelay = retryIncreaseDelay;
        return this;
    }
    
    /**
     * 超时重试延迟叠加时间
     */
    public static int getRetryIncreaseDelay() {
        return getInstance().mRetryIncreaseDelay;
    }
    
    /**
     * 获取OkHttp的缓存<br>
     */
    public static Cache getHttpCache() {
        return getInstance().mCache;
    }
    
    /**
     * 添加全局公共请求参数
     */
    public HttpClient addCommonParams(HttpParams commonParams) {
        if (mCommonParams == null) {
            mCommonParams = new HttpParams();
        }
        mCommonParams.put(commonParams);
        return this;
    }
    
    /**
     * 获取全局公共请求参数
     */
    public HttpParams getCommonParams() {
        return mCommonParams;
    }
    
    /**
     * 获取全局公共请求头
     */
    public HttpHeaders getCommonHeaders() {
        return mCommonHeaders;
    }
    
    /**
     * 添加全局公共请求参数
     */
    public HttpClient addCommonHeaders(HttpHeaders commonHeaders) {
        if (mCommonHeaders == null) {
            mCommonHeaders = new HttpHeaders();
        }
        mCommonHeaders.put(commonHeaders);
        return this;
    }
    
    /**
     * 添加全局拦截器
     */
    public HttpClient addInterceptor(Interceptor interceptor) {
        okHttpClientBuilder.addInterceptor(Utils.INSTANCE.checkNotNull(interceptor, "interceptor == null"));
        return this;
    }
    
    /**
     * 添加全局网络拦截器
     */
    public HttpClient addNetworkInterceptor(Interceptor interceptor) {
        okHttpClientBuilder.addNetworkInterceptor(Utils.INSTANCE.checkNotNull(interceptor, "interceptor == null"));
        return this;
    }
    
    /**
     * 全局设置Converter.Factory,默认GsonConverterFactory.create()
     */
    public HttpClient addConverterFactory(Converter.Factory factory) {
        retrofitBuilder.addConverterFactory(Utils.INSTANCE.checkNotNull(factory, "factory == null"));
        return this;
    }
    
    /**
     * 全局设置CallAdapter.Factory,默认RxJavaCallAdapterFactory.create()
     */
    public HttpClient addCallAdapterFactory(CallAdapter.Factory factory) {
        retrofitBuilder.addCallAdapterFactory(Utils.INSTANCE.checkNotNull(factory, "factory == null"));
        return this;
    }
    
    /**
     * 全局设置Retrofit callbackExecutor
     */
    public HttpClient setCallbackExecutor(Executor executor) {
        retrofitBuilder.callbackExecutor(Utils.INSTANCE.checkNotNull(executor, "executor == null"));
        return this;
    }
    
    /**
     * 全局设置Retrofit对象Factory
     */
    public HttpClient setCallFactory(okhttp3.Call.Factory factory) {
        retrofitBuilder.callFactory(Utils.INSTANCE.checkNotNull(factory, "factory == null"));
        return this;
    }
    
    /**
     * 全局设置baseurl
     */
    public HttpClient setBaseUrl(String baseUrl) {
        mBaseUrl = Utils.INSTANCE.checkNotNull(baseUrl, "baseUrl == null");
        return this;
    }
    
    /**
     * 获取全局baseurl
     */
    public static String getBaseUrl() {
        return getInstance().mBaseUrl;
    }
    
    /**
     * get请求
     */
    public static GetRequest get(String url) {
        return new GetRequest(url);
    }
    
    /**
     * post请求
     */
    public static PostRequest post(String url) {
        return new PostRequest(url);
    }
    
    /**
     * 自定义请求
     */
    public static CustomRequest custom() {
        return new CustomRequest();
    }
    
    public static CustomBodyRequest customBody() {
        return new CustomBodyRequest();
    }
    
    /**
     * 取消订阅
     */
    public static void cancelSubscription(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
    
}
