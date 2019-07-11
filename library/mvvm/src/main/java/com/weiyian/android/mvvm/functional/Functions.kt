package com.weiyian.android.mvvm.functional

typealias Supplier<T> = () -> T

interface Consumer<T> {

    fun accept(t: T)
}