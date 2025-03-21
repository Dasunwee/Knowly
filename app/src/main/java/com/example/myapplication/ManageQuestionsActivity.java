package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageQuestionsActivity extends AppCompatActivity {

    private EditText etQuestionText, etOption1, etOption2, etOption3, etOption4, etCorrectAnswer;
    private Button btnAddQuestion, btnUpdateQuestion;
    private ListView listViewQuestions;
    private QuestionAdapter questionAdapter;
    private ArrayList<Question> questionList;

    private FirebaseFirestore firestore;
    private String quizId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_questions);

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance();

        // Get quizId from intent
        quizId = getIntent().getStringExtra("quizId");

        // Initialize UI elements
        etQuestionText = findViewById(R.id.etQuestionText);
        etOption1 = findViewById(R.id.etOption1);
        etOption2 = findViewById(R.id.etOption2);
        etOption3 = findViewById(R.id.etOption3);
        etOption4 = findViewById(R.id.etOption4);
        etCorrectAnswer = findViewById(R.id.etCorrectAnswer);
        btnAddQuestion = findViewById(R.id.btnAddQuestion);
        btnUpdateQuestion = findViewById(R.id.btnUpdateQuestion);
        listViewQuestions = findViewById(R.id.listViewQuestions);

        // Initialize question list and adapter
        questionList = new ArrayList<>();
        questionAdapter = new QuestionAdapter(this, questionList);
        listViewQuestions.setAdapter(questionAdapter);

        // Load questions from Firestore
        loadQuestions();

        // Add question button click listener
        btnAddQuestion.setOnClickListener(v -> addQuestion());

        // Update question button click listener
        btnUpdateQuestion.setOnClickListener(v -> updateQuestion());
    }

    private void loadQuestions() {
        firestore.collection("quizzes")
                .document(quizId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        List<Map<String, Object>> questionsData = (List<Map<String, Object>>) documentSnapshot.get("questions");
                        if (questionsData != null) {
                            questionList.clear();
                            for (Map<String, Object> questionData : questionsData) {
                                String questionText = (String) questionData.get("questionText");
                                List<String> options = (List<String>) questionData.get("options");
                                String correctAnswer = (String) questionData.get("correctAnswer");
                                questionList.add(new Question(questionText, options, correctAnswer));
                            }
                            questionAdapter.notifyDataSetChanged();
                        }
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to load questions.", Toast.LENGTH_SHORT).show());
    }

    private void addQuestion() {
        String questionText = etQuestionText.getText().toString().trim();
        String option1 = etOption1.getText().toString().trim();
        String option2 = etOption2.getText().toString().trim();
        String option3 = etOption3.getText().toString().trim();
        String option4 = etOption4.getText().toString().trim();
        String correctAnswer = etCorrectAnswer.getText().toString().trim();

        if (questionText.isEmpty() || option1.isEmpty() || option2.isEmpty() || option3.isEmpty() || option4.isEmpty() || correctAnswer.isEmpty()) {
            Toast.makeText(this, "All fields are required.", Toast.LENGTH_SHORT).show();
        } else {
            List<String> options = new ArrayList<>();
            options.add(option1);
            options.add(option2);
            options.add(option3);
            options.add(option4);

            Map<String, Object> question = new HashMap<>();
            question.put("questionText", questionText);
            question.put("options", options);
            question.put("correctAnswer", correctAnswer);

            firestore.collection("quizzes")
                    .document(quizId)
                    .update("questions", FieldValue.arrayUnion(question))
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Question added successfully!", Toast.LENGTH_SHORT).show();
                        clearInputs();
                        loadQuestions(); // Refresh the list
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Failed to add question: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    private void updateQuestion() {
        // Implement update logic here
    }

    private void clearInputs() {
        etQuestionText.setText("");
        etOption1.setText("");
        etOption2.setText("");
        etOption3.setText("");
        etOption4.setText("");
        etCorrectAnswer.setText("");
    }
}