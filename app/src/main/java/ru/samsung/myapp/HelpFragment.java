package ru.samsung.myapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public class HelpFragment extends Fragment {

    private ListView leaderboardListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        leaderboardListView = view.findViewById(R.id.leaderboardList);
        loadLeaderboardData();
        return view;
    }

    private void loadLeaderboardData() {
        SharedPreferences prefs = requireContext().getSharedPreferences("Leaderboard", Context.MODE_PRIVATE);
        Map<String, ?> allEntries = prefs.getAll();

        ArrayList<UserScore> leaderboardData = new ArrayList<>();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String name = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Integer) {
                leaderboardData.add(new UserScore(name, (Integer) value));
            }
        }

        // Sort scores descending
        Collections.sort(leaderboardData, new Comparator<UserScore>() {
            @Override
            public int compare(UserScore u1, UserScore u2) {
                return Integer.compare(u2.score, u1.score);
            }
        });

        ArrayList<String> displayList = new ArrayList<>();
        for (int i = 0; i < leaderboardData.size(); i++) {
            UserScore user = leaderboardData.get(i);
            displayList.add((i + 1) + ". " + user.name + " - " + user.score + " pts");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                displayList
        );

        leaderboardListView.setAdapter(adapter);
    }

    static class UserScore {
        String name;
        int score;

        UserScore(String name, int score) {
            this.name = name;
            this.score = score;
        }
    }
}
