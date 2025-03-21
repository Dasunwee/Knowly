package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminPanelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        // Initialize buttons
        Button btnManageResources = findViewById(R.id.btnManageResources);
        Button btnManageCourses = findViewById(R.id.btnManageCourses);
        Button btnManageQuizzes = findViewById(R.id.btnManageQuizzes);

        // Set click listeners for buttons
        btnManageResources.setOnClickListener(v -> {
            // Navigate to ManageResourcesActivity
            Intent intent = new Intent(AdminPanelActivity.this, ManageResourcesActivity.class);
            startActivity(intent);
        });

        btnManageCourses.setOnClickListener(v -> {
            // Navigate to ManageCoursesActivity
            Intent intent = new Intent(AdminPanelActivity.this, ManageCoursesActivity.class);
            startActivity(intent);
        });

        btnManageQuizzes.setOnClickListener(v -> {
            // Navigate to ManageQuizzesActivity
            Intent intent = new Intent(AdminPanelActivity.this, ManageQuizzesActivity.class);
            startActivity(intent);
        });
    }
}