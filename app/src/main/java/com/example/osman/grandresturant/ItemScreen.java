package com.example.osman.grandresturant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.osman.grandresturant.Helper.HelperMethods;

public class ItemScreen extends AppCompatActivity {

    TextView Item_name, Item_country, Item_place, Item_time, Item_price, Item_desc, user_name, user_number, user_mail;
    ImageView user_image, Item_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_screen);

        Item_name = (TextView) findViewById(R.id.item_screen_name);
        Item_country = (TextView) findViewById(R.id.item_screen_country);
        Item_place = (TextView) findViewById(R.id.item_screen_place);
        Item_time = (TextView) findViewById(R.id.item_screen_time);
        Item_price = (TextView) findViewById(R.id.item_screen_price);
        Item_desc = (TextView) findViewById(R.id.item_screen_desc);
        user_name = (TextView) findViewById(R.id.item_screen_company_name);
        user_number = (TextView) findViewById(R.id.item_screen_company_number);
        user_mail = (TextView) findViewById(R.id.item_screen_company_mail);
        user_image = (ImageView) findViewById(R.id.item_screen_item_img);
        Item_image = (ImageView) findViewById(R.id.item_screen_company_image);

        Item_name.setText(HelperMethods.items_recycler_name);
        Item_country.setText(HelperMethods.items_recycler_country);
        Item_place.setText(HelperMethods.items_recycler_place);
        Item_time.setText(HelperMethods.items_recycler_Time);
        Item_price.setText(HelperMethods.items_recycler_price);
        Item_desc.setText(HelperMethods.items_recycler_desc);
        user_name.setText(HelperMethods.items_recycler_user_name);
        user_number.setText(HelperMethods.items_recycler_user_number);
        user_mail.setText(HelperMethods.items_recycler_user_email);

        Glide.with(ItemScreen.this).load(HelperMethods.items_recycler_user_Image).fitCenter().into(user_image);
        Glide.with(ItemScreen.this).load(HelperMethods.items_recycler_image).fitCenter().into(Item_image);

    }
}
