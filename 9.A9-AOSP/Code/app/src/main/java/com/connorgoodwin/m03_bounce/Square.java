package com.connorgoodwin.m03_bounce;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.Random;

/**
 * Square shape used in the bouncing view.
 * Reworked to use sideLength (no leftover 'middle' artifact).
 * Author: Connor Goodwin
 */
public class Square {

    private static final int DEFAULT_SIDE = 100;

    private final RectF bounds;
    private final Paint paint;
    private final Random r = new Random();

    private float sideLength;
    private float x;
    private float y;
    float speedX; // package-visible so BouncingBallView may adjust speed directly
    float speedY;

    // acceleration
    private double ax, ay, az = 0;

    public Square(int color) {
        this(color, DEFAULT_SIDE);
    }

    public Square(int color, float sideLength) {
        bounds = new RectF();
        paint = new Paint();
        paint.setColor(color);
        this.sideLength = sideLength;

        // random position and speed (keeps prior behavior)
        this.x = sideLength + r.nextInt(800);
        this.y = sideLength + r.nextInt(800);
        this.speedX = r.nextInt(10) - 5;
        this.speedY = r.nextInt(10) - 5;
    }

    public Square(int color, float x, float y, float speedX, float speedY) {
        bounds = new RectF();
        paint = new Paint();
        paint.setColor(color);

        this.sideLength = DEFAULT_SIDE;
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public void setAcc(double ax, double ay, double az) {
        this.ax = ax;
        this.ay = ay;
        this.az = az;
    }

    public void moveWithCollisionDetection(Box box) {
        x += speedX;
        y += speedY;

        speedX += ax;
        speedY += ay;

        if (x + sideLength > box.getXMax()) {
            speedX = -speedX;
            x = box.getXMax() - sideLength;
        } else if (x - sideLength < box.getXMin()) {
            speedX = -speedX;
            x = box.getXMin() + sideLength;
        }
        if (y + sideLength > box.getYMax()) {
            speedY = -speedY;
            y = box.getYMax() - sideLength;
        } else if (y - sideLength < box.getYMin()) {
            speedY = -speedY;
            y = box.getYMin() + sideLength;
        }
    }

    public void draw(Canvas canvas) {
        bounds.set(x - sideLength, y - sideLength, x + sideLength, y + sideLength);
        canvas.drawRect(bounds, paint);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    public float getSideLength() {
        return sideLength;
    }

    public void setSideLength(float sideLength) {
        this.sideLength = sideLength;
    }
}
