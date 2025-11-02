package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.List;

public class CustomView extends View {
    private Paint blackPaint;

    private ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
    private ArrayList<Ball> balls = new ArrayList<Ball>();
    private Ball ball;
    private Rectangle ballBounds;
    DBClass BallDB;

    private int screenSizeChanges = 0;

    public CustomView(Context context, AttributeSet attributes) {
        super(context, attributes);

        this.blackPaint = new Paint();
        blackPaint.setColor(Color.BLACK);

        ballBounds = new Rectangle();
        rectangles.add(ballBounds);

        BallDB = new DBClass(context);
        List<DataModel> savedBalls = BallDB.findAll();
        for (DataModel savedBall: savedBalls) {
            Log.w("DATAMODEL", "Saved Ball: " + savedBall.toString());

            Coords pos = new Coords(savedBall.getPositionX(), savedBall.getPositionY());
            Coords siz = new Coords(savedBall.getSizeX(), savedBall.getSizeY());
            Coords spd = new Coords(savedBall.getSpeedX(), savedBall.getSpeedY());
            balls.add(new Ball(
                    pos,
                    siz,
                    spd,
                    ballBounds,
                    255,
                    0,
                    0
            ));
        }
    }

public void createBall(
        SeekBar redSeekBar,
        SeekBar greenSeekBar,
        SeekBar blueSeekBar,
        SeekBar xPosSeekBar,
        SeekBar yPosSeekBar,
        SeekBar xSizeSeekBar,
        SeekBar ySizeSeekBar,
        SeekBar xSpeedSeekBar,
        SeekBar ySpeedSeekBar
) {
        ball = new Ball(
        new Coords(xSizeSeekBar.getProgress(), xSizeSeekBar.getProgress()),
        new Coords(xPosSeekBar.getProgress(), yPosSeekBar.getProgress()),
        new Coords(xSpeedSeekBar.getProgress(), ySpeedSeekBar.getProgress()),
        ballBounds,
        redSeekBar.getProgress(),
        greenSeekBar.getProgress(),
        blueSeekBar.getProgress()
        );

        balls.add(ball);
        DataModel saveBall = new DataModel(
                0,
                xSizeSeekBar.getProgress(),
                ySizeSeekBar.getProgress(),
                xPosSeekBar.getProgress(),
                yPosSeekBar.getProgress(),
                xSpeedSeekBar.getProgress(),
                ySpeedSeekBar.getProgress()
        );

        BallDB.save(saveBall);
    }

    public void resetShapes() {
        balls.clear();
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for(Rectangle rectangle: rectangles) {
            rectangle.draw(canvas, blackPaint);
        }

        for(Ball ballInBalls: balls) {
            ballInBalls.draw(canvas, ballInBalls.getPaint());
            ballInBalls.update(ballBounds);
        }

        this.invalidate();
    }

    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        this.screenSizeChanges++;

        float wReduction = 1f;
        float hReduction = 2f;

        float ballBoundW =  w / wReduction;
        float ballBoundH = h / hReduction;
        float ballBoundWPos = (w / 2f) - (ballBoundW / 2f);
        float ballBoundHPos = (h / 2f) - ballBoundH / 2f;

        ballBounds.setSize(new Coords(ballBoundW, ballBoundH));
        ballBounds.setPosition(new Coords(ballBoundWPos, ballBoundHPos -100));

        // Doesnt really work
//        if (oldW == 0 || oldH == 0) {
//            return;
//        }
//
//        float scaleW = (float) w / oldW;
//        float scaleH = (float) h / oldH;
//
//        for(Ball ballInBalls: balls) {
//            float sizeX = ballInBalls.getSize().getX();
//            float sizeY = ballInBalls.getSize().getY();
//
//            float newW = sizeX * scaleW;
//            float newH = sizeY * scaleH;
//
//            ballInBalls.setSize(new Coords(newW, newH));
//        }
    }
}
