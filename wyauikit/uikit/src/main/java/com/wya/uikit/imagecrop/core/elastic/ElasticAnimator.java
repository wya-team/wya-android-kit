package com.wya.uikit.imagecrop.core.elastic;

import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * @author : XuDonglin
 * @time : 2019-01-10
 * @description :
 */
public class ElasticAnimator extends ValueAnimator {

    private Elastic mElastic;

    public ElasticAnimator() {
        setEvaluator(new ElasticPointFEvaluator());
        setInterpolator(new AccelerateDecelerateInterpolator());
    }

    public ElasticAnimator(Elastic elastic) {
        this();
        setElastic(elastic);
    }

    public void setElastic(Elastic elastic) {
        mElastic = elastic;

        if (mElastic == null) {
            throw new IllegalArgumentException("Elastic cannot be null.");
        }
    }

    public void start(float x, float y) {
        setObjectValues(new PointF(x, y), mElastic.getPivot());
        start();
    }
}
