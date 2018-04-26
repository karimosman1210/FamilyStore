package com.example.osman.grandresturant.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.osman.grandresturant.Helper.HelperMethods;
import com.example.osman.grandresturant.ItemsRecycler;
import com.example.osman.grandresturant.R;
import com.example.osman.grandresturant.classes.RequestsClass;
import com.example.osman.grandresturant.classes.SallersClass;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by A.taher on 4/25/2018.
 */

public class NewRequests_Adapter extends RecyclerView.Adapter<NewRequests_Adapter.Holder>  {
    private Context context;
    ArrayList<RequestsClass> sellers;
    ArrayList<RequestsClass> sellersCopy;

    public NewRequests_Adapter(ArrayList<RequestsClass> sellers, ArrayList<RequestsClass> sellersCopy, Context context) {
        this.sellers = sellers;
        this.sellersCopy = sellers;
        this.context = context;
    }

    @Override
    public NewRequests_Adapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.requests_item, parent, false);
        return new NewRequests_Adapter.Holder(v);
    }

    @Override
    public void onBindViewHolder(NewRequests_Adapter.Holder holder, int position) {
        final RequestsClass requestsClass = sellers.get(position);
        holder.updateUI(requestsClass);


    }

    @Override
    public int getItemCount() {
        return sellers.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        View view;
        ImageView RequestItemImage;
        de.hdodenhof.circleimageview.CircleImageView RequestUserImage;
        TextView RequestUserName, RequestUserEmail, RequestUserNumber, RequestItemName, RequestItemPrice, RequestItemAdded;

        Holder(View itemView) {
            super(itemView);
            RequestUserName = (TextView) itemView.findViewById(R.id.request_profile_name);
            RequestUserEmail = (TextView) itemView.findViewById(R.id.request_profile_mail);
            RequestUserNumber = (TextView) itemView.findViewById(R.id.request_profile_number);
            RequestItemName = (TextView) itemView.findViewById(R.id.request_item_name);
            RequestItemPrice = (TextView) itemView.findViewById(R.id.request_item_price);

            RequestItemAdded = (TextView) itemView.findViewById(R.id.request_item_date);


            RequestItemImage = (ImageView) itemView.findViewById(R.id.request_item_image);
            RequestUserImage = (de.hdodenhof.circleimageview.CircleImageView) itemView.findViewById(R.id.request_profile_image);

        }

        void updateUI(RequestsClass requestsClass) {
            RequestUserName.setText(requestsClass.getRequestUserName());
            RequestUserEmail.setText(requestsClass.getRequestUserEmail());
            RequestUserNumber.setText(requestsClass.getRequestUserNumber());
            RequestItemName.setText(requestsClass.getRequestItemName());
            RequestItemPrice.setText(requestsClass.getRequestItemPrice());


            long timestamp = Long.parseLong(String.valueOf(requestsClass.getRequestTime())) * 1000L;
            RequestItemAdded.setText(getDate(timestamp));



            Glide.with(context).load(requestsClass.getRequestItemImage()).placeholder(RequestItemImage.getDrawable()).fitCenter().into(RequestItemImage);
            Glide.with(context).load(requestsClass.getRequestUserImage()).placeholder(RequestUserImage.getDrawable()).fitCenter().into(RequestUserImage);

        }
    }

    private String getDate(long timeStamp) {

        try {
            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
            Date netDate = (new Date(timeStamp));
            return sdf.format(netDate);
        } catch (Exception ex) {
            return "xx";
        }
    }
}