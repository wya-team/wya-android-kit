package com.weiyian.android.net.subsciber

import android.content.Context
import com.weiyian.android.net.callback.BaseCallBack
import com.weiyian.android.net.callback.BaseProgressDialogCallBack
import com.weiyian.android.net.exception.ApiException
import io.reactivex.annotations.NonNull

/**
 * @author :
 */
class CallBackSubsciber<T>(context: Context?, private val mCallBack: BaseCallBack<T>?) : BaseSubscriber<T>(context) {

    init {
        when (mCallBack) {
            is BaseProgressDialogCallBack<*> -> (mCallBack as BaseProgressDialogCallBack<*>).subscription(this)
        }
    }

    override fun onStart() {
        super.onStart()
        mCallBack?.onStart()
    }

    override fun onLoading() {
        mCallBack?.onLoading()
    }

    override fun onNetworkError() {
        mCallBack?.onNetworkError()
    }

    override fun onNext(@NonNull result: T) {
        super.onNext(result)
        mCallBack?.onSuccess(result)
    }

    override fun onError(e: ApiException) {
        mCallBack?.onError(e)
    }

    override fun onComplete() {
        super.onComplete()
        mCallBack?.onCompleted()
    }

}
