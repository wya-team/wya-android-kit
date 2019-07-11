package com.weiyian.android.net.func

import android.util.Log

import com.weiyian.android.net.exception.ApiException
import com.weiyian.android.net.exception.ServerException
import com.weiyian.android.net.model.ApiResult

import io.reactivex.annotations.NonNull
import io.reactivex.functions.Function

/**
 * @author :
 */
class HandleFuc<T> : Function<ApiResult<T>, T?> {

    override fun apply(@NonNull result: ApiResult<T>): T {
        when {
            ApiException.isSuccess(result) -> when {
                null != result.data -> {
                    Log.e("TAG", "[HandleFuc] [apply] result = $result")
                    return result.data!!
                }
            }
            else -> throw ServerException(result.status, result.msg)
        }
        return Any() as T
    }

}
