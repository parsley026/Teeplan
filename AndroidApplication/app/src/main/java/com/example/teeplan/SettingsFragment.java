package com.example.teeplan;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.teeplan.mailRelatedUtil.JavaMailAPI;
import com.example.teeplan.mailRelatedUtil.NetworkUtil;
import androidx.fragment.app.Fragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsFragment extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        Button buttonSignOut = rootView.findViewById(R.id.buttonSignOut);
        buttonSignOut.setOnClickListener(v -> signUserOut());

        TextView accountInfoName = rootView.findViewById(R.id.accountInfoName);
        TextView accountInfoEmail = rootView.findViewById(R.id.accountInfoEmail);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            mDatabase.child("users").child(userId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    UserData user = dataSnapshot.getValue(UserData.class);
                    if (user != null) {
                        accountInfoName.setText(user.first_name + " " + user.last_name);
                        accountInfoEmail.setText(user.email);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getActivity(), "Failed to load user data.", Toast.LENGTH_SHORT).show();
                }
            });
        }
        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUserOut();
            }
        });
        Button buttonReport = rootView.findViewById(R.id.buttonReport);
        buttonReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReportBugPopup();
            }
        });

        Button buttonChangePass = rootView.findViewById(R.id.buttonChangePass);
        buttonChangePass.setOnClickListener(v -> showChangePasswordPopup());


        return rootView;
    }

    private void signUserOut() {
        mAuth.signOut();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    private void showReportBugPopup() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.report_a_bug, null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        Button submitBugReport = dialogView.findViewById(R.id.sendReport);
        submitBugReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText bugDescription = dialogView.findViewById(R.id.reportBug);
                String description = bugDescription.getText().toString();
                if (!description.isEmpty()) {
                    if (NetworkUtil.isNetworkAvailable(getActivity())) {
                        JavaMailAPI javaMailAPI = new JavaMailAPI(getActivity(), "245849@edu.p.lodz.pl", "Bug Report", description);
                        javaMailAPI.execute();

                        alertDialog.dismiss();
                    }
                    else{
                        Toast.makeText(getActivity(), "Internet connection is required", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Please enter a description", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showChangePasswordPopup() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.change_password, null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        Button changePasswordButton = dialogView.findViewById(R.id.changePass);
        changePasswordButton.setOnClickListener(v -> {
            EditText currentPassword = dialogView.findViewById(R.id.currentPassword);
            EditText newPassword = dialogView.findViewById(R.id.newPassword);
            EditText confirmNewPassword = dialogView.findViewById(R.id.confirmNewPassword);

            String currentPass = currentPassword.getText().toString();
            String newPass = newPassword.getText().toString();
            String confirmPass = confirmNewPassword.getText().toString();

            if (!newPass.equals(confirmPass)) {
                Toast.makeText(getActivity(), "New passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null && !currentPass.isEmpty() && !newPass.isEmpty()) {
                    AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), currentPass);
                    user.reauthenticate(credential).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            user.updatePassword(newPass)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
                                                userRef.child("password").setValue(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(getActivity(), "Password changed successfully", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(getActivity(), "Failed to update password", Toast.LENGTH_SHORT).show();
                                                        }
                                                        alertDialog.dismiss();
                                                    }
                                                });
                                            } else {
                                                Toast.makeText(getActivity(), "Failed to change password", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(getActivity(), "Authentication failed. Check your current password", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}

