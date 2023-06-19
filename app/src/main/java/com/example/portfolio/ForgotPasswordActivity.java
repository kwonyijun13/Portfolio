package com.example.portfolio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        TextInputLayout emailTextInputLayout = findViewById(R.id.emailTextInputLayout);
        Button resetButton = findViewById(R.id.resetButton);
        Button signInButton = findViewById(R.id.signInButton);

        firebaseAuth = FirebaseAuth.getInstance();

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailTextInputLayout.getEditText().getText().toString().trim();
                if (!email.isEmpty()) {
                    firebaseAuth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    // Password reset email sent successfully
                                    Toast.makeText(getApplicationContext(), "Password reset email sent ƪ(˘⌣˘)ʃ", Toast.LENGTH_SHORT).show();
                                } else {
                                    // An error occurred while sending the password reset email
                                    Toast.makeText(getApplicationContext(), "Failed to send password reset email", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    emailTextInputLayout.setError("Invalid Email Address ಠ_ಠ");
                }
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
            }
        });
    }
}