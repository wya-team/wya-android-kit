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
import com.weiyian.android.saripaar.annotation.NotEmpty

/**
 * @author Ragunath Jawahar &lt;rj@mobsandgeeks.com&gt;
 * @since 2.0
 */
open class NotEmptyRule protected constructor(notEmpty: NotEmpty, validationContext: ValidationContext) : ContextualAnnotationRule<NotEmpty, String>(notEmpty, validationContext) {

    override fun isValid(data: String?): Boolean {
        var isEmpty = true
        if (data != null) {
            val text = if (mRuleAnnotation.trim) data.trim { it <= ' ' } else data

            val context = mValidationContext.context
            val emptyText = if (mRuleAnnotation.emptyTextResId != -1)
                context.getString(mRuleAnnotation.emptyTextResId)
            else
                mRuleAnnotation.emptyText

            isEmpty = emptyText == text || "" == text
        }
        return !isEmpty
    }

}
