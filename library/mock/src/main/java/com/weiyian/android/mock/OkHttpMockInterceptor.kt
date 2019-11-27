package com.weiyian.android.mock

import com.weiyian.android.mock.providers.InputStreamProvider
import com.weiyian.android.mock.utils.ResourcesUtil
import okhttp3.*
import java.io.IOException
import java.util.*
import kotlin.math.abs

/**
 * @author :
 */
class OkHttpMockInterceptor @JvmOverloads constructor(private val mInputStreamProvider: InputStreamProvider, private var mFailurePercent: Int, private var mBasePath: String? = DEFAULT_BASE_PATH, private var mMinDelayMilliseconds: Int = DELAY_DEFAULT_MIN, private var mMaxDelayMilliseconds: Int = DELAY_DEFAULT_MAX) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val url = chain.request().url()
        val encodedPath = url.encodedPath()
        // TODO api/xxx.json
        val path = "api" + encodedPath.substring(encodedPath.lastIndexOf("/"))

        when (val responseString = ResourcesUtil.loadAsString(mInputStreamProvider, path)) {
            null -> {
                // default
                val request = chain.request()
                return chain.proceed(request)
            }
            else -> {
                // local mock
                try {
                    Thread.sleep((abs(Random().nextInt() % (mMaxDelayMilliseconds - mMinDelayMilliseconds)) + mMinDelayMilliseconds).toLong())
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                val failure = abs(Random().nextInt() % 100) < mFailurePercent
                val statusCode = when {
                    failure -> 500
                    else -> 200
                }
                return Response.Builder()
                        .code(statusCode)
                        .message(responseString)
                        .request(chain.request())
                        .protocol(Protocol.HTTP_1_0)
                        .body(ResponseBody.create(MediaType.parse("application/json"), responseString))
                        .addHeader("content-type", "application/json")
                        .build()
            }
        }
    }

    companion object {

        private const val DEFAULT_BASE_PATH = ""
        private const val DELAY_DEFAULT_MIN = 500
        private const val DELAY_DEFAULT_MAX = 1500

    }

}