package com.example.myapplication;

import android.util.Log;
import android.content.Context;
import java.util.List;

public class BallDBTest {

    private DBClass db;
    private Context context;

    public BallDBTest(Context context) {
        this.context = context;
        db = new DBClass(context);
    }

    public void testAddAndRetrieveBall() {
        DataModel ball = new DataModel(0, 50f, 50f, 100f, 200f, 5f, 5f, 255, 0, 0);
        db.save(ball);

        List<DataModel> allBalls = db.findAll();
        for (DataModel b : allBalls) {
            Log.d("BallDBTest", "Retrieved: " + b.toString());
        }
    }

    public void testMultipleBalls() {
        for (int i = 0; i < 3; i++) {
            db.save(new DataModel(0, 30f + i*10, 30f + i*10, 50f + i*20, 50f + i*20, 2f + i, 2f + i, 0, 255, 0));
        }

        List<DataModel> allBalls = db.findAll();
        Log.d("BallDBTest", "Total balls: " + allBalls.size());
    }

    public void testDeleteBall() {
        List<DataModel> balls = db.findAll();
        if (!balls.isEmpty()) {
            db.deleteById(balls.get(0).getId());
            Log.d("BallDBTest", "Deleted first ball");
        }

        int i = 1;
        List<DataModel> allBalls = db.findAll();
        for (DataModel b : allBalls) {
            Log.d("BallDBTest", "Ball" + i + ": " + b.toString());
            i++;
        }
    }

    public void testClearBalls() {
        db.clearAll();
        Log.d("BallDBTest", "Cleared BallDB: " + db.findAll());
    }
}
