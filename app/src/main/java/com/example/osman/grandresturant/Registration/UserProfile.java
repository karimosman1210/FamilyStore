package com.example.osman.grandresturant.Registration;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.osman.grandresturant.R;
import com.example.osman.grandresturant.classes.Model_user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class UserProfile extends AppCompatActivity {
    EditText phoneEditProfile, countryEditProfile, emailEditProfile, password_old, password_new, password_new2;
    TextView tv_email, change_password, backTv;
    ImageView imageButton;
    Button editBtnProfile;
    FirebaseAuth auth;
    DatabaseReference database;
    ProgressBar progressall;
    LinearLayout linear_password, allLiniar;
    Uri imageUri;
    private static final int RUSLET_LOAD_IMAGE = 1;
    private StorageReference mStorageReference;
    boolean checkClick = false;


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        phoneEditProfile = (EditText) findViewById(R.id.phoneEditProfile);
        countryEditProfile = (EditText) findViewById(R.id.countryEditProfile);
        emailEditProfile = (EditText) findViewById(R.id.emailEditProfile);
        editBtnProfile = (Button) findViewById(R.id.editBtnProfile);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference().child("Users");
        final String id_user = auth.getCurrentUser().getUid();
        progressall = (ProgressBar) findViewById(R.id.progressall);
        tv_email = (TextView) findViewById(R.id.tv_email);
        password_old = (EditText) findViewById(R.id.password_old);
        password_new = (EditText) findViewById(R.id.password_new);
        password_new2 = (EditText) findViewById(R.id.password_new2);
        imageButton = (ImageView) findViewById(R.id.imagebutton_user);
        linear_password = (LinearLayout) findViewById(R.id.Linear_password);
        change_password = (TextView) findViewById(R.id.change_password);
        backTv = (TextView) findViewById(R.id.backTv);
        mStorageReference = FirebaseStorage.getInstance().getReference();
        allLiniar = (LinearLayout) findViewById(R.id.allLiniar);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent open = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(open, RUSLET_LOAD_IMAGE);


            }
        });


        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_password.setVisibility(View.VISIBLE);
                change_password.setVisibility(View.INVISIBLE);
                backTv.setVisibility(View.VISIBLE);
                backTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        linear_password.setVisibility(View.GONE);
                        backTv.setVisibility(View.INVISIBLE);
                        change_password.setVisibility(View.VISIBLE);

                    }
                });
            }
        });


        database.child(id_user).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Model_user model_user = dataSnapshot.getValue(Model_user.class);
                tv_email.setText(model_user.getEmail());

                phoneEditProfile.setText(model_user.getMobile());
                emailEditProfile.setText(model_user.getUsername());
                countryEditProfile.setText(model_user.getCountry());
                Picasso.with(UserProfile.this).load(model_user.getProfile_image()).into(imageButton);
                progressall.setVisibility(View.INVISIBLE);
                allLiniar.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        editBtnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {


                    if (linear_password.getVisibility() == View.VISIBLE) {
                        change();
                    } else if (!password_new2.getText().toString().isEmpty()) {

                    } else {


                        final AlertDialog.Builder builder = new AlertDialog.Builder(UserProfile.this);
                        builder.setMessage("هل انت متاكد من تعديل معلوماتك الشخصيه");

                        builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                String phone = phoneEditProfile.getText().toString().trim();
                                String country = countryEditProfile.getText().toString().trim();
                                String email = emailEditProfile.getText().toString().trim();
                                // database.child(id_user).child("profile_image").setValue(imageUri.toString());

                                database.child(id_user).child("country").setValue(country);
                                database.child(id_user).child("username").setValue(email);
                                database.child(id_user).child("mobile").setValue(phone);
                                try {
                                    if (checkClick == true) {
                                        progressall.setVisibility(View.VISIBLE);
                                        mStorageReference.child(id_user).child("profile.jpg").putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                                if (task.isSuccessful()) {

                                                    database.child(id_user).child("profile_image").setValue(task.getResult().getDownloadUrl().toString());
                                                    progressall.setVisibility(View.INVISIBLE);
                                                    Toast.makeText(UserProfile.this, "تم التعديل", Toast.LENGTH_SHORT).show();
                                                    progressall.setVisibility(View.INVISIBLE);
                                                    finish();

                                                }
                                            }
                                        });
                                    }


                                } catch (Exception e) {

                                }

                            }


                        });


                        builder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        //creating alert dialog
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                    }

                } catch (Exception e) {

                }

            }
        });


    }

    public void change() {
        if (password_old.getText().toString().isEmpty()) {
            Toast.makeText(this, "ادخل الرقم السري الحالي", Toast.LENGTH_SHORT).show();
        } else if (password_new.getText().toString().isEmpty()) {
            Toast.makeText(this, "ادخل الرقم السري الجديد", Toast.LENGTH_SHORT).show();
        } else if (password_new2.getText().toString().isEmpty()) {
            Toast.makeText(this, "اعد ادخال الرقم السري الجديد", Toast.LENGTH_SHORT).show();
        } else if (password_new.getText().toString().equals(password_new2.getText().toString())) {

            auth.signInWithEmailAndPassword(auth.getCurrentUser().getEmail(), password_old.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        auth.getCurrentUser().updatePassword(password_new.getText().toString());
                        Toast.makeText(UserProfile.this, "تم التعديل ", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {

                        Toast.makeText(UserProfile.this, "الباسورد غير صحيح", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "اعد كتابه الباسورد صحيح", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                imageUri = data.getData();
                checkClick = true;
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);

                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageButton.setImageBitmap(selectedImage);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(UserProfile.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(UserProfile.this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }
}
