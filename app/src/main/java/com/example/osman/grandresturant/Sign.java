package com.example.osman.grandresturant;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.osman.grandresturant.Helper.HelperMethods;
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
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class Sign extends AppCompatActivity {
    Button signUpBtnSignUp, signUpBtnCancel;
    EditText emailEtSignUp, passwordEtSignUp, surepassSignUp, nameEt, user_mobile, user_country;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener listener;
    String User_Type, myEmail, CountryLocation;
    private DatabaseReference mDatabaseUsers;
    MaterialBetterSpinner Country;
    private static final int RUSLET_LOAD_IMAGE = 1;
    private StorageReference mStorageRef;
    ArrayAdapter<String> CountrySpinnerAdapter;
    String[] spinnerListCountry = {"بنى سويف", "الشرقية", "المنصورة", "المنوفية", "الجيزة", "القاهرة"};
    ImageView myImgeSign;
    Uri imageUri;
    DatabaseReference currentuser_db;
    boolean check=false;
    ProgressBar progressSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        signUpBtnCancel = (Button) findViewById(R.id.signUpBtnCancel);
        Country = (MaterialBetterSpinner) findViewById(R.id.sign_in_spinner);
        myImgeSign = (ImageView) findViewById(R.id.myImgeSign);
        progressSignUp=(ProgressBar)findViewById(R.id.progressSignUp);
        CountrySpinnerAdapter = new ArrayAdapter<String>(Sign.this, android.R.layout.simple_dropdown_item_1line, spinnerListCountry);
        Country.setAdapter(CountrySpinnerAdapter);
        signUpBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mStorageRef=mStorageRef = FirebaseStorage.getInstance().getReference();

        myImgeSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent open = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(open, RUSLET_LOAD_IMAGE);

            }
        });


        Country.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                CountryLocation = adapterView.getItemAtPosition(i).toString();

            }
        });

        signUpBtnSignUp = (Button) findViewById(R.id.signUpBtnSignUp);
        emailEtSignUp = (EditText) findViewById(R.id.emailEtSignUp);
        passwordEtSignUp = (EditText) findViewById(R.id.passwordEtSignUp);
        surepassSignUp = (EditText) findViewById(R.id.surepassSignUp);
        nameEt = (EditText) findViewById(R.id.nameEt);
        user_mobile = (EditText) findViewById(R.id.phone_Et);


        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");

        signUpBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check==true) {
                    signUp();
                }
            }
        });
        auth = FirebaseAuth.getInstance();
        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    finish();

                }

            }
        };


    }


    private void signUp() {
        myEmail = emailEtSignUp.getText().toString().trim();
        String myPass = passwordEtSignUp.getText().toString().trim();
        String passSure = surepassSignUp.getText().toString().trim();

        if (TextUtils.isEmpty(myEmail) || TextUtils.isEmpty(myPass) || TextUtils.isEmpty(passSure)) {

            Toast.makeText(this, "Filed is empty", Toast.LENGTH_SHORT).show();


        } else if (!myPass.equals(passSure)) {
            Toast.makeText(this, "password not equle ", Toast.LENGTH_SHORT).show();
        } else if (User_Type == null) {
            Toast.makeText(this, "أختار حالة المستخدم ", Toast.LENGTH_SHORT).show();
        } else {

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
                            currentuser_db.child("country").setValue(CountryLocation);
                            HelperMethods.hideDialog(Sign.this);


                        } catch (Exception e) {


                        }
                     if (check==true) {

                         progressSignUp.setVisibility(View.VISIBLE);
                         mStorageRef.child("UserImage").child(auth.getCurrentUser().getUid()).putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                             @Override
                             public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                 if (task.isSuccessful()) {

                                     currentuser_db.child("profile_image").setValue(task.getResult().getDownloadUrl().toString());
                                     progressSignUp.setVisibility(View.INVISIBLE);
                                     Toast.makeText(Sign.this, "Signed Up Successfully", Toast.LENGTH_SHORT).show();
                                     finish();
                                 }

                             }
                         });
                     }


                    }

                }
            });

        }

    }


    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                imageUri = data.getData();
                check=true;
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                myImgeSign.setImageBitmap(selectedImage);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(Sign.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(Sign.this, "You haven't picked Image", Toast.LENGTH_LONG).show();
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
