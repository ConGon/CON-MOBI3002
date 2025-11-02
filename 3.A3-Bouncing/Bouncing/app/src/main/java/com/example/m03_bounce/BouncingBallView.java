package com.example.m03_bounce;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.Image;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Russ on 08/04/2014.
 */
public class BouncingBallView extends View {

    private ArrayList<Ball> balls = new ArrayList<Ball>(); // list of Balls
    private ArrayList<Square> squares = new ArrayList<Square>();

    private ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
    private ArrayList<ImageRectangle> imageRectangles = new ArrayList<ImageRectangle>();
    private Square square_1;
    private Rectangle rectangle_1;
    private ImageRectangle imageRectangle_1;
    private Ball ball_1;  // use this to reference first ball in arraylist

    private Box box;

    // For touch inputs - previous touch (x, y)
    private float previousX;
    private float previousY;

    public BouncingBallView(Context context, AttributeSet attrs) {
        super(context, attrs);

        Log.v("BouncingBallView", "Constructor BouncingBallView");

        // create the box
        box = new Box(Color.RED);  // ARGB

        //ball_1 = new Ball(Color.GREEN);
        balls.add(new Ball(Color.GREEN));
        ball_1 = balls.get(0);  // points ball_1 to the first; (zero-ith) element of list
        Log.w("BouncingBallLog", "Just added a bouncing ball");

        //ball_2 = new Ball(Color.CYAN);
        balls.add(new Ball(Color.CYAN));
        Log.w("BouncingBallLog", "Just added another bouncing ball");

        squares.add(new Square(Color.GREEN));
        squares.add(new Square(Color.WHITE, 5, 100, 10, 10));
        square_1 = squares.get(0);

        rectangles.add(new Rectangle(Color.BLUE));
        rectangle_1 = rectangles.get(0);

        imageRectangles.add(new ImageRectangle(context, R.drawable.pizza));






        // To enable keypad
        this.setFocusable(true);
        this.requestFocus();
        // To enable touch mode
        this.setFocusableInTouchMode(true);
    }

    // Called back to draw the view. Also called after invalidate().
    @Override
    protected void onDraw(Canvas canvas) {

//        Log.v("BouncingBallView", "onDraw");

        // Draw the components
        box.draw(canvas);
        //canvas.drawARGB(0,25,25,25);
        //canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);


        for (Ball b : balls) {
            b.draw(canvas);  //draw each ball in the list
            b.moveWithCollisionDetection(box);  // Update the position of the ball

//                /// check if ball is in this square
//                float rect_x1 = 100;
//                float rect_x2 = 200;
//                float rect_y1 = 250;
//                float rect_y2 = 350;
//
//                //check if x1-x2 is small AND y1-y2 is small
//                if ((b.x>rect_x1) &&
//                        (b.x<rect_x2) &&
//                        (b.y>rect_y1) &&
//                        (b.y<rect_y2)) {
//                    // Hit .. double speed
//                    b.speedX = 2* b.speedX;
//                    b.speedY = 2* b.speedY;
//                    Log.w("Bounce", "HIT!");
//                }

        }

        for (Square s : squares) {
            s.draw(canvas);  //draw each square in the list
            s.moveWithCollisionDetection(box);  // Update the position of the square
//            Log.w("SQUARE LOG", "SQUARE WAS DRAWN");
        }

        for (Rectangle r: rectangles) {
            r.draw(canvas);
            r.moveWithCollisionDetection(box, balls, squares);
        }

        for (ImageRectangle ir: imageRectangles) {
            ir.draw(canvas);
            ir.moveWithCollisionDetection(box);
        }

        // Delay on UI thread causes big problems!
        // Simulates doing busy work or waits on UI (DB connections, Network I/O, ....)
        //  I/Choreographer? Skipped 64 frames!  The application may be doing too much work on its main thread.
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//        }

        // Check what happens if you draw the box last
        //box.draw(canvas);

        // Check what happens if you don't call invalidate (redraw only when stopped-started)
        // Force a re-draw


        this.invalidate();
    }

    // Called back when the view is first created or its size changes.
    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        this.invalidate();
        // Set the movement bounds for the ball
        box.set(0, 0, w, h);
        Log.w("BouncingBallLog", "onSizeChanged w=" + w + " h=" + h);
    }

    // Touch-input handler
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.invalidate();
        float currentX = event.getX();
        float currentY = event.getY();
        float deltaX, deltaY;
        float scalingFactor = 5.0f / ((box.xMax > box.yMax) ? box.yMax : box.xMax);
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                // Modify rotational angles according to movement
                deltaX = currentX - previousX;
                deltaY = currentY - previousY;

                double xSpeed = Math.abs(deltaX);
                double ySpeed = Math.abs(deltaY);
                double totalSpeed = Math.sqrt((xSpeed * xSpeed) + (ySpeed * ySpeed));
                Log.w("SWIPE SPEED", "Total Swipe Speed: " + totalSpeed);

                if (totalSpeed >= 25) {
                    square_1.speedX += deltaX * scalingFactor;
                    square_1.speedY += deltaY * scalingFactor;
                    squares.add(new Square(Color.GREEN, previousX, previousY, deltaX, deltaY));  // add ball at every touch event
                } else {
                    ball_1.speedX += deltaX * scalingFactor;
                    ball_1.speedY += deltaY * scalingFactor;
                    balls.add(new Ball(Color.BLUE, previousX, previousY, deltaX, deltaY));  // add ball at every touch event
                }

                // A way to clear list when too many balls
                if (balls.size() > 100) {
                    // leave first ball, remove the rest
                    Log.v("BouncingBallLog", "too many balls, clear back to 1");
                    balls.clear();
                    balls.add(new Ball(Color.BLACK));
                    ball_1 = balls.get(0);  // points ball_1 to the first (zero-ith) element of list
                }

                if (squares.size() > 100) {
                    // leave first ball, remove the rest
                    Log.v("BouncingSquareLog", "too many squares, clear back to 1");
                    squares.clear();
                    squares.add(new Square(Color.BLACK));
                    square_1 = squares.get(0);
                }



        }
        // Save current x, y
        previousX = currentX;
        previousY = currentY;

        // Try this (remove other invalidate(); )
        //this.invalidate();

        return true;  // Event handled
    }

    Random rand = new Random();
    // called when button is pressed
    public void RussButtonPressed() {
        this.invalidate();
        Log.d("BouncingBallView  BUTTON", "User tapped the  button ... VIEW");

        //get half of the width and height as we are working with a circle
        int viewWidth = this.getMeasuredWidth();
        int viewHeight = this.getMeasuredHeight();

        // make random x,y, velocity
        int x = rand.nextInt(viewWidth);
        int y = rand.nextInt(viewHeight);

        int dx;
        int dy;

        int fastOrSlow = rand.nextInt(3);

        if (fastOrSlow == 1) {
            dx = 150;
            dy = 150;
        } else {
            dx = 10;
            dy = 10;
        }

        squares.add(new Square(Color.GREEN, x, y, dx, dy));

        int randomDx = rand.nextInt(51);
        int randomDy = rand.nextInt(51);

        int randomRed = rand.nextInt(256);
        int randomGreen = rand.nextInt(256);
        int randomBlue = rand.nextInt(256);

        balls.add(new Ball((Color.rgb(randomRed, randomGreen, randomBlue)), x, y, randomDx, randomDy));  // add ball at every touch event
    }
}
