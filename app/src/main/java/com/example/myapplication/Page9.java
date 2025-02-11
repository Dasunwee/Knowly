package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Page9 extends AppCompatActivity {

    private ImageButton profile;
    private ImageButton home;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_page9);

        // Initialize UI components
        home = findViewById(R.id.home_icon);
        profile = findViewById(R.id.btn_profile);

        // Set click listeners
        home.setOnClickListener(v -> navigateTo(page5.class));
        profile.setOnClickListener(v -> navigateTo(page3.class));
    }
    // Utility method for navigation
    private void navigateTo(Class<?> targetPage) {
        startActivity(new Intent(Page9.this, targetPage));
    }
}