package com.example.osman.grandresturant.NavigationActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.osman.grandresturant.Adapters.BasketAdapter;
import com.example.osman.grandresturant.Dialogs.MyBasket_delete_dialog;
import com.example.osman.grandresturant.Helper.HelperMethods;
import com.example.osman.grandresturant.ItemScreen;
import com.example.osman.grandresturant.R;
import com.example.osman.grandresturant.classes.RequestsClass;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyBasket extends AppCompatActivity {
    RecyclerView recycleBasket;
    RecyclerView recyclerView;
    DatabaseReference mDatabaseReference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_basket);

        Toolbar ToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(ToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Requests");

        recyclerView = (RecyclerView) findViewById(R.id.basket_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        firebaseAuth = FirebaseAuth.getInstance();


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<RequestsClass, MyBasket.holder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<RequestsClass, MyBasket.holder>(
                RequestsClass.class,
                R.layout.my_basket_item,
                MyBasket.holder.class,
                mDatabaseReference.orderByChild("RequestUserID").equalTo(firebaseAuth.getCurrentUser().getUid())
        ) {
            @Override
            protected void populateViewHolder(MyBasket.holder viewHolder, final RequestsClass model, int position) {

                final String key_post = getRef(position).getKey();
                viewHolder.RequestItemName.setText(model.getRequestItemName());
                viewHolder.RequestItemPrice.setText(model.getRequestItemPrice());


                viewHolder.setItemImage(model.getRequestItemImage());


                viewHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        Intent intent = new Intent(MyBasket.this, ItemScreen.class);
                        intent.putExtra("Item_ID", model.getRequestItemID());
                        startActivity(intent);

                    }
                });

                viewHolder.RequestDeleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        HelperMethods.delete_ads_id = key_post ;
                        MyBasket_delete_dialog cdd=new MyBasket_delete_dialog(MyBasket.this);
                        cdd.show();

                    }
                });

            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }


    public static class holder extends RecyclerView.ViewHolder {


        View view;
        ImageView RequestItemImage, RequestDeleteBtn;
        TextView RequestItemName, RequestItemPrice, RequestItemPlace, RequestItemcategory, RequestItemAdded;


        public holder(View itemView) {
            super(itemView);
            view = itemView;


            RequestItemName = (TextView) view.findViewById(R.id.My_Basket_item_name);
            RequestItemPrice = (TextView) view.findViewById(R.id.My_Basket_item_price);


            RequestItemImage = (ImageView) view.findViewById(R.id.My_Basket_item_image);
            RequestDeleteBtn = (ImageView) view.findViewById(R.id.My_Basket_delete_btn);


        }

        public void setItemImage(String image) {
            Glide.with(view.getContext()).load(image).placeholder(RequestItemImage.getDrawable()).fitCenter().into(RequestItemImage);
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

