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

package com.weiyian.android.saripaar;

import java.lang.annotation.Annotation;

/**
 * @author :
 */
public abstract class ContextualAnnotationRule<RULE_ANNOTATION extends Annotation, DATA_TYPE> extends AnnotationRule<RULE_ANNOTATION, DATA_TYPE> {
    
    protected ValidationContext mValidationContext;
    
    /**
     * Constructor. All subclasses MUST have a constructor with the same signature.
     *
     * @param ruleAnnotation    The rule {@link Annotation} instance to which this rule is paired.
     * @param validationContext A {@link ValidationContext}.
     */
    protected ContextualAnnotationRule(final RULE_ANNOTATION ruleAnnotation, final ValidationContext validationContext) {
        super(ruleAnnotation);
        if (validationContext == null) {
            throw new IllegalArgumentException("'validationContext' cannot be null.");
        }
        mValidationContext = validationContext;
    }
    
}
