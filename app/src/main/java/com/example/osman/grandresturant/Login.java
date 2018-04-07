package com.example.osman.grandresturant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private EditText email, password;
    private Button logInBtn;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private ProgressBar progress;
    TextView Tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        email = (EditText) findViewById(R.id.emailEt);
        password = (EditText) findViewById(R.id.passwordEt);
        logInBtn = (Button) findViewById(R.id.loginBtn);

        Tv = (TextView) findViewById(R.id.Tv);
        Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Sign.class));

            }
        });


        auth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    startActivity(new Intent(Login.this, DrawLayout.class));

                }


            }
        };

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myEmail = email.getText().toString().trim();
                String myPassword = password.getText().toString().trim();
                if (!TextUtils.isEmpty(myEmail) || !TextUtils.isEmpty(myPassword)) {


                }
                logIn();

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    private void logIn() {
        String myEmail = email.getText().toString().trim();
        String myPassword = password.getText().toString().trim();
        if (TextUtils.isEmpty(myEmail) || TextUtils.isEmpty(myPassword)) {
            Toast.makeText(this, "Filed is empty", Toast.LENGTH_SHORT).show();
        } else {
            auth.signInWithEmailAndPassword(myEmail, myPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()) {

                        Toast.makeText(Login.this, "email or password is incorrect", Toast.LENGTH_SHORT).show();

                    }

                }
            });

        }

    }
}
