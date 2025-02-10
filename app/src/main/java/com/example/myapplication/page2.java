package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class page2 extends AppCompatActivity {
    private Button signup;
    private Button login2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);

        // Initialize and set click listeners for buttons
        signup = findViewById(R.id.btnSignUp);
        signup.setOnClickListener(v -> {
            // Navigate to page5 on signup button click
            Intent intent = new Intent(page2.this, page5.class);
            startActivity(intent);
        });

        login2 = findViewById(R.id.btnLoginTwo);
        login2.setOnClickListener(v -> {
            // Navigate to page4 on login button click
            Intent intent = new Intent(page2.this, page4.class);
            startActivity(intent);
        });
    }
}
