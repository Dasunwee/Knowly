package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class page3 extends AppCompatActivity {
    private Button logout;
    private ImageButton homeP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page3);

        // Logout button event
        logout = findViewById(R.id.logout_button);
        logout.setOnClickListener(v -> {
            Intent intent = new Intent(page3.this, page2.class);
            startActivity(intent);
            finish(); // Close current activity to prevent returning with the back button
        });

        // Home button event
        homeP = findViewById(R.id.home_icon);
        homeP.setOnClickListener(v -> {
            Intent intent = new Intent(page3.this, page5.class);
            startActivity(intent);
        });
    }
}
