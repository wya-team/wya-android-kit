package com.weiyian.android.net.subsciber

import android.content.Context
import com.weiyian.android.net.exception.ApiException
import com.weiyian.android.net.utils.Utils
import io.reactivex.annotations.NonNull
import io.reactivex.observers.DisposableObserver
import java.lang.ref.WeakReference

/**
 * @author :
 */
abstract class BaseSubscriber<T> : DisposableObserver<T> {

    private var contextWeakReference: WeakReference<Context>? = null

    constructor()

    constructor(context: Context?) {
        context?.let {
            contextWeakReference = WeakReference(context)
        }
    }

    /**
     * onStart 无网络时直接onCompleted
     */
    override fun onStart() {
        contextWeakReference?.let {
            when {
                it.get() != null && !Utils.isNetworkAvailable(it.get()) -> {
                    onNetworkError()
                    dispose()
                    onComplete()
                }
                else -> onLoading()
            }
        }
    }


    abstract fun onLoading()

    /**
     * onNetworkError
     */
    abstract fun onNetworkError()

    override fun onNext(@NonNull result: T) {

    }

    /**
     * 统一异常处理
     *
     * @param e :
     */
    override fun onError(e: Throwable) {
        when (e) {
            is ApiException -> {
                onError(e)
            }
            else -> {
                onError(ApiException.handleException(e))
            }
        }
    }

    /**
     * onError
     *
     * @param e :
     */
    abstract fun onError(e: ApiException)

    override fun onComplete() {
    }
}
