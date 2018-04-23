package com.example.osman.grandresturant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.osman.grandresturant.Helper.HelperMethods;
import com.example.osman.grandresturant.classes.SallersClass;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class SallersRecycler extends AppCompatActivity {

    RecyclerView recyclerView, adminRecyclerView;
    DatabaseReference mDatabaseReference;
    ImageView secrchItem;
    LinearLayout barsearch;
    RelativeLayout null_layout;
    ImageButton goHome;
    private TextView sallerRecyclerItemName;
    private ImageView sallerRecyclerItemImage;
    private TextView sallerRecyclerItemLocation;
    private TextView sallerRecyclerItemMobile;
    private CardView adminAdminContainer;
    private String id;

    private SearchView searchView;
    private TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sallers_recycler);
        initView();
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        null_layout = (RelativeLayout) findViewById(R.id.saller_recycler_reltivelayout_null);


        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        //goHome = (ImageButton) findViewById(R.id.goHome);

        recyclerView = (RecyclerView) findViewById(R.id.saller_recycler_item_image_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        LinearLayoutManager layoutManagerAdmin = new LinearLayoutManager(this);
        layoutManagerAdmin.setReverseLayout(true);
        layoutManagerAdmin.setStackFromEnd(true);

        recyclerView.setNestedScrollingEnabled(false);

        recyclerView.setLayoutManager(layoutManager);


//        goHome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(SallersRecycler.this, HomeScreen.class));
//                finish();
//            }
//        });


        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                toolbarTitle.setVisibility(View.VISIBLE);
                return false;
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbarTitle.setVisibility(View.GONE);
            }
        });

        initAdminView();

    }

    private void initAdminView() {

        mDatabaseReference.orderByChild("user_tpe").equalTo("admin").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

                final DataSnapshot child = dataSnapshot.getChildren().iterator().next();
                SallersClass user = child.getValue(SallersClass.class);

                sallerRecyclerItemName.setText(user.getUsername());
                sallerRecyclerItemLocation.setText(user.getCountry());
                sallerRecyclerItemMobile.setText(user.getMobile());
                Glide.with(getApplicationContext()).load(user.getProfile_image()).placeholder(sallerRecyclerItemImage.getDrawable()).fitCenter().into(sallerRecyclerItemImage);

                id = child.getKey();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        adminAdminContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id != null) {
                    HelperMethods.Home_Filtter_sallerID = id;
                    startActivity(new Intent(SallersRecycler.this, ItemsRecycler.class));
                }
            }
        });

    }

    @Override
    protected void onStart() {


        super.onStart();

        mDatabaseReference.orderByChild("user_tpe").equalTo("company").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FirebaseRecyclerAdapter<SallersClass, AdminHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<SallersClass, AdminHolder>(
                SallersClass.class,
                R.layout.saller_recycler_item,
                AdminHolder.class,
                mDatabaseReference.orderByChild("user_tpe").equalTo("company")
        ) {
            @Override
            protected void populateViewHolder(AdminHolder viewHolder, final SallersClass model, int position) {
                if (model.getCountry().equals(HelperMethods.Home_Filtter_Country_name)) {
                    final String key_post = getRef(position).getKey();
                    viewHolder.setItemName(model.getUsername());
                    viewHolder.setLocation(model.getCountry());
                    viewHolder.setNumber(model.getMobile());
                    viewHolder.setItemImage(model.getProfile_image());

                    viewHolder.view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            HelperMethods.Home_Filtter_sallerID = key_post;
                            startActivity(new Intent(SallersRecycler.this, ItemsRecycler.class));
                        }
                    });
                }
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    private void initView() {
        sallerRecyclerItemName = (TextView) findViewById(R.id.saller_recycler_item_name);
        sallerRecyclerItemImage = (ImageView) findViewById(R.id.saller_recycler_item_image);
        sallerRecyclerItemLocation = (TextView) findViewById(R.id.saller_recycler_item_location);
        sallerRecyclerItemMobile = (TextView) findViewById(R.id.saller_recycler_item_mobile);
        adminAdminContainer = (CardView) findViewById(R.id.admin_admin_container);
        searchView = (SearchView) findViewById(R.id.search_view);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
    }

    public static class holder extends RecyclerView.ViewHolder {


        public View view;
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
            Glide.with(view.getContext()).load(image).placeholder(saller_img.getDrawable()).fitCenter().into(saller_img);
        }

    }

    public static class AdminHolder extends RecyclerView.ViewHolder {


        public View view;
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
            Glide.with(view.getContext()).load(image).placeholder(saller_img.getDrawable()).fitCenter().into(saller_img);
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
