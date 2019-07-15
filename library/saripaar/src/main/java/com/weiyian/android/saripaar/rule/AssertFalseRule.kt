package com.weiyian.android.saripaar.rule

import com.weiyian.android.saripaar.AnnotationRule
import com.weiyian.android.saripaar.annotation.AssertFalse

/**
 * @author :
 */
open class AssertFalseRule protected constructor(assertFalse: AssertFalse) : AnnotationRule<AssertFalse, Boolean>(assertFalse) {

    override fun isValid(value: Boolean?): Boolean {
        if (value == null) {
            throw IllegalArgumentException("'data' cannot be null.")
        }
        return !value
    }

}
