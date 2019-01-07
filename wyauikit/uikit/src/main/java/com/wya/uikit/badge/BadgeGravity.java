package com.wya.uikit.badge;

import android.support.annotation.IntDef;
@IntDef
public @interface BadgeGravity {
    
    int GRAVITY_START_TOP = android.view.Gravity.START | android.view.Gravity.TOP;
    int GRAVITY_END_TOP = android.view.Gravity.END | android.view.Gravity.TOP;
    int GRAVITY_START_BOTTOM = android.view.Gravity.START | android.view.Gravity.BOTTOM;
    int GRAVITY_END_BOTTOM = android.view.Gravity.END | android.view.Gravity.BOTTOM;
    
    int GRAVITY_CENTER = android.view.Gravity.CENTER;
    
    int GRAVITY_CENTER_TOP = android.view.Gravity.CENTER | android.view.Gravity.TOP;
    int GRAVITY_CENTER_BOTTOM = android.view.Gravity.CENTER | android.view.Gravity.BOTTOM;
    int GRAVITY_CENTER_START = android.view.Gravity.CENTER | android.view.Gravity.START;
    int GRAVITY_CENTER_END = android.view.Gravity.CENTER | android.view.Gravity.END;
    
}