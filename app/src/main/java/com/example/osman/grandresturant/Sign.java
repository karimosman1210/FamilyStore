package com.example.osman.grandresturant;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Sign extends AppCompatActivity {
Button intentSignIn,signUpBtnSignUp;
EditText emailEtSignUp,passwordEtSignUp,surepassSignUp,nameEt;
FirebaseAuth auth;
FirebaseAuth.AuthStateListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        intentSignIn=(Button)findViewById(R.id.intentSignIn);
        signUpBtnSignUp=(Button)findViewById(R.id.signUpBtnSignUp);

        emailEtSignUp=(EditText)findViewById(R.id.emailEtSignUp);
        passwordEtSignUp=(EditText)findViewById(R.id.passwordEtSignUp);
        surepassSignUp=(EditText)findViewById(R.id.surepassSignUp);
        nameEt=(EditText)findViewById(R.id.nameEt);
        intentSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        signUpBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();

            }
        });
        auth=FirebaseAuth.getInstance();
         listener=new FirebaseAuth.AuthStateListener() {
             @Override
             public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                 FirebaseUser user=firebaseAuth.getCurrentUser();
                 if (user!=null){
                     finish();

                 }

             }
         };


    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(listener);
    }

    private void signUp() {
        String myEmail=emailEtSignUp.getText().toString().trim();
        String myPass=passwordEtSignUp.getText().toString().trim();
        String passSure=surepassSignUp.getText().toString().trim();

        if (TextUtils.isEmpty(myEmail)||TextUtils.isEmpty(myPass)||TextUtils.isEmpty(passSure)){

            Toast.makeText(this, "Filed is empty", Toast.LENGTH_SHORT).show();


        }else if (!myPass.equals(passSure)){
            Toast.makeText(this, "password not equle ", Toast.LENGTH_SHORT).show();
        }
        else {

            auth.createUserWithEmailAndPassword(myEmail,myPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()){

                        Toast.makeText(Sign.this, "password less than 6 character !", Toast.LENGTH_SHORT).show();
                    }
                    else if (task.isSuccessful()){
                            String token=auth.getCurrentUser().getUid();
                        String myName=nameEt.getText().toString();


                    }

                }
            });

        }

    }
}
