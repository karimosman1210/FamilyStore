package com.example.osman.grandresturant.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.osman.grandresturant.MyAds;
import com.example.osman.grandresturant.R;
import com.example.osman.grandresturant.classes.ItemClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.List;

/**
 * Created by a.taher on 7/3/2016.
 */
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    private Context context;
    private List<ItemClass> my_data;

    public ItemsAdapter(Context context, List<ItemClass> my_data) {
        this.context = context;
        this.my_data = my_data;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView item_image, favoritBtn;
        TextView item_name, item_price, item_type, item_place, item_category;

        public ViewHolder(View view) {
            super(view);

            item_name = (TextView) view.findViewById(R.id.item_recycler_name);
            item_price = (TextView) view.findViewById(R.id.item_recycler_price);
            item_place = (TextView) view.findViewById(R.id.item_recycler_place);
            item_category = (TextView) view.findViewById(R.id.item_recycler_category);
            item_image = (ImageView) view.findViewById(R.id.item_recycler_image);

            favoritBtn = (ImageView) view.findViewById(R.id.favoritBtn);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_details, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        ItemClass itemClass = my_data.get(position);


        holder.item_name.setText(itemClass.getName());
        holder.item_price.setText(itemClass.getPrice());
        holder.item_place.setText(itemClass.getPlaceLocation());
        holder.item_category.setText(itemClass.getItemType());

        final String id = itemClass.getID();
        holder.favoritBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Favorite").child(auth.getCurrentUser().getUid());
                //.child("id").child(id).push();
                //.child(id).push();

//                databaseReference.child("id").setValue(id);
                databaseReference.setValue(id);
                Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show();
            }
        });

        Glide.with(context).load(my_data.get(position).getImage()).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                return false;
            }
        }).into(holder.item_image);

    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

}