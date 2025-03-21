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

public class QuizAdapter extends ArrayAdapter<Quiz> {

    private Context context;
    private List<Quiz> quizList;

    public QuizAdapter(Context context, List<Quiz> quizList) {
        super(context, 0, quizList);
        this.context = context;
        this.quizList = quizList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_quiz, parent, false);
        }

        Quiz quiz = quizList.get(position);

        TextView tvQuizName = convertView.findViewById(R.id.tvQuizName);
        TextView tvQuizDescription = convertView.findViewById(R.id.tvQuizDescription);
        Button btnUpdateQuiz = convertView.findViewById(R.id.btnUpdateQuiz);
        Button btnDeleteQuiz = convertView.findViewById(R.id.btnDeleteQuiz);

        tvQuizName.setText(quiz.getName());
        tvQuizDescription.setText(quiz.getDescription());

        // Update button click listener
        btnUpdateQuiz.setOnClickListener(v -> {
            ((ManageQuizzesActivity) context).selectQuizForUpdate(quiz.getId(), quiz.getName(), quiz.getDescription());
        });

        // Delete button click listener
        btnDeleteQuiz.setOnClickListener(v -> {
            ((ManageQuizzesActivity) context).deleteQuiz(quiz.getId());
        });

        return convertView;
    }

}