package com.weiyian.android.net.func

import android.util.Log

import com.weiyian.android.net.exception.ApiException

import io.reactivex.Observable
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Function

/**
 * @author :
 */
class HttpResponseFunc<T> : Function<Throwable, Observable<T>> {

    @Throws(Exception::class)
    override fun apply(@NonNull throwable: Throwable): Observable<T> {
        Log.e("TAG", "[HttpResponseFunc] [apply] throwable = $throwable")
        return Observable.error(ApiException.handleException(throwable))
    }

}
