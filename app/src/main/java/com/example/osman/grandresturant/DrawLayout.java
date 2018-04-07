package com.example.osman.grandresturant;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DrawLayout extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private android.support.v7.widget.Toolbar toolbar;
    NavigationView navigationView;
    FirebaseAuth auth;
    TextView loginTv;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    ArrayList<Item_recycle> arrayList;
    ImageButton imageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawlayout);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.mytoolpar);
        //setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.mydraw);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_cat);
        imageButton = (ImageButton) findViewById(R.id.add_adsIb);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.myNav);
        navigationView.setNavigationItemSelectedListener(this);
        auth = FirebaseAuth.getInstance();
        loginTv = (TextView) findViewById(R.id.loginTv);
        if (auth.getCurrentUser() == null) {
            loginTv.setVisibility(View.VISIBLE);
        }
        if(auth.getCurrentUser()!=null){
            imageButton.setVisibility(View.VISIBLE);
        }

        loginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DrawLayout.this, Login.class));
                finish();
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(DrawLayout.this,Activity_upload.class);
                startActivity(intent);

            }
        });
        arrayList = new ArrayList<>();

        final Adapter_category adapter = new Adapter_category(this, arrayList);

        databaseReference = FirebaseDatabase.getInstance().getReference("Category");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {


                    String name = data.child("name").getValue().toString();
                    String image = data.child("image").getValue().toString();
                    String id = data.child("id").getValue().toString();


                    arrayList.add(new Item_recycle(name, image, id));
                    adapter.notifyDataSetChanged();
                }
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new GridLayoutManager(DrawLayout.this, 2));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.yourLocation) {

        }

        if (id == R.id.logOut) {
            auth.signOut();
            finish();
            startActivity(new Intent(getApplicationContext(), DrawLayout.class));

            Toast.makeText(this, "Logout Done ", Toast.LENGTH_SHORT).show();

        }

        return true;
    }
}