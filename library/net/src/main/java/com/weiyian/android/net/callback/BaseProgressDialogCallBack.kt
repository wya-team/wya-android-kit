package com.weiyian.android.net.callback

import android.util.Log
import com.weiyian.android.net.exception.ApiException
import com.weiyian.android.net.subsciber.ILoadingView
import com.weiyian.android.net.subsciber.ProgressCancelListener
import io.reactivex.disposables.Disposable

/**
 * @author :
 */
abstract class BaseProgressDialogCallBack<T> : BaseCallBack<T>, ProgressCancelListener {

    private var mILoading: ILoadingView? = null
    private var isShowProgress = true

    private var disposed: Disposable? = null

    constructor(iLoading: ILoadingView?) {
        this.mILoading = iLoading
        init(false)
    }

    /**
     * @param iLoading       :
     * @param isShowProgress :
     * @param isCancel       :
     */
    constructor(iLoading: ILoadingView, isShowProgress: Boolean, isCancel: Boolean) {
        this.mILoading = iLoading
        this.isShowProgress = isShowProgress
        init(isCancel)
    }

    /**
     * 初始化
     *
     * @param isCancel :
     */
    private fun init(isCancel: Boolean) {
        if (mILoading == null) {
            return
        }
        val view = mILoading?.view ?: return
        view.setOnClickListener {
            if (isCancel) {
                mILoading?.dismissLoading()
                onCancelProgress()
            }
        }
    }

    /**
     * 展示进度框
     */
    private fun showProgress() {
        if (!isShowProgress) {
            return
        }
        mILoading?.let {
            if (!mILoading?.isLoading!!) {
                mILoading?.showLoading()
            }
        }
    }

    /**
     * 取消进度框
     */
    private fun dismissProgress() {
        if (!isShowProgress) {
            return
        }
        mILoading?.let {
            if (mILoading?.isLoading!!) {
                mILoading?.dismissLoading()
            }
        }
    }

    override fun onStart() {
    }

    override fun onLoading() {
        showProgress()
    }

    override fun onData(data: Any) {
    }

    override fun onDataEmpty(message: String) {
    }

    override fun onCompleted() {
        dismissProgress()
    }

    override fun onError(e: ApiException?) {
        dismissProgress()
    }

    override fun onNetworkError() {
        dismissProgress()
    }

    override fun onCancelProgress() {
        disposed?.let {
            if (!disposed?.isDisposed!!) {
                disposed?.dispose()
            }
        }
    }

    fun subscription(disposed: Disposable) {
        this.disposed = disposed
    }

}
