package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Rectangle extends CustomShape {

    public Rectangle() {
    }

    public Rectangle(Coords size, Coords position, Coords speed) {
        super(size, position, speed);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawRect(
                getPosition().getX(),
                getPosition().getY(),
                getPosition().getX() + getSize().getX(),
                getPosition().getY() + getSize().getY(),
                paint
        );
    }

}
