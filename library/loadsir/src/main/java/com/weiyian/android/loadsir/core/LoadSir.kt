package com.weiyian.android.loadsir.core

import com.weiyian.android.loadsir.callback.Callback
import java.util.*

/**
 * @author :
 */
class LoadSir {

    private var builder: Builder? = null

    private constructor() {
        this.builder = Builder()
    }

    private constructor(builder: Builder) {
        this.builder = builder
    }

    private fun setBuilder(builder: Builder) {
        this.builder = builder
    }

    @JvmOverloads
    fun <T> register(target: Any, onReloadListener: Callback.OnReloadListener? = null, convertor: Convertor<T>? = null): LoadService<*> {
        val targetContext = LoadSirUtil.getTargetContext(target)
        return LoadService(convertor, targetContext, onReloadListener!!, builder!!)
    }

    class Builder {

        internal val callbacks = ArrayList<Callback>()
        internal var defaultCallback: Class<out Callback>? = null

        fun addCallback(callback: Callback): Builder {
            callbacks.add(callback)
            return this
        }

        fun setDefaultCallback(defaultCallback: Class<out Callback>): Builder {
            this.defaultCallback = defaultCallback
            return this
        }

        internal fun getCallbacks(): List<Callback> {
            return callbacks
        }

        fun getDefaultCallback(): Class<out Callback>? {
            return defaultCallback
        }

        fun commit() {
            getDefault()?.setBuilder(this)
        }

        fun build(): LoadSir {
            return LoadSir(this)
        }

    }

    companion object {

        @Volatile
        private var loadSir: LoadSir? = null

        fun getDefault(): LoadSir? {
            if (loadSir == null) {
                synchronized(LoadSir::class.java) {
                    if (loadSir == null) {
                        loadSir = LoadSir()
                    }
                }
            }
            return loadSir
        }

        fun beginBuilder(): Builder {
            return Builder()
        }

    }

}
