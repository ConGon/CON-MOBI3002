package com.example.myapplication;

import android.content.Context;
import android.util.Log;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ATBallDBTest {

    private DBClass ballDB;
    private Context ctx;

    @Before
    public void setUp() {
        ctx = ApplicationProvider.getApplicationContext();

        String uniqueName = "test_db_" + System.currentTimeMillis() + ".db";
        ballDB = new DBClass(ctx, uniqueName);

//        ballDB.save(new DataModel(30, 30, 50, 50, 2, 2, 0, 255, 0));
//        ballDB.save(new DataModel(40, 40, 70, 70, 3, 3, 0, 255, 0));
//        ballDB.save(new DataModel(50, 50, 90, 90, 4, 4, 0, 255, 0));
    }

    @Test
    public void testAddAndRetrieveBall() {
        // Create a sample ball
        DataModel ball = new DataModel(
                0,     // ID (autoincrement)
                50f, 50f,   // sizeX, sizeY
                100f, 200f, // posX, posY
                2f, -3f,    // speedX, speedY
                255, 0, 0   // RGB
        );

        ballDB.save(ball);
        Log.i("BallDBTest", "Saved ball: " + ball.toString());

        // Retrieve
        List<DataModel> savedBalls = ballDB.findAll();
        assertEquals(1, savedBalls.size());

        DataModel retrieved = savedBalls.get(0);
        Log.i("BallDBTest", "Retrieved ball: " + retrieved.toString());

        // Assertions
        assertEquals(ball.getSizeX(), retrieved.getSizeX(), 0.001f);
        assertEquals(ball.getSizeY(), retrieved.getSizeY(), 0.001f);
        assertEquals(ball.getPositionX(), retrieved.getPositionX(), 0.001f);
        assertEquals(ball.getPositionY(), retrieved.getPositionY(), 0.001f);
        assertEquals(ball.getSpeedX(), retrieved.getSpeedX(), 0.001f);
        assertEquals(ball.getSpeedY(), retrieved.getSpeedY(), 0.001f);
        assertEquals(ball.getR(), retrieved.getR());
        assertEquals(ball.getG(), retrieved.getG());
        assertEquals(ball.getB(), retrieved.getB());
    }

    @Test
    public void testMultipleBalls() {
        for (int i = 0; i < 5; i++) {
            DataModel ball = new DataModel(
                    0,
                    10f * i, 10f * i,
                    20f * i, 30f * i,
                    i, -i,
                    100, i*50, i*25
            );
            ballDB.save(ball);
            Log.i("BallDBTest", "Saved ball " + i + ": " + ball.toString());
        }

        List<DataModel> savedBalls = ballDB.findAll();
        assertEquals(5, savedBalls.size());
    }

    @Test
    public void testDeleteBall() {
        DataModel ball = new DataModel(0, 50f, 50f, 100f, 200f, 2f, -3f, 255, 0, 0);
        ballDB.save(ball);

        List<DataModel> savedBalls = ballDB.findAll();
        assertTrue(savedBalls.size() > 0);

        ballDB.deleteById(savedBalls.get(0).getId());
        savedBalls = ballDB.findAll();
        assertEquals(0, savedBalls.size());
        Log.i("BallDBTest", "Ball deleted successfully.");
    }
}

