package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.logging.Logger;

public class CustomShape {
    private Coords size;
    private Coords position;
    private Coords speed;
    private Paint paint;

    public CustomShape() {
        this.size = new Coords(0, 0);
        this.position = new Coords(0, 0);
        this.speed = new Coords(0, 0);
    }

    // This constructor is used to define the relativity
    // to another thing. So if I say i want my shape to
    // be at 0,0, normally that means top left of the screen.
    // But if i define a bounds. if the position of that bounds
    // is 100, 100, then 0,0 to my shape will be in the top left
    // corner of the bounds, and not the screen.
    public CustomShape(
            Coords size,
            Coords position,
            Coords speed,
            Rectangle bounds,
            int r,
            int g,
            int b
    ) {
        this.paint = new Paint();
        this.paint.setColor(Color.rgb(r, g, b));

        float boundsPosX = bounds.getPosition().getX();
        float boundsPosY = bounds.getPosition().getY();
        float boundsSizeX = bounds.getSize().getX();
        float boundsSizeY = bounds.getSize().getY();

        // Put my new shape at bounds 0,0, then add the argument position.
        // if that position is outside of the bounds, then put my shape at the very edge.

        float newX = Math.min(
                // If the position I am trying to place the shape is less
                // than the left side of the bound, then this equals the left side
                Math.max(
                        position.getX() + boundsPosX + size.getX(),
                        boundsPosX + size.getX()), // represents the left side,

                (boundsPosX + boundsSizeX) - size.getX());


        float newY = Math.min(
                Math.max(
                        position.getY() + boundsPosY + size.getY(),
                        boundsPosY + size.getY()),

                boundsPosY + boundsSizeY - size.getY());

        this.position = new Coords(newX, newY);
        this.size = size;
        this.speed = speed;
    }

    public CustomShape(Coords size, Coords position, Coords speed) {
        this.size = size;
        this.position = position;
        this.speed = speed;
    }

    public Coords getMiddle() {
        float middleX = this.position.getX() + (this.size.getX() / 2);
        float middleY = this.position.getY() + (this.size.getY() / 2);

        return new Coords(middleX, middleY);
    }

    public void draw(Canvas canvas, Paint paint) {

    }

    public WallName collisionDetect(Rectangle bounds) {
        float x = this.position.getX();
        float y = this.position.getY();

        float boundsPosX = bounds.getPosition().getX();
        float boundsPosY = bounds.getPosition().getY();
        float boundsSizeX = bounds.getSize().getX();
        float boundsSizeY = bounds.getSize().getY();

        if (x - this.size.getX() <= boundsPosX) {
            Log.w("WALL HIT", "LEFT" + x + " | " + boundsPosX);
            return WallName.LEFT;

        } else if (x + this.size.getX() >= boundsPosX + boundsSizeX) {
            Log.w("WALL HIT", "RIGHT");
            return WallName.RIGHT;
        }

        if (y - this.size.getY() <= boundsPosY) {
            Log.w("WALL HIT", "TOP");
            return WallName.TOP;

        } else if (y + this.size.getY() >= boundsPosY + boundsSizeY) {
            Log.w("WALL HIT", "BOTTOM");
            return WallName.BOTTOM;
        }

        return WallName.NONE;
    }

    public void handleCollision(Rectangle bounds) {
        WallName wall = collisionDetect(bounds);

        switch (wall) {
            case TOP:
                this.speed.setY(Math.abs(this.getSpeed().getY()));
                break;

            case BOTTOM:
                this.speed.setY(-this.getSpeed().getY());
                break;

            case LEFT:
                this.speed.setX(Math.abs(this.getSpeed().getX()));
                break;

            case RIGHT:
                this.speed.setX(-this.getSpeed().getX());
                break;

            default:
                break;

        }
    }

    public void update(Rectangle bounds) {
        float posX = this.position.getX();
        float posY = this.position.getY();
        float spdX = this.speed.getX();
        float spdY = this.speed.getY();

        float newX = posX + spdX;
        float newY = posY + spdY;

        this.position.setX(newX);
        this.position.setY(newY);

        handleCollision(bounds);
    }

    public Coords getSize() {
        return size;
    }

    public void setSize(Coords size) {
        this.size = size;
    }

    public Coords getPosition() {
        return position;
    }

    public void setPosition(Coords position) {
        this.position = position;
    }

    public Coords getSpeed() {
        return speed;
    }

    public void setSpeed(Coords speed) {
        this.speed = speed;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }
}
