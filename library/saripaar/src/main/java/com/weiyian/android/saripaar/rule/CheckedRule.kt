package com.weiyian.android.saripaar.rule

import com.weiyian.android.saripaar.AnnotationRule
import com.weiyian.android.saripaar.annotation.Checked

/**
 * @author :
 */
open class CheckedRule protected constructor(checked: Checked) : AnnotationRule<Checked, Boolean>(checked) {

    override fun isValid(value: Boolean?): Boolean {
        if (value == null) {
            throw IllegalArgumentException("'data' cannot be null.")
        }
        return mRuleAnnotation.value == value
    }

}
