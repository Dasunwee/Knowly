package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class page5 extends AppCompatActivity {

    private Button courses, quizzes, resources;
    private ImageButton profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page5);

        courses = findViewById(R.id.btn_courses);
        quizzes = findViewById(R.id.btn_quizzes);
        resources = findViewById(R.id.btn_resources);
        profile = findViewById(R.id.btn_profile);

        // Button Click Listeners
        courses.setOnClickListener(v -> navigateTo(page6.class));
        quizzes.setOnClickListener(v -> navigateTo(page7.class));
        resources.setOnClickListener(v -> navigateTo(page10.class));
        profile.setOnClickListener(v -> navigateTo(page3.class));
    }

    // Navigation method for cleaner code
    private void navigateTo(Class<?> targetPage) {
        Intent intent = new Intent(page5.this, targetPage);
        startActivity(intent);
    }
}
