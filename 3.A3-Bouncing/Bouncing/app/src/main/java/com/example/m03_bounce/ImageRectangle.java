package com.example.m03_bounce;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.Random;

public class ImageRectangle {

    float height = 100;
    float width = 150;
    float x;
    float y;
    float speedX;
    float speedY;

    private RectF bounds;
    private Bitmap bitmap;

    // acceleration if you want
    private double ax, ay, az = 0;

    public void setAcc(double ax, double ay, double az){
        this.ax = ax;
        this.ay = ay;
        this.az = az;
    }

    Random r = new Random();

    public ImageRectangle(Context context, int imageResId) {
        bounds = new RectF();

        this.x = width;
        this.y = height;


        this.speedX = 10;
        this.speedY = 25;


        bitmap = BitmapFactory.decodeResource(context.getResources(), imageResId);
        bitmap = Bitmap.createScaledBitmap(bitmap, (int)(width*2), (int)(height*2), true);
    }

    public void moveWithCollisionDetection(Box box) {
        x += speedX;
        y += speedY;

        speedX += ax;
        speedY += ay;

        // bounce off box
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
        canvas.drawBitmap(bitmap, null, bounds, null);
    }
}

