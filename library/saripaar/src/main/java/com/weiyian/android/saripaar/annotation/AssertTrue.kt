package com.weiyian.android.saripaar.annotation

import android.support.annotation.StringRes

import com.weiyian.android.saripaar.rule.AssertTrueRule
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * @author Ragunath Jawahar &lt;rj@mobsandgeeks.com&gt;
 * @since 2.0
 */
@ValidateUsing(AssertTrueRule::class)
@Retention(RetentionPolicy.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class AssertTrue(@StringRes val messageResId: Int = -1, val message: String = "Should be true", val sequence: Int = -1)
