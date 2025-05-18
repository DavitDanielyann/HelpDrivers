package ru.samsung.myapp;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class LearnActivity extends AppCompatActivity {

    LinearLayout learnLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        learnLayout = findViewById(R.id.learnLayout);

        List<Question> incorrectQuestions = LearnStorage.getIncorrectAnswers();

        if (incorrectQuestions.isEmpty()) {
            TextView noErrorsText = new TextView(this);
            noErrorsText.setText("No incorrect answers to review!");
            noErrorsText.setTextSize(18f);
            learnLayout.addView(noErrorsText);
        } else {
            for (Question q : incorrectQuestions) {
                TextView questionView = new TextView(this);
                questionView.setText("Q: " + q.getQuestionText());
                questionView.setTextSize(18f);
                questionView.setPadding(0, 20, 0, 8);
                learnLayout.addView(questionView);

                TextView correctAnswerView = new TextView(this);
                correctAnswerView.setText("Correct Answer: " + q.getCorrectAnswer());
                correctAnswerView.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                learnLayout.addView(correctAnswerView);
            }
        }
    }
}