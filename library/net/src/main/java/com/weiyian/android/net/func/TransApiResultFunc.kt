package com.weiyian.android.net.func

import android.text.TextUtils
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.weiyian.android.net.model.ApiResult
import com.weiyian.android.net.utils.Utils
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Function
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import java.lang.reflect.Modifier
import java.lang.reflect.Type

/**
 * @author :
 */
open class TransApiResultFunc<T>(protected var type: Type) : Function<ResponseBody, ApiResult<out T>> {

    protected var gson: Gson = GsonBuilder()
            .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
            .serializeNulls()
            .create()

    @Throws(Exception::class)
    override fun apply(@NonNull responseBody: ResponseBody): ApiResult<out T> {
        var apiResult = ApiResult<T>()
        apiResult.status = 1
        apiResult.msg = ""
        parseDefault(responseBody, apiResult)
        return apiResult
    }


    private fun parseDefault(responseBody: ResponseBody, apiResult: ApiResult<T>): ApiResult<T> {
        var result = apiResult
        try {
            val json: String? = responseBody.string()
            val response = parseApiResult(json, result)
            Log.d("TAG", "[TransApiResultFunc] [parseDefault] [response] = $response")

            when (val clazz = Utils.getClass(type, 0)) {
                String::class.java -> {
                    val parseApiResult = parseApiResult(json, result)
                    if (parseApiResult != null) {
                        result = parseApiResult
                        result.data = json as T
                    }
                }
                else -> {
                    val parseApiResult = parseApiResult(json, result)
                    if (parseApiResult != null) {
                        result = parseApiResult
                        if (result.data != null) {
                            val data = gson.fromJson<T>(result.data?.toString(), clazz)
                            result.data = data
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("TAG", "[TransApiResultFunc] [parseDefault] e = " + e.message)
        } finally {
            responseBody.close()
        }

        Log.d("TAG", "[TransApiResultFunc] [parseDefault] apiResult = $result")
        return result
    }

    @Throws(JSONException::class)
    private fun parseApiResult(json: String?, apiResult: ApiResult<T>): ApiResult<T>? {
        when {
            TextUtils.isEmpty(json) -> return null
            else -> {
                val jsonObject = JSONObject(json)
                when {
                    jsonObject.has("status") -> apiResult.status = jsonObject.getInt("status")
                }

                when {
                    jsonObject.has("data") -> apiResult.data = jsonObject.getString("data") as T
                }

                when {
                    jsonObject.has("msg") -> apiResult.msg = jsonObject.getString("msg")
                }
                return apiResult
            }
        }
    }

}
