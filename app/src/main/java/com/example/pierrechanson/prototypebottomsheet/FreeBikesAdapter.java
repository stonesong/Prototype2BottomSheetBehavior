package com.example.pierrechanson.prototypebottomsheet;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by guillaumemeral on 11/11/16.
 */

public class FreeBikesAdapter extends RecyclerView.Adapter<FreeBikesAdapter.BikeHolder>  {

    public ArrayList<String> bikeList;

    public FreeBikesAdapter(ArrayList<String> bikeList) {
        this.bikeList = bikeList;
    }

    @Override
    public FreeBikesAdapter.BikeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.simple_free_bikes_list, parent, false);
        return new BikeHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(FreeBikesAdapter.BikeHolder holder, int position) {
        String text = bikeList.get(position);
        holder.bindBikeText(text);
    }

    @Override
    public int getItemCount() {
        return bikeList.size();
    }

    public static class BikeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView;

        private static final String BIKE_KEY = "BIKE";

        public BikeHolder(View v) {
            super(v);
            textView = (TextView) itemView.findViewById(R.id.simple_free_bikes_list_bike_nr);
            v.setOnClickListener(this);
        }

        public void bindBikeText(String text) {
            textView.setText(text);
        }

        @Override
        public void onClick(View v) {
            Log.d("RecyclerView", "CLICK!");
        }
    }

    public void setData(ArrayList<String> data) {
        bikeList.clear();
        bikeList.addAll(data);
        notifyDataSetChanged();
    }
}
