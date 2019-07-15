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

import com.weiyian.android.saripaar.AnnotationRule
import com.weiyian.android.saripaar.annotation.Password
import java.util.*

/**
 * @author Ragunath Jawahar &lt;rj@mobsandgeeks.com&gt;
 * @since 2.0
 */
open class PasswordRule protected constructor(password: Password) : AnnotationRule<Password, String>(password) {

    /*
     * http://stackoverflow.com/questions/1559751/
     * regex-to-make-sure-that-the-string-contains-at-least-one-lower-case-char-upper
     */
    private val SCHEME_PATTERNS = object : HashMap<Password.Scheme, String>() {
        init {
            put(Password.Scheme.ANY, ".+")
            put(Password.Scheme.ALPHA, "\\w+")
            put(Password.Scheme.ALPHA_MIXED_CASE, "(?=.*[a-z])(?=.*[A-Z]).+")
            put(Password.Scheme.NUMERIC, "\\d+")
            put(Password.Scheme.ALPHA_NUMERIC, "(?=.*[a-zA-Z])(?=.*[\\d]).+")
            put(Password.Scheme.ALPHA_NUMERIC_MIXED_CASE,
                    "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d]).+")
            put(Password.Scheme.ALPHA_NUMERIC_SYMBOLS,
                    "(?=.*[a-zA-Z])(?=.*[\\d])(?=.*([^\\w]|_)).+")
            put(Password.Scheme.ALPHA_NUMERIC_MIXED_CASE_SYMBOLS,
                    "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*([^\\w]|_)).+")
        }
    }

    override fun isValid(password: String): Boolean {
        val hasMinChars = password.length >= mRuleAnnotation.min
        val matchesScheme = password.matches(SCHEME_PATTERNS[mRuleAnnotation.scheme]!!.toRegex())
        return hasMinChars && matchesScheme
    }
}
