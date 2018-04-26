package com.example.osman.grandresturant.Adapters;

import android.app.Activity;
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
import com.example.osman.grandresturant.Dialogs.MyBasket_delete_dialog;
import com.example.osman.grandresturant.Helper.HelperMethods;
import com.example.osman.grandresturant.ItemScreen;
import com.example.osman.grandresturant.ItemsRecycler;
import com.example.osman.grandresturant.NavigationActivities.MyBasket;
import com.example.osman.grandresturant.R;
import com.example.osman.grandresturant.classes.RequestsClass;

import com.example.osman.grandresturant.classes.SallersClass;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by A.taher on 4/25/2018.
 */

public class MyBasket_Adapter extends RecyclerView.Adapter<MyBasket_Adapter.Holder> implements Filterable {
    private Context context;
    ArrayList<RequestsClass> sellers;
    ArrayList<RequestsClass> sellersCopy;

    public MyBasket_Adapter(ArrayList<RequestsClass> sellers, ArrayList<RequestsClass> sellersCopy, Context context) {
        this.sellers = sellers;
        this.sellersCopy = sellers;
        this.context = context;
    }

    @Override
    public MyBasket_Adapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.my_basket_item, parent, false);
        return new MyBasket_Adapter.Holder(v);
    }

    @Override
    public void onBindViewHolder(MyBasket_Adapter.Holder holder, int position) {
        final RequestsClass itemClass = sellers.get(position);
        holder.updateUI(itemClass);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(context, ItemScreen.class);
                intent.putExtra("Item_ID", itemClass.getRequestItemID());
                context.startActivity(intent);

            }
        });

        holder.RequestDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                HelperMethods.delete_ads_id = itemClass.getRequestID();
                MyBasket_delete_dialog cdd = new MyBasket_delete_dialog((Activity) context);
                cdd.show();

            }
        });





    }

    @Override
    public int getItemCount() {
        return sellers.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        View view;
        ImageView RequestItemImage, RequestDeleteBtn;
        TextView RequestItemName, RequestItemPrice;
        Holder(View itemView) {
            super(itemView);
            view = itemView;
            RequestItemName = (TextView) view.findViewById(R.id.My_Basket_item_name);
            RequestItemPrice = (TextView) view.findViewById(R.id.My_Basket_item_price);
            RequestItemImage = (ImageView) view.findViewById(R.id.My_Basket_item_image);
            RequestDeleteBtn = (ImageView) view.findViewById(R.id.My_Basket_delete_btn);

        }

        void updateUI(RequestsClass itemsClass) {

            RequestItemName.setText(itemsClass.getRequestItemName());
            RequestItemPrice.setText(itemsClass.getRequestItemPrice());

            Glide.with(view.getContext()).load(itemsClass.getRequestItemImage()).placeholder(RequestItemImage.getDrawable()).fitCenter().into(RequestItemImage);

        }
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                ArrayList<RequestsClass> filteredResults = null;
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
                sellers = (ArrayList<RequestsClass>) results.values;
                MyBasket_Adapter.this.notifyDataSetChanged();
            }
        };
    }

    private ArrayList<RequestsClass> getFilteredResults(String constraint) {
        ArrayList<RequestsClass> results = new ArrayList<>();

        for (RequestsClass item : sellersCopy) {
            if (item.getRequestItemName().toLowerCase().contains(constraint)) {
                results.add(item);
            }
        }
        return results;
    }

}