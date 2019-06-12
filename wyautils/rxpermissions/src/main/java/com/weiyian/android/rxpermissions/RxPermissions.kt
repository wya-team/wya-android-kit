package com.weiyian.android.rxpermissions

import android.annotation.TargetApi
import android.app.Activity
import android.os.Build
import android.support.annotation.VisibleForTesting
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.functions.Function
import io.reactivex.subjects.PublishSubject
import java.util.*

/**
 * @author :
 */
class RxPermissions {

    @VisibleForTesting
    internal var mRxPermissionsFragment: Lazy<RxPermissionsFragment>

    constructor(activity: FragmentActivity) {
        mRxPermissionsFragment = getLazySingleton(activity.supportFragmentManager)
    }

    constructor(fragment: Fragment) {
        mRxPermissionsFragment = getLazySingleton(fragment.childFragmentManager)
    }

    private fun getLazySingleton(fragmentManager: FragmentManager): Lazy<RxPermissionsFragment> {
        return object : Lazy<RxPermissionsFragment> {
            private var rxPermissionsFragment: RxPermissionsFragment? = null

            @Synchronized
            override fun get(): RxPermissionsFragment? {
                if (rxPermissionsFragment == null) {
                    rxPermissionsFragment = getRxPermissionsFragment(fragmentManager)
                }
                return rxPermissionsFragment
            }
        }
    }

    private fun getRxPermissionsFragment(fragmentManager: FragmentManager): RxPermissionsFragment? {
        var rxPermissionsFragment: RxPermissionsFragment? = findRxPermissionsFragment(fragmentManager)
        val isNewInstance = rxPermissionsFragment == null
        if (isNewInstance) {
            rxPermissionsFragment = RxPermissionsFragment()
            fragmentManager
                    .beginTransaction()
                    .add(rxPermissionsFragment, TAG)
                    .commitNow()
        }
        return rxPermissionsFragment
    }

    /**
     * String -> Observable<Boolean>
     */
    fun request(vararg permissions: String): Observable<Boolean> {
        return Observable.just(TRIGGER)
                .compose(ensure(*permissions))
    }

    fun requestEach(vararg permissions: String): Observable<Permission> {
        return Observable.just(TRIGGER)
                .compose(ensureEach(*permissions))
    }

    fun requestEachCombined(vararg permissions: String): Observable<Permission> {
        return Observable.just(TRIGGER)
                .compose(ensureEachCombined(*permissions))
    }

    /**
     * String -> ObservableTransformer<T,Boolean>
     */
    private fun <T> ensure(vararg permissions: String): ObservableTransformer<T, Boolean> {
        return ObservableTransformer { observable ->
            request(observable, *permissions)
                    .buffer(permissions.size)
                    // Observable<Permission> -> Observable<Boolean>
                    .flatMap(Function<List<Permission>, ObservableSource<Boolean>> { permissions ->
                        if (permissions.isEmpty()) {
                            return@Function Observable.empty()
                        }

                        // Return true if all permissions are granted.
                        for (p in permissions) {
                            if (!p.granted) {
                                return@Function Observable.just(false)
                            }
                        }
                        Observable.just(true)
                    })
        }
    }

    private fun <T> ensureEach(vararg permissions: String): ObservableTransformer<T, Permission> {
        return ObservableTransformer { observable -> request(observable, *permissions) }
    }

    private fun <T> ensureEachCombined(vararg permissions: String): ObservableTransformer<T, Permission> {
        return ObservableTransformer { observable ->
            request(observable, *permissions)
                    .buffer(permissions.size)
                    .flatMap { permissions ->
                        if (permissions.isEmpty()) {
                            Observable.empty()
                        } else Observable.just(Permission(permissions))
                    }
        }
    }

