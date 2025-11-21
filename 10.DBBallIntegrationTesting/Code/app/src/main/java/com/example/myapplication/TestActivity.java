package com.example.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BallDBTest test = new BallDBTest(this);
        test.testAddAndRetrieveBall();
        test.testMultipleBalls();
        test.testDeleteBall();
//        test.testClearBalls();

        finish(); // close activity after tests
    }
}

