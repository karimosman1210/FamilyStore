package com.example.osman.grandresturant.Registration;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.osman.grandresturant.Dialogs.LocationDialog;
import com.example.osman.grandresturant.Helper.HelperMethods;
import com.example.osman.grandresturant.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class Sign extends AppCompatActivity {
    Button signUpBtnSignUp, signUpBtnCancel;
    EditText emailEtSignUp, passwordEtSignUp, surepassSignUp, nameEt, user_mobile, user_country;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener listener;
    String User_Type, myEmail, CountryLocation;
    private DatabaseReference mDatabaseUsers;
    private StorageReference mStorageRef;
    DatabaseReference currentuser_db;
    TextView sign_location ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        signUpBtnCancel = (Button) findViewById(R.id.signUpBtnCancel);



        sign_location = (TextView) findViewById(R.id.sign_location_textview) ;
        signUpBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mStorageRef=mStorageRef = FirebaseStorage.getInstance().getReference();


        sign_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LocationDialog cdd=new LocationDialog(Sign.this);
                cdd.show();
            }
        });








        signUpBtnSignUp = (Button) findViewById(R.id.signUpBtnSignUp);
        emailEtSignUp = (EditText) findViewById(R.id.emailEtSignUp);
        passwordEtSignUp = (EditText) findViewById(R.id.passwordEtSignUp);
        surepassSignUp = (EditText) findViewById(R.id.surepassSignUp);
        nameEt = (EditText) findViewById(R.id.nameEt);
        user_mobile = (EditText) findViewById(R.id.phone_Et);
        auth = FirebaseAuth.getInstance();



        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");

        signUpBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    signUp();



            }
        });



    }


    private void signUp() {
        myEmail = emailEtSignUp.getText().toString().trim();
        String myPass = passwordEtSignUp.getText().toString().trim();
        String passSure = surepassSignUp.getText().toString().trim();

        if (TextUtils.isEmpty(myEmail) || TextUtils.isEmpty(myPass)  || TextUtils.isEmpty(passSure)) {

            Toast.makeText(this, "أكمل البيانات", Toast.LENGTH_SHORT).show();


        } else if (!myPass.equals(passSure)) {
            Toast.makeText(this, "كلمة السر غير متطابقة", Toast.LENGTH_SHORT).show();
        } else if (User_Type == null) {
            Toast.makeText(this, "أختار حالة المستخدم ", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(HelperMethods.sign_location)) {
            Toast.makeText(this, "أختار مكان المستخدم ", Toast.LENGTH_SHORT).show();
        }else {

            HelperMethods.showDialog(Sign.this, "Wait...", "Create new user");

            auth.createUserWithEmailAndPassword(myEmail, myPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()) {

                        Toast.makeText(Sign.this, "password less than 6 character !", Toast.LENGTH_SHORT).show();
                    } else if (task.isSuccessful()) {
                        String token = auth.getCurrentUser().getUid();
                        String myName = nameEt.getText().toString();

                        try {
                            String user_id = auth.getCurrentUser().getUid();
                             currentuser_db = mDatabaseUsers.child(user_id);
                            currentuser_db.child("username").setValue(nameEt.getText().toString());
                            currentuser_db.child("user_tpe").setValue(User_Type);
                            currentuser_db.child("email").setValue(myEmail);
                            currentuser_db.child("mobile").setValue(user_mobile.getText().toString());
                            currentuser_db.child("country").setValue( HelperMethods.sign_location);
                            currentuser_db.child("profile_image,").setValue("https://firebasestorage.googleapis.com/v0/b/clashbook-3a339.appspot.com/o/default-user-icon-profile.png?alt=media&token=27cc7679-276a-497e-90a5-b558c26275ab");


                            HelperMethods.hideDialog(Sign.this);
                            startActivity(new Intent(Sign.this , User_Profile_image.class));
                            HelperMethods.sign_location = null;

                        } catch (Exception e) {


                        }


                    }

                }
            });

        }

    }



    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {

            case R.id.radio_user:
                if (checked)
                    User_Type = "user";
                break;
            case R.id.radio_company:
                if (checked)
                    User_Type = "company";
                break;
        }


    }



}
