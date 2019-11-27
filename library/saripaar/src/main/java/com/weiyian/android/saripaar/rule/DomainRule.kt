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

import com.weiyian.android.saripaar.AnnotationRule
import com.weiyian.android.saripaar.annotation.Domain

import com.weiyian.android.validator.DomainValidator

/**
 * @author Ragunath Jawahar &lt;rj@mobsandgeeks.com&gt;
 * @since 2.0
 */
class DomainRule protected constructor(domain: Domain) : AnnotationRule<Domain, String>(domain) {

    override fun isValid(domain: String): Boolean {
        val allowLocal = mRuleAnnotation.allowLocal
        val domainValidator = DomainValidator.getInstance(allowLocal)
        return domainValidator.isValid(domain)
    }
}
