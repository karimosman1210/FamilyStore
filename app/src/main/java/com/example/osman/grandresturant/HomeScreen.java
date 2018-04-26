package com.example.osman.grandresturant;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import com.bumptech.glide.Glide;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.osman.grandresturant.Adapters.Adapter_category;
import com.example.osman.grandresturant.Helper.HelperMethods;
import com.example.osman.grandresturant.NavigationActivities.FeedBack;
import com.example.osman.grandresturant.NavigationActivities.MyAds;
import com.example.osman.grandresturant.NavigationActivities.MyBasket;
import com.example.osman.grandresturant.NavigationActivities.NavItemRecycler;
import com.example.osman.grandresturant.NavigationActivities.NavigationCategoriesRecycler;
import com.example.osman.grandresturant.NavigationActivities.NavigationSallerRecycler;
import com.example.osman.grandresturant.NavigationActivities.RequstsRecycler;
import com.example.osman.grandresturant.Registration.Login;
import com.example.osman.grandresturant.Registration.UserProfile;
import com.example.osman.grandresturant.classes.ItemClass;
import com.example.osman.grandresturant.classes.Item_recycle;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.squareup.picasso.Picasso;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class HomeScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

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
    TextView   nav_Text_view;
    de.hdodenhof.circleimageview.CircleImageView nav_Image_view;
    String[] spinnerListCountry = {"الكل", "بنى سويف", "الشرقية", "المنصورة", "المنوفية", "الجيزة", "القاهرة"};
    DatabaseReference databaseReference;
   // MaterialBetterSpinner Country;
   // ArrayAdapter<String> CountrySpinnerAdapter;
    ArrayList<Item_recycle> arrayList;
    String Country_name;
    static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    double latti;
    double longi;
    Timer timer;
    SliderLayout sliderLayout;
    HashMap<String,String> Hash_file_maps ;
    private FusedLocationProviderClient mLocationManager;
    private LocationCallback locationCallback;
    private LocationRequest mLocationRequest;
    MaterialSearchView searchView;
