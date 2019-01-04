package com.wya.example.module.utils;

import android.view.GestureDetector;
import android.view.MotionEvent;

import com.wya.example.base.BaseApplication;
import com.wya.utils.utils.ScreenUtil;

public class GestureFlingRightHelper implements GestureDetector.OnGestureListener {
    private int minDistance = 180;
    private GestureRightCallBack mGestureCallBack = null;
    private static GestureFlingRightHelper mGfHelper;
    
    public GestureFlingRightHelper() {
    }
    
    public static GestureFlingRightHelper getInstance() {
        if (mGfHelper == null) {
            Class var0 = GestureFlingRightHelper.class;
            synchronized (GestureFlingRightHelper.class) {
                mGfHelper = new GestureFlingRightHelper();
            }
        }
        
        return mGfHelper;
    }
    
    public GestureDetector getGestureDetector(GestureRightCallBack callBack, int widthPixels) {
        this.minDistance = widthPixels / 3;
        this.mGestureCallBack = callBack;
        if (this.mGestureCallBack == null) {
            this.mGestureCallBack = new GestureRightCallBack() {
                @Override
                public boolean handleFlingRightAction() {
                    return false;
                }
            };
        }
        
        return new GestureDetector(this);
    }
    
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }
    
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        boolean result = false;
        if (e1 != null && e2 != null) {
            float xFling = e2.getX() - e1.getX();
            float yFling = e2.getY() - e1.getY();
            if (e1.getX() < (float) (ScreenUtil.getScreenWidth(BaseApplication.getInstance()) / 5) && xFling > (float) this.minDistance && Math.abs(yFling) < xFling / 2.0F) {
                result = this.mGestureCallBack.handleFlingRightAction();
            }
        }
        
        return result;
    }
    
    @Override
    public void onLongPress(MotionEvent e) {
    }
    
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }
    
    @Override
    public void onShowPress(MotionEvent e) {
    }
    
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }
    
    public interface GestureRightCallBack {
        boolean handleFlingRightAction();
    }
}
