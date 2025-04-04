package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageQuestionsActivity extends AppCompatActivity implements QuestionAdapter.OnQuestionActionListener {

    private EditText etQuestionText, etOptionA, etOptionB, etOptionC, etOptionD;
    private Spinner spCorrectOption;
    private Button btnAddQuestion;

    private Button home;

    private ListView listViewQuestions;
    private ProgressBar progressBar;
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

        // Get quiz ID from intent
        quizId = getIntent().getStringExtra("quizId");
        if (quizId == null || quizId.isEmpty()) {
            Toast.makeText(this, "Error: Quiz ID not found", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        initializeViews();
        setupSpinner();
        setupAdapter();
        loadQuestions();

        btnAddQuestion.setOnClickListener(v -> addQuestion());
    }

    private void initializeViews() {

        etQuestionText = findViewById(R.id.etQuestionText);
        etOptionA = findViewById(R.id.etOptionA);
        etOptionB = findViewById(R.id.etOptionB);
        etOptionC = findViewById(R.id.etOptionC);
        etOptionD = findViewById(R.id.etOptionD);
        spCorrectOption = findViewById(R.id.spCorrectOption);
        btnAddQuestion = findViewById(R.id.btnAddQuestion);
        listViewQuestions = findViewById(R.id.listViewQuestions);
        progressBar = findViewById(R.id.progressBar);
    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.options_array,  // Using your defined string array
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCorrectOption.setAdapter(adapter);
    }

    private void setupAdapter() {
        questionList = new ArrayList<>();
        questionAdapter = new QuestionAdapter(this, questionList, this);
        listViewQuestions.setAdapter(questionAdapter);
    }

    private void loadQuestions() {
        progressBar.setVisibility(View.VISIBLE);
        firestore.collection("quizzes").document(quizId).collection("questions")
                .get()
                .addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        questionList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String id = document.getId();
                            String text = document.getString("question");
                            Map<String, String> options = (Map<String, String>) document.get("options");
                            String correctOption = document.getString("correctOption");

                            // Convert options map to list and get correct index
                            List<String> optionsList = Arrays.asList(
                                    options.get("A"),
                                    options.get("B"),
                                    options.get("C"),
                                    options.get("D")
                            );
                            int correctIndex = correctOption.charAt(0) - 'A'; // Convert 'A'-'D' to 0-3

                            questionList.add(new Question(id, quizId, text, optionsList, correctIndex));
                        }
                        questionAdapter.notifyDataSetChanged();
                    } else {
                        Log.e("Firestore", "Error loading questions", task.getException());
                        Toast.makeText(this, "Failed to load questions", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addQuestion() {
        String questionText = etQuestionText.getText().toString().trim();
        String optionA = etOptionA.getText().toString().trim();
        String optionB = etOptionB.getText().toString().trim();
        String optionC = etOptionC.getText().toString().trim();
        String optionD = etOptionD.getText().toString().trim();
        String correctOption = spCorrectOption.getSelectedItem().toString();

        // Validate inputs
        if (questionText.isEmpty() || optionA.isEmpty() ||
                optionB.isEmpty() || optionC.isEmpty() || optionD.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create question object
        Question newQuestion = new Question(
                null, // ID will be generated by Firestore
                quizId,
                questionText,
                Arrays.asList(optionA, optionB, optionC, optionD),
                correctOption.charAt(0) - 'A'  // Convert to index
        );

        // Save to Firestore
        firestore.collection("quizzes").document(quizId)
                .collection("questions")
                .add(newQuestion.toFirestoreMap())
                .addOnSuccessListener(documentReference -> {
                    clearForm();
                    loadQuestions();
                    Toast.makeText(this, "Question added!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error adding question", Toast.LENGTH_SHORT).show();
                    Log.e("Firestore", "Error adding question", e);
                });
    }

    private void clearForm() {
        etQuestionText.setText("");
        etOptionA.setText("");
        etOptionB.setText("");
        etOptionC.setText("");
        etOptionD.setText("");
        spCorrectOption.setSelection(0);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onEditQuestion(Question question) {
        Intent intent = new Intent(this, EditQuestionsActivity.class);
        intent.putExtra("quizId", quizId);
        intent.putExtra("questionId", question.getId());
        intent.putExtra("questionText", question.getText());
        intent.putExtra("options", question.getOptions().toArray(new String[0]));
        intent.putExtra("correctIndex", question.getCorrectAnswerIndex());
        startActivity(intent);
    }

    @Override
    public void onDeleteQuestion(Question question) {
        progressBar.setVisibility(View.VISIBLE);
        firestore.collection("quizzes").document(quizId).collection("questions")
                .document(question.getId())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    loadQuestions();
                    Toast.makeText(this, "Question deleted", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(this, "Failed to delete", Toast.LENGTH_SHORT).show();
                    Log.e("Firestore", "Error deleting question", e);
                });
    }
}