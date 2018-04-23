package com.example.osman.grandresturant.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.osman.grandresturant.Helper.HelperMethods;
import com.example.osman.grandresturant.ItemsRecycler;
import com.example.osman.grandresturant.R;
import com.example.osman.grandresturant.classes.SallersClass;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by osman on 4/23/2018.
 */

public class SallerAdapter extends RecyclerView.Adapter<SallerAdapter.Holder> implements Filterable {
    private Context context;
    ArrayList<SallersClass> sellers;
    ArrayList<SallersClass> sellersCopy;

    public SallerAdapter(ArrayList<SallersClass> sellers, ArrayList<SallersClass> sellersCopy, Context context) {
        this.sellers = sellers;
        this.sellersCopy = sellers;
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.saller_recycler_item, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        final SallersClass sallersClass = sellers.get(position);
        holder.updateUI(sallersClass);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperMethods.Home_Filtter_sallerID = sallersClass.getId();
                context.startActivity(new Intent(context, ItemsRecycler.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return sellers.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView name, location, mobile;
        ImageView image;

        Holder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.saller_recycler_item_name);
            location = (TextView) itemView.findViewById(R.id.saller_recycler_item_location);
            mobile = (TextView) itemView.findViewById(R.id.saller_recycler_item_mobile);
            image = (ImageView) itemView.findViewById(R.id.saller_recycler_item_image);

        }

        void updateUI(SallersClass sallersClass) {
            name.setText(sallersClass.getUsername());
            location.setText(sallersClass.getCountry());
            mobile.setText(sallersClass.getMobile());
            Picasso.with(context).load(sallersClass.getProfile_image()).into(image);
        }
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                ArrayList<SallersClass> filteredResults = null;
                if (constraint.length() == 0) {
                    filteredResults = sellersCopy;
                } else {
                    filteredResults = getFilteredResults(constraint.toString().toLowerCase());
                }

                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                sellers = (ArrayList<SallersClass>) results.values;
                SallerAdapter.this.notifyDataSetChanged();
            }
        };
    }

    private ArrayList<SallersClass> getFilteredResults(String constraint) {
        ArrayList<SallersClass> results = new ArrayList<>();

        for (SallersClass item : sellersCopy) {
            if (item.getUsername().toLowerCase().contains(constraint)) {
                results.add(item);
            }
        }
        return results;
    }

}
