package com.connorgoodwin.m03_bounce;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.Random;

/**
 * Represents a single bouncing ball object.
 * Author: Connor Goodwin
 */
public class Ball {

    private float radius = 50;
    private float x;
    private float y;
    private float speedX;
    private float speedY;
    private final RectF bounds;
    private final Paint paint;

    // Add accelerometer
    private double ax, ay, az = 0;

    private final Random r = new Random();

    private void init(int color) {
        paint.setColor(color);
        x = radius + r.nextInt(800);
        y = radius + r.nextInt(800);
        speedX = r.nextInt(10) - 5;
        speedY = r.nextInt(10) - 5;
    }

    public Ball(int color) {
        bounds = new RectF();
        paint = new Paint();
        init(color);
    }

    public Ball(int color, float x, float y, float speedX, float speedY) {
        bounds = new RectF();
        paint = new Paint();
        paint.setColor(color);
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    // Encapsulation: getters used by Rectangle
    public float getX() { return x; }
    public float getY() { return y; }
    public float getRadius() { return radius; }

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

        if (x + radius > box.getXMax()) {
            speedX = -speedX;
            x = box.getXMax() - radius;
        } else if (x - radius < box.getXMin()) {
            speedX = -speedX;
            x = box.getXMin() + radius;
        }
        if (y + radius > box.getYMax()) {
            speedY = -speedY;
            y = box.getYMax() - radius;
        } else if (y - radius < box.getYMin()) {
            speedY = -speedY;
            y = box.getYMin() + radius;
        }
    }

    public void draw(Canvas canvas) {
        bounds.set(x - radius, y - radius, x + radius, y + radius);
        canvas.drawOval(bounds, paint);
    }

    public void setX(float x) {
        this.x = x;
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
}
