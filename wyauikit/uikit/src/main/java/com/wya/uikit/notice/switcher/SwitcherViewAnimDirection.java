package com.wya.uikit.notice.switcher;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.wya.uikit.notice.switcher.SwitcherViewAnimDirection.DOWN_2_UP;
import static com.wya.uikit.notice.switcher.SwitcherViewAnimDirection.LEFT_2_RIGHT;
import static com.wya.uikit.notice.switcher.SwitcherViewAnimDirection.RIGHT_2_LEFT;
import static com.wya.uikit.notice.switcher.SwitcherViewAnimDirection.UP_2_DOWN;

@IntDef({DOWN_2_UP, UP_2_DOWN, LEFT_2_RIGHT, RIGHT_2_LEFT})
@Retention(RetentionPolicy.SOURCE)
public @interface SwitcherViewAnimDirection {
    
    int DOWN_2_UP = 0;
    
    int UP_2_DOWN = 1;
    
    int LEFT_2_RIGHT = 2;
    
    int RIGHT_2_LEFT = 3;
    
}