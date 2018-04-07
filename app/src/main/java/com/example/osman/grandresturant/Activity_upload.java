package com.example.osman.grandresturant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Activity_upload extends AppCompatActivity {

    EditText name, title, price, location, description, phone, email;
    Button upload;
    Spinner spinner_category;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        name = (EditText) findViewById(R.id.et_upload_name);
        title = (EditText) findViewById(R.id.et_upload_title);
        price = (EditText) findViewById(R.id.et_upload_price);
        location = (EditText) findViewById(R.id.et_uplaod_location);
        description = (EditText) findViewById(R.id.et_upload_description);
        phone = (EditText) findViewById(R.id.et_upload_phone);
        email = (EditText) findViewById(R.id.et_upload_email);
        upload = (Button) findViewById(R.id.upload_button);
        spinner_category = (Spinner) findViewById(R.id.spinner_category);


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                databaseReference = FirebaseDatabase.getInstance().getReference("Item").child(uid).push();

                databaseReference.child("title").setValue(title.getText().toString());
                databaseReference.child("price").setValue(price.getText().toString());
                databaseReference.child("description").setValue(description.getText().toString());

                Toast.makeText(Activity_upload.this, "Added", Toast.LENGTH_SHORT).show();
            }
            });


        }
    }
