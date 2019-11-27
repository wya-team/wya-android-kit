package com.weiyian.android.mvvm.ext.reactivex

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.LiveDataReactiveStreams
import io.reactivex.*

fun <T> Flowable<T>.toLiveData(): LiveData<T> =
        LiveDataReactiveStreams.fromPublisher(this)

fun <T> Observable<T>.toLiveData(
        backPressureStrategy: BackpressureStrategy = BackpressureStrategy.LATEST
): LiveData<T> =
        this.toFlowable(backPressureStrategy).toLiveData()

fun <T> Single<T>.toLiveData(): LiveData<T> =
        this.toFlowable().toLiveData()

fun <T> Maybe<T>.toLiveData(): LiveData<T> =
        this.toFlowable().toLiveData()