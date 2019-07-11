/*
 * Copyright (C) 2017 zhouyou(478319399@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http:// www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.weiyian.android.net.exception

import android.net.ParseException
import com.google.gson.JsonParseException
import com.google.gson.JsonSerializer
import com.weiyian.android.net.model.ApiResult
import org.json.JSONException
import retrofit2.HttpException
import java.io.NotSerializableException
import java.net.ConnectException
import java.net.UnknownHostException

/**
 * @author :
 */
class ApiException(throwable: Throwable, val code: Int) : Exception(throwable) {
    var displayMessage: String? = null
        set(msg) {
            field = "$msg(code:$code)"
        }

    override var message: String? = null

    init {
        this.message = throwable.message
    }

    /**
     * 约定异常
     */
    object ERROR {
        /**
         * 未知错误
         */
        internal val UNKNOWN = 1000
        /**
         * 解析错误
         */
        internal val PARSE_ERROR = UNKNOWN + 1
        /**
         * 网络错误
         */
        val NETWORD_ERROR = PARSE_ERROR + 1
        /**
         * 协议出错
         */
        internal val HTTP_ERROR = NETWORD_ERROR + 1

        /**
         * 证书出错
         */
        internal val SSL_ERROR = HTTP_ERROR + 1

        /**
         * 连接超时
         */
        val TIMEOUT_ERROR = SSL_ERROR + 1

        /**
         * 调用错误
         */
        internal val INVOKE_ERROR = TIMEOUT_ERROR + 1
        /**
         * 类转换错误
         */
        internal val CAST_ERROR = INVOKE_ERROR + 1
        /**
         * 请求取消
         */
        internal val REQUEST_CANCEL = CAST_ERROR + 1
        /**
         * 未知主机错误
         */
        internal val UNKNOWNHOST_ERROR = REQUEST_CANCEL + 1

        /**
         * 空指针错误
         */
        internal val NULLPOINTER_EXCEPTION = UNKNOWNHOST_ERROR + 1
    }

    companion object {

        fun isSuccess(apiResult: ApiResult<*>?): Boolean {
            return apiResult?.isSuccess() ?: false
        }

        fun handleException(e: Throwable): ApiException {
            val ex: ApiException
            if (e is HttpException) {
                ex = ApiException(e, e.code())
                ex.message = e.message
                return ex
            } else if (e is ServerException) {
                ex = ApiException(e, e.errCode)
                ex.message = e.message
                return ex
            } else if (e is JsonParseException
                    || e is JSONException
                    || e is JsonSerializer<*>
                    || e is NotSerializableException
                    || e is ParseException) {
                ex = ApiException(e, ERROR.PARSE_ERROR)
                ex.message = "解析错误"
                return ex
            } else if (e is ClassCastException) {
                ex = ApiException(e, ERROR.CAST_ERROR)
                ex.message = "类型转换错误"
                return ex
            } else if (e is ConnectException) {
                ex = ApiException(e, ERROR.NETWORD_ERROR)
                ex.message = "连接失败"
                return ex
            } else if (e is javax.net.ssl.SSLHandshakeException) {
                ex = ApiException(e, ERROR.SSL_ERROR)
                ex.message = "证书验证失败"
                return ex
            } else if (e is java.net.SocketTimeoutException) {
                ex = ApiException(e, ERROR.TIMEOUT_ERROR)
                ex.message = "连接超时"
                return ex
            } else if (e is UnknownHostException) {
                ex = ApiException(e, ERROR.UNKNOWNHOST_ERROR)
                ex.message = "无法解析该域名"
                return ex
            } else if (e is NullPointerException) {
                ex = ApiException(e, ERROR.NULLPOINTER_EXCEPTION)
                ex.message = "NullPointerException"
                return ex
            } else {
                ex = ApiException(e, ERROR.UNKNOWN)
                ex.message = "未知错误 , " + e.message
                return ex
            }
        }

        fun getMessage(apiException: ApiException): String? {
            return apiException.message
        }
    }
}