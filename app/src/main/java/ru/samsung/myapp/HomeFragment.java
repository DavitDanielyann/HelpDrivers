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

public class HomeFragment extends Fragment {
    Button Expensebutton;
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Find the Expense History button by ID
        Button reminderButton = view.findViewById(R.id.Reminder_button);

        // Set click listener to navigate to ExpenseHistoryActivity1
        reminderButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ExpenseHistoryActivity1.class);
            startActivity(intent);
        });

        Button expenseButton = view.findViewById(R.id.Expense_button);

        // Set click listener to navigate to ExpenseHistoryActivity
        expenseButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ExpenseHistoryActivity.class);
            startActivity(intent);
        });
        return view;
    }
}


