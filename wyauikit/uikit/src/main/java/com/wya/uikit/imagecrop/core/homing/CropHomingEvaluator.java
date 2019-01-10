package com.wya.uikit.imagecrop.core.homing;

import android.animation.TypeEvaluator;

/**
 * @author : XuDonglin
 * @time : 2019-01-10
 * @description :
 */
public class CropHomingEvaluator implements TypeEvaluator<CropHoming> {
    
    private CropHoming homing;
    
    public CropHomingEvaluator() {
    
    }
    
    public CropHomingEvaluator(CropHoming homing) {
        this.homing = homing;
    }
    
    @Override
    public CropHoming evaluate(float fraction, CropHoming startValue, CropHoming endValue) {
        float x = startValue.x + fraction * (endValue.x - startValue.x);
        float y = startValue.y + fraction * (endValue.y - startValue.y);
        float scale = startValue.scale + fraction * (endValue.scale - startValue.scale);
        float rotate = startValue.rotate + fraction * (endValue.rotate - startValue.rotate);
        
        if (homing == null) {
            homing = new CropHoming(x, y, scale, rotate);
        } else {
            homing.set(x, y, scale, rotate);
        }
        
        return homing;
    }
}
