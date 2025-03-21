package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class QuestionAdapter extends ArrayAdapter<Question> {

    private Context context;
    private List<Question> questionList;

    public QuestionAdapter(Context context, List<Question> questionList) {
        super(context, 0, questionList);
        this.context = context;
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_question, parent, false);
        }

        Question question = questionList.get(position);

        TextView tvQuestionText = convertView.findViewById(R.id.tvQuestionText);
        TextView tvOptions = convertView.findViewById(R.id.tvOptions);
        TextView tvCorrectAnswer = convertView.findViewById(R.id.tvCorrectAnswer);
        Button btnUpdateQuestion = convertView.findViewById(R.id.btnUpdateQuestion);
        Button btnDeleteQuestion = convertView.findViewById(R.id.btnDeleteQuestion);

        tvQuestionText.setText(question.getQuestionText());
        tvOptions.setText("Options: " + String.join(", ", question.getOptions()));
        tvCorrectAnswer.setText("Correct Answer: " + question.getCorrectAnswer());

        // Update button click listener
        btnUpdateQuestion.setOnClickListener(v -> {
            // Implement update logic here
        });

        // Delete button click listener
        btnDeleteQuestion.setOnClickListener(v -> {
            // Implement delete logic here
        });

        return convertView;
    }
}