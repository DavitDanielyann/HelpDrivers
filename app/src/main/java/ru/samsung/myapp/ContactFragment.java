package ru.samsung.myapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ContactFragment extends Fragment {

    private EditText editName, editEmail, editPassword, editCurrentPassword;
    private Button saveButton;
    private DatabaseReference reference;
    private String username;
    private TextView logout;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        editName = view.findViewById(R.id.editName);
        editEmail = view.findViewById(R.id.editEmail);
        editPassword = view.findViewById(R.id.editPassword);
        editCurrentPassword = view.findViewById(R.id.editCurrentPassword);
        saveButton = view.findViewById(R.id.contact_button);
        logout = view.findViewById(R.id.logout);
        progressBar = view.findViewById(R.id.progressBar);
        
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        // Retrieve logged-in user's data from SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");

        if (!username.isEmpty()) {
            progressBar.setVisibility(View.VISIBLE);
            reference = FirebaseDatabase.getInstance().getReference("Users").child(username);

            // Load data from Firebase
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    progressBar.setVisibility(View.GONE);
                    if (snapshot.exists()) {
                        HelperClass user = snapshot.getValue(HelperClass.class);
                        if (user != null) {
                            editName.setText(user.getName());
                            editEmail.setText(user.getEmail());
                            // Don't set password for security reasons
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Failed to load profile", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Set onClickListener to update profile data
        saveButton.setOnClickListener(v -> {
            if (validateInputs()) {
                showReAuthDialog();
            }
        });

        // Set onClickListener for logout button
        logout.setOnClickListener(v -> logoutUser());

        return view;
    }
    
    private boolean validateInputs() {
        String newName = editName.getText().toString().trim();
        String newEmail = editEmail.getText().toString().trim();
        String newPassword = editPassword.getText().toString().trim();
        String currentPassword = editCurrentPassword.getText().toString().trim();
        
        if (newName.isEmpty()) {
            editName.setError("Name is required");
            return false;
        }
        
        if (newEmail.isEmpty()) {
            editEmail.setError("Email is required");
            return false;
        }
        
        if (currentPassword.isEmpty()) {
            editCurrentPassword.setError("Current password is required to make changes");
            return false;
        }
        
        return true;
    }
    
    private void showReAuthDialog() {
        String currentPassword = editCurrentPassword.getText().toString().trim();
        
        if (currentUser != null) {
            progressBar.setVisibility(View.VISIBLE);
            
            // Get credentials for re-authentication
            AuthCredential credential = EmailAuthProvider.getCredential(currentUser.getEmail(), currentPassword);
            
            // Re-authenticate
            currentUser.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // User has been re-authenticated, proceed with updates
                                updateProfile();
                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "Authentication failed. Check your current password.", 
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(getActivity(), "You must be logged in to update your profile", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateProfile() {
        String newName = editName.getText().toString().trim();
        String newEmail = editEmail.getText().toString().trim();
        String newPassword = editPassword.getText().toString().trim();

        // Update in Firebase Auth and Database
        if (currentUser != null) {
            // Update display name in Firebase Auth
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(newName)
                    .build();
            
            currentUser.updateProfile(profileUpdates);
            
            // Update email if changed
            if (!currentUser.getEmail().equals(newEmail)) {
                currentUser.updateEmail(newEmail)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // Email updated, send verification if needed
                                    currentUser.sendEmailVerification();
                                    Toast.makeText(getActivity(), "Email updated. Please verify your new email address.", 
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getActivity(), "Failed to update email: " + task.getException().getMessage(), 
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
            
            // Update password if provided
            if (!newPassword.isEmpty()) {
                currentUser.updatePassword(newPassword)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Password updated successfully", 
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), "Failed to update password: " + task.getException().getMessage(), 
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
            
            // Update in Realtime Database
            reference = FirebaseDatabase.getInstance().getReference("Users").child(username);
            
            if (reference != null) {
                reference.child("name").setValue(newName);
                reference.child("email").setValue(newEmail);
                if (!newPassword.isEmpty()) {
                    reference.child("password").setValue(newPassword);
                }
            }
            
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "Profile Updated Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    private void logoutUser() {
        // Sign out from Firebase
        if (mAuth != null) {
            mAuth.signOut();
        }
        
        // Clear SharedPreferences to remove user login data
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // Clear all preferences
        editor.apply();

        // Navigate to LoginActivity
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish(); // Optional: Finish the current activity to prevent returning to it after logging out
    }
}
