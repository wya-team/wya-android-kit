/*
 * Copyright (C) 2014 Mobs & Geeks
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
import com.weiyian.android.saripaar.annotation.Pattern

import com.weiyian.android.validator.RegexValidator

/**
 * @author Ragunath Jawahar &lt;rj@mobsandgeeks.com&gt;
 * @since 2.0
 */
open class PatternRule protected constructor(pattern: Pattern, validationContext: ValidationContext) : ContextualAnnotationRule<Pattern, String>(pattern, validationContext) {

    override fun isValid(text: String): Boolean {
        val regexResId = mRuleAnnotation.regexResId
        val regex = if (regexResId != -1)
            mValidationContext.context.getString(regexResId)
        else
            mRuleAnnotation.regex
        val caseSensitive = mRuleAnnotation.caseSensitive
        return RegexValidator(regex, caseSensitive).isValid(text)
    }
}
