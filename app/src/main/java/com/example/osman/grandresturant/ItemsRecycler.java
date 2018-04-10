package com.example.osman.grandresturant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.osman.grandresturant.Helper.HelperMethods;
import com.example.osman.grandresturant.classes.ItemClass;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ItemsRecycler extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference mDatabaseReference;
    ImageView secrchItem;
    LinearLayout barsearch;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_recycler);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        secrchItem=(ImageView)findViewById(R.id.secrchItem);
        barsearch=(LinearLayout)findViewById(R.id.barsearch);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Items");
        recyclerView = (RecyclerView) findViewById(R.id.item_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        secrchItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barsearch.setVisibility(View.VISIBLE);

            }
        });





    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<ItemClass, holder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ItemClass, holder>(
                ItemClass.class,
                R.layout.item_details,
                holder.class,
                mDatabaseReference
        ) {
            @Override
            protected void populateViewHolder(holder viewHolder, final ItemClass model, int position) {

                viewHolder.setItemName(model.getName());
                viewHolder.setItemImage(model.getImage());
                viewHolder.setPlace(model.getCountryLocation());
               /* viewHolder.setItemType(model.getItemType());*/
                viewHolder.setPrice(model.getPrice());
                viewHolder.setUserName(model.getUserName());

                viewHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        HelperMethods.items_recycler_name = model.getName();
                        HelperMethods.items_recycler_image = model.getImage();
                        HelperMethods.items_recycler_desc = model.getDescription();
                        HelperMethods.items_recycler_place = model.getPlaceLocation();
                        HelperMethods.items_recycler_price = model.getPrice();
                        HelperMethods.items_recycler_type = model.getItemType();
                        HelperMethods.items_recycler_country = model.getCountryLocation();
                        HelperMethods.items_recycler_Time = model.getUploadedTime();
                        HelperMethods.items_recycler_user_Image = model.getUserImage();
                        HelperMethods.items_recycler_user_name = model.getUserName();
                        HelperMethods.items_recycler_user_email = model.getUserEmail();
                        HelperMethods.items_recycler_user_number = model.getUserNumber();

                        startActivity(new Intent(ItemsRecycler.this , ItemScreen.class));

                    }
                });

            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class holder extends RecyclerView.ViewHolder {


        View view;
        ImageView item_image;
        TextView item_name, item_price, item_type, item_place, item_user_name;


        public holder(View itemView) {
            super(itemView);
            view = itemView;

            item_name = (TextView) view.findViewById(R.id.item_recycler_name);
            item_price = (TextView) view.findViewById(R.id.item_recycler_price);
            item_place = (TextView) view.findViewById(R.id.item_recycler_place);
//            item_type = (TextView) view.findViewById(R.id.item_recycler_type);
            item_user_name = (TextView) view.findViewById(R.id.item_recycler_user_name);
            item_image = (ImageView) view.findViewById(R.id.item_recycler_image);

        }

        public void setItemName(String name) {

            item_name.setText(name);
        }

        public void setPrice(String price) {

            item_price.setText(price);
        }

        public void setPlace(String place) {

            item_place.setText(place);
        }

        public void setUserName(String userName) {

            item_user_name.setText(userName);
        }



        public void setItemImage(String image) {
            Glide.with(view.getContext()).load(image).fitCenter().into(item_image);
        }

    }
}
