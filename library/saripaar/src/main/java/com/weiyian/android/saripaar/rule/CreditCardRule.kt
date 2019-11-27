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
import com.weiyian.android.saripaar.annotation.CreditCard
import com.weiyian.android.validator.CreditCardValidator
import java.util.*

/**
 * @author Ragunath Jawahar &lt;rj@mobsandgeeks.com&gt;
 * @since 2.0
 */
open class CreditCardRule protected constructor(creditCard: CreditCard) : AnnotationRule<CreditCard, String>(creditCard) {

    override fun isValid(creditCardNumber: String): Boolean {
        val types = mRuleAnnotation.cardTypes
//        val typesSet = HashSet<CreditCard.Type>(Arrays.asList<Type>(*types))
        val typesSet = HashSet<CreditCard.Type>()

        var options: Long = 0
        if (!typesSet.contains(CreditCard.Type.NONE)) {
            for (type in typesSet) {
                options += CARD_TYPE_REGISTRY[type]!!
            }
        } else {
            options = CreditCardValidator.NONE
        }

        return CreditCardValidator(options).isValid(creditCardNumber.replace("\\s".toRegex(), ""))
    }

    companion object {

        private val CARD_TYPE_REGISTRY = object : HashMap<CreditCard.Type, Long>() {
            init {
                put(CreditCard.Type.AMEX, CreditCardValidator.AMEX)
                put(CreditCard.Type.DINERS, CreditCardValidator.DINERS)
                put(CreditCard.Type.DISCOVER, CreditCardValidator.DISCOVER)
                put(CreditCard.Type.MASTERCARD, CreditCardValidator.MASTERCARD)
                put(CreditCard.Type.VISA, CreditCardValidator.VISA)
            }
        }

    }
}
