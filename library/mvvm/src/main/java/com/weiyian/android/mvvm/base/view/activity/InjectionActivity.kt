package com.weiyian.android.mvvm.base.view.activity

import android.app.Activity
import com.weiyian.android.mvvm.base.view.IView
import org.kodein.di.Copy
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.android.retainedKodein
import org.kodein.di.generic.kcontext


/**
 * 6.0.1 -- androidx
 * 6.0.0
 */
abstract class InjectionActivity : AutoDisposeActivity(), KodeinAware, IView {

    protected val parentKodein by closestKodein()

    override val kodeinContext = kcontext<Activity>(this)

    override val kodein: Kodein by retainedKodein {
        extend(parentKodein, copy = Copy.All)
    }

}