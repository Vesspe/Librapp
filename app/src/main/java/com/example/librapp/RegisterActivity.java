package com.example.librapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {


    private EditText emailText;
    private EditText passwordText;
    private EditText passwordTextConfirm;
    private FirebaseAuth mAuth;
    public static final String TAG = "register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        emailText = findViewById(R.id.emailRegister);
        passwordText = findViewById(R.id.passwordRegister);
        passwordTextConfirm = findViewById(R.id.passwordRegisterConfirm);
        mAuth = FirebaseAuth.getInstance();
    }

    public void Register(View view) {
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String passwordConfirm = passwordTextConfirm.getText().toString();
        if (password.equals(passwordConfirm)) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //sign in success
                                Context context = getApplicationContext();
                                Log.d(TAG, "createUserWithEmail:success");
                                Toast.makeText(context, "Welcome!", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                //updateUI(user);
                            } else {
                                //sign in fail
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegisterActivity.this, "Something went wrong, please try again in a moment",
                                        Toast.LENGTH_LONG).show();
                                //updateUI(null);
                            }
                        }
                    });
        }
    }
}
