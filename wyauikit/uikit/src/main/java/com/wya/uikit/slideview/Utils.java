package com.wya.uikit.slideview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class Utils {
    
    public static Bitmap drawableToBitmap(int size, Drawable drawable) {
        Bitmap bitmap = null;
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            bitmap = bitmapDrawable.getBitmap();
            if (bitmap != null && bitmap.getHeight() > 0) {
                Matrix matrix = new Matrix();
                float scaleHeight = size * 1.0f / bitmapDrawable.getIntrinsicHeight();
                matrix.postScale(scaleHeight, scaleHeight);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                return bitmap;
            }
        }
        bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
    
    public static int dp2px(Context context, float dpValue) {
        if (context == null || compareFloat(0f, dpValue) == 0) return 0;
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    
    public static int sp2px(Context context, float sp) {
        return (int) (getFontDensity(context) * sp + 0.5);
    }
    
    public static float getFontDensity(Context context) {
        return context.getResources().getDisplayMetrics().scaledDensity;
    }
    
    private static int compareFloat(float a, float b) {
        int ta = Math.round(a * 100000);
        int tb = Math.round(b * 100000);
        if (ta > tb) {
            return 1;
        } else if (ta < tb) {
            return -1;
        } else {
            return 0;
        }
    }
}
