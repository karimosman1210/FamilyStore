package com.example.osman.grandresturant;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Favorit_item extends AppCompatActivity {
    android.support.v7.widget.Toolbar toolFavorit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorit_item);
        toolFavorit = (android.support.v7.widget.Toolbar) findViewById(R.id.toolFavorit);
        setSupportActionBar(toolFavorit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}

