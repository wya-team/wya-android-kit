package com.weiyian.android.mvvm.command

import com.weiyian.android.mvvm.consumer.Consumer0
import com.weiyian.android.mvvm.function.Function0

import io.reactivex.functions.Consumer

/**
 * Created by kelin on 15-8-4.
 */
class ReplyCommand<T> {

    private var execute0: Consumer0? = null
    private var execute1: Consumer<T>? = null

    private var canExecute0: Function0<Boolean>? = null

    constructor(execute: Consumer0) {
        this.execute0 = execute
    }

    constructor(execute: Consumer<T>) {
        this.execute1 = execute
    }

    /**
     * @param execute     callback for event
     * @param canExecute0 if this function return true the action execute would be invoked! otherwise would't invoked!
     */
    constructor(execute: Consumer0, canExecute0: Function0<Boolean>) {
        this.execute0 = execute
        this.canExecute0 = canExecute0
    }

    /**
     * @param execute     callback for event,this callback need a params
     * @param canExecute0 if this function return true the action execute would be invoked! otherwise would't invoked!
     */
    constructor(execute: Consumer<T>, canExecute0: Function0<Boolean>) {
        this.execute1 = execute
        this.canExecute0 = canExecute0
    }

    fun execute() {
        if (execute0 != null && canExecute0()) {
            try {
                execute0!!.accept()
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
    }

    private fun canExecute0(): Boolean {
        if (canExecute0 == null) {
            return true
        }
        try {
            return canExecute0!!.apply()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    fun execute(parameter: T) {
        if (execute1 != null && canExecute0()) {
            try {
                execute1!!.accept(parameter)
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
    }

}
