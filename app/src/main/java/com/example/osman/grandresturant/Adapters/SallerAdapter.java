package com.example.osman.grandresturant.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.osman.grandresturant.R;
import com.example.osman.grandresturant.SallersRecycler;
import com.example.osman.grandresturant.classes.Item_recycle;
import com.example.osman.grandresturant.classes.SallersClass;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by osman on 4/23/2018.
 */

public class SallerAdapter extends RecyclerView.Adapter<SallerAdapter.Holder> {
    private Context context;
    ArrayList<SallersClass> arrayList;
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.saller_recycler_item,parent,false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        SallersClass sallersClass=arrayList.get(position);

        holder.name.setText(sallersClass.getUsername());
        holder.location.setText(sallersClass.getCountry());
        holder.mobile.setText(sallersClass.getMobile());
        Picasso.with(context).load(sallersClass.getProfile_image()).into(holder.image);


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
       TextView name,location,mobile;
       ImageView image;
        public Holder(View itemView) {
            super(itemView);

            name=(TextView)itemView.findViewById(R.id.saller_recycler_item_mobile);
            location=(TextView)itemView.findViewById(R.id.saller_recycler_item_location);
            mobile=(TextView)itemView.findViewById(R.id.saller_recycler_item_mobile);
            image=(ImageView)itemView.findViewById(R.id.saller_recycler_item_image);


        }
    }
}
