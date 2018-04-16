package com.example.osman.grandresturant;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.osman.grandresturant.Adapters.Adapter_category;
import com.example.osman.grandresturant.Helper.HelperMethods;
import com.example.osman.grandresturant.Registration.Login;
import com.example.osman.grandresturant.Registration.UserProfile;
import com.example.osman.grandresturant.classes.ItemClass;
import com.example.osman.grandresturant.classes.Item_recycle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class HomeScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference mDatabase;

    NavigationView navigationView;
    TextView Country_choose;
    boolean doubleBackToExitPressedOnce = false;
    FloatingActionButton fab;
    public static List<ItemClass> item_list = new ArrayList<>();
    RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    TextView login_textview;
    ImageView image_shopping;
    String[] spinnerListCountry = {"بنى سويف", "الشرقية", "المنصورة", "المنوفية", "الجيزة", "القاهرة"};
    DatabaseReference databaseReference;
    MaterialBetterSpinner Country;
    ArrayAdapter<String> CountrySpinnerAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    public static GridLayoutManager gridLayoutManager;
    ArrayList<Item_recycle> arrayList;
    String ItemType,  countryName1;
    static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    double latti;
    double longi;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        login_textview = (TextView) findViewById(R.id.home_login_btn);
        image_shopping = (ImageView) findViewById(R.id.home_shopping);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.home_SwipeRefreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.home_screen_card_recycler_view);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Country_choose = (TextView) findViewById(R.id.home_screen_place);
        arrayList = new ArrayList<>();




        Geocoder geocoder = new Geocoder(HomeScreen.this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latti, longi, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            assert addresses != null;



            countryName1    = addresses.get(0).getAdminArea();

            Country_choose.setText(countryName1);


            if(countryName1.contains(" ")){
                countryName1= countryName1.substring(0, countryName1.indexOf(" "));
                System.out.println(countryName1);
            }


        } catch (Exception e) {
        }









        Country = (MaterialBetterSpinner) findViewById(R.id.home_screen_spinner);
        CountrySpinnerAdapter = new ArrayAdapter<String>(HomeScreen.this, android.R.layout.simple_dropdown_item_1line, spinnerListCountry);
        Country.setAdapter(CountrySpinnerAdapter);


        Country.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ItemType = adapterView.getItemAtPosition(i).toString();
                Country_choose.setText(ItemType);

            }
        });


        login_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreen.this, Login.class));
            }
        });

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                item_list.clear();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {


                    String id = mAuth.getCurrentUser().getUid();

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(id);
                    // HelperMethods.showDialog2(HomeScreen.this, "Wait", "Loading...");
                    mDatabase.child("user_tpe").addValueEventListener(new ValueEventListener() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {

                            System.out.println(snapshot.getValue());
                            if (Objects.equals(snapshot.getValue(), "User")) {
                                navigationView.getMenu().clear();
                                navigationView.inflateMenu(R.menu.activity_home_screen_drawer_user);
                                fab.setVisibility(View.GONE);
                                login_textview.setVisibility(View.GONE);
                                image_shopping.setVisibility(View.VISIBLE);
                                //   HelperMethods.hideDialog2(HomeScreen.this);
                            } else {
                                navigationView.getMenu().clear();
                                navigationView.inflateMenu(R.menu.activity_home_screen_drawer_company);
                                fab.setVisibility(View.VISIBLE);
                                login_textview.setVisibility(View.GONE);
                                image_shopping.setVisibility(View.VISIBLE);
                                //    HelperMethods.hideDialog2(HomeScreen.this);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                    });


                } else {

                    navigationView.getMenu().clear();
                    navigationView.inflateMenu(R.menu.activity_home_screen_drawer);
                    fab.setVisibility(View.GONE);
                    login_textview.setVisibility(View.VISIBLE);
                    image_shopping.setVisibility(View.GONE);
                    //    HelperMethods.hideDialog2(HomeScreen.this);


                }
            }
        };

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                startActivity(new Intent(HomeScreen.this, Activity_upload.class));

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        navigationView.setNavigationItemSelectedListener(this);

        loadData();


    }


    public void loadData() {
        HelperMethods.showDialog(HomeScreen.this, "Wait", "Loading data...");
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
                HelperMethods.hideDialog(HomeScreen.this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new GridLayoutManager(HomeScreen.this, 2));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_Sallers) {
            startActivity(new Intent(HomeScreen.this, SallersRecycler.class));
        } else if (id == R.id.nav_AboutUs) {

        }

       else if (id == R.id.nav_company_favorite_Ads) {

            startActivity(new Intent(HomeScreen.this,Favorite_item.class));

        }

        else if (id == R.id.nav_Login) {
            mAuth.signOut();
            startActivity(new Intent(HomeScreen.this, Login.class));

        } else if (id == R.id.nav_company_sallers) {

            startActivity(new Intent(HomeScreen.this, SallersRecycler.class));

        } else if (id == R.id.nav_company_New_Requests) {

        } else if (id == R.id.nav_company_My_Requests) {

        } else if (id == R.id.nav_company_My_Ads) {
            startActivity(new Intent(HomeScreen.this, MyAds.class));

        } else if (id == R.id.nav_company_favorite_Ads) {


            Intent intent=new Intent(HomeScreen.this,Favorite_item.class);
            startActivity(intent);
        } else if (id == R.id.nav_company_FeedBack) {

        } else if (id == R.id.nav_company_Abouts_Us) {

        } else if (id == R.id.nav_company_Edite_Profile) {

            startActivity(new Intent(HomeScreen.this, UserProfile.class));


        } else if (id == R.id.nav_company_Log_Out) {
            mAuth.signOut();
            startActivity(new Intent(HomeScreen.this, Login.class));


        } else if (id == R.id.nav_User_Sallers) {

        } else if (id == R.id.nav_User_My_Requests) {

        } else if (id == R.id.nav_User_favorite_Ads) {

        } else if (id == R.id.nav_User_FeedBack) {

        } else if (id == R.id.nav_User_Abouts_Us) {

        } else if (id == R.id.nav_User_Edite_Profile) {

        } else if (id == R.id.nav_User_Log_Out) {

            mAuth.signOut();
            startActivity(new Intent(HomeScreen.this, Login.class));


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthStateListener != null) {
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_LOCATION:
                getLocation();
                break;
        }
    }


    void getLocation() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {
                latti = location.getLatitude();
                longi = location.getLongitude();


                String total2 = Double.toString(latti);
                String total = Double.toString(longi);

                Toast.makeText(this, total2 + total, Toast.LENGTH_SHORT).show();

            }
            else {

            }
        }

    }
