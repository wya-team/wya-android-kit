package com.weiyian.android.net.callback

import com.weiyian.android.net.exception.ApiException

/**
 * @author :
 */
abstract class SimpleCallBack<T> : BaseCallBack<T>() {

    override fun onStart() {
    }

    override fun onLoading() {
    }

    override fun onNetworkError() {
    }

    override fun onSuccess(result: T?) {
    }

    override fun onError(e: ApiException?) {
    }

    override fun onCompleted() {
    }

    override fun onData(data: Any?) {
    }

    override fun onDataEmpty(message: String?) {
    }
}
