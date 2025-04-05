package ru.samsung.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ExpenseHistoryActivity extends AppCompatActivity {
    Button oilbutton, taxbutton, tiresbutton, inspection, accumlator, appa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_expense_history);
        super.onCreate(savedInstanceState);
        oilbutton=findViewById(R.id.oil_button);
        taxbutton=findViewById(R.id.tax_button);
        tiresbutton=findViewById(R.id.Tires_button);
        inspection=findViewById(R.id.inspection_button);
        accumlator=findViewById(R.id.accumlator_button);
        appa=findViewById(R.id.appa_button);
        oilbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExpenseHistoryActivity.this, oil.class);
                startActivity(intent);
                finish();
            }
        });
        taxbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExpenseHistoryActivity.this, tax.class);
                startActivity(intent);
                finish();
            }
        });
        tiresbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExpenseHistoryActivity.this, tires.class);
                startActivity(intent);
                finish();
            }
        });
        inspection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExpenseHistoryActivity.this, inspection.class);
                startActivity(intent);
                finish();
            }
        });
        accumlator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExpenseHistoryActivity.this, accumlator.class);
                startActivity(intent);
                finish();
            }
        });
        appa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExpenseHistoryActivity.this, appa.class);
                startActivity(intent);
                finish();
            }
        });
    }
}