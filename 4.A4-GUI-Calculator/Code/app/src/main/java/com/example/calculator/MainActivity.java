package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.accessibilityservice.AccessibilityGestureEvent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    enum CalculateState{
        ADD,
        SUBTRACT,
        MULTIPLY,
        DIVIDE
    }

    public void calculate(ImageButton button, CalculateState state) {
        Log.d("M01_Calculator ADD BUTTON", "User tapped the Add button");
        Log.d("M01_Calculator ADD BUTTON", "button =>"+button.toString());
        Log.d("M01_Calculator ADD BUTTON", "button =>"+button.getId());

        Double d1 = 0.0;
        Double d2 = 0.0;
        Double answer = 0.0;

        EditText textN1 = (EditText) findViewById(R.id.editText_Num1);
        EditText textN2 = (EditText) findViewById(R.id.editText_Num2);
        // we actually don't need to get ans from screen
        EditText textANS = (EditText) findViewById(R.id.editTextNumAns);

        try {
            d1 = Double.parseDouble(textN1.getText().toString());
            d2 = Double.parseDouble(textN2.getText().toString());

            if (state == CalculateState.ADD) {
                answer = d1 + d2;
            } else if (state == CalculateState.SUBTRACT) {
                answer = d1 - d2;
            } else if (state == CalculateState.MULTIPLY) {
                answer = d1 * d2;
            } else if (state == CalculateState.DIVIDE) {
                answer = d1 / d2;
            }

        }
        catch (Exception e) {
            Log.w("M01_Calculator ADD BUTTON", "Add Selected with no inputs ... " + answer);
        }

        // Set the Answer into the the answer field
        textANS.setText(answer.toString());

        // log what we are doing
        Log.w("M01_Calculator ADD BUTTON", "Add Selected with => " + d1 + " + " + d2 + "=" + answer);
    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Action when "Add" button is pressed
        ImageButton addButton = findViewById(R.id.b_Add);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculate(addButton, CalculateState.ADD);
            }
        });

        ImageButton subtractButton =  findViewById(R.id.b_Subtract);
        subtractButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculate(subtractButton, CalculateState.SUBTRACT);
            }
        });

        ImageButton multiplyButton = findViewById(R.id.b_Multiply);
        multiplyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculate(multiplyButton, CalculateState.MULTIPLY);
            }
        });

        ImageButton divideButton = findViewById(R.id.b_Divide);
        divideButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                calculate(divideButton, CalculateState.DIVIDE);
            }
        });
    }

}
