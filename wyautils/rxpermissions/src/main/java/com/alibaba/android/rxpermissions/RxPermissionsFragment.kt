package com.alibaba.android.rxpermissions

import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import io.reactivex.subjects.PublishSubject
import java.util.*

/**
 * @author :
 */
class RxPermissionsFragment : Fragment() {

    private val mPermissionSubjectMap = HashMap<String, PublishSubject<Permission>>()
    private var mLogging: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    @TargetApi(Build.VERSION_CODES.M)
    internal fun requestPermissions(permissions: Array<String>) {
        requestPermissions(permissions, PERMISSIONS_REQUEST_CODE)
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != PERMISSIONS_REQUEST_CODE) {
            return
        }
        val shouldShowRequestPermissionRationales = BooleanArray(permissions.size)
        for (i in permissions.indices) {
            shouldShowRequestPermissionRationales[i] = shouldShowRequestPermissionRationale(permissions[i])
        }
        onRequestPermissionsResult(permissions, grantResults, shouldShowRequestPermissionRationales)
    }

    internal fun onRequestPermissionsResult(permissions: Array<String>, grantResults: IntArray, shouldShowRequestPermissionRationales: BooleanArray) {
        var i = 0
        val size = permissions.size
        while (i < size) {
            log("onRequestPermissionsResult  " + permissions[i])
            // Find the corresponding subject
            val permissionSubject: PublishSubject<Permission>? = mPermissionSubjectMap[permissions[i]]
            if (permissionSubject == null) {
                Log.e(RxPermissions.TAG, "RxPermissions.onRequestPermissionsResult invoked but didn't find the corresponding permission request.")
                return
            }
            mPermissionSubjectMap.remove(permissions[i])
            val granted = grantResults[i] == PackageManager.PERMISSION_GRANTED
            permissionSubject.onNext(Permission(permissions[i], granted, shouldShowRequestPermissionRationales[i]))
            permissionSubject.onComplete()
            i++
        }
    }

    internal fun log(message: String) {
        if (mLogging) {
            Log.d(RxPermissions.TAG, message)
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    internal fun isGranted(permission: String): Boolean {
        val fragmentActivity = activity ?: throw IllegalStateException("This fragment must be attached to an activity.")
        return fragmentActivity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    @TargetApi(Build.VERSION_CODES.M)
    internal fun isRevoked(permission: String): Boolean {
        val fragmentActivity = activity ?: throw IllegalStateException("This fragment must be attached to an activity.")
        return fragmentActivity.packageManager.isPermissionRevokedByPolicy(permission, activity!!.packageName)
    }

    fun getSubjectByPermission(permission: String): PublishSubject<Permission>? {
        return mPermissionSubjectMap[permission]
    }

    fun containsByPermission(permission: String): Boolean {
        return mPermissionSubjectMap.containsKey(permission)
    }

    fun setSubjectForPermission(permission: String, subject: PublishSubject<Permission>) {
        mPermissionSubjectMap[permission] = subject
    }

    companion object {

        private const val PERMISSIONS_REQUEST_CODE = 42

    }

}
