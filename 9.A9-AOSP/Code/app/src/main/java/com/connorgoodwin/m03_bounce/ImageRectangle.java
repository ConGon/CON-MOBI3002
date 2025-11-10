package com.connorgoodwin.m03_bounce;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * Image-backed rectangle — inherits rectangle logic and renders a bitmap inside bounds.
 * Author: Connor Goodwin
 */
public class ImageRectangle extends Rectangle {

    private Bitmap bitmap;
    private final RectF imageBounds = new RectF();

    public ImageRectangle(Context context, int imageResId) {
        super(0); // color not used for bitmap drawing; keep base initialized
        // Use the rectangle's default width/height fields
        this.x = width;
        this.y = height;
        this.speedX = 10;
        this.speedY = 25;

        bitmap = BitmapFactory.decodeResource(context.getResources(), imageResId);
        // scale bitmap to rectangle bounds (double the width/height like original did)
        bitmap = Bitmap.createScaledBitmap(bitmap, (int)(width * 2), (int)(height * 2), true);
    }

    @Override
    public void draw(Canvas canvas) {
        imageBounds.set(x - width, y - height, x + width, y + height);
        // Draw the bitmap into the rectangle area
        canvas.drawBitmap(bitmap, null, imageBounds, null);
    }

}



