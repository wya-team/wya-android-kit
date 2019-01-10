package com.wya.uikit.imagecrop.core.elastic;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * @author : XuDonglin
 * @time : 2019-01-10
 * @description :
 */
public class ElasticPointFEvaluator implements TypeEvaluator<PointF> {

    private PointF mPoint;

    public ElasticPointFEvaluator() {

    }

    public ElasticPointFEvaluator(PointF reuse) {
        mPoint = reuse;
    }

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        float x = startValue.x + (fraction * (endValue.x - startValue.x));
        float y = startValue.y + (fraction * (endValue.y - startValue.y));

        if (mPoint != null) {
            mPoint.set(x, y);
            return mPoint;
        } else {
            return new PointF(x, y);
        }
    }
}
