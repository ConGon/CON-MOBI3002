package com.connorgoodwin.m03_bounce; // Fixed package name per Shayne McNeil

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * Main activity for Bouncing Ball app.
 * Handles setup and user button interactions.
 * Author: Connor Goodwin
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity"; // Added consistent logging tag

    // bbView is our bouncing ball view
    private BouncingBallView bbView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bbView = findViewById(R.id.custView);
    }

    // Renamed per Hudson Latimer’s review (avoid personal names)
    public void onAddBallButtonClick(View v) {
        Log.d(TAG, "User tapped the button ... MAIN");
        bbView.spawnBallButtonPressed(); // renamed method (lowercase first letter), more descriptive
    }
}
