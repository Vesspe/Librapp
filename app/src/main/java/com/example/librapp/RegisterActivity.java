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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {


    private EditText emailText;
    private EditText passwordText;
    private EditText passwordTextConfirm;
    private EditText nameText;
    private FirebaseAuth mAuth;
    public static final String TAG = "register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        emailText = findViewById(R.id.emailRegister);
        passwordText = findViewById(R.id.passwordRegister);
        nameText = findViewById(R.id.nameRegister);
        passwordTextConfirm = findViewById(R.id.passwordRegisterConfirm);

        mAuth = FirebaseAuth.getInstance();
    }

    public void Register(View view) {
        final String email = emailText.getText().toString();
        final String name =  nameText.getText().toString();
        String password = passwordText.getText().toString();
        String passwordConfirm = passwordTextConfirm.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 8) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 8 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

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

                                //creating user in database
                                UserModel newUser = new UserModel(mAuth.getCurrentUser().getUid(), email,name);
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference();
                                myRef.child("Users")
                                        .child(mAuth.getCurrentUser().getUid())
                                        .setValue(newUser);


                                /*FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference();
                                User user = new User();
                                myRef.setValue(user);
                                myRef.child("Users")
                                        .child(user.getUid())
                                        .setValue(user);*/

                                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);

                            } else {
                                //sign in fail
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegisterActivity.this, "Something went wrong, please try again in a moment",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
}
