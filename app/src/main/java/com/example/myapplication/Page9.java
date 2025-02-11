package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Page9 extends AppCompatActivity {

    private ImageButton profile;
    private ImageButton home;

    private Button enroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_page9);

        // Initialize UI components
        home = findViewById(R.id.home_icon);
        profile = findViewById(R.id.btn_profile);
        enroll = findViewById(R.id.start_course_button);

        // Set click listeners
        home.setOnClickListener(v -> navigateTo(page5.class));
        profile.setOnClickListener(v -> navigateTo(page3.class));

        // Set the click listener for the Enroll button
        enroll.setOnClickListener(v -> {
            // Open the CourseActivity when the Enroll button is clicked
            Intent intent = new Intent(Page9.this, CourseActivity.class);
            startActivity(intent);
        });
    }

    // Utility method for navigation
    private void navigateTo(Class<?> targetPage) {
        startActivity(new Intent(Page9.this, targetPage));
    }

}
