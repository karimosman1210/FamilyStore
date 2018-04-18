package com.example.osman.grandresturant;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.osman.grandresturant.Adapters.Favorite_Adapter;
import com.example.osman.grandresturant.classes.ItemClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Favorite_item extends AppCompatActivity {
    ArrayList<ItemClass> list = new ArrayList<>();
    RecyclerView recyclerView;
    DatabaseReference database, itemfav;
    String auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_item);

        recyclerView = (RecyclerView) findViewById(R.id.recycle_favorite);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        auth = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance().getReference().child("Favorite").child(auth);
        itemfav = FirebaseDatabase.getInstance().getReference().child("Items");
        final com.example.osman.grandresturant.Adapters.Favorite_Adapter favorite_adapter = new Favorite_Adapter(this, list);

        recyclerView.setAdapter(favorite_adapter);
        favorite_adapter.notifyDataSetChanged();
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot allData : dataSnapshot.getChildren()) {

                    itemfav.child(allData.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            ItemClass itemClass = dataSnapshot.getValue(ItemClass.class);
                            System.out.println(itemClass.getIdItem());
                            list.add(itemClass);
                            favorite_adapter.notifyDataSetChanged();


                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
