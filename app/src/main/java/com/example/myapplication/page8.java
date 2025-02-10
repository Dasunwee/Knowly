package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class page8 extends AppCompatActivity {

    private ImageButton profile;
    private ImageButton home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page8);

        // Initialize UI components
        home = findViewById(R.id.home_icon);
        profile = findViewById(R.id.btn_profile);

        // Set click listeners
        home.setOnClickListener(v -> navigateTo(page5.class));
        profile.setOnClickListener(v -> navigateTo(page3.class));
    }

    // Utility method for navigation
    private void navigateTo(Class<?> targetPage) {
        startActivity(new Intent(page8.this, targetPage));
    }
}
