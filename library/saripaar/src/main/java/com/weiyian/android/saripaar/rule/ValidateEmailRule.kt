package com.weiyian.android.saripaar.rule

import com.weiyian.android.saripaar.ValidationContext
import com.weiyian.android.saripaar.annotation.ConfirmEmail
import com.weiyian.android.saripaar.annotation.Email

/**
 * @author :
 */
open class ValidateEmailRule protected constructor(confirmEmail: ConfirmEmail, validationContext: ValidationContext) : SameValueContextualRule<ConfirmEmail, Email, String>(confirmEmail, Email::class.java, validationContext) {

    override fun isValid(confirmValue: String): Boolean {
        return super.isValid(confirmValue)
    }

}
