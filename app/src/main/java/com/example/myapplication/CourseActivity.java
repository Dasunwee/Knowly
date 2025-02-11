package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CourseActivity extends AppCompatActivity {

    private TextView moduleTitle, moduleContent;
    private Button nextButton;

    // Simple course content
    private String[] moduleTitles = {
            "Module 1: Introduction to Cybersecurity",
            "Module 2: Basic Cybersecurity Concepts",
            "Module 3: Types of Cyber Attacks",
            "Module 4: Simple Security Practices"
    };

    private String[] moduleContents = {
            "Cybersecurity is the practice of protecting systems, networks, and data from digital attacks. It involves technologies, processes, and practices to safeguard digital assets.",
            "Basic cybersecurity concepts include firewalls, encryption, and authentication mechanisms...",
            "Common types of cyberattacks include phishing, malware, and denial of service (DoS)...",
            "To protect yourself, use strong passwords, enable two-factor authentication, and keep software updated..."
    };

    private int currentModuleIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        // Initialize UI components
        moduleTitle = findViewById(R.id.module_title);
        moduleContent = findViewById(R.id.module_content);
        nextButton = findViewById(R.id.next_button);

        // Set initial content for the first module
        updateModuleContent();

        // Set click listener for the "Next" button
        nextButton.setOnClickListener(v -> {
            // Move to the next module
            if (currentModuleIndex < moduleTitles.length - 1) {
                currentModuleIndex++;
                updateModuleContent();
            } else {
                // Course completed, change button text and add functionality to navigate to page6
                nextButton.setText("Course Completed");
                nextButton.setOnClickListener(v1 -> navigateToPage6());
            }
        });
    }

    private void updateModuleContent() {
        moduleTitle.setText(moduleTitles[currentModuleIndex]);
        moduleContent.setText(moduleContents[currentModuleIndex]);
    }

    private void navigateToPage6() {
        Intent intent = new Intent(CourseActivity.this, page6.class);
        startActivity(intent);
        finish();
    }
}
