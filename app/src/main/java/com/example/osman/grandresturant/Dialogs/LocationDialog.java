package com.example.osman.grandresturant.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.osman.grandresturant.Activity_upload;
import com.example.osman.grandresturant.Helper.HelperMethods;
import com.example.osman.grandresturant.Manifest;
import com.example.osman.grandresturant.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocationDialog extends Dialog {

    public Activity c;
    MaterialBetterSpinner spinner;
    Button auto, manual;
    TextView location;
    ArrayAdapter<String> arrayAdapter;
    String[] spinnerList = {"بنى سويف", "الشرقية", "المنصورة", "المنوفية", "الجيزة", "القاهرة"};
    static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    double latti;
    double longi;


    public LocationDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    public LocationDialog(@NonNull Context context) {
        super(context);
    }

    public LocationDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected LocationDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_dialog);

        auto = (Button) findViewById(R.id.location_dialog_btn_auto);
        manual = (Button) findViewById(R.id.location_dialog_btn_manual);
        spinner = (MaterialBetterSpinner) findViewById(R.id.location_dialog_spinner);

        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, spinnerList);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



                HelperMethods.sign_location =  adapterView.getItemAtPosition(i).toString();

                manual.setText( adapterView.getItemAtPosition(i).toString());
                auto.setText("إختيار المكان الحالى");
            }
        });


        manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.setVisibility(View.VISIBLE);
            }
        });

        auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });


        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismiss();
            }
        });


    }

    void getLocation() {

        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

            Toast.makeText(getContext(), total2 + total, Toast.LENGTH_SHORT).show();

        }
    }

}


