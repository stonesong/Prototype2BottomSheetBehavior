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


        return view ;
    }
}
