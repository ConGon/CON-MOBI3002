package com.example.m03_bounce;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;


/**
 * Created by Russ on 08/04/2014.
 */
public class Rectangle {

    float height = 100;
    float width = 150;
    int collisionCount = 0;
    float halfHeight = height / 2;
    float halfWidth = width / 2;
    float x;                // Ball's center (x,y)
    float y;
    float speedX;           // Ball's speed
    float speedY;
    private RectF bounds;   // Needed for Canvas.drawOval
    private Paint paint;    // The paint style, color used for drawing

    // Add accelerometer
    // Add ... implements SensorEventListener
    private double ax, ay, az = 0; // acceration from different axis

    public void setAcc(double ax, double ay, double az){
        this.ax = ax;
        this.ay = ay;
        this.az = az;
    }

    Random r = new Random();  // seed random number generator

    // Constructor
    public Rectangle(int color) {
        bounds = new RectF();
        paint = new Paint();
        paint.setColor(color);

        // random position and speed
        x = width;
        y = height;
        speedX = r.nextInt(10) - 5;
        speedY = r.nextInt(10) - 5;
    }

    // Constructor
    public Rectangle(int color, float x, float y, float speedX, float speedY) {
        bounds = new RectF();
        paint = new Paint();
        paint.setColor(color);

        // use parameter position and speed
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public void moveWithCollisionDetection(Box box, ArrayList<Ball> balls, ArrayList<Square> squares) {
        // Get new (x,y) position
        x += speedX;
        y += speedY;

        // Add acceleration to speed
        speedX += ax;
        speedY += ay;

        for (Ball ball : balls) {
            float closestX = Math.max(x - this.width, Math.min(ball.x, x + this.width));
            float closestY = Math.max(y - this.height, Math.min(ball.y, y + this.height));

            float dx = ball.x - closestX;
            float dy = ball.y - closestY;

            if (dx*dx + dy*dy < ball.radius * ball.radius) {
                this.collisionCount++;
                Log.w("COLLISION", "Rectangle hit ball!" + " Collision Count: " + this.collisionCount);
            }
        }



        // Detect collision and react
        if (x + width > box.xMax) {
            speedX = -speedX;
            x = box.xMax - width;
        } else if (x - width < box.xMin) {
            speedX = -speedX;
            x = box.xMin + width;
        }
        if (y + height > box.yMax) {
            speedY = -speedY;
            y = box.yMax - height;
        } else if (y - height < box.yMin) {
            speedY = -speedY;
            y = box.yMin + height;
        }
    }

    public void draw(Canvas canvas) {
        bounds.set(x - width, y - height, x + width, y + height);
        canvas.drawRect(bounds, paint);
    }

}
