package com.example.portfolio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.Login;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    // FACEBOOK
    //private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextInputLayout emailTextInputLayout = findViewById(R.id.emailTextInputLayout);
        TextInputLayout passwordTextInputLayout = findViewById(R.id.passwordTextInputLayout);
        Button signInButton = findViewById(R.id.signInButton);
        Button registerButton = findViewById(R.id.registerButton);
        LoginButton facebookButton = findViewById(R.id.facebookLoginButton);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailTextInputLayout.getEditText().getText().toString().trim();
                String password = passwordTextInputLayout.getEditText().getText().toString().trim();

                if (!email.isEmpty() && !password.isEmpty()) {
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (firebaseUser != null) {
                                        boolean isEmailVerified = firebaseUser.isEmailVerified();
                                        // IF LOGIN IS SUCCESSFUL
                                        if (task.isSuccessful()) {
                                            // CHECK IF EMAIL HAS BEEN VERIFIED
                                            if (isEmailVerified) {
                                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                            } else {
                                                Toast.makeText(LoginActivity.this, "Email not verified （￣︶￣）↗", Toast.LENGTH_LONG).show();
                                            }
                                        } else {
                                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_LONG).show();
                                        }


                                    }
                                }
                            });
                } else {
                    Toast.makeText(LoginActivity.this, "Please fill in all fields ( •̀ ω •́ )✧", Toast.LENGTH_LONG).show();
                }

            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        // FACEBOOK LOG IN (WARNING: CANNOT BE TESTED UNTIL PUBLISHED AS OF 2023-06-19)
//        callbackManager = CallbackManager.Factory.create();
//        facebookButton.setReadPermissions(Arrays.asList(emailTextInputLayout.getEditText().getText().toString().trim()));
//
//        LoginManager.getInstance().registerCallback(callbackManager,
//                new FacebookCallback<LoginResult>() {
//                    @Override
//                    public void onSuccess(LoginResult loginResult) {
//                        // App code
//                    }
//
//                    @Override
//                    public void onCancel() {
//                        // App code
//                    }
//
//                    @Override
//                    public void onError(FacebookException exception) {
//                        // App code
//                    }
//                });
//
//
//        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile"));
        // END OF FACEBOOK LOG IN (EXPRESS LOGIN NOT ENABLED)
    }

    @Override
    public void onStart() {
        super.onStart();
        boolean isEmailVerified = firebaseUser.isEmailVerified();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser != null && isEmailVerified){
            reload();
        }

        // CHECK FOR FACEBOOK USER ALREADY SIGNED IN
//        AccessToken accessToken = AccessToken.getCurrentAccessToken();
//        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
    }

    private void reload() {
        firebaseAuth.getCurrentUser().reload().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //updateUI(mAuth.getCurrentUser());
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    Toast.makeText(LoginActivity.this, "Welcome Back!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Failed to reload user.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // FACEBOOK
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//        super.onActivityResult(requestCode, resultCode, data);
//    }
}

/* TODO
Password recovery
 */