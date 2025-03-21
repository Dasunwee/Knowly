package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class page4 extends AppCompatActivity {
    private Button login1;
    private Button createAcc;

    private EditText inputEmail;
    private EditText inputPassword;

    // Admin credentials
    private static final String ADMIN_EMAIL = "admin@knowly.com";
    private static final String ADMIN_PASSWORD = "admin@123";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page4);

        // EditText action
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);

        // Login button action
        login1 = findViewById(R.id.btn_login);
        login1.setOnClickListener(v -> {
            String email = inputEmail.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();

            if (email.isEmpty()) {
                showToast("Email is required.");
            } else if (!isValidEmail(email)) {
                showToast("Please enter a valid email address.");
            } else if (password.isEmpty()) {
                showToast("Please enter your password.");
            } else {
                // Check if the credentials match the admin credentials
                if (email.equals(ADMIN_EMAIL) && password.equals(ADMIN_PASSWORD)) {
                    // Redirect to Admin Panel
                    Intent intent = new Intent(page4.this, AdminPanelActivity.class);
                    startActivity(intent);
                    showToast("Welcome, Admin!");
                } else {
                    // Redirect to regular user page (page5.java)
                    Intent intent = new Intent(page4.this, page5.class);
                    startActivity(intent);
                    showToast("Login successful!");
                }
            }
        });

        // Create account button action
        createAcc = findViewById(R.id.btn_create_account);
        createAcc.setOnClickListener(v -> {
            Intent intent = new Intent(page4.this, page2.class);
            startActivity(intent);
        });
    }

    // Show a toast message
    private void showToast(String message) {
        Toast.makeText(page4.this, message, Toast.LENGTH_SHORT).show();
    }

    // Email validation using regex
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}