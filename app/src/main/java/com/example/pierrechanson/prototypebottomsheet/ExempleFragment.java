package com.example.pierrechanson.prototypebottomsheet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by pierrechanson on 14/07/16.
 */
public class ExempleFragment extends Fragment {

    private RecyclerView freeBikesListView;
    private FreeBikesAdapter freeBikesAdapter;
    private LinearLayoutManager linearLayoutManager;

    public static Fragment newInstance() {
        ExempleFragment fragment = new ExempleFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.first_page_fragment,
                container, false);

        freeBikesListView = (RecyclerView) view.findViewById(R.id.free_bike_list);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        freeBikesListView.setLayoutManager(linearLayoutManager);
        ArrayList<String> data = new ArrayList<>();
        data.add("derp");
        data.add("derperp");
        data.add("derpiderp");
        freeBikesAdapter = new FreeBikesAdapter(data);
        freeBikesListView.setAdapter(freeBikesAdapter);
        freeBikesListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> data = new ArrayList<>();
                data.add("flcon");
                data.add("de");
                data.add("neige");
//                freeBikesAdapter.bikeList = data;
            }
        });

        return view ;
    }
}
