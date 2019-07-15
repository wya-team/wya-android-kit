package com.weiyian.android.net.func

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
        return Observable.error(ApiException.handleException(throwable))
    }

}
