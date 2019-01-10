package com.wya.uikit.imagecrop.core.elastic;

import android.animation.TypeEvaluator;
import android.graphics.RectF;

/**
 * @author : XuDonglin
 * @time : 2019-01-10
 * @description :
 */
public class ElasticRectFEvaluator implements TypeEvaluator<RectF> {
    
    private RectF mRect;
    
    public ElasticRectFEvaluator() {
    
    }
    
    public ElasticRectFEvaluator(RectF reuseRect) {
        mRect = reuseRect;
    }
    
    @Override
    public RectF evaluate(float fraction, RectF startValue, RectF endValue) {
        float left = startValue.left + (endValue.left - startValue.left) * fraction;
        float top = startValue.top + (endValue.top - startValue.top) * fraction;
        float right = startValue.right + (endValue.right - startValue.right) * fraction;
        float bottom = startValue.bottom + (endValue.bottom - startValue.bottom) * fraction;
        if (mRect == null) {
            return new RectF(left, top, right, bottom);
        } else {
            mRect.set(left, top, right, bottom);
            return mRect;
        }
    }
}
