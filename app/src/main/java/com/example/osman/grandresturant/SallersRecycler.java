package com.example.osman.grandresturant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.osman.grandresturant.Helper.HelperMethods;
import com.example.osman.grandresturant.classes.SallersClass;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SallersRecycler extends AppCompatActivity {
    RecyclerView recyclerView, adminRecyclerView;
    DatabaseReference mDatabaseReference;
    ImageView secrchItem;
    LinearLayout barsearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sallers_recycler);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        secrchItem = (ImageView) findViewById(R.id.secrchItem);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        recyclerView = (RecyclerView) findViewById(R.id.saller_recycler_item_image_recycler);
        adminRecyclerView = (RecyclerView) findViewById(R.id.saller_recycler_item_image_recycler_amin);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        LinearLayoutManager layoutManagerAdmin = new LinearLayoutManager(this);
        layoutManagerAdmin.setReverseLayout(true);
        layoutManagerAdmin.setStackFromEnd(true);

        recyclerView.setLayoutManager(layoutManager);
        adminRecyclerView.setLayoutManager(layoutManagerAdmin);


    }

    @Override
    protected void onStart() {


        super.onStart();


        FirebaseRecyclerAdapter<SallersClass, SallersRecycler.holder> firebaseRecyclerAdapterAdmin = new FirebaseRecyclerAdapter<SallersClass, holder>(
                SallersClass.class,
                R.layout.saller_admin_item,
                holder.class,
                mDatabaseReference.orderByChild("user_tpe").equalTo("admin")
        ) {
            @Override
            protected void populateViewHolder(holder viewHolder, SallersClass model, int position) {

                final String key_post = getRef(position).getKey();
                viewHolder.setItemName(model.getUsername());
                viewHolder.setLocation(model.getCountry());
                viewHolder.setNumber(model.getMobile());
                viewHolder.setItemImage(model.getProfile_image());


                viewHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        HelperMethods.sallerID = key_post;
                        startActivity(new Intent(SallersRecycler.this, ItemsRecycler.class));

                    }
                });

            }
        };


        adminRecyclerView.setAdapter(firebaseRecyclerAdapterAdmin);


        FirebaseRecyclerAdapter<SallersClass, SallersRecycler.AdminHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<SallersClass, SallersRecycler.AdminHolder>(
                SallersClass.class,
                R.layout.saller_recycler_item,
                SallersRecycler.AdminHolder.class,
                mDatabaseReference.orderByChild("user_tpe").equalTo("company")
        ) {
            @Override
            protected void populateViewHolder(SallersRecycler.AdminHolder viewHolder, final SallersClass model, int position) {
                final String key_post = getRef(position).getKey();
                viewHolder.setItemName(model.getUsername());
                viewHolder.setLocation(model.getCountry());
                viewHolder.setNumber(model.getMobile());
                viewHolder.setItemImage(model.getProfile_image());


                viewHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        HelperMethods.sallerID = key_post;
                        startActivity(new Intent(SallersRecycler.this, ItemsRecycler.class));

                    }
                });

            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class holder extends RecyclerView.ViewHolder {


        View view;
        ImageView saller_img;
        TextView saller_name, saller_location, saller_number;


        public holder(View itemView) {
            super(itemView);
            view = itemView;

            saller_name = (TextView) view.findViewById(R.id.saller_recycler_item_name);
            saller_location = (TextView) view.findViewById(R.id.saller_recycler_item_location);
            saller_number = (TextView) view.findViewById(R.id.saller_recycler_item_mobile);

            saller_img = (ImageView) view.findViewById(R.id.saller_recycler_item_image);

        }

        public void setItemName(String name) {

            saller_name.setText(name);
        }

        public void setLocation(String location) {

            saller_location.setText(location);
        }

        public void setNumber(String number) {

            saller_number.setText(number);
        }


        public void setItemImage(String image) {
            Glide.with(view.getContext()).load(image).fitCenter().into(saller_img);
        }

    }

    public static class AdminHolder extends RecyclerView.ViewHolder {


        View view;
        ImageView saller_img;
        TextView saller_name, saller_location, saller_number;


        public AdminHolder(View itemView) {
            super(itemView);
            view = itemView;

            saller_name = (TextView) view.findViewById(R.id.saller_recycler_item_name);
            saller_location = (TextView) view.findViewById(R.id.saller_recycler_item_location);
            saller_number = (TextView) view.findViewById(R.id.saller_recycler_item_mobile);

            saller_img = (ImageView) view.findViewById(R.id.saller_recycler_item_image);

        }

        public void setItemName(String name) {

            saller_name.setText(name);
        }

        public void setLocation(String location) {

            saller_location.setText(location);
        }

        public void setNumber(String number) {

            saller_number.setText(number);
        }


        public void setItemImage(String image) {
            Glide.with(view.getContext()).load(image).fitCenter().into(saller_img);
        }

    }


}