package com.weiyian.android.mvvm.base.view.fragment

import android.arch.lifecycle.Lifecycle
import android.support.v4.app.Fragment
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider

abstract class AutoDisposeFragment : Fragment() {

    protected val scopeProvider: AndroidLifecycleScopeProvider by lazy {
        AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)
    }

}