package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private CustomView dbBallView;

    private SeekBar redSeekBar, greenSeekBar, blueSeekBar;
    private SeekBar xPositionSeekBar, yPositionSeekBar;
    private SeekBar xSizeSeekBar, ySizeSeekBar;
    private SeekBar xSpeedSeekBar, ySpeedSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbBallView = findViewById(R.id.db_ball_view);

        redSeekBar = findViewById(R.id.redSeekBar);
        greenSeekBar = findViewById(R.id.greenSeekBar);
        blueSeekBar = findViewById(R.id.blueSeekBar);

        xPositionSeekBar = findViewById(R.id.xPositionSeekBar);
        yPositionSeekBar = findViewById(R.id.yPositionSeekBar);

        xSizeSeekBar = findViewById(R.id.xSizeSeekBar);
        ySizeSeekBar = findViewById(R.id.ySizeSeekBar);

        xSpeedSeekBar = findViewById(R.id.xSpeedSeekBar);
        ySpeedSeekBar = findViewById(R.id.ySpeedSeekBar);
    }

    public void createBall(View view) {
        dbBallView.createBall(
                redSeekBar, greenSeekBar, blueSeekBar,
                xPositionSeekBar, yPositionSeekBar,
                xSizeSeekBar, ySizeSeekBar,
                xSpeedSeekBar, ySpeedSeekBar
        );
    }

    public void resetShapes(View view) {
        dbBallView.resetShapes();
    }
}
