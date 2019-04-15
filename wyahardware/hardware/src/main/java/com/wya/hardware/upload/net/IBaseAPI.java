package com.wya.hardware.upload.net;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

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
    Observable<BaseResult> upload(@HeaderMap Map<String, String> headerMap, @Part List<MultipartBody.Part> list);
    
    
    /**
     * 如果不需要多个 BaseUrl, 继续使用初始化时传入 Retrofit 中的默认 BaseUrl, 就不要加上 DOMAIN_NAME_HEADER 这个 Header
     */
    @Headers({"https://wyatest.oss-cn-hangzhou.aliyuncs.com/"})
    /**
     * 可以通过在注解里给全路径达到使用不同的 BaseUrl, 但是这样无法在 App 运行时动态切换 BaseUrl
     */
    @GET("/users")
    Observable<ResponseBody> getUsers(@Query("since") int lastIdQueried, @Query("per_page") int perPage);
    
    /**
     * 切换 Url 的优先级: DomainHeader 中对应 BaseUrl 的将覆盖全局的 BaseUrl
     * 这里不配置 DomainHeader, 将只受到设置的全局 BaseUrl 的影响, 没有全局 BaseUrl 将请求原始的 BaseUrl
     * 当你项目中只有一个 BaseUrl, 但需要动态切换 BaseUrl 时, 全局 BaseUrl 显得非常方便
     * 当你设置了全局 BaseUrl, 整个项目中没有加入 DomainHeader 的网络请求地址都将被替换成这个全局 BaseUrl
     */
    @GET("/BaseUrl-Solution")
    Observable<ResponseBody> requestDefault();
}
