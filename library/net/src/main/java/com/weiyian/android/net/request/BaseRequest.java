package com.weiyian.android.net.request;

import android.content.Context;
import android.text.TextUtils;

import com.weiyian.android.net.HttpClient;
import com.weiyian.android.net.api.ApiService;
import com.weiyian.android.net.interceptor.HeadersInterceptor;
import com.weiyian.android.net.model.HttpHeaders;
import com.weiyian.android.net.model.HttpParams;
import com.weiyian.android.net.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.weiyian.android.net.HttpClient.getRetrofitBuilder;

/**
 * @author :
 */
@SuppressWarnings(value = {"unchecked", "deprecation"})
public abstract class BaseRequest<R extends BaseRequest> {
    
    protected Cache cache;
    protected String baseUrl;
    protected String url;
    protected long readTimeOut;
    protected long writeTimeOut;
    protected long connectTimeout;
    int retryCount;
    int retryDelay;
    int retryIncreaseDelay;
    boolean isSyncRequest;
    private final List<Interceptor> networkInterceptors = new ArrayList<>();
    protected HttpHeaders headers = new HttpHeaders();
    protected HttpParams params = new HttpParams();
    Retrofit retrofit;
    ApiService apiService;
    protected Context context;
    private HttpUrl httpUrl;
    private List<Converter.Factory> converterFactories = new ArrayList<>();
    private List<CallAdapter.Factory> adapterFactories = new ArrayList<>();
    private final List<Interceptor> interceptors = new ArrayList<>();
    
    public BaseRequest(String url) {
        this.url = url;
        context = HttpClient.getContext();
        this.baseUrl = HttpClient.getBaseUrl();
        if (!TextUtils.isEmpty(this.baseUrl)) {
            httpUrl = HttpUrl.parse(baseUrl);
        }
        converterFactories.add(GsonConverterFactory.create());
        
        boolean needRebuildBaseUrl = baseUrl == null && url != null && (url.startsWith("http:// ") || url.startsWith("https:// "));
        if (needRebuildBaseUrl) {
            httpUrl = HttpUrl.parse(url);
            baseUrl = httpUrl.url().getProtocol() + ":// " + httpUrl.url().getHost() + "/";
        }
        retryCount = HttpClient.getRetryCount();
        retryDelay = HttpClient.getRetryDelay();
        retryIncreaseDelay = HttpClient.getRetryIncreaseDelay();
        // Okhttp  cache
        cache = HttpClient.getHttpCache();
        HttpClient easyHttp = HttpClient.getInstance();
        // 添加公共请求参数
        if (easyHttp.getCommonParams() != null) {
            params.put(easyHttp.getCommonParams());
        }
        
        if (easyHttp.getCommonHeaders() != null) {
            headers.put(easyHttp.getCommonHeaders());
        }
    }
    
    public HttpParams getParams() {
        return this.params;
    }
    
    public R baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        if (!TextUtils.isEmpty(this.baseUrl)) {
            httpUrl = HttpUrl.parse(baseUrl);
        }
        return (R) this;
    }
    
    public R addInterceptor(Interceptor interceptor) {
        interceptors.add(Utils.INSTANCE.checkNotNull(interceptor, "interceptor == null"));
        return (R) this;
    }
    
    public R addConverterFactory(Converter.Factory factory) {
        converterFactories.add(factory);
        return (R) this;
    }
    
    public R addCallAdapterFactory(CallAdapter.Factory factory) {
        adapterFactories.add(factory);
        return (R) this;
    }
    
    public R headers(HttpHeaders headers) {
        this.headers.put(headers);
        return (R) this;
    }
    
    public R params(HttpParams params) {
        this.params.put(params);
        return (R) this;
    }
    
    public R params(String key, String value) {
        params.put(key, value);
        return (R) this;
    }
    
    public R params(Map<String, String> keyValues) {
        params.put(keyValues);
        return (R) this;
    }
    
    public R syncRequest(boolean syncRequest) {
        this.isSyncRequest = syncRequest;
        return (R) this;
    }
    
    public R build() {
        // okHttpClient
        OkHttpClient.Builder okHttpClientBuilder = generateOkClient();
        OkHttpClient okHttpClient = okHttpClientBuilder.build();
        
        // retrofit
        final Retrofit.Builder retrofitBuilder = generateRetrofit();
        retrofitBuilder.client(okHttpClient);
        retrofit = retrofitBuilder.build();
        apiService = retrofit.create(ApiService.class);
        
        return (R) this;
    }
    
    private OkHttpClient.Builder generateOkClient() {
        if (readTimeOut <= 0 && writeTimeOut <= 0 && connectTimeout <= 0 && headers.isEmpty()) {
            return HttpClient.getOkHttpClientBuilder();
        } else {
            final OkHttpClient.Builder newClientBuilder = HttpClient.getOkHttpClient().newBuilder();
            if (readTimeOut > 0) {
                newClientBuilder.readTimeout(readTimeOut, TimeUnit.MILLISECONDS);
            }
            if (writeTimeOut > 0) {
                newClientBuilder.writeTimeout(writeTimeOut, TimeUnit.MILLISECONDS);
            }
            if (connectTimeout > 0) {
                newClientBuilder.connectTimeout(connectTimeout, TimeUnit.MILLISECONDS);
            }
            
            newClientBuilder.addInterceptor(new HeadersInterceptor(headers));
            
            for (Interceptor interceptor : interceptors) {
                newClientBuilder.addInterceptor(interceptor);
            }
            
            if (networkInterceptors.size() > 0) {
                for (Interceptor interceptor : networkInterceptors) {
                    newClientBuilder.addNetworkInterceptor(interceptor);
                }
            }
            return newClientBuilder;
        }
    }
    
    private Retrofit.Builder generateRetrofit() {
        if (converterFactories.isEmpty() && adapterFactories.isEmpty()) {
            Retrofit.Builder builder = getRetrofitBuilder();
            if (!TextUtils.isEmpty(baseUrl)) {
                builder.baseUrl(baseUrl);
            }
            return builder;
        } else {
            final Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
            if (!TextUtils.isEmpty(baseUrl)) {
                retrofitBuilder.baseUrl(baseUrl);
            }
            if (!converterFactories.isEmpty()) {
                for (Converter.Factory converterFactory : converterFactories) {
                    retrofitBuilder.addConverterFactory(converterFactory);
                }
            } else {
                // 获取全局的对象重新设置
                Retrofit.Builder newBuilder = HttpClient.getRetrofitBuilder();
                if (!TextUtils.isEmpty(baseUrl)) {
                    newBuilder.baseUrl(baseUrl);
                }
                List<Converter.Factory> listConverterFactory = newBuilder.build().converterFactories();
                for (Converter.Factory factory : listConverterFactory) {
                    retrofitBuilder.addConverterFactory(factory);
                }
            }
            if (!adapterFactories.isEmpty()) {
                for (CallAdapter.Factory adapterFactory : adapterFactories) {
                    retrofitBuilder.addCallAdapterFactory(adapterFactory);
                }
            } else {
                // 获取全局的对象重新设置
                Retrofit.Builder newBuilder = HttpClient.getRetrofitBuilder();
                List<CallAdapter.Factory> listAdapterFactory = newBuilder.baseUrl(baseUrl).build().callAdapterFactories();
                for (CallAdapter.Factory factory : listAdapterFactory) {
                    retrofitBuilder.addCallAdapterFactory(factory);
                }
            }
            return retrofitBuilder;
        }
    }
    
    /**
     * generateRequest
     *
     * @return :
     */
    protected abstract Observable<ResponseBody> generateRequest();
}
