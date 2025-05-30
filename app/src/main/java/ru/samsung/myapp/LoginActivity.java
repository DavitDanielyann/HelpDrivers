package ru.samsung.myapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.FirebaseApp;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    EditText login_email, login_password;
    Button login_button;
    TextView signupRedirectText;
    TextView testMode; // Renamed from guest
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private SharedPreferences sharedPreferences;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase
        try {
            FirebaseApp.initializeApp(this);
            mAuth = FirebaseAuth.getInstance();
            Log.d(TAG, "Firebase initialized successfully");
        } catch (Exception e) {
            Log.e(TAG, "Firebase initialization failed: " + e.getMessage());
            Toast.makeText(this, "Error initializing app. Please try again.", Toast.LENGTH_LONG).show();
        }

        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);
        login_button = findViewById(R.id.login_button);
        signupRedirectText = findViewById(R.id.signupRedirectText);
        testMode = findViewById(R.id.guest); // Keep the same ID in XML for reuse
        progressBar = findViewById(R.id.progressBar);

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_button.setEnabled(false);

                if (!checkConnectivity()) {
                    login_button.setEnabled(true);
                    return;
                }

                String email = login_email.getText().toString().trim();
                String password = login_password.getText().toString().trim();

                if (email.isEmpty()) {
                    login_email.setError("Email is required");
                    login_email.requestFocus();
                    login_button.setEnabled(true);
                    return;
                }

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    login_email.setError("Please enter a valid email");
                    login_email.requestFocus();
                    login_button.setEnabled(true);
                    return;
                }

                if (password.isEmpty()) {
                    login_password.setError("Password is required");
                    login_password.requestFocus();
                    login_button.setEnabled(true);
                    return;
                }

                if (password.length() < 6) {
                    login_password.setError("Password must be at least 6 characters");
                    login_password.requestFocus();
                    login_button.setEnabled(true);
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                loginUser(email, password);
            }
        });

        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        // TEST MODE login logic
        testMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String testEmail = "individualproject2025@gmail.com";
                String testPassword = "Samsung2025";

                progressBar.setVisibility(View.VISIBLE);

                mAuth.signInWithEmailAndPassword(testEmail, testPassword)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "Test Mode Login Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Test Mode Login Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }

    private boolean checkConnectivity() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            boolean isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnected();

            if (!isConnected) {
                Toast.makeText(this, "No internet connection. Please check your network settings.", Toast.LENGTH_LONG).show();
                Log.e(TAG, "No network connectivity");
                return false;
            }

            if (mAuth == null) {
                Toast.makeText(this, "Error connecting to authentication service. Please restart the app.", Toast.LENGTH_LONG).show();
                Log.e(TAG, "Firebase Auth is null");
                return false;
            }

            return true;
        }
        Toast.makeText(this, "Unable to check network connection. Please restart the app.", Toast.LENGTH_LONG).show();
        Log.e(TAG, "ConnectivityManager is null");
        return false;
    }

    private void loginUser(String email, String password) {
        Log.d(TAG, "Attempting to login with email: " + email);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);

                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d(TAG, "Login successful for user: " + email);

                            if (user != null) {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("email", email);
                                editor.apply();

                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            String errorMessage;
                            Exception exception = task.getException();

                            if (exception instanceof FirebaseNetworkException) {
                                errorMessage = "Network error. Please check your internet connection and try again.";
                                Log.e(TAG, "FirebaseNetworkException: " + exception.getMessage());
                            } else if (exception != null) {
                                errorMessage = exception.getMessage();
                                Log.e(TAG, "Login failed: " + exception.getMessage());
                            } else {
                                errorMessage = "Authentication failed";
                                Log.e(TAG, "Login failed with no exception");
                            }
                            Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }
                        login_button.setEnabled(true);
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        login_button.setEnabled(true);
        progressBar.setVisibility(View.GONE);

        if (mAuth == null) {
            try {
                FirebaseApp.initializeApp(this);
                mAuth = FirebaseAuth.getInstance();
                Log.d(TAG, "Firebase re-initialized successfully");
            } catch (Exception e) {
                Log.e(TAG, "Firebase re-initialization failed: " + e.getMessage());
                Toast.makeText(this, "Error connecting to authentication service. Please restart the app.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
