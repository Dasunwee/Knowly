package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class page6 extends AppCompatActivity {

    private Button cyber;
    private ImageButton profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page6);

        // Initialize UI elements
        cyber = findViewById(R.id.btn_cyber_security);
        profile = findViewById(R.id.btn_profile);

        // Set up button click listeners
        cyber.setOnClickListener(v -> navigateTo(Page9.class));
        profile.setOnClickListener(v -> navigateTo(page3.class));
    }

    // Navigation utility method
    private void navigateTo(Class<?> targetPage) {
        startActivity(new Intent(page6.this, targetPage));
    }
}