    /**
     * String -> Observable<Permission>
     */
    private fun request(observable: Observable<*>, vararg permissions: String): Observable<Permission> {
        if (permissions.isEmpty()) {
            throw IllegalArgumentException("RxPermissions.request/requestEach requires at least one input permission")
        }
        return oneOf(observable, pending(*permissions))
                .flatMap { requestImplementation(*permissions) }
    }

    /**
     * Observable<*>? -> Observable<*>
     */
    private fun oneOf(observable: Observable<*>?, pending: Observable<*>): Observable<*> {
        return when (observable) {
            null -> Observable.just(TRIGGER)
            else -> Observable.merge<Any>(observable, pending)
        }
    }

    private fun pending(vararg permissions: String): Observable<*> {
        for (permission in permissions) {
            if (mRxPermissionsFragment.get()?.containsByPermission(permission)!!.not()) {
                return Observable.empty<Any>()
            }
        }
        return Observable.just(TRIGGER)
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun requestImplementation(vararg permissions: String): Observable<Permission> {
        val permissionList = ArrayList<Observable<Permission>>(permissions.size)
        val unrequestedPermissionList = ArrayList<String>()

        for (permission in permissions) {
            if (isGranted(permission)) {
                permissionList.add(Observable.just(Permission(permission, granted = true, shouldShowRequestPermissionRationale = false)))
                continue
            }

            if (isRevoked(permission)) {
                permissionList.add(Observable.just(Permission(permission, granted = false, shouldShowRequestPermissionRationale = false)))
                continue
            }

            var subject: PublishSubject<Permission>? = mRxPermissionsFragment.get()?.getSubjectByPermission(permission)
            if (subject == null) {
                unrequestedPermissionList.add(permission)
                subject = PublishSubject.create()
                mRxPermissionsFragment.get()?.setSubjectForPermission(permission, subject)
            }
            permissionList.add(subject!!)
        }

        if (unrequestedPermissionList.isNotEmpty()) {
            val unrequestedPermissionsArray = unrequestedPermissionList.toTypedArray()
            requestPermissionsFromFragment(unrequestedPermissionsArray)
        }
        return Observable.concat(Observable.fromIterable(permissionList))
    }

    private fun isGranted(permission: String): Boolean {
        return !isMarshmallow() || mRxPermissionsFragment.get()!!.isGranted(permission)
    }

    private fun isRevoked(permission: String): Boolean {
        return isMarshmallow() && mRxPermissionsFragment.get()!!.isRevoked(permission)
    }

    @TargetApi(Build.VERSION_CODES.M)
    internal fun requestPermissionsFromFragment(permissions: Array<String>) {
        mRxPermissionsFragment.get()?.requestPermissions(permissions)
    }

    private fun findRxPermissionsFragment(fragmentManager: FragmentManager?): RxPermissionsFragment? {
        if (fragmentManager?.findFragmentByTag(TAG) != null) {
            return fragmentManager.findFragmentByTag(TAG) as RxPermissionsFragment
        }
        return null
    }

    fun shouldShowRequestPermissionRationale(activity: Activity, vararg permissions: String): Observable<Boolean> {
        return if (!isMarshmallow()) {
            Observable.just(false)
        } else Observable.just(shouldShowRequestPermissionRationaleImplementation(activity, *permissions))
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun shouldShowRequestPermissionRationaleImplementation(activity: Activity, vararg permissions: String): Boolean {
        for (p in permissions) {
            if (!isGranted(p) && !activity.shouldShowRequestPermissionRationale(p)) {
                return false
            }
        }
        return true
    }

    internal fun onRequestPermissionsResult(permissions: Array<String>, grantResults: IntArray) {
        mRxPermissionsFragment.get()?.onRequestPermissionsResult(permissions, grantResults, BooleanArray(permissions.size))
    }

    @FunctionalInterface
    interface Lazy<V> {
        fun get(): RxPermissionsFragment?
    }

    private fun isMarshmallow(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    companion object {
        internal val TAG = RxPermissions::class.java.simpleName
        internal val TRIGGER = Any()
    }

}
