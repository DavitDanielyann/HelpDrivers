package ru.samsung.myapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView questionText, timerText;
    private Button[] optionButtons = new Button[4];
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionText = findViewById(R.id.questionText);
        timerText = findViewById(R.id.timerText);
        optionButtons[0] = findViewById(R.id.option1);
        optionButtons[1] = findViewById(R.id.option2);
        optionButtons[2] = findViewById(R.id.option3);
        optionButtons[3] = findViewById(R.id.option4);

        String category = getIntent().getStringExtra("CATEGORY");
        questions = QuestionBank.getQuestions(category);

        showQuestion();
    }

    private void showQuestion() {
        resetButtonColors();
        if (currentQuestionIndex < questions.size()) {
            Question question = questions.get(currentQuestionIndex);
            questionText.setText(question.getQuestionText());
            String[] options = {question.getOption1(), question.getOption2(), question.getOption3(), question.getOption4()};

            for (int i = 0; i < 4; i++) {
                optionButtons[i].setText(options[i]);
                int finalI = i;
                optionButtons[i].setOnClickListener(v -> handleAnswer(optionButtons[finalI].getText().toString(), optionButtons[finalI], question));
            }

            startTimer();
        } else {
            endQuiz();
        }
    }

    private void handleAnswer(String selected, Button selectedButton, Question question) {
        countDownTimer.cancel();

        for (Button btn : optionButtons) {
            btn.setEnabled(false);
        }

        if (selected.equals(question.getCorrectAnswer())) {
            selectedButton.setBackgroundColor(Color.GREEN);
            score++;
        } else {
            selectedButton.setBackgroundColor(Color.RED);
            LearnStorage.addIncorrectAnswer(question); // Save incorrect answer

            // Highlight correct one
            for (Button btn : optionButtons) {
                if (btn.getText().equals(question.getCorrectAnswer())) {
                    btn.setBackgroundColor(Color.GREEN);
                }
            }
        }

        selectedButton.postDelayed(() -> {
            currentQuestionIndex++;
            for (Button btn : optionButtons) btn.setEnabled(true);
            showQuestion();
        }, 1500);
    }

    private void resetButtonColors() {
        for (Button btn : optionButtons) {
            btn.setBackgroundColor(Color.LTGRAY);
        }
    }

    private void startTimer() {
        timerText.setText("60");
        countDownTimer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                timerText.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                timerText.setText("0");
                handleAnswer("", null, questions.get(currentQuestionIndex));
            }
        }.start();
    }

    private void endQuiz() {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("SCORE", score);
        intent.putExtra("TOTAL", questions.size());
        startActivity(intent);
        finish();
    }
}
