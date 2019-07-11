package com.weiyian.android.net.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * @author :
 */
class NoCacheInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        Log.e("NetworkTag", "[NoCacheInterceptor] [intercept]")
        var request = chain.request()
        request = request.newBuilder().header("Cache-Control", "no-cache").build()
        var originalResponse = chain.proceed(request)
        originalResponse = originalResponse.newBuilder().header("Cache-Control", "no-cache").build()
        return originalResponse
    }

}



