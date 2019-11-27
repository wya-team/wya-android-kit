package com.weiyian.android.net.api

import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 * @author :
 */
interface ApiService {

    /**
     * post
     *
     * @param url  :
     * @param maps :
     * @return :
     */
    @POST
    @FormUrlEncoded
    fun post(@Url url: String,
             @FieldMap maps: Map<String, String>): Observable<ResponseBody>

    /**
     * postBody
     *
     * @param url    :
     * @param object :
     * @return :
     */
    @POST
    fun postBody(@Url url: String,
                 @Body `object`: Any): Observable<ResponseBody>

    /**
     * postBody
     *
     * @param url  :
     * @param body :
     * @return :
     */
    @POST
    fun postBody(@Url url: String,
                 @Body body: RequestBody): Observable<ResponseBody>

    /**
     * postJson
     *
     * @param url      :
     * @param jsonBody :
     * @return :
     */
    @POST
    @Headers("Content-Type: application/json", "Accept: application/json")
    fun postJson(@Url url: String,
                 @Body jsonBody: RequestBody): Observable<ResponseBody>

    /**
     * uploadFiles
     *
     * @param url  :
     * @param maps :
     * @return :
     */
    @Multipart
    @POST
    fun uploadFiles(@Url url: String,
                    @PartMap maps: Map<String, RequestBody>): Observable<ResponseBody>

    /**
     * get
     *
     * @param url  :
     * @param maps :
     * @return :
     */
    @GET
    operator fun get(@Url url: String,
                     @QueryMap maps: Map<String, String>): Observable<ResponseBody>

    /**
     * uploadFiles
     *
     * @param url   :
     * @param parts :
     * @return :
     */
    @Multipart
    @POST
    fun uploadFiles(@Url url: String,
                    @Part parts: List<MultipartBody.Part>): Observable<ResponseBody>

}
