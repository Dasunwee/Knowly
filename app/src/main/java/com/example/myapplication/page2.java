package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;
import java.util.Map;


public class page2 extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    private EditText emailInput, passwordInput, confirmPassword, etUsername;
    private Button signup, login2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);

        setContentView(R.layout.activity_page2);

        // Initialize Firebase Auth and Firestore
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // Initialize UI elements
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        confirmPassword = findViewById(R.id.confirmPassword);
        etUsername = findViewById(R.id.etUsername);
        signup = findViewById(R.id.btnSignUp);
        login2 = findViewById(R.id.btnLoginTwo);

        // Signup button click listener
        signup.setOnClickListener(v -> registerUser());

        // Login button click listener
        login2.setOnClickListener(v -> {
            Intent intent = new Intent(page2.this, page4.class);
            startActivity(intent);
        });
    }

    private void registerUser() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String confirmpassword = confirmPassword.getText().toString().trim();
        String username = etUsername.getText().toString().trim();

        if (email.isEmpty()) {
            showToast("Email is required.");
        } else if (username.isEmpty()) {
            showToast("Username is required.");
        } else if (password.isEmpty()) {
            showToast("Please enter a Password");
        } else if (!password.equals(confirmpassword)) {
            showToast("Passwords don't match. Please enter again.");
        } else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            saveUserToFirestore(email, username);
                        } else {
                            showToast("Registration failed: " + task.getException().getMessage());
                        }
                    });
        }
    }

    private void saveUserToFirestore(String email, String username) {
        String userId = mAuth.getCurrentUser().getUid();
        Map<String, Object> user = new HashMap<>();
        user.put("email", email);
        user.put("username", username);

        firestore.collection("users").document(userId).set(user)
                .addOnSuccessListener(aVoid -> {
                    showToast("Registration successful!");
                    startActivity(new Intent(page2.this, page5.class));
                    finish();
                })
                .addOnFailureListener(e -> showToast("Error saving user: " + e.getMessage()));
    }

    private void showToast(String message) {
        Toast.makeText(page2.this, message, Toast.LENGTH_SHORT).show();
    }
}