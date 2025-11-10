package com.connorgoodwin.m03_bounce;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Simple bounding box used by shapes.
 * Author: Connor Goodwin
 */
public class Box {

    private int xMin, xMax, yMin, yMax;
    private final Paint paint;  // paint style and color
    private final Rect bounds;

    public Box(int color) {
        paint = new Paint();
        paint.setColor(color);
        bounds = new Rect();
    }

    /**
     * Set the box using origin (x,y) and width/height.
     */
    public void set(int x, int y, int width, int height) {
        xMin = x;
        xMax = x + width - 1;
        yMin = y;
        yMax = y + height - 1;
        bounds.set(xMin, yMin, xMax, yMax);
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(bounds, paint);
    }

    // Accessors for other classes

    public int getXMin() {
        return xMin;
    }

    public void setXMin(int xMin) {
        this.xMin = xMin;
    }

    public int getXMax() {
        return xMax;
    }

    public void setXMax(int xMax) {
        this.xMax = xMax;
    }

    public int getYMin() {
        return yMin;
    }

    public void setYMin(int yMin) {
        this.yMin = yMin;
    }

    public int getYMax() {
        return yMax;
    }

    public void setYMax(int yMax) {
        this.yMax = yMax;
    }
}
