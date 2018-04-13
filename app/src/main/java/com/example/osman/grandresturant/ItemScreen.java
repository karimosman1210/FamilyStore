package com.example.osman.grandresturant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.osman.grandresturant.Helper.HelperMethods;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ItemScreen extends AppCompatActivity {

    TextView Item_name, Item_country, Item_place, Item_time, Item_price, Item_desc, user_name, user_number, user_mail;
    ImageView user_image, Item_image;
    Button item_screen_add_btn;
    android.support.v7.widget.Toolbar itemToolbar;
    ImageButton basketButn;

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


        Item_name.setText(HelperMethods.items_recycler_name);
        Item_country.setText(HelperMethods.items_recycler_country);
        Item_place.setText(HelperMethods.items_recycler_place);
        itemToolbar=findViewById(R.id.itemToolbar);

        setSupportActionBar(itemToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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


        long timestamp = Long.parseLong(String.valueOf(HelperMethods.items_recycler_Time)) * 1000L;
        Item_time.setText(getDate(timestamp));

        Item_price.setText(HelperMethods.items_recycler_price);
        Item_desc.setText(HelperMethods.items_recycler_desc);
        user_name.setText(HelperMethods.items_recycler_user_name);
        user_number.setText(HelperMethods.items_recycler_user_number);
        user_mail.setText(HelperMethods.items_recycler_user_email);

        Glide.with(ItemScreen.this).load(HelperMethods.items_recycler_user_Image).fitCenter().into(user_image);
        Glide.with(ItemScreen.this).load(HelperMethods.items_recycler_image).fitCenter().into(Item_image);

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
