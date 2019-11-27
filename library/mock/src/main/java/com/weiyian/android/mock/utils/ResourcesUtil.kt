package com.weiyian.android.mock.utils

import com.weiyian.android.mock.providers.InputStreamProvider
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 * @author :
 */
object ResourcesUtil {

    fun loadAsString(provider: InputStreamProvider, path: String): String? {
        var bufferedReader: BufferedReader? = null
        try {
            val stringBuilder = StringBuilder()
            val inputStream = provider.provide(path)

            when {
                null != inputStream -> {
                    bufferedReader = BufferedReader(InputStreamReader(inputStream))
                    var line: String
                    while (true) {
                        line = bufferedReader.readLine() ?: break
                        stringBuilder.append(line)
                    }
                    return stringBuilder.toString()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            when {
                bufferedReader != null -> {
                    try {
                        bufferedReader.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
        return null
    }

}
