package com.example.myapplication;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.ManageQuestionsActivity;
import com.example.myapplication.ManageQuizzesActivity;
import com.example.myapplication.Quiz;
import com.example.myapplication.R;


import java.util.ArrayList;

public class QuizAdapter extends ArrayAdapter<Quiz> {
    private final ManageQuizzesActivity activity;

    public QuizAdapter(ManageQuizzesActivity context, ArrayList<Quiz> quizzes) {
        super(context, R.layout.item_quiz, quizzes);
        this.activity = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_quiz, parent, false);
            holder = new ViewHolder();
            holder.tvQuizName = convertView.findViewById(R.id.tvQuizName);
            holder.tvQuizDescription = convertView.findViewById(R.id.tvQuizDescription);
            holder.btnEdit = convertView.findViewById(R.id.btnUpdateQuiz);
            holder.btnDelete = convertView.findViewById(R.id.btnDeleteQuiz);
            holder.btnAddQuestions = convertView.findViewById(R.id.btnAddQuestions);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Quiz quiz = getItem(position);
        if (quiz != null) {
            holder.tvQuizName.setText(quiz.getName());
            holder.tvQuizDescription.setText(quiz.getDescription());

            holder.btnEdit.setOnClickListener(v -> {
                if (activity != null) {
                    activity.selectQuizForUpdate(quiz.getId(), quiz.getName(), quiz.getDescription());
                }
            });

            holder.btnDelete.setOnClickListener(v -> {
                if (activity != null) {
                    activity.deleteQuiz(quiz.getId());
                }
            });

            holder.btnAddQuestions.setOnClickListener(v -> {
                if (activity != null) {
                    Intent intent = new Intent(activity, ManageQuestionsActivity.class);
                    intent.putExtra("quizId", quiz.getId());
                    intent.putExtra("quizName", quiz.getName());
                    activity.startActivity(intent);
                }
            });
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView tvQuizName;
        TextView tvQuizDescription;
        Button btnEdit;
        Button btnDelete;
        Button btnAddQuestions;
    }
}