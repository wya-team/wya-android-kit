/*
 * Copyright (C) 2015 Mobs & Geeks
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.weiyian.android.saripaar.rule

import com.weiyian.android.saripaar.ContextualAnnotationRule
import com.weiyian.android.saripaar.ValidationContext

/**
 * A generic class for comparing values across two [android.view.View]s.
 *
 * @see com.weiyian.saripaar.annotation.ConfirmEmail
 *
 * @see com.weiyian.saripaar.annotation.ConfirmPassword
 *
 *
 * @author Ragunath Jawahar &lt;rj@mobsandgeeks.com&gt;
 * @since 2.0
 */
open class SameValueContextualRule<CONFIRM : Annotation, SOURCE : Annotation, DATA_TYPE> protected constructor(confirmAnnotation: CONFIRM, private val mSourceClass: Class<SOURCE>, validationContext: ValidationContext)
    : ContextualAnnotationRule<CONFIRM, DATA_TYPE>(confirmAnnotation, validationContext) {

    //    private val mConfirmClass: Class<CONFIRM> = confirmAnnotation.annotationType as Class<CONFIRM>
    private val mConfirmClass: Class<CONFIRM> = confirmAnnotation as Class<CONFIRM>

    override fun isValid(confirmValue: DATA_TYPE): Boolean {
        val sourceViews = mValidationContext.getAnnotatedViews(mSourceClass)
        val nSourceViews = sourceViews.size

        if (nSourceViews == 0) {
            val message = String.format(
                    "You should have a view annotated with '%s' to use '%s'.",
                    mSourceClass.name, mConfirmClass.name)
            throw IllegalStateException(message)
        } else if (nSourceViews > 1) {
            val message = String.format(
                    "More than 1 field annotated with '%s'.", mSourceClass.name)
            throw IllegalStateException(message)
        }

        // There's only one, then we're good to go :)
        val view = sourceViews[0]
        val sourceValue = mValidationContext.getData(view, mSourceClass)

        return confirmValue == sourceValue
    }

}
