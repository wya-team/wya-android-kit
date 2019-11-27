package com.weiyian.android.net.subsciber

import android.view.View

/**
 * @author :
 */
interface ILoadingView {

    /**
     * isLoading
     *
     * @return :
     */
    val isLoading: Boolean

    /**
     * getView
     *
     * @return :
     */
    val view: View

    /**
     * showLoading
     */
    fun showLoading()

    /**
     * dismissLoading
     */
    fun dismissLoading()

}