package com.wya.uikit.imagecrop.core.elastic;

import android.graphics.PointF;

/**
 * @author : XuDonglin
 * @time : 2019-01-10
 * @description :
 */
public class Elastic {

    private float width, height;

    private PointF pivot = new PointF();

    public Elastic() {

    }

    public Elastic(float x, float y) {
        pivot.set(x, y);
    }

    public Elastic(float x, float y, float width, float height) {
        pivot.set(x, y);
        this.width = width;
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getX() {
        return pivot.x;
    }

    public void setX(float x) {
        pivot.x = x;
    }

    public float getY() {
        return pivot.y;
    }

    public void setY(float y) {
        pivot.y = y;
    }

    public void setXY(float x, float y) {
        pivot.set(x, y);
    }

    public PointF getPivot() {
        return pivot;
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void set(float x, float y, float width, float height) {
        pivot.set(x, y);
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        return "Elastic{" +
                "width=" + width +
                ", height=" + height +
                ", pivot=" + pivot +
                '}';
    }
}
