package com.example.osman.grandresturant;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.osman.grandresturant.Adapters.ItemsAdapter;
import com.example.osman.grandresturant.Helper.HelperMethods;
import com.example.osman.grandresturant.classes.ItemClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class ItemsRecycler extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference mDatabaseReference;
    ImageView secrchItem;
    ArrayList<ItemClass> arrayList;
    LinearLayout barsearch;
    DatabaseReference databaseReference;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
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

        secrchItem = (ImageView) findViewById(R.id.secrchItem);
        barsearch = (LinearLayout) findViewById(R.id.barsearch);
        arrayList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Items");
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

        loadData();
    }


    public void loadData() {
        HelperMethods.showDialog(ItemsRecycler.this, "Wait", "Loading data...");
        final ItemsAdapter adapter = new ItemsAdapter(this, arrayList);


        databaseReference.orderByChild("UserID").equalTo(HelperMethods.Home_Filtter_sallerID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {


                    if(Objects.equals(HelperMethods.Home_Filtter_Country_name, String.valueOf(R.string.action_settings)))
                    {
                        if (data.child("ItemType").getValue().equals(HelperMethods.Home_Filtter_categoryName)) {

                            String id = data.getRef().getKey();
                            String Itemid = data.child("idItem").getValue().toString();
                            String Name = data.child("Name").getValue().toString();
                            String image = data.child("image").getValue().toString();
                            String CountryLocation = data.child("CountryLocation").getValue().toString();
                            String Description = data.child("Description").getValue().toString();
                            String ItemType = data.child("ItemType").getValue().toString();
                            String PlaceLocation = data.child("PlaceLocation").getValue().toString();
                            String Price = data.child("Price").getValue().toString();
                            String UserID = data.child("UserID").getValue().toString();
                            String UserName = data.child("UserName").getValue().toString();
                            String UserEmail = data.child("UserEmail").getValue().toString();
                            String UserNumber = data.child("UserNumber").getValue().toString();
                            long UploadedTime = (long) data.child("UploadedTime").getValue();

                            arrayList.add(new ItemClass(id , Itemid, Name, image, CountryLocation, Description, ItemType, PlaceLocation, Price, UserID, UserName, UserEmail, UserNumber, UploadedTime));
                            adapter.notifyDataSetChanged();
                        } else {
                        }
                    }
                    else
                    {
                        if (data.child("CountryLocation").getValue().equals(HelperMethods.Home_Filtter_Country_name) && data.child("ItemType").getValue().equals(HelperMethods.Home_Filtter_categoryName)) {

                            String id = data.getRef().getKey();
                            String Itemid = data.child("idItem").getValue().toString();
                            String Name = data.child("Name").getValue().toString();
                            String image = data.child("image").getValue().toString();
                            String CountryLocation = data.child("CountryLocation").getValue().toString();
                            String Description = data.child("Description").getValue().toString();
                            String ItemType = data.child("ItemType").getValue().toString();
                            String PlaceLocation = data.child("PlaceLocation").getValue().toString();
                            String Price = data.child("Price").getValue().toString();
                            String UserID = data.child("UserID").getValue().toString();
                            String UserName = data.child("UserName").getValue().toString();
                            String UserEmail = data.child("UserEmail").getValue().toString();
                            String UserNumber = data.child("UserNumber").getValue().toString();
                            long UploadedTime = (long) data.child("UploadedTime").getValue();

                            arrayList.add(new ItemClass(id , Itemid, Name, image, CountryLocation, Description, ItemType, PlaceLocation, Price, UserID, UserName, UserEmail, UserNumber, UploadedTime));
                            adapter.notifyDataSetChanged();
                        } else {
                        }
                    }







                }
                HelperMethods.hideDialog(ItemsRecycler.this);
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }





}
