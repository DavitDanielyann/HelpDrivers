package ru.samsung.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Find cards by ID
        View cardArmenia = view.findViewById(R.id.cardArmenia);
        View cardProgramming = view.findViewById(R.id.cardProgramming);
        View cardGeneral = view.findViewById(R.id.cardGeneral);
        View cardLearn = view.findViewById(R.id.cardLearn);

        // Set click listeners
        cardArmenia.setOnClickListener(v -> launchQuiz("armenia"));
        cardProgramming.setOnClickListener(v -> launchQuiz("programming"));
        cardGeneral.setOnClickListener(v -> launchQuiz("general"));
        cardLearn.setOnClickListener(v -> startActivity(new Intent(getActivity(), LearnActivity.class)));

        return view;
    }

    private void launchQuiz(String category) {
        Intent intent = new Intent(getActivity(), QuizActivity.class);
        intent.putExtra("CATEGORY", category);
        startActivity(intent);
    }
}
