package com.example.pierrechanson.prototypebottomsheet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by pierrechanson on 22/07/16.
 */
public class StationDetailFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<String> items = new ArrayList(Arrays.asList("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1"));
    private LinearLayoutManager layoutManager;
    private InvoiceListAdapter mAdapter;


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
        View view = (View) inflater.inflate(R.layout.station_detail_layout,
                container, false);

        recyclerView =(RecyclerView)view.findViewById(R.id.bs_recycle_view);
        setUpRecycleView();

        return view ;
    }


    private void setUpRecycleView(){
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new InvoiceListAdapter(items);
        recyclerView.setAdapter(mAdapter);
    }

}
