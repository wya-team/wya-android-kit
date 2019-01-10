package com.wya.uikit.imagecrop.core.sticker;

import android.graphics.Matrix;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.wya.uikit.imagecrop.view.BaseStickerView;

/**
 * @author : XuDonglin
 * @time : 2019-01-10
 * @description :
 */
public class StickerAdjustHelper implements View.OnTouchListener {
    
    private static final String TAG = "StickerAdjustHelper";
    
    private View mView;
    
    private BaseStickerView mContainer;
    
    private float mCenterX, mCenterY;
    
    private double mRadius, mDegrees;
    
    private Matrix m = new Matrix();
    
    public StickerAdjustHelper(BaseStickerView container, View view) {
        mView = view;
        mContainer = container;
        mView.setOnTouchListener(this);
    }
    
    private static double toDegrees(float v, float v1) {
        return Math.toDegrees(Math.atan2(v, v1));
    }
    
    private static double toLength(float x1, float y1, float x2, float y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }
    
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                
                float x = event.getX();
                
                float y = event.getY();
                
                mCenterX = mCenterY = 0;
                
                float pointX = mView.getX() + x - mContainer.getPivotX();
                
                float pointY = mView.getY() + y - mContainer.getPivotY();
                
                Log.d(TAG, String.format("X=%f,Y=%f", pointX, pointY));
                
                mRadius = toLength(0, 0, pointX, pointY);
                
                mDegrees = toDegrees(pointY, pointX);
                
                m.setTranslate(pointX - x, pointY - y);
                
                Log.d(TAG, String.format("degrees=%f", toDegrees(pointY, pointX)));
                
                m.postRotate((float) -toDegrees(pointY, pointX), mCenterX, mCenterY);
                
                return true;
            
            case MotionEvent.ACTION_MOVE:
                
                float[] xy = {event.getX(), event.getY()};
                
                pointX = mView.getX() + xy[0] - mContainer.getPivotX();
                
                pointY = mView.getY() + xy[1] - mContainer.getPivotY();
                
                Log.d(TAG, String.format("X=%f,Y=%f", pointX, pointY));
                
                double radius = toLength(0, 0, pointX, pointY);
                
                double degrees = toDegrees(pointY, pointX);
                
                float scale = (float) (radius / mRadius);
                
                mContainer.addScale(scale);
                
                Log.d(TAG, "    D   = " + (degrees - mDegrees));
                
                mContainer.setRotation((float) (mContainer.getRotation() + degrees - mDegrees));
                
                mRadius = radius;
                
                return true;
            default:
                break;
        }
        return false;
    }
}
