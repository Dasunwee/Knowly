package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class page8 extends AppCompatActivity {

    private ImageButton profile;
    private ImageButton home;

    private Button youtube;
    private Button git;
    private Button w3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page8);

        // Initialize UI components
        home = findViewById(R.id.home_icon);
        profile = findViewById(R.id.btn_profile);
        youtube = findViewById(R.id.youtubeBtn);
        git = findViewById(R.id.gitBtn);
        w3 = findViewById(R.id.w3Btn);

        // Set click listeners for navigation
        home.setOnClickListener(v -> navigateTo(page5.class));
        profile.setOnClickListener(v -> navigateTo(page3.class));

        // Set click listeners for opening external links
        youtube.setOnClickListener(v -> openUrl("https://youtu.be/lpa8uy4DyMo?list=PL9ooVrP1hQOGPQVeapGsJCktzIO4DtI4_"));
        git.setOnClickListener(v -> openUrl("https://github.com/topics/cyber-security"));
        w3.setOnClickListener(v -> openUrl("https://www.w3schools.com/cybersecurity/index.php"));
    }

    // Utility method for navigation between activities
    private void navigateTo(Class<?> targetPage) {
        startActivity(new Intent(page8.this, targetPage));
    }

    // Utility method to open URLs in the browser
    private void openUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
