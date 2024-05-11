package com.example.teeplan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {
    Button buttonSignup, buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonSignup = findViewById(R.id.buttonSignup);
        buttonSignup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                swapToSignUpActivity();
            }
        });

        buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void swapToSignUpActivity() {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    public void login() {
        EditText emailEdit = (EditText) findViewById(R.id.entryEmail);
        EditText passwordEdit = (EditText) findViewById(R.id.entryPassword);
        String email = emailEdit.getText().toString();
        String password = passwordEdit.getText().toString();

        if (isLoginSuccessful(email, password)) {
            toastNotification("Login successful");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            toastNotification("Login failed");
        }

    }

    public boolean isLoginSuccessful(String email, String password) {
        //TODO implement login check
        return true;
    }

    public void toastNotification(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}