package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class page11 extends AppCompatActivity {

    private ImageButton home, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page11);

        // Initialize buttons
        home = findViewById(R.id.home_icon);
        profile = findViewById(R.id.btn_profile);

        // Set button click listeners
        home.setOnClickListener(v -> navigateTo(page5.class));
        profile.setOnClickListener(v -> navigateTo(page3.class));
    }

    // Navigation utility method
    private void navigateTo(Class<?> targetPage) {
        startActivity(new Intent(page11.this, targetPage));
    }
}
