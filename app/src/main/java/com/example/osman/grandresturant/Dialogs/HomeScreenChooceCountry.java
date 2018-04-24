package com.example.osman.grandresturant.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.osman.grandresturant.Helper.HelperMethods;
import com.example.osman.grandresturant.R;
import com.example.osman.grandresturant.SallersRecycler;
import com.google.firebase.database.DatabaseReference;

public class HomeScreenChooceCountry extends Dialog {

    public Activity c;
    public Dialog d;
    ArrayAdapter<String> CountryListViewAdapter ;
    ListView countryList;
    String[] spinnerListCountry = {"الكل", "بنى سويف", "الشرقية", "المنصورة", "المنوفية", "الجيزة", "القاهرة"};

    public HomeScreenChooceCountry(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }


    public HomeScreenChooceCountry(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected HomeScreenChooceCountry(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen_chooce_country);
        countryList = (ListView) findViewById(R.id.chooce_country_listView) ;
        CountryListViewAdapter = new ArrayAdapter<String>(c, android.R.layout.simple_dropdown_item_1line, spinnerListCountry);
        countryList.setAdapter(CountryListViewAdapter);

        countryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                HelperMethods.Home_Filtter_Country_name = adapterView.getItemAtPosition(i).toString();
                c.startActivity(new Intent(c , SallersRecycler.class));
            }
        });

    }
}
