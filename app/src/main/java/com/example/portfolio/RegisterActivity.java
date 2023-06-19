package com.example.portfolio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextInputLayout emailTextInputLayout = findViewById(R.id.emailTextInputLayout);
        TextInputLayout passwordTextInputLayout = findViewById(R.id.passwordTextInputLayout);
        TextInputLayout confirmPasswordTextInputLayout = findViewById(R.id.confirmPasswordTextInputLayout);
        Button registerButton = findViewById(R.id.registerButton);
        MaterialButton signInButton = findViewById(R.id.signInButton);

        firebaseAuth = FirebaseAuth.getInstance();

        // EMAIL VALIDATION
        emailTextInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                String email = s.toString().trim();
                if (!isValidEmail(email)) {
                    emailTextInputLayout.setError("Invalid Email Address ಠ_ಠ");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String email = s.toString().trim();
                if (!isValidEmail(email)) {
                    emailTextInputLayout.setError("Invalid Email Address ಠ_ಠ");
                } else {
                    emailTextInputLayout.setError(null);
                }
            }
        });

        // PASSWORD VALIDATION
        passwordTextInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                String password = s.toString().trim();
                if (password.length() < 6) {
                    passwordTextInputLayout.setError("Password requires at least 6 characters ~(￣▽￣)~");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String password = s.toString().trim();
                if (password.length() < 6) {
                    passwordTextInputLayout.setError("Password requires at least 6 characters ~(￣▽￣)~");
                } else {
                    passwordTextInputLayout.setError(null);
                }
            }
        });

        // CONFIRM PASSWORD VALIDATION
        confirmPasswordTextInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                String confirm = s.toString().trim();
                if (!confirm.equals(passwordTextInputLayout.getEditText().getText().toString().trim())) {
                    confirmPasswordTextInputLayout.setError("Passwords do not match (╬▔皿▔)╯");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String confirm = s.toString().trim();
                if (!confirm.equals(passwordTextInputLayout.getEditText().getText().toString().trim())) {
                    confirmPasswordTextInputLayout.setError("Passwords do not match (╬▔皿▔)╯");
                } else {
                    confirmPasswordTextInputLayout.setError(null);
                }
            }
        });

        // REGISTER ACCOUNT
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailTextInputLayout.getEditText().getText().toString().trim();
                String password = passwordTextInputLayout.getEditText().getText().toString().trim();
                String confirmPassword = confirmPasswordTextInputLayout.getEditText().getText().toString().trim();

                // CHECK FOR EMPTY FIELDS
                if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill in all the fields (っ °Д °;)っ", Toast.LENGTH_LONG).show();
                    return;
                }

                // CHECK IF PASSWORDS MATCH
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "Passwords do not match (╬▔皿▔)╯", Toast.LENGTH_LONG).show();
                    return;
                }

                // CREATE NEW USER
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    sendEmailVerification();
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailRegex);
    }

    private void sendEmailVerification() {
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Verification email sent to " + user.getEmail(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}