TextView privTv;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        recyclerView = (RecyclerView) findViewById(R.id.home_screen_card_recycler_view);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerLayout = navigationView.getHeaderView(0);
      //  Country_choose = (TextView) findViewById(R.id.home_screen_place);
        arrayList = new ArrayList<>();
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        mLocationManager = LocationServices.getFusedLocationProviderClient(this);
        nav_Image_view = (de.hdodenhof.circleimageview.CircleImageView) headerLayout.findViewById(R.id.nav_image_user);
        nav_Text_view = (TextView) headerLayout.findViewById(R.id.nav_text_view);
        recyclerView.setNestedScrollingEnabled(false);

        privTv=(TextView)findViewById(R.id.privTv);
        privTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreen.this,Privacy.class));
            }
        });




        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic


                //Toast.makeText(HomeScreen.this, query, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(HomeScreen.this, NavItemRecycler.class);
                intent.putExtra("Item_type", "Search");
                intent.putExtra("Filter", query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });




        timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

  checkForLocationPermissions();
                    }
                });


            }
        }, 0, 1 * (1000 * 1));


        /*Country = (MaterialBetterSpinner) findViewById(R.id.home_screen_spinner);
        CountrySpinnerAdapter = new ArrayAdapter<String>(HomeScreen.this, android.R.layout.simple_dropdown_item_1line, spinnerListCountry);
        Country.setAdapter(CountrySpinnerAdapter);


        Country.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //timer.cancel();
                Country_name = adapterView.getItemAtPosition(i).toString();
                Country_choose.setText(Country_name);
                HelperMethods.Home_Filtter_Country_name = Country_name;
            }
        });*/




        NavigationView navigation = (NavigationView) findViewById(R.id.nav_view);
        navigation.setNavigationItemSelectedListener(this);
        final View header = navigation.getHeaderView(0);



        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {


                    String id = mAuth.getCurrentUser().getUid();

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(id);

                    mDatabase.child("user_tpe").addValueEventListener(new ValueEventListener() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {

                            System.out.println(snapshot.getValue());
                            if (Objects.equals(snapshot.getValue(), "User")) {
                                navigationView.getMenu().clear();
                                navigationView.inflateMenu(R.menu.activity_home_screen_drawer_user);
                                fab.setVisibility(View.GONE);



                            } else {
                                navigationView.getMenu().clear();
                                navigationView.inflateMenu(R.menu.activity_home_screen_drawer_company);
                                fab.setVisibility(View.VISIBLE);

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                    });

                    mDatabase.child("profile_image").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                           String imageuri= (String) dataSnapshot.getValue();
                            Glide.with(HomeScreen.this)
                                    .load(imageuri)
                                    .into(nav_Image_view);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                    mDatabase.child("username").addValueEventListener(new ValueEventListener() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {

                            nav_Text_view.setText(snapshot.getValue().toString());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                    });


                } else {
                    navigationView.getMenu().clear();
                    navigationView.inflateMenu(R.menu.activity_home_screen_drawer);
                    fab.setVisibility(View.GONE);


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
        initSlider();

        loadData();

    }

    private void initSlider() {

        Hash_file_maps = new HashMap<String, String>();

        sliderLayout = (SliderLayout)findViewById(R.id.slider);


        Hash_file_maps.put("Android CupCake", "http://androidblog.esy.es/images/cupcake-1.png");
        Hash_file_maps.put("Android Donut", "http://androidblog.esy.es/images/donut-2.png");
        Hash_file_maps.put("Android Eclair", "http://androidblog.esy.es/images/eclair-3.png");
        Hash_file_maps.put("Android Froyo", "http://androidblog.esy.es/images/froyo-4.png");
        Hash_file_maps.put("Android GingerBread", "http://androidblog.esy.es/images/gingerbread-5.png");

        for(String name : Hash_file_maps.keySet()){

            TextSliderView textSliderView = new TextSliderView(HomeScreen.this);
            textSliderView
                    .description(name)
                    .image(Hash_file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(HomeScreen.this);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(3000);
        sliderLayout.addOnPageChangeListener(HomeScreen.this);
    }


    public void loadData() {
        HelperMethods.showDialog(HomeScreen.this, "من فضلك انتظر", "جاري اظهار النتائج . . .");
        final Adapter_category adapter = new Adapter_category(this, arrayList);

        databaseReference = FirebaseDatabase.getInstance().getReference("Category");
        databaseReference.keepSynced(true);
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
        }
        else if(searchView.isSearchOpen())
        {
            searchView.closeSearch();
        }
        else {
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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_Sallers) {
            startActivity(new Intent(HomeScreen.this, NavigationSallerRecycler.class));
        } else if (id == R.id.nav_Categories) {

            startActivity(new Intent(HomeScreen.this, NavigationCategoriesRecycler.class));

        } else if (id == R.id.nav_AboutUs) {
            startActivity(new Intent(HomeScreen.this,AboutAs.class));


        } else if (id == R.id.nav_company_favorite_Ads) {

            startActivity(new Intent(HomeScreen.this, Favorite_item.class));

        } else if (id == R.id.nav_Login) {
            mAuth.signOut();
            startActivity(new Intent(HomeScreen.this, Login.class));

        } else if (id == R.id.nav_company_sallers) {

            startActivity(new Intent(HomeScreen.this, NavigationSallerRecycler.class));

        } else if (id == R.id.nav_company_Categories) {

            startActivity(new Intent(HomeScreen.this, NavigationCategoriesRecycler.class));

        } else if (id == R.id.nav_company_New_Requests) {
            FirebaseUser user = mAuth.getCurrentUser();

            if (user != null) {
                startActivity(new Intent(HomeScreen.this, RequstsRecycler.class));
            } else {
            }


        } else if (id == R.id.nav_company_My_Requests) {
            FirebaseUser user = mAuth.getCurrentUser();
            if (user != null) {
                startActivity(new Intent(HomeScreen.this, MyBasket.class));
            } else {
            }

        } else if (id == R.id.nav_company_My_Ads) {
            startActivity(new Intent(HomeScreen.this, MyAds.class));

        } else if (id == R.id.nav_company_favorite_Ads) {


            Intent intent = new Intent(HomeScreen.this, Favorite_item.class);
            startActivity(intent);
        } else if (id == R.id.nav_company_FeedBack) {
            startActivity(new Intent(HomeScreen.this, FeedBack.class));

        } else if (id == R.id.nav_company_Abouts_Us) {
            startActivity(new Intent(HomeScreen.this,AboutAs.class));



        } else if (id == R.id.nav_company_Edite_Profile) {

            startActivity(new Intent(HomeScreen.this, UserProfile.class));


        } else if (id == R.id.nav_company_Log_Out) {
            mAuth.signOut();
            startActivity(new Intent(HomeScreen.this, Login.class));


        } else if (id == R.id.nav_User_Sallers) {

            startActivity(new Intent(HomeScreen.this, NavigationSallerRecycler.class));

        } else if (id == R.id.nav_Use_Categories) {

            startActivity(new Intent(HomeScreen.this, NavigationCategoriesRecycler.class));

        } else if (id == R.id.nav_User_My_Requests) {
            FirebaseUser user = mAuth.getCurrentUser();
            if (user != null) {
                startActivity(new Intent(HomeScreen.this, MyBasket.class));
            } else {
            }

        } else if (id == R.id.nav_User_favorite_Ads) {

        } else if (id == R.id.nav_User_FeedBack) {
            startActivity(new Intent(HomeScreen.this, FeedBack.class));

        } else if (id == R.id.nav_User_Abouts_Us) {
            startActivity(new Intent(HomeScreen.this,AboutAs.class));



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

        sliderLayout.stopAutoCycle();

        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                        Intent GPSIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(GPSIntent, REQUEST_LOCATION);
                    } else {
                        getLocation();
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOCATION) {
            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                getLocation();
            }
        }
    }


    private void filterLocation() {
        Locale mLocale = new Locale("ar");

        Geocoder geocoder = new Geocoder(HomeScreen.this, mLocale);
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latti, longi, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            //assert addresses != null;
            for (Address address : addresses) {
                String countryName = address.getAdminArea();
                String regex = "\\s*\\bمحافظة\\b\\s*";
                String country_Name = countryName.replaceAll(regex, "");

                Country_choose.setText(country_Name);
                country_Name = country_Name.replaceFirst("\u202C", "");
                HelperMethods.Home_Filtter_Country_name = country_Name;

                System.out.println("2 : " + country_Name);
                //if (address.getLocale().getDisplayName().equals(mLocale.getDisplayName())) break;
            }

        } catch (Exception e) {
        }
    }

    private void checkForLocationPermissions() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);

            return;
        }
        getLocation();
    }

    void getLocation() {


        //Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) return;

                Location location = locationResult.getLastLocation();
                latti = location.getLatitude();
                longi = location.getLongitude();
                filterLocation();
                stopLocationUpdates();
            }
        };

        createLocationRequest();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocationManager.requestLocationUpdates(mLocationRequest, locationCallback, null);

    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    private void stopLocationUpdates() {
        mLocationManager.removeLocationUpdates(locationCallback);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_screen, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;

    }


    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
