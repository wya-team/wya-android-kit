package com.weiyian.android.aop;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.Arrays;

/**
 * @author :
 */
@Aspect
public class LogMethodAspect {
    
    @Around("execution(!synthetic * *(..)) && onLogMethod()")
    public Object doLogMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
        Log.e("TAG", "[LogMethodAspect] [doLogMethod]");
        return logMethod(joinPoint);
    }
    
    @Pointcut("@within(com.weiyian.android.aop.annotation.LogMethod)||@annotation(com.weiyian.android.aop.annotation.LogMethod)")
    public void onLogMethod() {
        Log.e("TAG", "[LogMethodAspect] [onLogMethod]");
    }
    
    private Object logMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
        Log.e("TAG", "[LogMethodAspect] [logMethod]");
        Log.e("TAG", "[LogMethodAspect]" + joinPoint.getSignature().toShortString() + " Args : " + (joinPoint.getArgs() != null ? Arrays.deepToString(joinPoint.getArgs()) : ""));
        Object result = joinPoint.proceed();
        String type = ((MethodSignature) joinPoint.getSignature()).getReturnType().toString();
        Log.e("TAG", "[LogMethodAspect]" + joinPoint.getSignature().toShortString() + " Result : " + ("void".equalsIgnoreCase(type) ? "void" : result));
        return result;
    }
    
}
