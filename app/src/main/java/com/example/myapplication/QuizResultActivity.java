package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class QuizResultActivity extends AppCompatActivity {

    private TextView resultText;
    private Button restartButton;
    private Button quizButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        resultText = findViewById(R.id.result_text);
        restartButton = findViewById(R.id.restart_button);  // Ensure this ID is correct
        quizButton = findViewById(R.id.quiz_button);  // Ensure this ID is correct

        int score = getIntent().getIntExtra("SCORE", 0);
        int total = getIntent().getIntExtra("TOTAL", 0);

        resultText.setText("You scored " + score + " out of " + total);


        restartButton.setOnClickListener(v -> {
            Intent intent = new Intent(QuizResultActivity.this, QuizActivity.class);
            startActivity(intent);
            finish();
        });

        // Go back to Page 11 when quizButton is clicked
        quizButton.setOnClickListener(v -> {
            Intent intent = new Intent(QuizResultActivity.this, page11.class);  // Replace Page11Activity with your actual activity class name
            startActivity(intent);
            finish();  // Close the current activity
        });
    }
}
