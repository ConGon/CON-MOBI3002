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

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.SeekBar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class CustomView extends View {

    private Paint blackPaint;

    private ArrayList<Rectangle> rectangles = new ArrayList<>();
    private ArrayList<Ball> balls = new ArrayList<>();
    private Rectangle ballBounds;

    private DBClass BallDB;

    private boolean initialized = false;

    public CustomView(Context context, AttributeSet attributes) {
        super(context, attributes);

        blackPaint = new Paint();
        blackPaint.setColor(Color.BLACK);

        ballBounds = new Rectangle();
        rectangles.add(ballBounds);

        BallDB = new DBClass(context);
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
        Ball ball = new Ball(
                new Coords(xSizeSeekBar.getProgress(), ySizeSeekBar.getProgress()),
                new Coords(xPosSeekBar.getProgress(), yPosSeekBar.getProgress()),
                new Coords(xSpeedSeekBar.getProgress(), ySpeedSeekBar.getProgress()),
                ballBounds,
                redSeekBar.getProgress(), greenSeekBar.getProgress(), blueSeekBar.getProgress()
        );

        balls.add(ball);

        DataModel saveBall = new DataModel(
                0,
                xSizeSeekBar.getProgress(),
                ySizeSeekBar.getProgress(),
                xPosSeekBar.getProgress(),
                yPosSeekBar.getProgress(),
                xSpeedSeekBar.getProgress(),
                ySpeedSeekBar.getProgress(),
                redSeekBar.getProgress(),
                greenSeekBar.getProgress(),
                blueSeekBar.getProgress()
        );

        BallDB.save(saveBall);
    }

    public void resetShapes() {
        balls.clear();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (Rectangle rectangle : rectangles) {
            rectangle.draw(canvas, blackPaint);
        }

        for (Ball ball : balls) {
            ball.draw(canvas, ball.getPaint());
            ball.update(ballBounds);
        }

        invalidate();
    }

    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);

        float wReduction = 1f;
        float hReduction = 2f;
        float ballBoundW = w / wReduction;
        float ballBoundH = h / hReduction;
        float ballBoundWPos = (w / 2f) - (ballBoundW / 2f);
        float ballBoundHPos = (h / 2f) - (ballBoundH / 2f);

        ballBounds.setSize(new Coords(ballBoundW, ballBoundH));
        ballBounds.setPosition(new Coords(ballBoundWPos, ballBoundHPos - 100));

        // Load saved balls only once after bounds are valid
        if (!initialized) {
            initialized = true;
            List<DataModel> savedBalls = BallDB.findAll();
            for (DataModel savedBall : savedBalls) {
                Ball loadedBall = new Ball(
                        new Coords(savedBall.getSizeX(), savedBall.getSizeY()),
                        new Coords(savedBall.getPositionX(), savedBall.getPositionY()),
                        new Coords(savedBall.getSpeedX(), savedBall.getSpeedY()),
                        ballBounds,
                        savedBall.getR(),
                        savedBall.getG(),
                        savedBall.getB()
                );
                balls.add(loadedBall);
            }
        }
    }
}
