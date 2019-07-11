package com.weiyian.android.net.callback

import android.os.Handler
import android.os.Looper
import android.os.Message

import java.io.Serializable
import java.lang.ref.WeakReference

/**
 * @author :
 */
abstract class UIProgressResponseCallBack : ProgressResponseCallBack {

    private val mHandler = BaseUiHandler(Looper.getMainLooper(), this)

    private class BaseUiHandler(looper: Looper, uiProgressResponseListener: UIProgressResponseCallBack) : Handler(looper) {

        private val mUIProgressResponseListenerWeakReference: WeakReference<UIProgressResponseCallBack> = WeakReference(uiProgressResponseListener)

        override fun handleMessage(msg: Message) {
            when (msg.what) {
                RESPONSE_UPDATE -> {
                    val uiProgressResponseListener = mUIProgressResponseListenerWeakReference.get()
                    if (uiProgressResponseListener != null) {
                        // 获得进度实体类
                        val progressModel = msg.obj as ProgressModel
                        // 回调抽象方法
                        uiProgressResponseListener.onUIResponseProgress(progressModel.getCurrentBytes(), progressModel.getContentLength(), progressModel.isDone())
                    }
                }
                else -> super.handleMessage(msg)
            }
        }
    }

    override fun onResponseProgress(bytesWritten: Long, contentLength: Long, done: Boolean) {
        // 通过Handler发送进度消息
        val message = Message.obtain()
        message.obj = ProgressModel(bytesWritten, contentLength, done)
        message.what = RESPONSE_UPDATE
        mHandler.sendMessage(message)
    }

    /**
     * onUIResponseProgress
     * @param bytesRead     :
     * @param contentLength :
     * @param done          :
     */
    abstract fun onUIResponseProgress(bytesRead: Long, contentLength: Long, done: Boolean)

    inner class ProgressModel(
            /**
             * 当前读取字节长度
             */
            private var currentBytes: Long,
            /**
             * 总字节长度
             */
            private var contentLength: Long,
            /**
             * 是否读取完成
             */
            private var done: Boolean) : Serializable {

        fun getCurrentBytes(): Long {
            return currentBytes
        }

        fun setCurrentBytes(currentBytes: Long): ProgressModel {
            this.currentBytes = currentBytes
            return this
        }

        fun getContentLength(): Long {
            return contentLength
        }

        fun setContentLength(contentLength: Long): ProgressModel {
            this.contentLength = contentLength
            return this
        }

        fun isDone(): Boolean {
            return done
        }

        fun setDone(done: Boolean): ProgressModel {
            this.done = done
            return this
        }

        override fun toString(): String {
            return "ProgressModel{" +
                    "currentBytes=" + currentBytes +
                    ", contentLength=" + contentLength +
                    ", done=" + done +
                    '}'.toString()
        }
    }

    companion object {

        private const val RESPONSE_UPDATE = 0x02

    }

}
