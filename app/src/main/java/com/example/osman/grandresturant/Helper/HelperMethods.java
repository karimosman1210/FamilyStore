package com.example.osman.grandresturant.Helper;

/**
 * Created by A.taher on 4/8/2018.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;

import com.example.osman.grandresturant.classes.Encaps_Basket;

import java.util.ArrayList;


public class HelperMethods {




    public static String categoryName;
    public static String sallerID;
    public static String User_id;
    public static String delete_ads_id;
    public static String sign_location;


    public static ArrayList<Encaps_Basket>encaps_baskets=new ArrayList<>();
    public static String items_recycler_id;
    public static String items_recycler_name;
    public static String items_recycler_type;
    public static String items_recycler_price;
    public static String items_recycler_country;
    public static String items_recycler_place;
    public static String items_recycler_image;
    public static String items_recycler_user_name;
    public static String items_recycler_user_email;
    public static String items_recycler_user_number;
    public static String items_recycler_user_Image;
    public static long items_recycler_Time;
    public static String items_recycler_desc;



    public static Uri uri_usre_profile_image;
    public static ProgressDialog blg , blg2;

    public static void showDialog(Activity currentActivity, String title, String msg) {
        blg = new ProgressDialog(currentActivity);
        blg.setTitle(title);
        blg.setCanceledOnTouchOutside(false);
        blg.setMessage(msg);
        blg.show();
    }

    public static void showDialog2(Activity currentActivity, String title, String msg) {
        blg2 = new ProgressDialog(currentActivity);
        blg2.setTitle(title);
        blg2.setCanceledOnTouchOutside(false);
        blg2.setMessage(msg);
        blg2.show();
    }

    public static void hideDialog(Activity currentActivity) {
        blg.cancel();
    }
    public static void hideDialog2(Activity currentActivity) {
        blg2.cancel();
    }
}
