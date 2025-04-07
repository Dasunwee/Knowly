package com.example.myapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditQuestionsActivity extends AppCompatActivity {

    private EditText etQuestionText, etOptionA, etOptionB, etOptionC, etOptionD;
    private Spinner spCorrectOption;
    private Button btnUpdateQuestion;
    private FirebaseFirestore firestore;
    private String quizId, questionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_questions);

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance();

        // Get data from intent
        quizId = getIntent().getStringExtra("quizId");
        questionId = getIntent().getStringExtra("questionId");
        String questionText = getIntent().getStringExtra("questionText");
        String[] options = getIntent().getStringArrayExtra("options");
        int correctIndex = getIntent().getIntExtra("correctIndex", 0);

        // Initialize views
        etQuestionText = findViewById(R.id.etQuestionText);
        etOptionA = findViewById(R.id.etOptionA);
        etOptionB = findViewById(R.id.etOptionB);
        etOptionC = findViewById(R.id.etOptionC);
        etOptionD = findViewById(R.id.etOptionD);
        spCorrectOption = findViewById(R.id.spCorrectOption);
        btnUpdateQuestion = findViewById(R.id.btnUpdateQuestion);

        // Set up spinner adapter for correct options (A-D)
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.options_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCorrectOption.setAdapter(adapter);

        // Populate fields with existing data
        etQuestionText.setText(questionText);
        if (options != null && options.length == 4) {
            etOptionA.setText(options[0]);
            etOptionB.setText(options[1]);
            etOptionC.setText(options[2]);
            etOptionD.setText(options[3]);
        }
        spCorrectOption.setSelection(correctIndex);

        // Set update button action
        btnUpdateQuestion.setOnClickListener(v -> updateQuestion());
    }

    private void updateQuestion() {
        String questionText = etQuestionText.getText().toString().trim();
        String optionA = etOptionA.getText().toString().trim();
        String optionB = etOptionB.getText().toString().trim();
        String optionC = etOptionC.getText().toString().trim();
        String optionD = etOptionD.getText().toString().trim();
        String correctOption = spCorrectOption.getSelectedItem().toString();

        if (questionText.isEmpty() || optionA.isEmpty() || optionB.isEmpty() ||
                optionC.isEmpty() || optionD.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        // Prepare options map
        Map<String, String> options = new HashMap<>();
        options.put("A", optionA);
        options.put("B", optionB);
        options.put("C", optionC);
        options.put("D", optionD);

        // Prepare question update
        Map<String, Object> updatedQuestion = new HashMap<>();
        updatedQuestion.put("question", questionText);
        updatedQuestion.put("options", options);
        updatedQuestion.put("correctOption", correctOption);

        // Update Firestore
        firestore.collection("quizzes").document(quizId)
                .collection("questions").document(questionId)
                .update(updatedQuestion)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Question updated", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to update question", Toast.LENGTH_SHORT).show();
                });
    }
}
