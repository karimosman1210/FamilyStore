package com.example.osman.grandresturant;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
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
import com.example.osman.grandresturant.classes.ItemClass;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyAds extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference mDatabaseReference , mDatabase;
    FirebaseAuth firebaseAuth;
    String profile_image_url , saller_name ;
    ImageView SallerImageView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ads);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(saller_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Items");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        recyclerView = (RecyclerView) findViewById(R.id.my_ads_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        SallerImageView = (ImageView) findViewById(R.id.saller_app_bar_image);



        String id = firebaseAuth.getCurrentUser().getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(id);

        mDatabase.child("profile_image").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                System.out.println(snapshot.getValue());
                try
                {
                    profile_image_url = snapshot.getValue().toString();
                }
                catch (Exception e)
                {

                }

                Glide.with(MyAds.this).load(profile_image_url).into(SallerImageView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(id);

        mDatabase.child("username").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                System.out.println(snapshot.getValue());
                try
                {
                    saller_name = snapshot.getValue().toString();
                }
                catch (Exception e)
                {

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<ItemClass, MyAds.holder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ItemClass, MyAds.holder>(
                ItemClass.class,
                R.layout.my_ads_item,
                MyAds.holder.class,
                mDatabaseReference.orderByChild("UserID").equalTo(firebaseAuth.getCurrentUser().getUid())
        ) {
            @Override
            protected void populateViewHolder(MyAds.holder viewHolder, final ItemClass model, int position) {
                final String key_post = getRef(position).getKey();

                viewHolder.setItemName(model.getName());
                viewHolder.setLocation(model.getCountryLocation());
                viewHolder.setprice(model.getPrice());
                viewHolder.setUserName(model.getUserName());
                viewHolder.setItemImage(model.getImage());

                viewHolder.myAds_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        HelperMethods.delete_ads_id = key_post ;
                        custom_dialog cdd=new custom_dialog(MyAds.this);
                        cdd.show();


                    }
                });

                viewHolder.myAds_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(MyAds.this,Edit_Ads.class);
                        intent.putExtra("id",key_post);
                        startActivity(intent);

                    }
                });


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

                        startActivity(new Intent(MyAds.this, ItemScreen.class));

                    }
                });

            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }


    public static class holder extends RecyclerView.ViewHolder {


        View view;
        ImageView myAds_img, myAds_delete, myAds_edit;
        TextView myAds_name, myAds_price, myAds_country, myAds_username;


        public holder(View itemView) {
            super(itemView);
            view = itemView;

            myAds_name = (TextView) view.findViewById(R.id.my_ads_recycler_name);
            myAds_price = (TextView) view.findViewById(R.id.my_ads_recycler_price);
            myAds_country = (TextView) view.findViewById(R.id.my_ads_recycler__place);
            myAds_username = (TextView) view.findViewById(R.id.my_ads_recycler_user_name);
            myAds_img = (ImageView) view.findViewById(R.id.my_ads_recycler_img);
            myAds_delete = (ImageView) view.findViewById(R.id.my_ads_recycler_delete);
            myAds_edit = (ImageView) view.findViewById(R.id.my_ads_recycler_edit);

        }

        public void setItemName(String name) {

            myAds_name.setText(name);
        }

        public void setLocation(String location) {

            myAds_country.setText(location);
        }

        public void setprice(String price) {

            myAds_price.setText(price);
        }

        public void setUserName(String userName) {

            myAds_username.setText(userName);
        }


        public void setItemImage(String image) {
            Glide.with(view.getContext()).load(image).fitCenter().into(myAds_img);
        }

    }
}
