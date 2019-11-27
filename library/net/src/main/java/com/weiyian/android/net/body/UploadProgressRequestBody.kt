package com.weiyian.android.net.body

import com.weiyian.android.net.callback.ProgressResponseCallBack
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.*
import java.io.IOException

/**
 * @author :
 */
class UploadProgressRequestBody : RequestBody {

    private lateinit var delegate: RequestBody
    private var progressCallBack: ProgressResponseCallBack
    private lateinit var countingSink: CountingSink

    constructor(listener: ProgressResponseCallBack) {
        this.progressCallBack = listener
    }

    constructor(delegate: RequestBody, progressCallBack: ProgressResponseCallBack) {
        this.delegate = delegate
        this.progressCallBack = progressCallBack
    }

    fun setRequestBody(delegate: RequestBody) {
        this.delegate = delegate
    }

    override fun contentType(): MediaType? {
        return delegate.contentType()
    }

    override fun contentLength(): Long {
        return try {
            delegate.contentLength()
        } catch (e: IOException) {
            e.printStackTrace()
            -1
        }
    }

    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {
        val bufferedSink: BufferedSink = Okio.buffer(countingSink)

        countingSink = CountingSink(sink)

        delegate.writeTo(bufferedSink)

        bufferedSink.flush()
    }

    private inner class CountingSink(delegate: Sink) : ForwardingSink(delegate) {

        private var bytesWritten: Long = 0

        private var contentLength: Long = 0

        private var lastRefreshUiTime: Long = 0

        @Throws(IOException::class)
        override fun write(source: Buffer, byteCount: Long) {
            super.write(source, byteCount)
            if (contentLength <= 0) {
                // 获得contentLength的值，后续不再调用
                contentLength = contentLength()
            }
            // 增加当前写入的字节数
            bytesWritten += byteCount

            val curTime = System.currentTimeMillis()
            // 每100毫秒刷新一次数据,防止频繁无用的刷新
            if (curTime - lastRefreshUiTime >= 100 || bytesWritten == contentLength) {
                progressCallBack.onResponseProgress(bytesWritten, contentLength, bytesWritten == contentLength)
                lastRefreshUiTime = System.currentTimeMillis()
            }
        }
    }

}
