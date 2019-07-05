package com.weiyian.android.rxpermissions

import android.util.Log
import io.reactivex.Observable

/**
 * @author :
 */
class Permission {

    private val name: String
    val granted: Boolean

    /**
     * 第一次请求权限时，用户如果拒绝，下一次请求shouldShowRequestPermissionRationale()返回true
     */
    private val shouldShowRequestPermissionRationale: Boolean

    @JvmOverloads
    constructor(name: String, granted: Boolean, shouldShowRequestPermissionRationale: Boolean = false) {
        this.name = name
        this.granted = granted
        this.shouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale
    }

    constructor(permissions: List<Permission>) {
        name = combineName(permissions)
        Log.e("TAG", "[Permission] [constructor permissions] name = $name")
        granted = combineGranted(permissions)
        Log.e("TAG", "[Permission] [constructor permissions] granted = $granted")
        shouldShowRequestPermissionRationale = combineShouldShowRequestPermissionRationale(permissions)
        Log.e("TAG", "[Permission] [constructor permissions] shouldShowRequestPermissionRationale = $shouldShowRequestPermissionRationale")
    }

    private fun combineName(permissions: List<Permission>): String {
        return Observable.fromIterable(permissions)
                .map { permission -> permission.name }
                .collectInto(StringBuilder(),
                        { s, s2 ->
                            if (s.isEmpty()) {
                                s.append(s2)
                            } else {
                                s.append(", ").append(s2)
                            }
                        })
                .blockingGet()
                .toString()
    }

    private fun combineGranted(permissions: List<Permission>): Boolean {
        return Observable.fromIterable(permissions)
                .all { permission -> permission.granted }
                .blockingGet()
    }

    private fun combineShouldShowRequestPermissionRationale(permissions: List<Permission>): Boolean {
        return Observable.fromIterable(permissions)
                .any { permission -> permission.shouldShowRequestPermissionRationale }
                .blockingGet()
    }

    override fun equals(o: Any?): Boolean {
        when {
            this === o -> return true
            o == null || javaClass != o.javaClass -> return false
            else -> {
                val that = o as Permission?

                if (granted != that!!.granted) {
                    return false
                }
                return if (shouldShowRequestPermissionRationale != that.shouldShowRequestPermissionRationale) {
                    false
                } else name == that.name
            }
        }
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + if (granted) 1 else 0
        result = 31 * result + if (shouldShowRequestPermissionRationale) 1 else 0
        return result
    }

    override fun toString(): String {
        return "Permission{" +
                "name='" + name + '\''.toString() +
                ", granted=" + granted +
                ", shouldShowRequestPermissionRationale=" + shouldShowRequestPermissionRationale +
                '}'.toString()
    }
}
