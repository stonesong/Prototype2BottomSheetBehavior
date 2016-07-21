package com.example.pierrechanson.prototypebottomsheet;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class InvoiceListAdapter extends RecyclerView.Adapter<InvoiceListAdapter.ViewHolder> {

    private ArrayList<String> items;
    private boolean showLoadingView = false;


    public InvoiceListAdapter(ArrayList<String> items) {
        this.items = items;
    }

    @Override
    public InvoiceListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.car_item, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // - get data from your items at this position
        // - replace the contents of the view with that items
        final String item = items.get(position);

        viewHolder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"clicked on: "+ position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View itemLayout;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            itemLayout = itemLayoutView;
        }
    }


    public void updateItems(List<String> itms) {
        this.items.clear();
        this.items.addAll(itms);
        notifyItemRangeInserted(0, items.size());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}