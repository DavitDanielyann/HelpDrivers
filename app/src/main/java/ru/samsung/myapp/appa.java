/*package ru.samsung.myapp;


import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import java.util.Calendar;

public class appa extends AppCompatActivity {

    private CalendarView calendarView;
    private Button btnSave;
    private long selectedDate; // Stores the selected date

    private static final String CHANNEL_ID = "oil_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oil);

        calendarView = findViewById(R.id.calendarView);
        btnSave = findViewById(R.id.btnSave);

        // Get the first day of 2025
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.JANUARY, 1);
        long minDate = calendar.getTimeInMillis();

        // Set minimum selectable date to 2025
        calendarView.setMinDate(minDate);
        selectedDate = minDate; // Default to first day of 2025

        // Handle date selection
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                if (year >= 2025) {
                    selectedDate = view.getDate();
                    Toast.makeText(appa.this, "Selected Date: " + dayOfMonth + "/" + (month + 1) + "/" + year, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(appa.this, "Please select a date from 2025 onwards!", Toast.LENGTH_SHORT).show();
                    calendarView.setDate(minDate); // Reset to first day of 2025
                }
            }
        });
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(appa.this, CHANNEL_ID);
                builder.setContentTitle("My Title");
                builder.setContentText("Data saved successfully");
                builder.setSmallIcon(R.drawable.ic_launcher_background);
                builder.setAutoCancel(true);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(appa.this);
                managerCompat.notify(12, builder.build());

            }
        });

    }

}*/
