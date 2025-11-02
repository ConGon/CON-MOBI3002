package com.example.m03_bounce;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.Random;


/**
 * Created by Russ on 08/04/2014.
 */
public class Square {

    float middle = 50;
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
    public Square(int color) {
        bounds = new RectF();
        paint = new Paint();
        paint.setColor(color);

        // random position and speed
        x = middle + r.nextInt(800);
        y = middle + r.nextInt(800);
        speedX = r.nextInt(10) - 5;
        speedY = r.nextInt(10) - 5;
    }

    // Constructor
    public Square(int color, float x, float y, float speedX, float speedY) {
        bounds = new RectF();
        paint = new Paint();
        paint.setColor(color);

        // use parameter position and speed
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public void moveWithCollisionDetection(Box box) {
        // Get new (x,y) position
        x += speedX;
        y += speedY;

        // Add acceleration to speed
        speedX += ax;
        speedY += ay;

        // Detect collision and react
        if (x + middle > box.xMax) {
            speedX = -speedX;
            x = box.xMax - middle;
        } else if (x - middle < box.xMin) {
            speedX = -speedX;
            x = box.xMin + middle;
        }
        if (y + middle > box.yMax) {
            speedY = -speedY;
            y = box.yMax - middle;
        } else if (y - middle < box.yMin) {
            speedY = -speedY;
            y = box.yMin + middle;
        }
    }

    public void draw(Canvas canvas) {
        bounds.set(x - middle, y - middle, x + middle, y + middle);
        canvas.drawRect(bounds, paint);
    }

}
