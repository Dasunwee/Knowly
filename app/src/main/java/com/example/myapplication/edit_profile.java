package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class edit_profile extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private FirebaseUser currentUser;

    private EditText etEmail, etUsername, etAge, etCountry;
    private RadioGroup rgGender;
    private RadioButton rbMale, rbFemale, rbOther;
    private Button btnSave, btnCancel, btnDeleteProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Initialize Firebase Auth and Firestore
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        currentUser = mAuth.getCurrentUser();

        // Initialize UI elements
        etEmail = findViewById(R.id.etEmail);
        etUsername = findViewById(R.id.etUsername);
        etAge = findViewById(R.id.etAge);
        etCountry = findViewById(R.id.etCountry);
        rgGender = findViewById(R.id.rgGender);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        rbOther = findViewById(R.id.rbOther);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        btnDeleteProfile = findViewById(R.id.btnDeleteProfile);

        // Load current user data
        loadUserData();

        // Save button click listener
        btnSave.setOnClickListener(v -> updateUserProfile());

        // Cancel button click listener
        btnCancel.setOnClickListener(v -> finish());

        // Delete profile button click listener
        btnDeleteProfile.setOnClickListener(v -> showDeleteConfirmationDialog());
    }

    private void loadUserData() {
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DocumentReference userRef = firestore.collection("users").document(userId);

            userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            etEmail.setText(document.getString("email"));
                            etUsername.setText(document.getString("username"));
                            etAge.setText(document.getString("age"));
                            etCountry.setText(document.getString("country"));

                            String gender = document.getString("gender");
                            if (gender != null) {
                                switch (gender) {
                                    case "Male":
                                        rbMale.setChecked(true);
                                        break;
                                    case "Female":
                                        rbFemale.setChecked(true);
                                        break;
                                    case "Other":
                                        rbOther.setChecked(true);
                                        break;
                                }
                            }
                        } else {
                            showToast("No such document");
                        }
                    } else {
                        showToast("Failed to load user data: " + task.getException().getMessage());
                    }
                }
            });
        }
    }

    private void updateUserProfile() {
        String email = etEmail.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String age = etAge.getText().toString().trim();
        String country = etCountry.getText().toString().trim();
        String gender = getSelectedGender();

        if (email.isEmpty()) {
            showToast("Email is required.");
        } else if (username.isEmpty()) {
            showToast("Username is required.");
        } else if (age.isEmpty()) {
            showToast("Age is required.");
        } else if (country.isEmpty()) {
            showToast("Country is required.");
        } else if (gender == null) {
            showToast("Gender is required.");
        } else {
            String userId = currentUser.getUid();
            Map<String, Object> user = new HashMap<>();
            user.put("email", email);
            user.put("username", username);
            user.put("age", age);
            user.put("country", country);
            user.put("gender", gender);

            firestore.collection("users").document(userId).set(user)
                    .addOnSuccessListener(aVoid -> {
                        showToast("Profile updated successfully!");
                        finish();
                    })
                    .addOnFailureListener(e -> showToast("Error updating profile: " + e.getMessage()));
        }
    }

    private String getSelectedGender() {
        int selectedId = rgGender.getCheckedRadioButtonId();
        if (selectedId == R.id.rbMale) {
            return "Male";
        } else if (selectedId == R.id.rbFemale) {
            return "Female";
        } else if (selectedId == R.id.rbOther) {
            return "Other";
        }
        return null;
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Profile");
        builder.setMessage("Are you sure you want to delete your profile? This action cannot be undone.");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteUserProfile();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void deleteUserProfile() {
        if (currentUser != null) {
            String userId = currentUser.getUid();

            // Delete user data from Firestore
            firestore.collection("users").document(userId).delete()
                    .addOnSuccessListener(aVoid -> {
                        // Delete user from Firebase Auth
                        currentUser.delete().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                showToast("Profile deleted successfully!");
                                // Redirect to page4.java
                                Intent intent = new Intent(edit_profile.this, page4.class);
                                startActivity(intent);
                                finish();
                            } else {
                                showToast("Failed to delete user: " + task.getException().getMessage());
                            }
                        });
                    })
                    .addOnFailureListener(e -> showToast("Failed to delete profile: " + e.getMessage()));
        }
    }

    private void showToast(String message) {
        Toast.makeText(edit_profile.this, message, Toast.LENGTH_SHORT).show();
    }
}