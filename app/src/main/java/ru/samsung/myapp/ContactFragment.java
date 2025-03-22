package ru.samsung.myapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ContactFragment extends Fragment {

    private EditText editName, editEmail, editPassword;
    private Button saveButton;
    private DatabaseReference reference;
    private String username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        editName = view.findViewById(R.id.editName);
        editEmail = view.findViewById(R.id.editEmail);
        editPassword = view.findViewById(R.id.editPassword);
        saveButton = view.findViewById(R.id.contact_button);

        // Ստանալ Logged-in օգտատիրոջ տվյալները SharedPreferences-ից
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");

        if (!username.isEmpty()) {
            reference = FirebaseDatabase.getInstance().getReference("Users").child(username);

            // Տվյալների բեռնում Firebase-ից
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        HelperClass user = snapshot.getValue(HelperClass.class);
                        if (user != null) {
                            editName.setText(user.getName());
                            editEmail.setText(user.getEmail());
                            editPassword.setText(user.getPassword());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getActivity(), "Failed to load profile", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Տեղադրելու կոճակի գործառույթը՝ տվյալները նորացնելիս
        saveButton.setOnClickListener(v -> updateProfile());

        return view;
    }

    private void updateProfile() {
        String newName = editName.getText().toString().trim();
        String newEmail = editEmail.getText().toString().trim();
        String newPassword = editPassword.getText().toString().trim();

        if (newName.isEmpty() || newEmail.isEmpty() || newPassword.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Նոր տվյալները պահելու Firebase-ում
        reference.child("name").setValue(newName);
        reference.child("email").setValue(newEmail);
        reference.child("password").setValue(newPassword);

        Toast.makeText(getActivity(), "Profile Updated Successfully!", Toast.LENGTH_SHORT).show();
    }
}