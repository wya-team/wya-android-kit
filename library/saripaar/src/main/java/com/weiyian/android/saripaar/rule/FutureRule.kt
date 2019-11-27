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
import com.weiyian.android.saripaar.annotation.Future
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Ragunath Jawahar &lt;rj@mobsandgeeks.com&gt;
 * @since 2.0
 */
class FutureRule protected constructor(future: Future, validationContext: ValidationContext) : ContextualAnnotationRule<Future, String>(future, validationContext) {

    private val dateFormat: DateFormat
        get() {
            val context = mValidationContext.context
            val dateFormatResId = mRuleAnnotation.dateFormatResId
            val dateFormatString = if (dateFormatResId != -1)
                context.getString(dateFormatResId)
            else
                mRuleAnnotation.dateFormat
            return SimpleDateFormat(dateFormatString)
        }

    override fun isValid(dateString: String): Boolean {
        val dateFormat = dateFormat
        var parsedDate: Date? = null
        try {
            parsedDate = dateFormat.parse(dateString)
        } catch (ignored: ParseException) {
        }

        val now = Date()
        return parsedDate != null && parsedDate.after(now)
    }
}
