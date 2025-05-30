package ru.samsung.myapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    TextView resultText;
    Button btnGoHome, btnReview;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultText = findViewById(R.id.resultText);
        btnGoHome = findViewById(R.id.btnGoHome);
        btnReview = findViewById(R.id.btnReview);

        int score = getIntent().getIntExtra("SCORE", 0);
        int total = getIntent().getIntExtra("TOTAL", 20);
        resultText.setText("Your Score: " + score + " / " + total);

        // Save score to leaderboard
        saveScore(score);

        btnGoHome.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        btnReview.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, LearnActivity.class);
            startActivity(intent);
        });
    }

    private void saveScore(int score) {
        SharedPreferences prefs = getSharedPreferences("Leaderboard", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Default to Guest unless you have login info
        String username = getSharedPreferences("MyPrefs", MODE_PRIVATE)
                .getString("email", "Guest");

        int previousBest = prefs.getInt(username, 0);
        if (score > previousBest) {
            editor.putInt(username, score); // Save only if it's a higher score
            editor.apply();
        }
    }
}
