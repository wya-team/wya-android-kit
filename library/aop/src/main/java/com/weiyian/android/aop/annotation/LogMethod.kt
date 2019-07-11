package com.weiyian.android.aop.annotation

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * @author :
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(RetentionPolicy.CLASS)
annotation class LogMethod