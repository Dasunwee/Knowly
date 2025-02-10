package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class page7 extends AppCompatActivity {

    private ImageButton profile;
    private Button quizDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page7);

        // Initialize UI elements
        profile = findViewById(R.id.btn_profile);
        quizDetails = findViewById(R.id.cyber_security_button);

        // Set up button click listeners
        profile.setOnClickListener(v -> navigateTo(page3.class));
        quizDetails.setOnClickListener(v -> navigateTo(page11.class));
    }

    // Navigation utility method
    private void navigateTo(Class<?> targetPage) {
        startActivity(new Intent(page7.this, targetPage));
    }
}
