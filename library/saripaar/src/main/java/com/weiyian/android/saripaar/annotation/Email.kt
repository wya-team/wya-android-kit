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

package com.weiyian.android.saripaar.annotation

import android.support.annotation.StringRes

import com.weiyian.android.saripaar.rule.EmailRule
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * @author Ragunath Jawahar &lt;rj@mobsandgeeks.com&gt;
 * @since 1.0
 */
@ValidateUsing(EmailRule::class)
@Retention(RetentionPolicy.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class Email(val allowLocal: Boolean = false, @StringRes val messageResId: Int = -1, val message: String = "Invalid email", val sequence: Int = -1)
