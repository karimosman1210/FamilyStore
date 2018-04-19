package com.example.osman.grandresturant;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class splash extends AppCompatActivity {

ProgressBar progress;
Button retry;
    private final int splash=3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
retry=(Button)findViewById(R.id.retry);

        progress = (ProgressBar)  findViewById(R.id.progress_splash);

        progress.setProgress(10);


        if (stautes() == true) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent intent = new Intent(splash.this, HomeScreen.class);
                    splash.this.startActivity(intent);
                    //splash.this.finish();
                }
            }, splash);


        } else {



            Toast.makeText(this, "Check internet connection", Toast.LENGTH_SHORT).show();
    progress.setVisibility(View.INVISIBLE);
    retry.setVisibility(View.VISIBLE);
        }
    retry.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent=new Intent(splash.this,splash.class);
            startActivity(intent);

        }
    });
    }


    private boolean stautes(){
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected()) {
            return true;
        }else{
            return false;
        }

    }


    }








