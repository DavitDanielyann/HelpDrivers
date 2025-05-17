package ru.samsung.myapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    EditText signupName, signupUsername, signupEmail, signupPassword, signupConfirmPassword;
    TextView loginRedirectText;
    Button signupButton;
    FirebaseDatabase database;
    DatabaseReference reference;
    SharedPreferences sharedPreferences;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupName = findViewById(R.id.signup_name);
        signupEmail = findViewById(R.id.signup_email);
        signupUsername = findViewById(R.id.signup_username);
        signupPassword = findViewById(R.id.signup_password);
        signupConfirmPassword = findViewById(R.id.signup_confirm_password);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        signupButton = findViewById(R.id.signup_button);
        progressBar = findViewById(R.id.progressBar);
        
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        
        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = signupName.getText().toString();
                String email = signupEmail.getText().toString();
                String username = signupUsername.getText().toString();
                String password = signupPassword.getText().toString();
                String confirmPassword = signupConfirmPassword.getText().toString();

                if (validateInputs(name, email, username, password, confirmPassword)) {
                    progressBar.setVisibility(View.VISIBLE);
                    registerUser(name, email, username, password);
                }
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean validateInputs(String name, String email, String username, String password, String confirmPassword) {
        if (name.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signupEmail.setError("Invalid email address!");
            signupEmail.requestFocus();
            return false;
        }

        if (!isValidPassword(password)) {
            signupPassword.setError("Password must have at least 8 characters, a capital letter, a small letter, a number, and a special character!");
            signupPassword.requestFocus();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            signupConfirmPassword.setError("Passwords do not match!");
            signupConfirmPassword.requestFocus();
            return false;
        }

        return true;
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&  // At least one capital letter
                password.matches(".*[a-z].*") &&  // At least one small letter
                password.matches(".*\\d.*") &&    // At least one digit
                password.matches(".*[@#$%^&+=!].*"); // At least one special character
    }

    private void saveUserData(String username, String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.apply();
    }

    private void registerUser(final String name, final String email, final String username, final String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            FirebaseUser user = mAuth.getCurrentUser();
                            
                            // Send verification email
                            sendVerificationEmail(user);
                            
                            // Save user to Firebase Realtime Database
                            saveUserToFirebase(name, email, username, password);
                            
                            // Save local user data
                            saveUserData(username, password);
                            
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(SignupActivity.this, "Registration successful! Please check your email for verification.", 
                                    Toast.LENGTH_LONG).show();
                            
                            // Return to login screen
                            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(SignupActivity.this, "Registration failed: " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    
    private void sendVerificationEmail(FirebaseUser user) {
        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignupActivity.this, 
                                        "Verification email sent to " + user.getEmail(), 
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignupActivity.this,
                                        "Failed to send verification email: " + task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void saveUserToFirebase(String name, String email, String username, String password) {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users");

        HelperClass helperClass = new HelperClass(name, email, username, password);
        reference.child(username).setValue(helperClass);
    }
}
