package com.connorgoodwin.m03_bounce;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * Rectangle shape used in the bouncing view.
 * Simplified and cleaned (no halfWidth/halfHeight artifacts).
 * Author: Connor Goodwin
 */
public class Rectangle {

    private static final String TAG = "Rectangle";

    protected float height = 100;
    protected float width = 150;
    private int collisionCount = 0;

    protected float x;
    protected float y;
    protected float speedX;
    protected float speedY;

    private final RectF bounds;
    private final Paint paint;

    // acceleration
    private double ax, ay, az = 0;

    private final Random r = new Random();

    // Constructor (keeps previous default behavior but uses fields properly)
    public Rectangle(int color) {
        bounds = new RectF();
        paint = new Paint();
        paint.setColor(color);

        // initial position and speed (mirrors prior code's simple defaults)
        x = width;
        y = height;
        speedX = r.nextInt(10) - 5;
        speedY = r.nextInt(10) - 5;
    }

    public Rectangle(int color, float x, float y, float speedX, float speedY) {
        bounds = new RectF();
        paint = new Paint();
        paint.setColor(color);

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

    public void moveWithCollisionDetection(Box box, ArrayList<Ball> balls, ArrayList<Square> squares) {
        x += speedX;
        y += speedY;

        speedX += ax;
        speedY += ay;

        // Check collisions against balls (use Ball getters)
        for (Ball ball : balls) {
            float closestX = Math.max(x - width, Math.min(ball.getX(), x + width));
            float closestY = Math.max(y - height, Math.min(ball.getY(), y + height));

            float dx = ball.getX() - closestX;
            float dy = ball.getY() - closestY;

            if (dx * dx + dy * dy < ball.getRadius() * ball.getRadius()) {
                this.collisionCount++;
                Log.w(TAG, "Rectangle hit ball! Collision Count: " + this.collisionCount);
            }
        }

        if (x + width > box.getXMax()) {
            speedX = -speedX;
            x = box.getXMax() - width;
        } else if (x - width < box.getXMin()) {
            speedX = -speedX;
            x = box.getXMin() + width;
        }
        if (y + height > box.getYMax()) {
            speedY = -speedY;
            y = box.getYMax() - height;
        } else if (y - height < box.getYMin()) {
            speedY = -speedY;
            y = box.getYMin() + height;
        }
    }

    public void draw(Canvas canvas) {
        bounds.set(x - width, y - height, x + width, y + height);
        canvas.drawRect(bounds, paint);
    }

    // Getters for subclass / external usage if needed
    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
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
}
