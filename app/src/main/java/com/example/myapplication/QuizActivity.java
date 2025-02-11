package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView questionText;
    private RadioGroup optionsGroup;
    private Button nextButton;

    private List<Question> questionList;
    private int currentQuestionIndex = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Initialize UI components
        questionText = findViewById(R.id.question_text);
        optionsGroup = findViewById(R.id.options_group);
        nextButton = findViewById(R.id.next_button);

        // Load quiz questions
        loadQuestions();
        showQuestion();

        // Set button click listener
        nextButton.setOnClickListener(v -> checkAnswer());
    }

    private void loadQuestions() {
        questionList = new ArrayList<>();
        questionList.add(new Question("Which of the following is a common method for preventing unauthorized access to a computer system?",
                new String[]{"a)Encryption", "b) Antivirus software", "c) Authentication", "d) Backups"}, 2));
        questionList.add(new Question("What is the primary purpose of a firewall?",
                new String[]{"a) To encrypt sensitive data", "b) To monitor network traffic and block unauthorized access", "c) To prevent physical theft of hardware", "d) To ensure data privacy in cloud computing"}, 1));
        questionList.add(new Question("Which of the following is an example of social engineering?",
                new String[]{"a) Phishing", "b) Brute force attack", "c) SQL injection", "d) Denial of Service (DoS)"}, 0));
        questionList.add(new Question("What does the term \"malware\" refer to?",
                new String[]{"a) Software designed to perform malicious actions on a computer system", "b) A type of encryption algorithm", "c) A technique to secure communication channels", "d) A hardware device used for security"}, 0));
        questionList.add(new Question("Which of the following is a best practice for creating a strong password?",
                new String[]{"a) Use only lowercase letters", "b) Use a mix of uppercase and lowercase letters, numbers, and special characters", "c) Use personal information like your name and birthdate", "d) Use the same password for multiple accounts"}, 0));
    }




    private void showQuestion() {
        if (currentQuestionIndex < questionList.size()) {
            Question q = questionList.get(currentQuestionIndex);
            questionText.setText(q.getQuestion());

            optionsGroup.removeAllViews();
            for (int i = 0; i < q.getOptions().length; i++) {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setText(q.getOptions()[i]);
                radioButton.setId(i);
                optionsGroup.addView(radioButton);
            }
        } else {
            // Quiz completed, show result
            Intent intent = new Intent(QuizActivity.this, QuizResultActivity.class);
            intent.putExtra("SCORE", score);
            intent.putExtra("TOTAL", questionList.size());
            startActivity(intent);
            finish();
        }
    }

    private void checkAnswer() {
        int selectedId = optionsGroup.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedId == questionList.get(currentQuestionIndex).getCorrectAnswerIndex()) {
            score++;
        }

        currentQuestionIndex++;
        showQuestion();
    }
}
