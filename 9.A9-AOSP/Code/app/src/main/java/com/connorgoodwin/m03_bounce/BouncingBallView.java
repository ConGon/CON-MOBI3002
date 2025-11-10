package com.connorgoodwin.m03_bounce;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

/**
 * Custom view that renders and animates bouncing balls and shapes.
 * Updated per code review feedback.
 * Author: Connor Goodwin
 */
public class BouncingBallView extends View {

    // region Constants
    private static final String TAG = "BouncingBallView";
    private static final int MAX_OBJECTS_LIMIT = 100; // replaced hardcoded 100
    private static final int FAST_SPEED = 150;
    private static final int SLOW_SPEED = 10;
    // endregion
    private final ArrayList<Ball> balls = new ArrayList<>();
    private final ArrayList<Square> squares = new ArrayList<>();
    private final ArrayList<Rectangle> rectangles = new ArrayList<>();

    // Changed from ArrayList<ImageRectangle> to a single final variable (Hudson McNeil)
    private final ImageRectangle IMAGE_RECTANGLE;

    private Square square1;
    private Rectangle rectangle1;
    private Ball ball1;
    private Box box;

    private float previousX;
    private float previousY;
    private final Random rand = new Random();

    public BouncingBallView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.v(TAG, "Constructor BouncingBallView");

        box = new Box(Color.RED);

        balls.add(new Ball(Color.GREEN));
        ball1 = balls.get(0);
        Log.w(TAG, "Added green ball");

        balls.add(new Ball(Color.CYAN));
        Log.w(TAG, "Added cyan ball");

        squares.add(new Square(Color.GREEN));
        squares.add(new Square(Color.WHITE, 5, 100, 10, 10));
        square1 = squares.get(0);

        rectangles.add(new Rectangle(Color.BLUE));
        rectangle1 = rectangles.get(0);

        IMAGE_RECTANGLE = new ImageRectangle(context, R.drawable.pizza);

        setFocusable(true);
        requestFocus();
        setFocusableInTouchMode(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        box.draw(canvas);
        drawBalls(canvas);
        drawSquares(canvas);
        drawRectangles(canvas);
        drawImageRectangle(canvas);

        // Only redraw if something is moving
        if (!balls.isEmpty() || !squares.isEmpty()) {
            invalidate();
        }
    }

    private void drawBalls(Canvas canvas) {
        for (Ball b : balls) {
            b.draw(canvas);
            b.moveWithCollisionDetection(box);
        }
    }

    private void drawSquares(Canvas canvas) {
        for (Square s : squares) {
            s.draw(canvas);
            s.moveWithCollisionDetection(box);
        }
    }

    private void drawRectangles(Canvas canvas) {
        for (Rectangle r : rectangles) {
            r.draw(canvas);
            r.moveWithCollisionDetection(box, balls, squares);
        }
    }

    private void drawImageRectangle(Canvas canvas) {
        IMAGE_RECTANGLE.draw(canvas);
        IMAGE_RECTANGLE.moveWithCollisionDetection(box, balls, squares);
    }

    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        box.set(0, 0, w, h);
        Log.w(TAG, "onSizeChanged w=" + w + " h=" + h);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float currentX = event.getX();
        float currentY = event.getY();

        float deltaX;
        float deltaY;
        float scalingFactor = 5.0f / (Math.min(box.getXMax(), box.getYMax()));

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                deltaX = currentX - previousX;
                deltaY = currentY - previousY;

                double totalSpeed = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                Log.w(TAG, "Total Swipe Speed: " + totalSpeed);

                if (totalSpeed >= 25) {
                    square1.setSpeedX(square1.getSpeedX() + deltaX * scalingFactor);
                    square1.setSpeedY(square1.getSpeedY() + deltaY * scalingFactor);
                    squares.add(new Square(Color.GREEN, previousX, previousY, deltaX, deltaY));
                } else {
                    ball1.setSpeedX(ball1.getSpeedX() + deltaX * scalingFactor);
                    ball1.setSpeedY(ball1.getSpeedY() + deltaX * scalingFactor);
                    balls.add(new Ball(Color.BLUE, previousX, previousY, deltaX, deltaY));
                }

                if (balls.size() > MAX_OBJECTS_LIMIT) {
                    Log.v(TAG, "Too many balls, clearing list");
                    balls.clear();
                    balls.add(new Ball(Color.BLACK));
                    ball1 = balls.get(0);
                }

                if (squares.size() > MAX_OBJECTS_LIMIT) {
                    Log.v(TAG, "Too many squares, clearing list");
                    squares.clear();
                    squares.add(new Square(Color.BLACK));
                    square1 = squares.get(0);
                }
                break;
        }

        previousX = currentX;
        previousY = currentY;

        invalidate();
        return true;
    }

    // renamed per J02 rule: lowercase first letter
    public void spawnBallButtonPressed() {
        Log.d(TAG, "User tapped the button ... VIEW");

        int viewWidth = getMeasuredWidth();
        int viewHeight = getMeasuredHeight();

        int x = rand.nextInt(viewWidth);
        int y = rand.nextInt(viewHeight);

        int fastOrSlow = rand.nextInt(3);
        int dx = (fastOrSlow == 1) ? FAST_SPEED : SLOW_SPEED;
        int dy = (fastOrSlow == 1) ? FAST_SPEED : SLOW_SPEED;

        squares.add(new Square(Color.GREEN, x, y, dx, dy));

        int randomDx = rand.nextInt(51);
        int randomDy = rand.nextInt(51);

        int randomRed = rand.nextInt(256);
        int randomGreen = rand.nextInt(256);
        int randomBlue = rand.nextInt(256);

        balls.add(new Ball(Color.rgb(randomRed, randomGreen, randomBlue), x, y, randomDx, randomDy));
        invalidate();
    }
}
