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

import android.annotation.SuppressLint

import com.weiyian.android.saripaar.AnnotationRule
import com.weiyian.android.saripaar.annotation.Length

/**
 * @author Ragunath Jawahar &lt;rj@mobsandgeeks.com&gt;
 * @since 2.0
 */
class LengthRule constructor(length: Length) : AnnotationRule<Length, String>(length) {

    override fun isValid(text: String?): Boolean {
        if (text == null) {
            throw IllegalArgumentException("'text' cannot be null.")
        }
        val ruleMin = mRuleAnnotation.min
        val ruleMax = mRuleAnnotation.max

        // Assert min is <= max
        assertMinMax(ruleMin, ruleMax)

        // Trim?
        val length = if (mRuleAnnotation.trim) text.trim { it <= ' ' }.length else text.length

        // Check for min length
        var minIsValid = true
        if (ruleMin != Integer.MIN_VALUE) { // Min is set
            minIsValid = length >= ruleMin
        }

        // Check for max length
        var maxIsValid = true
        if (ruleMax != Integer.MAX_VALUE) { // Max is set
            maxIsValid = length <= ruleMax
        }

        return minIsValid && maxIsValid
    }

    private fun assertMinMax(min: Int, max: Int) {
        if (min > max) {
            @SuppressLint("DefaultLocale") val message = String.format("'min' (%d) should be less than or equal to 'max' (%d).", min, max)
            throw IllegalStateException(message)
        }
    }
}
