package com.example.osman.grandresturant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.osman.grandresturant.Adapters.BasketAdapter;
import com.example.osman.grandresturant.Helper.HelperMethods;

public class MyBasket extends AppCompatActivity {
RecyclerView recycleBasket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_basket);

      /*  recycleBasket=(RecyclerView)findViewById(R.id.recycleBasket);
        recycleBasket.setLayoutManager(new LinearLayoutManager(this));
        BasketAdapter basketAdapter=new BasketAdapter(this,HelperMethods.encaps_baskets);
        recycleBasket.setAdapter(basketAdapter);*/



    }
}
