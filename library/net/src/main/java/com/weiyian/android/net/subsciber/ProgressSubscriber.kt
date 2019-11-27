package com.weiyian.android.net.subsciber

import android.content.Context
import com.weiyian.android.net.exception.ApiException

/**
 * @author :
 */
abstract class ProgressSubscriber<T> : BaseSubscriber<T>, ProgressCancelListener {

    private var mILoading: ILoadingView? = null
    private var isShowProgress = true

    /**
     * 默认不显示弹出框，不可以取消
     *
     * @param context 上下文
     */
    constructor(context: Context) : super(context) {
        init(false)
    }

    /**
     * 自定义加载进度框
     *
     * @param context  上下文
     * @param iLoading 自定义对话框
     */
    constructor(context: Context, iLoading: ILoadingView) : super(context) {
        this.mILoading = iLoading
        init(false)
    }

    /**
     * 自定义加载进度框,可以设置是否显示弹出框，是否可以取消
     *
     * @param context        上下文
     * @param iLoading       对话框
     * @param isShowProgress 是否显示对话框
     * @param isCancel       对话框是否可以取消
     */
    constructor(context: Context, iLoading: ILoadingView, isShowProgress: Boolean, isCancel: Boolean) : super(context) {
        this.mILoading = iLoading
        this.isShowProgress = isShowProgress
        init(isCancel)
    }

    /**
     * 初始化
     *
     * @param isCancel 对话框是否可以取消
     */
    private fun init(isCancel: Boolean) {
        when (mILoading) {
            null -> return
            else -> {
                val view = mILoading?.view ?: return
                when {
                    isCancel -> view.setOnClickListener { this@ProgressSubscriber.onCancelProgress() }
                }
            }
        }
    }

    /**
     * 展示进度框
     */
    private fun showProgress() {
        when {
            !isShowProgress -> return
            mILoading != null -> {
                when {
                    !mILoading!!.isLoading -> mILoading?.showLoading()
                }
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

        if (mILoading != null) {
            if (mILoading!!.isLoading) {
                mILoading!!.dismissLoading()
            }
        }
    }

    public override fun onStart() {
        showProgress()
    }

    override fun onComplete() {
        dismissProgress()
    }

    override fun onError(e: ApiException) {
        dismissProgress()
    }

    override fun onCancelProgress() {
        if (!isDisposed) {
            dispose()
        }
    }
}
