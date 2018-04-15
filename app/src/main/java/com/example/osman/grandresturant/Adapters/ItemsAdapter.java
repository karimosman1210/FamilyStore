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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.osman.grandresturant.ItemScreen;
import com.example.osman.grandresturant.R;
import com.example.osman.grandresturant.classes.ItemClass;


import java.util.List;

/**
 * Created by a.taher on 7/3/2016.
 */
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    private Context context;
    private List<ItemClass> my_data;

    public ItemsAdapter(Context context, List<ItemClass> my_data) {
        this.context =  context;
        this.my_data = my_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_details,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


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

        holder.item_name.setText(my_data.get(position).getName());
        holder.item_price.setText(my_data.get(position).getPrice());
        holder.item_place.setText(my_data.get(position).getCountryLocation());
        holder.item_user_name.setText(my_data.get(position).getUserName());





    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{

        ImageView item_image,favoritBtn;
        TextView item_name, item_price , item_place, item_user_name;


        public ViewHolder(View view) {
            super(view);

            item_name = (TextView) view.findViewById(R.id.item_recycler_name);
            item_price = (TextView) view.findViewById(R.id.item_recycler_price);
            item_place = (TextView) view.findViewById(R.id.item_recycler_place);
            item_user_name = (TextView) view.findViewById(R.id.item_recycler_user_name);
            item_image = (ImageView) view.findViewById(R.id.item_recycler_image);
            favoritBtn=(ImageView)view.findViewById(R.id.favoritBtn);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int postion = getLayoutPosition();

                    String ID = my_data.get(postion).getID();

                    Intent intent = new Intent(context, ItemScreen.class);

                    intent.putExtra("Item_ID", ID);

                    context.startActivity(intent);

                }
            });

        }
    }
}