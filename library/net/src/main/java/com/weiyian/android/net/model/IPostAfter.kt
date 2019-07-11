package com.weiyian.android.net.model

/**
 * @author :
 */
interface IPostAfter<T> {

    /**
     * onPostAfter
     */
    fun onPostAfter(status: Int, msg: String, data: T)

}
