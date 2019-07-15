package com.weiyian.android.saripaar.annotation

import android.support.annotation.StringRes

import com.weiyian.android.saripaar.rule.CreditCardRule
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * @author Ragunath Jawahar &lt;rj@mobsandgeeks.com&gt;
 * @since 2.0
 */
@ValidateUsing(CreditCardRule::class)
@Retention(RetentionPolicy.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class CreditCard(val cardTypes: Array<Type> = [Type.AMEX, Type.DINERS, Type.DISCOVER, Type.MASTERCARD, Type.VISA], @StringRes val messageResId: Int = -1, val message: String = "Invalid card", val sequence: Int = -1) {

    enum class Type {
        AMEX,
        DINERS,
        DISCOVER,
        MASTERCARD,
        VISA,
        NONE
    }

}
