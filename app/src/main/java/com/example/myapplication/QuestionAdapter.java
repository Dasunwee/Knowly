package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class QuestionAdapter extends ArrayAdapter<Question> {
    private final Context context;
    private final List<Question> questionList;
    private final OnQuestionActionListener listener;

    public interface OnQuestionActionListener {
        void onEditQuestion(Question question);
        void onDeleteQuestion(Question question);
    }

    public QuestionAdapter(Context context, List<Question> questionList, OnQuestionActionListener listener) {
        super(context, R.layout.item_question, questionList);
        this.context = context;
        this.questionList = questionList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_question, parent, false);
            holder = new ViewHolder();
            holder.tvQuestionText = convertView.findViewById(R.id.tvQuestionText);
            holder.radioGroupOptions = convertView.findViewById(R.id.radioGroupOptions);
            holder.btnEdit = convertView.findViewById(R.id.btnEditQuestion);
            holder.btnDelete = convertView.findViewById(R.id.btnDeleteQuestion);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Question question = questionList.get(position);
        holder.tvQuestionText.setText(question.getText());

        // Clear existing radio buttons
        holder.radioGroupOptions.removeAllViews();

        // Add new radio buttons for each option
        for (int i = 0; i < question.getOptions().size(); i++) {
            RadioButton radioButton = new RadioButton(context);
            radioButton.setText(question.getOptions().get(i));
            radioButton.setId(View.generateViewId());
            holder.radioGroupOptions.addView(radioButton);

            // Mark correct answer
            if (i == question.getCorrectAnswerIndex()) {
                radioButton.setChecked(true);
            }
        }

        // Set up button listeners
        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditQuestion(question);
            }
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteQuestion(question);
            }
        });

        return convertView;
    }

    private static class ViewHolder {
        TextView tvQuestionText;
        RadioGroup radioGroupOptions;
        Button btnEdit;
        Button btnDelete;
    }
}