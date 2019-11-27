package com.weiyian.android.mvvm.base.view.fragment

import android.support.v4.app.Fragment
import com.weiyian.android.mvvm.base.view.IView
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.closestKodein
import org.kodein.di.generic.kcontext

abstract class InjectionFragment : AutoDisposeFragment(), KodeinAware, IView {

    protected val parentKodein by closestKodein()

    override val kodeinContext = kcontext<Fragment>(this)

}