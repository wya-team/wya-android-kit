package com.weiyian.android.net.model

/**
 * @author :
 */
open class ApiResult<T> {

    var status: Int = 0
    var msg: String? = null
    var data: T? = null

    constructor(status: Int, msg: String?, data: T?) {
        this.status = status
        this.msg = msg
        this.data = data
    }

    constructor()

    fun isInvalidate(): Boolean = status == -1

    fun isTokenInvalid(): Boolean = status == -1

    fun isSuccess(): Boolean = status == 1

    override fun toString(): String {
        return "ApiResult(status=$status, msg=$msg, data=${data?.toString()})"
    }

    companion object {

        const val STATUS_SUCCESS = 1
        const val STATUS_NETWORK_ERROR = -2

    }

}
