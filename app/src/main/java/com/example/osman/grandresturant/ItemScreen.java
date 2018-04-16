package com.example.osman.grandresturant;

import android.content.Intent;
import android.net.sip.SipSession;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.osman.grandresturant.Helper.HelperMethods;
import com.example.osman.grandresturant.classes.Encaps_Basket;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class ItemScreen extends AppCompatActivity {

    TextView Item_name, Item_country, Item_place, Item_time, Item_price, Item_desc, user_name, user_number, user_mail;
    ImageView user_image, Item_image;
    Button item_screen_add_btn;
    android.support.v7.widget.Toolbar itemToolbar;
    ImageButton basketButn;
    DatabaseReference databaseReference;
    String newString ;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_screen);
        itemToolbar=findViewById(R.id.itemToolbar);
        setSupportActionBar(itemToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        item_screen_add_btn=(Button)findViewById(R.id.item_screen_add_btn);
        basketButn=(ImageButton)findViewById(R.id.basketButn);
        Item_name = (TextView) findViewById(R.id.item_screen_name);
        Item_country = (TextView) findViewById(R.id.item_screen_country);
        Item_place = (TextView) findViewById(R.id.item_screen_place);
        Item_time = (TextView) findViewById(R.id.item_screen_time);
        Item_price = (TextView) findViewById(R.id.item_screen_price);
        Item_desc = (TextView) findViewById(R.id.item_screen_desc);
        user_name = (TextView) findViewById(R.id.item_screen_company_name);
        user_number = (TextView) findViewById(R.id.item_screen_company_number);
        user_mail = (TextView) findViewById(R.id.item_screen_company_mail);
        Item_image= (ImageView) findViewById(R.id.item_screen_item_img);
        user_image = (ImageView) findViewById(R.id.item_screen_company_image);



        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getString("Item_ID");
            }
        } else {
            newString = (String) savedInstanceState.getSerializable("Item_ID");
        }


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Items").child(newString);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(DataSnapshot snapshot) {



                Item_name.setText(snapshot.child("Name").getValue().toString());
                Item_country.setText(snapshot.child("CountryLocation").getValue().toString());
                Item_place.setText(snapshot.child("PlaceLocation").getValue().toString());
                Item_time.setText(snapshot.child("CountryLocation").getValue().toString());
                Item_price.setText(snapshot.child("Price").getValue().toString());
                Item_desc.setText(snapshot.child("Description").getValue().toString());
                user_name.setText(snapshot.child("UserName").getValue().toString());
                user_number.setText(snapshot.child("UserNumber").getValue().toString());
                user_mail.setText(snapshot.child("UserEmail").getValue().toString());

                Glide.with(ItemScreen.this).load(snapshot.child("UserImage").getValue().toString()).fitCenter().into(user_image);
                Glide.with(ItemScreen.this).load(snapshot.child("image").getValue().toString()).fitCenter().into(Item_image);


                long timestamp = Long.parseLong(String.valueOf(snapshot.child("UploadedTime").getValue().toString())) * 1000L;
                Item_time.setText(getDate(timestamp));



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });




        item_screen_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=HelperMethods.items_recycler_name;
                String price=HelperMethods.items_recycler_price;
                String nameCompany=HelperMethods.items_recycler_user_name;
                String place=HelperMethods.items_recycler_place;
                String image=HelperMethods.items_recycler_image;

                Encaps_Basket encaps_basket=new Encaps_Basket(name,image,price,nameCompany,place);
                HelperMethods.encaps_baskets.add(encaps_basket);


                Toast.makeText(ItemScreen.this, "تم الاضافه الي السله  (Test)", Toast.LENGTH_SHORT).show();
            }
        });




        basketButn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent(ItemScreen.this,MyBasket.class);
                startActivity(intent);
            }
        });


    }



    private String getDate(long timeStamp) {

        try {
            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
            Date netDate = (new Date(timeStamp));
            return sdf.format(netDate);
        } catch (Exception ex) {
            return "xx";
        }
    }

}
