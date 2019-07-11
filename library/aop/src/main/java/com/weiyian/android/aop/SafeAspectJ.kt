package com.weiyian.android.aop

import android.util.Log

import com.weiyian.android.aop.annotation.Safe

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut

/**
 * @author :
 */
@Aspect
class SafeAspectJ {

    @Pointcut("within(@com.weiyian.android.aop.annotation.Safe *)")
    fun withinAnnotatedClass() {
    }

    @Pointcut("execution(!synthetic * *(..)) && withinAnnotatedClass()")
    fun methodInsideAnnotatedType() {
    }

    /**
     * 方法切入点
     */
    @Pointcut("execution(@com.weiyian.android.aop.annotation.Safe * *(..)) || methodInsideAnnotatedType()")
    fun method() {
    }

    /**
     * 在连接点进行方法替换
     *
     * @param joinPoint :
     * @param safe      :
     * @return :
     * @throws Throwable :
     */
    @Around("method() && @annotation(safe)")
    @Throws(Throwable::class)
    fun aroundJoinPoint(joinPoint: ProceedingJoinPoint, safe: Safe): Any? {
        var result: Any? = null
        Log.e("TAG", "[SafeAspectJ] [aroundJoinPoint] safe = $safe")
        try {
            result = joinPoint.proceed()
        } catch (e: Throwable) {
            Log.e("TAG", "[SafeAspect] [aroundJoinPoint] e = ${e.message}")
        }
        return result
    }

}
