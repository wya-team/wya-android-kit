package com.weiyian.android.net.transformer

import com.weiyian.android.net.func.HttpResponseFunc

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.annotations.NonNull

/**
 * @author :
 */
class HandleErrTransformer<T> : ObservableTransformer<T, T> {

    override fun apply(@NonNull upstream: Observable<T>): ObservableSource<T> {
        return upstream.onErrorResumeNext(HttpResponseFunc())
    }

}
