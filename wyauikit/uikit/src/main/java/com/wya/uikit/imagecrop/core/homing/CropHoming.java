package com.wya.uikit.imagecrop.core.homing;

/**
 * @author : XuDonglin
 * @time : 2019-01-10
 * @description :
 */
public class CropHoming {
    
    public float x, y;
    
    public float scale;
    
    public float rotate;
    
    public CropHoming(float x, float y, float scale, float rotate) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.rotate = rotate;
    }
    
    public static boolean isRotate(CropHoming sHoming, CropHoming eHoming) {
        return Float.compare(sHoming.rotate, eHoming.rotate) != 0;
    }
    
    public void set(float x, float y, float scale, float rotate) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.rotate = rotate;
    }
    
    public void concat(CropHoming homing) {
        this.scale *= homing.scale;
        this.x += homing.x;
        this.y += homing.y;
    }
    
    public void rConcat(CropHoming homing) {
        this.scale *= homing.scale;
        this.x -= homing.x;
        this.y -= homing.y;
    }
    
    @Override
    public String toString() {
        return "CropHoming{" +
                "x=" + x +
                ", y=" + y +
                ", scale=" + scale +
                ", rotate=" + rotate +
                '}';
    }
}
