/*package ru.samsung.myapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ExpenseHistoryActivity1 extends AppCompatActivity {

    TableLayout expenseTable;
    Button saveButton;
    TextView addButton;
    FirebaseDatabase database;
    DatabaseReference reference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_history1);

        // Initialize Firebase
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("ExpenseHistory");

        // Initialize views
        expenseTable = findViewById(R.id.expenseTable);
        addButton = findViewById(R.id.addButton);
        saveButton = findViewById(R.id.saveButton);

        // List of months for Spinner
        String[] monthsArray = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

        // Add new row on click
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewRow(monthsArray);
            }
        });

        // Save all expenses to Firebase
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveExpenses();
            }
        });
    }

    private void addNewRow(String[] monthsArray) {
        // Create a new TableRow
        TableRow newRow = new TableRow(this);

        // Create Spinner for Month selection
        Spinner monthSpinner = new Spinner(this);
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, monthsArray);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(monthAdapter);
        monthSpinner.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));

        // Create EditText for Description
        EditText descriptionEditText = new EditText(this);
        descriptionEditText.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2));
        descriptionEditText.setHint("Description");

        // Create EditText for Price
        EditText priceEditText = new EditText(this);
        priceEditText.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
        priceEditText.setHint("Price");

        // Add views to TableRow
        newRow.addView(monthSpinner);
        newRow.addView(descriptionEditText);
        newRow.addView(priceEditText);

        // Add the new row to TableLayout
        expenseTable.addView(newRow);
    }

    private void saveExpenses() {
        // Loop through all rows in the TableLayout
        for (int i = 0; i < expenseTable.getChildCount(); i++) {
            View rowView = expenseTable.getChildAt(i);

            if (rowView instanceof TableRow) {
                TableRow row = (TableRow) rowView;

                Spinner monthSpinner = (Spinner) row.getChildAt(0);
                EditText descriptionEditText = (EditText) row.getChildAt(1);
                EditText priceEditText = (EditText) row.getChildAt(2);

                String month = monthSpinner.getSelectedItem().toString();
                String description = descriptionEditText.getText().toString().trim();
                String price = priceEditText.getText().toString().trim();

                // Validate fields
                if (description.isEmpty() || price.isEmpty()) {
                    Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Convert price to double
                double priceValue;
                try {
                    priceValue = Double.parseDouble(price);
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Invalid price format", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create a unique ID for each expense entry
                String expenseId = reference.push().getKey();

                if (expenseId == null) {
                    Toast.makeText(this, "Failed to generate expense ID", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create Expense object
                Expense expense = new Expense(month, description, priceValue);

                // Save to Firebase database
                reference.child(expenseId).setValue(expense).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Expense saved successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Error saving expense", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    // Expense model class
    public static class Expense {
        String month, description;
        double price;

        public Expense() {
            // Default constructor required for Firebase
        }

        public Expense(String month, String description, double price) {
            this.month = month;
            this.description = description;
            this.price = price;
        }
    }
}*/
