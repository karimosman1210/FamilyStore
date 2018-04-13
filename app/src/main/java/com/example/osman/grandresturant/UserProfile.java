package com.example.osman.grandresturant;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.osman.grandresturant.classes.Model_user;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity {
EditText phoneEditProfile,countryEditProfile,emailEditProfile;
Button editBtnProfile;
FirebaseAuth auth;
DatabaseReference database;
ProgressBar myProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        phoneEditProfile=(EditText)findViewById(R.id.phoneEditProfile);
        countryEditProfile=(EditText)findViewById(R.id.countryEditProfile);
        emailEditProfile=(EditText)findViewById(R.id.emailEditProfile);
        editBtnProfile=(Button)findViewById(R.id.editBtnProfile);
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance().getReference().child("Users");
        final String id_user=auth.getCurrentUser().getUid();
        myProgress=(ProgressBar)findViewById(R.id.myProgress);



        database.child(id_user).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Model_user model_user=dataSnapshot.getValue(Model_user.class);
                phoneEditProfile.setText(model_user.getMobile());
                emailEditProfile.setText(model_user.getEmail());
                countryEditProfile.setText(model_user.getCountry());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        editBtnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(UserProfile.this);
                builder.setMessage("هل انت متاكد من تعديل معلوماتك الشخصيه");

                builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myProgress.setVisibility(View.VISIBLE);
                        String phone=phoneEditProfile.getText().toString().trim();
                        String country=countryEditProfile.getText().toString().trim();
                        String email=emailEditProfile.getText().toString().trim();

                        database.child(id_user).child("country").setValue(country);
                        database.child(id_user).child("email").setValue(email);
                        database.child(id_user).child("mobile").setValue(phone);
                        myProgress.setVisibility(View.INVISIBLE);
                        finish();

                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //perform any action
                    }
                });

                //creating alert dialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();



            }
        });





    }
}
