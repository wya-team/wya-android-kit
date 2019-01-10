/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.wya.uikit.gallery.photoview;

interface OnGestureListener {
    
    /**
     * onDrag
     *
     * @param dx
     * @param dy
     */
    void onDrag(float dx, float dy);
    
    /**
     * onFling
     *
     * @param startX
     * @param startY
     * @param velocityX
     * @param velocityY
     */
    void onFling(float startX, float startY, float velocityX,
                 float velocityY);
    
    /**
     * onScale
     *
     * @param scaleFactor
     * @param focusX
     * @param focusY
     */
    void onScale(float scaleFactor, float focusX, float focusY);
    
}