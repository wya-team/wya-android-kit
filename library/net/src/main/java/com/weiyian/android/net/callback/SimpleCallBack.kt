package com.weiyian.android.net.callback

import android.util.Log
import com.weiyian.android.net.exception.ApiException

/**
 * @author :
 */
abstract class SimpleCallBack<T> : BaseCallBack<T>() {

    override fun onStart() {
        Log.e("TAG", "[SimpleCallBack] [onStart] ")
    }

    override fun onLoading() {
        Log.e("TAG", "[SimpleCallBack] [onLoading] ")
    }

    override fun onNetworkError() {
        Log.e("TAG", "[SimpleCallBack] [onNetworkError] ")
    }

    override fun onSuccess(result: T?) {
        Log.e("TAG", "[SimpleCallBack] [onSuccess] t = " + result?.toString())
    }

    override fun onError(e: ApiException?) {
        // TODO ZCQ TEST
        Log.e("TAG", "[SimpleCallBack] [onError] e = " + e?.message)
    }

    override fun onCompleted() {
        Log.e("TAG", "[SimpleCallBack] [onCompleted] ")
    }


    override fun onData(data: Any?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDataEmpty(message: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
