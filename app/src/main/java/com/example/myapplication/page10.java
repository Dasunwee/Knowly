package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class page10 extends AppCompatActivity {

    private Button cybersecurity;
    private ImageButton profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page10);

        // Initialize buttons
        cybersecurity = findViewById(R.id.cyberSecurityButton);
        profile = findViewById(R.id.btn_profile);

        // Set button click listeners
        cybersecurity.setOnClickListener(v -> navigateTo(page8.class));
        profile.setOnClickListener(v -> navigateTo(page3.class));
    }

    // Navigation utility method
    private void navigateTo(Class<?> targetPage) {
        startActivity(new Intent(page10.this, targetPage));
    }
}
