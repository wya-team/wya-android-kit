package com.wya.example.module.library

import android.app.Application
import android.net.Uri
import android.text.TextUtils
import com.weiyian.android.router.facade.Postcard
import com.weiyian.android.router.launcher.ARouter

/**
 * @author :
 */
object Router {

    // TODO "https://m.xxx.com" edit it
    private fun isRouter(url: String): Boolean {
        return !TextUtils.isEmpty(url) && url.startsWith("https://m.oa.com")
    }

    /**
     * register
     */
    @JvmStatic
    fun register(application: Application) {
        ARouter.init(application)
    }

    @JvmStatic
    fun getRouter(url: String): Postcard {
        if (isRouter(url)) {
            val uri = Uri.parse(url)
            val path = uri.path
            if (!TextUtils.isEmpty(path)) {
                val result = ARouter.getInstance().build(path)
                val parameterNames = uri.queryParameterNames
                if (null != parameterNames && parameterNames.size > 0) {
                    for (parameterName in parameterNames) {
                        result.withString(parameterName, uri.getQueryParameter(parameterName))
                    }
                }
                return result
            }
        }
        return Postcard()
    }

}