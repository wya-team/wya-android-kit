package com.wya.uikit.badge;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
public class BadgeHelper {
    
    public static void showBadgeCenterStart(Context context, View view, String text) {
        if (view == null) {
            return;
        }
        showBadgeCenterStart(context, view, text, null, false);
    }
    
    public static void showBadgeCenterStart(Context context, View view, String text, Drawable badgeDrawable, boolean isAttach) {
        if (view == null) {
            return;
        }
        IBadgeView badgeView = new Builder(context)
                .setText(text)
                .setGravity(new Builder.Gravity(Builder.BadgeGravity.GRAVITY_CENTER_START, 0, 0))
                .setTextSize(DisplayUtil.sp2px(context, 10))
                .setAttach(isAttach)
                .setBackgroundDrawable(badgeDrawable)
                .create();
        badgeView.bindToTarget(view);
    }
    
    public static void showBadgeDrawableCenterStart(Context context, View view, Drawable badgeDrawable, boolean isAttach) {
        if (view == null) {
            return;
        }
        IBadgeView badgeView = new Builder(context)
                .setGravity(new Builder.Gravity(Builder.BadgeGravity.GRAVITY_CENTER_START, 0, 0))
                .setAttach(isAttach)
                .setBackgroundDrawable(badgeDrawable)
                .create();
        badgeView.bindToTarget(view);
    }
    
}
