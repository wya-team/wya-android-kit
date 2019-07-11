package com.weiyian.android.net.model

import android.os.Build
import android.text.TextUtils
import android.util.Log
import com.weiyian.android.net.HttpClient
import org.json.JSONObject
import java.io.Serializable
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author :
 */
class HttpHeaders : Serializable {

    var headersMap: LinkedHashMap<String, String>? = null

    val isEmpty: Boolean
        get() = headersMap!!.isEmpty()

    val names: Set<String>
        get() = headersMap!!.keys

    private fun init() {
        headersMap = LinkedHashMap()
    }

    constructor() {
        init()
    }

    constructor(key: String, value: String) {
        init()
        put(key, value)
    }

    fun put(key: String?, value: String?) {
        if (key != null && value != null) {
            headersMap!!.remove(key)
            headersMap!![key] = value
        }
    }

    fun put(headers: HttpHeaders?) {
        if (headers != null) {
            if (headers.headersMap != null && !headers.headersMap!!.isEmpty()) {
                val set = headers.headersMap!!.entries
                for ((key, value) in set) {
                    headersMap!!.remove(key)
                    headersMap!![key] = value
                }
            }

        }
    }

    operator fun get(key: String): String? {
        return headersMap!![key]
    }

    fun remove(key: String): String? {
        return headersMap!!.remove(key)
    }

    fun clear() {
        headersMap!!.clear()
    }

    fun toJSONString(): String {
        val jsonObject = JSONObject()
        try {
            for ((key, value) in headersMap!!) {
                jsonObject.put(key, value)
            }
        } catch (e: Exception) {
            Log.e("TAG", "[HttpHeaders] [toJSONString] [e] = " + e.message)
        }

        return jsonObject.toString()
    }

    override fun toString(): String {
        return "HttpHeaders{headersMap=$headersMap}"
    }

    companion object {

        private const val FORMAT_HTTP_DATA = "EEE, dd MMM y HH:mm:ss 'GMT'"
        private val GMT_TIME_ZONE = TimeZone.getTimeZone("GMT")

        /**
         * Accept-Language: zh-CN,zh;q=0.8
         */
        var acceptLanguage: String? = null
            get() {
                if (TextUtils.isEmpty(field)) {
                    val locale = Locale.getDefault()
                    val language = locale.language
                    val country = locale.country
                    val acceptLanguageBuilder = StringBuilder(language)
                    if (!TextUtils.isEmpty(country)) {
                        acceptLanguageBuilder.append('-').append(country).append(',').append(language).append(";q=0.8")
                    }
                    this.acceptLanguage = acceptLanguageBuilder.toString()
                    return field
                }
                return field
            }
        /**
         * User-Agent: Mozilla/5.0 (Linux; U; Android 5.0.2; zh-cn; Redmi Note 3 Build/LRX22G) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Mobile Safari/537.36
         */
        // We have nothing to do
        // Add version
        // default to "1.0"
        // default to "en"
        // add the model for the release build
        var userAgent: String? = null
            get() {
                if (TextUtils.isEmpty(field)) {
                    var webUserAgent: String? = null
                    try {
                        val sysResCls = Class.forName("com.android.internal.R\$string")
                        val webUserAgentField = sysResCls.getDeclaredField("web_user_agent")
                        val resId = webUserAgentField.get(null) as Int
                        webUserAgent = HttpClient.getContext().getString(resId)
                    } catch (e: Exception) {
                        Log.e("TAG", "[HttpHeaders] [getUserAgent] [e] = " + e.message)
                    }

                    if (TextUtils.isEmpty(webUserAgent)) {
                        webUserAgent = "Mozilla/5.0 (Linux; U; Android %s) AppleWebKit/533.1 (KHTML, like Gecko) Version/5.0 %sSafari/533.1"
                    }

                    val locale = Locale.getDefault()
                    val buffer = StringBuffer()
                    val version = Build.VERSION.RELEASE
                    if (version.isNotEmpty()) {
                        buffer.append(version)
                    } else {
                        buffer.append("1.0")
                    }
                    buffer.append("; ")
                    val language = locale.language
                    if (language != null) {
                        buffer.append(language.toLowerCase(locale))
                        val country = locale.country
                        if (!TextUtils.isEmpty(country)) {
                            buffer.append("-")
                            buffer.append(country.toLowerCase(locale))
                        }
                    } else {
                        buffer.append("en")
                    }
                    if ("REL" == Build.VERSION.CODENAME) {
                        val model = Build.MODEL
                        if (model.isNotEmpty()) {
                            buffer.append("; ")
                            buffer.append(model)
                        }
                    }
                    val id = Build.ID
                    if (id.isNotEmpty()) {
                        buffer.append(" Build/")
                        buffer.append(id)
                    }
                    if (null != webUserAgent) {
                        this.userAgent = String.format(webUserAgent, buffer, "Mobile ")
                    }
                    return field
                }
                return field
            }

        fun getDate(gmtTime: String): Long {
            try {
                return parseGMTToMillis(gmtTime)
            } catch (e: Exception) {
                Log.e("TAG", "[HttpHeaders] [getDate] [e] = " + e.message)
                return 0
            }

        }

        fun getDate(milliseconds: Long): String {
            return formatMillisToGMT(milliseconds)
        }

        fun getExpiration(expiresTime: String): Long {
            try {
                return parseGMTToMillis(expiresTime)
            } catch (e: ParseException) {
                Log.e("TAG", "[HttpHeaders] [getExpiration] [e] = " + e.message)
                return -1
            }

        }

        fun getLastModified(lastModified: String): Long {
            try {
                return parseGMTToMillis(lastModified)
            } catch (e: Exception) {
                Log.e("TAG", "[HttpHeaders] [getLastModified] [e] = " + e.message)
                return 0
            }

        }

        fun getCacheControl(cacheControl: String?, pragma: String?): String? {
            // first http1.1, second http1.0
            return cacheControl ?: pragma
        }

        @Throws(ParseException::class)
        fun parseGMTToMillis(gmtTime: String): Long {
            if (TextUtils.isEmpty(gmtTime)) {
                return 0
            }
            val formatter = SimpleDateFormat(FORMAT_HTTP_DATA, Locale.US)
            formatter.timeZone = GMT_TIME_ZONE
            val date = formatter.parse(gmtTime)
            return date.time
        }

        private fun formatMillisToGMT(milliseconds: Long): String {
            val date = Date(milliseconds)
            val simpleDateFormat = SimpleDateFormat(FORMAT_HTTP_DATA, Locale.US)
            simpleDateFormat.timeZone = GMT_TIME_ZONE
            return simpleDateFormat.format(date)
        }
    }
}