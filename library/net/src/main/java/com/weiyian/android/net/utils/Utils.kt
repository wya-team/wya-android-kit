package com.weiyian.android.net.utils

import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.os.Looper
import com.weiyian.android.net.body.UploadProgressRequestBody
import io.reactivex.annotations.NonNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.Closeable
import java.io.File
import java.io.IOException
import java.lang.reflect.GenericArrayType
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.TypeVariable
import java.util.*

/**
 * @author :
 */
object Utils {

    fun <T> checkNotNull(t: T?, message: String): T {
        when (t) {
            null -> throw NullPointerException(message)
            else -> return t
        }
    }

    fun checkMain(): Boolean {
        return Thread.currentThread() === Looper.getMainLooper().thread
    }

    fun createJson(jsonString: String): RequestBody {
        checkNotNull(jsonString, "json not null!")
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonString)
    }

    /**
     * @param name :
     * @return :
     */
    fun createFile(name: String): RequestBody {
        checkNotNull(name, "name not null!")
        return RequestBody.create(okhttp3.MediaType.parse("multipart/form-data; charset=utf-8"), name)
    }

    /**
     * @param file :
     * @return :
     */
    fun createFile(file: File): RequestBody {
        checkNotNull(file, "file not null!")
        return RequestBody.create(okhttp3.MediaType.parse("multipart/form-data; charset=utf-8"), file)
    }

    /**
     * create MultipartBody Parts
     *
     * @param partName :
     * @param list     : List<File>
     * @param type     : ContentType type
     * @param callback : ResponseCallback
     * @return : Map<String , MultipartBody.Part>
    </String></File> */
    @NonNull
    fun createPartLists(partName: String, list: List<File>?, @NonNull type: ContentType, callback: UploadProgressRequestBody): List<MultipartBody.Part> {
        val parts = ArrayList<MultipartBody.Part>()
        when {
            list != null && list.isNotEmpty() -> {

                var requestBody: RequestBody?
                for (file in list) {
                    when {
                        !FileUtil.exists(file) -> throw Resources.NotFoundException(file.path + "file 路径无法找到")
                        else -> {
                            requestBody = createRequestBody(file)
                            //  MultipartBody.Part is used to send also the actual file name
                            val body = MultipartBody.Part.createFormData(partName, file.name, requestBody)
                            parts.add(body)
                        }
                    }
                }
            }
        }
        return parts
    }

    /**
     * createRequestBody
     *
     * @param file file
     * @param type see [ContentType]
     * @return NovateRequestBody
     */
    @NonNull
    fun createRequestBody(@NonNull file: File): RequestBody {
        return RequestBody.create(okhttp3.MediaType.parse("multipart/form-data"), file)
    }

    /**
     * @param file :
     * @return :
     */
    fun createImage(file: File): RequestBody {
        checkNotNull(file, "file not null!")
        return RequestBody.create(okhttp3.MediaType.parse("image/jpg; charset=utf-8"), file)
    }

    fun close(close: Closeable?) {
        when {
            close != null -> try {
                closeThrowException(close)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @Throws(IOException::class)
    fun closeThrowException(close: Closeable?) {
        close?.close()
    }

    /**
     * find the type by interfaces
     *
     * @param cls :
     * @param <R> :
     * @return :
    </R> */
    fun <R> findNeedType(cls: Class<R>): Type {
        val typeList = getMethodTypes(cls)
        return when {
            typeList == null || typeList.isEmpty() -> RequestBody::class.java
            else -> typeList[0]
        }
    }

    /**
     * MethodHandler
     */
    private fun <T> getMethodTypes(cls: Class<T>): List<Type>? {
        val typeOri = cls.genericSuperclass
        var needtypes: MutableList<Type>? = null
        //  if Type is T
        when (typeOri) {
            is ParameterizedType -> {
                needtypes = ArrayList()
                val parentypes = typeOri.actualTypeArguments
                for (childtype in parentypes) {
                    needtypes.add(childtype)
                    when (childtype) {
                        is ParameterizedType -> {
                            val childtypes = childtype.actualTypeArguments
                            Collections.addAll(needtypes, *childtypes)
                        }
                    }
                }
            }
        }
        return needtypes
    }

    fun getClass(type: Type, i: Int): Class<*>? {
        return (type as? ParameterizedType)?.let {
            //  处理泛型类型
            getGenericClass(it, i)
        } ?: when (type //  处理泛型擦拭对象
            ) {
            is TypeVariable<*> -> getClass(type.bounds[0], 0)
            else -> //  class本身也是type，强制转型
                type as Class<*>
        }
    }

    fun getType(type: Type, i: Int): Type? {
        return (type as? ParameterizedType)?.let {
            //  处理泛型类型
            getGenericType(it, i)
        } ?: when (type) {
            is TypeVariable<*> -> //  处理泛型擦拭对象
                getType(type.bounds[0], 0)
            else -> //  class本身也是type，强制转型
                type
        }
    }

    fun getParameterizedType(type: Type, i: Int): Type? {
        return when (type) {
            is ParameterizedType -> //  处理泛型类型
                type.actualTypeArguments[i]
            is TypeVariable<*> -> //  处理泛型擦拭对象
                getType(type.bounds[0], 0)
            else -> //  class本身也是type，强制转型
                type
        }
    }

    fun getGenericClass(parameterizedType: ParameterizedType, i: Int): Class<*>? {
        val genericClass = parameterizedType.actualTypeArguments[i]
        return when (genericClass) {
            is ParameterizedType -> //  处理多级泛型
                genericClass.rawType as Class<*>
            is GenericArrayType -> //  处理数组泛型
                genericClass.genericComponentType as Class<*>
            is TypeVariable<*> -> //  处理泛型擦拭对象
                getClass(genericClass.bounds[0], 0)
            else -> genericClass as Class<*>
        }
    }

    private fun getGenericType(parameterizedType: ParameterizedType, i: Int): Type? {
        return when (val genericType = parameterizedType.actualTypeArguments[i]) {
            is ParameterizedType -> //  处理多级泛型
                genericType.rawType
            is GenericArrayType -> //  处理数组泛型
                genericType.genericComponentType
            is TypeVariable<*> -> //  处理泛型擦拭对象
                getClass(genericType.bounds[0], 0)
            else -> genericType
        }
    }

    fun isNetworkAvailable(context: Context?): Boolean {
        return when (context) {
            null -> false
            else -> {
                val manager = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val info = manager.activeNetworkInfo
                when {
                    null == info || !info.isAvailable -> false
                    else -> true
                }
            }
        }
    }

    /**
     * 普通类反射获取泛型方式，获取需要实际解析的类型
     *
     * @param <T> :
     * @return :
    </T> */
    fun <T> findNeedClass(cls: Class<T>): Type? {
        // 以下代码是通过泛型解析实际参数,泛型必须传
        when (val genType = cls.genericSuperclass) {
            is ParameterizedType -> {
                val params = genType.actualTypeArguments

                val type = params[0]
                val finalNeedType: Type
                finalNeedType = when {
                    params.size > 1 -> {
                        // 这个类似是：CacheResult<SkinTestResult> 2层
                        when (type) {
                            !is ParameterizedType -> throw IllegalStateException("没有填写泛型参数")
                            else -> type.actualTypeArguments[0]
                        }
                    }
                    else -> // 这个类似是:SkinTestResult  1层
                        type
                }
                return finalNeedType
            }
            else -> return genType
        }

    }

    /**
     * 普通类反射获取泛型方式，获取最顶层的类型
     */
    fun <T> findRawType(cls: Class<T>): Type? {
        val genType = cls.genericSuperclass
        return when {
            null != genType && genType is ParameterizedType -> getGenericType(genType, 0)
            else -> genType
        }
    }

}
