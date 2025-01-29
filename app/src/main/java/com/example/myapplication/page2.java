package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class page2 extends AppCompatActivity {
    private Button signup;
    private Button login2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Assuming EdgeToEdge is a library for handling full-screen mode
        setContentView(R.layout.activity_page2);

        // Initialize the signup button
        signup = findViewById(R.id.btnSignUp);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to page3 when the signup button is clicked
                Intent intent = new Intent(page2.this, page3.class);
                startActivity(intent);
            }
        });

        // Initialize the login2 button
        login2 = findViewById(R.id.btnLoginTwo);
        login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to page4 when the login2 button is clicked
                Intent intent = new Intent(page2.this, page4.class);
                startActivity(intent);
            }
        });

        // Optional: Handle insets for both buttons
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.btnSignUp), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.btnLoginTwo), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
