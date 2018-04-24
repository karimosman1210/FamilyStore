package com.example.osman.grandresturant.NavigationActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.osman.grandresturant.R;
import com.example.osman.grandresturant.classes.RequestsClass;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RequstsRecycler extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference mDatabaseReference;
    FirebaseAuth firebaseAuth;
    RelativeLayout null_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requsts_recycler);
        Toolbar ToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(ToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        null_layout = (RelativeLayout) findViewById(R.id.saller_recycler_reltivelayout_null);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Requests");
        recyclerView = (RecyclerView) findViewById(R.id.requests_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        firebaseAuth = FirebaseAuth.getInstance();


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<RequestsClass, RequstsRecycler.holder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<RequestsClass, RequstsRecycler.holder>(
                RequestsClass.class,
                R.layout.requests_item,
                RequstsRecycler.holder.class,
                mDatabaseReference.orderByChild("RequestSallerID").equalTo(firebaseAuth.getCurrentUser().getUid())
        ) {
            @Override
            protected void populateViewHolder(RequstsRecycler.holder viewHolder, final RequestsClass model, int position) {


                viewHolder.RequestUserName.setText(model.getRequestUserName());
                viewHolder.RequestUserEmail.setText(model.getRequestUserEmail());
                viewHolder.RequestUserNumber.setText(model.getRequestUserNumber());
                viewHolder.RequestItemName.setText(model.getRequestItemName());
                viewHolder.RequestItemPrice.setText(model.getRequestItemPrice());

                viewHolder.setItemImage(model.getRequestItemImage());
                viewHolder.setUserImage(model.getRequestUserImage());



            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);

        if (firebaseRecyclerAdapter.getItemCount() == 0) {
            null_layout.setVisibility(View.VISIBLE);
        } else {
        }
    }


    public static class holder extends RecyclerView.ViewHolder {


        View view;
        ImageView RequestItemImage;
        de.hdodenhof.circleimageview.CircleImageView RequestUserImage;
        TextView RequestUserName, RequestUserEmail, RequestUserNumber, RequestItemName, RequestItemPrice, RequestItemAdded;


        public holder(View itemView) {
            super(itemView);
            view = itemView;

            RequestUserName = (TextView) view.findViewById(R.id.request_profile_name);
            RequestUserEmail = (TextView) view.findViewById(R.id.request_profile_mail);
            RequestUserNumber = (TextView) view.findViewById(R.id.request_profile_number);
            RequestItemName = (TextView) view.findViewById(R.id.request_item_name);
            RequestItemPrice = (TextView) view.findViewById(R.id.request_item_price);
            RequestItemImage = (ImageView) view.findViewById(R.id.request_item_image);
            RequestUserImage = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.request_profile_image);


        }

        public void setItemImage(String image) {
            Glide.with(view.getContext()).load(image).placeholder(RequestItemImage.getDrawable()).fitCenter().into(RequestItemImage);
        }

        public void setUserImage(String image) {
            Glide.with(view.getContext()).load(image).placeholder(RequestUserImage.getDrawable()).fitCenter().into(RequestUserImage);
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

