package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageQuizzesActivity extends AppCompatActivity {

    private EditText etQuizName, etQuizDescription;
    private Button btnAddQuiz, btnUpdateQuiz;
    private ListView listViewQuizzes;
    private QuizAdapter quizAdapter;
    private ArrayList<Quiz> quizList;

    private FirebaseFirestore firestore;
    private String selectedQuizId = null; // Track the selected quiz for update

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_quizzes);

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance();

        // Initialize UI elements
        etQuizName = findViewById(R.id.etQuizName);
        etQuizDescription = findViewById(R.id.etQuizDescription);
        btnAddQuiz = findViewById(R.id.btnAddQuiz);
        btnUpdateQuiz = findViewById(R.id.btnUpdateQuiz);
        listViewQuizzes = findViewById(R.id.listViewQuizzes);

        // Initialize quiz list and adapter
        quizList = new ArrayList<>();
        quizAdapter = new QuizAdapter(this, quizList);
        listViewQuizzes.setAdapter(quizAdapter);

        // Load quizzes from Firestore
        loadQuizzes();

        // Add quiz button click listener
        btnAddQuiz.setOnClickListener(v -> addQuiz());

        // Update quiz button click listener
        btnUpdateQuiz.setOnClickListener(v -> updateQuiz());
    }

    private void loadQuizzes() {
        firestore.collection("quizzes")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        quizList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String id = document.getId();
                            String name = document.getString("name");
                            String description = document.getString("description");
                            quizList.add(new Quiz(id, name, description));
                        }
                        quizAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "Failed to load quizzes.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addQuiz() {
        String name = etQuizName.getText().toString().trim();
        String description = etQuizDescription.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Quiz name is required.", Toast.LENGTH_SHORT).show();
        } else if (description.isEmpty()) {
            Toast.makeText(this, "Quiz description is required.", Toast.LENGTH_SHORT).show();
        } else {
            // Create a new quiz object
            Map<String, Object> quiz = new HashMap<>();
            quiz.put("name", name);
            quiz.put("description", description);

            // Add quiz to Firestore
            firestore.collection("quizzes")
                    .add(quiz)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(this, "Quiz added successfully!", Toast.LENGTH_SHORT).show();
                        clearInputs();
                        loadQuizzes(); // Refresh the list
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Failed to add quiz: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    private void updateQuiz() {
        if (selectedQuizId == null) {
            Toast.makeText(this, "Please select a quiz to update.", Toast.LENGTH_SHORT).show();
            return;
        }

        String name = etQuizName.getText().toString().trim();
        String description = etQuizDescription.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Quiz name is required.", Toast.LENGTH_SHORT).show();
        } else if (description.isEmpty()) {
            Toast.makeText(this, "Quiz description is required.", Toast.LENGTH_SHORT).show();
        } else {
            // Update the quiz object
            Map<String, Object> quiz = new HashMap<>();
            quiz.put("name", name);
            quiz.put("description", description);

            // Update quiz in Firestore
            firestore.collection("quizzes")
                    .document(selectedQuizId)
                    .update(quiz)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Quiz updated successfully!", Toast.LENGTH_SHORT).show();
                        clearInputs();
                        loadQuizzes(); // Refresh the list
                        selectedQuizId = null; // Reset selected quiz
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Failed to update quiz: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    public void selectQuizForUpdate(String quizId, String name, String description) {
        selectedQuizId = quizId; // Set the selected quiz ID
        etQuizName.setText(name); // Populate the quiz name field
        etQuizDescription.setText(description); // Populate the quiz description field
    }

    public void deleteQuiz(String quizId) {
        firestore.collection("quizzes")
                .document(quizId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Quiz deleted successfully!", Toast.LENGTH_SHORT).show();
                    loadQuizzes(); // Refresh the list
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to delete quiz: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void clearInputs() {
        etQuizName.setText("");
        etQuizDescription.setText("");
    }
}