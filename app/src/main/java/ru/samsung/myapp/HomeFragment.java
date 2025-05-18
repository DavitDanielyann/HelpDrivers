// HomeFragment.java
package ru.samsung.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.samsung.myapp.R;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button btnArmenia = view.findViewById(R.id.btnArmenia);
        Button btnProgramming = view.findViewById(R.id.btnProgramming);
        Button btnGeneral = view.findViewById(R.id.btnGeneral);
        Button btnLearn = view.findViewById(R.id.btnLearn);

        btnArmenia.setOnClickListener(v -> launchQuiz("armenia"));
        btnProgramming.setOnClickListener(v -> launchQuiz("programming"));
        btnGeneral.setOnClickListener(v -> launchQuiz("general"));
        btnLearn.setOnClickListener(v -> startActivity(new Intent(getActivity(), LearnActivity.class)));

        return view;
    }

    private void launchQuiz(String category) {
        Intent intent = new Intent(getActivity(), QuizActivity.class);
        intent.putExtra("CATEGORY", category);
        startActivity(intent);
    }
}