package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class page2 extends AppCompatActivity {

    private Button signup;
    private Button login2;
    private EditText emailInput;

    private EditText confirmPassword;

    private EditText passwordInput;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);

        // Initialize EditText and Buttons
        emailInput = findViewById(R.id.emailInput);
        signup = findViewById(R.id.btnSignUp);
        login2 = findViewById(R.id.btnLoginTwo);
        passwordInput = findViewById(R.id.passwordInput);
        confirmPassword = findViewById(R.id.confirmPassword);

        // Signup button click listener
        signup.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();
            String confirmpassword = confirmPassword.getText().toString().trim();

            // Check for empty fields
            if (email.isEmpty()) {
                showToast("Email is required.");
            } else if (!isValidEmail(email)) {
                showToast("Please enter a valid email address.");
            }else if (password.isEmpty()){
                showToast("Please enter a Password");
            }
            else if (!password.equals(confirmpassword)){
                showToast("Passwords don't match. Please enter again");
            }
            else {

                Intent intent = new Intent(page2.this, page5.class);
                startActivity(intent);
            }

        });

        // Login button click listener
        login2.setOnClickListener(v -> {

                // Proceed with login action if email is valid
                Intent intent = new Intent(page2.this, page4.class);
                startActivity(intent);

        });
    }

    // Show a toast message
    private void showToast(String message) {
        Toast.makeText(page2.this, message, Toast.LENGTH_SHORT).show();
    }

    // Email validation using regex
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
