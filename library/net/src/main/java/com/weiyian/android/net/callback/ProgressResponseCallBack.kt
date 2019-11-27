package com.weiyian.android.net.callback

/**
 * @author :
 */
interface ProgressResponseCallBack {

    /**
     * onResponseProgress
     *
     * @param bytesWritten  :
     * @param contentLength :
     * @param done          :
     */
    fun onResponseProgress(bytesWritten: Long, contentLength: Long, done: Boolean)

}
