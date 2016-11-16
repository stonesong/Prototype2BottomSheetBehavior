package com.example.pierrechanson.prototypebottomsheet;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by pierrechanson on 14/07/16.
 */
public class CoordinatorFragment extends Fragment implements GoogleMap.OnMarkerClickListener {

    MapView mapView;
    private GoogleMap map;
    MapView mapView2;
    GoogleMap map2;

    private CabBottomSheet bottomSheet;
    private View bottomSheetLayout;
    private ArrayList<Marker> markers;
    private Marker marker2;

    private RecyclerView freeBikesListView;
    private FreeBikesAdapter freeBikesAdapter;
    private LinearLayoutManager linearLayoutManager;

    private ViewPager pager;
    private ViewPager recyclerPager;
    private ImageView chat;
    private PagerAdapter pagerAdapter;

    private HashMap<Marker, DummyDataObject> markerDataMap = new HashMap<>();


    public static Fragment newInstance() {
        CoordinatorFragment fragment = new CoordinatorFragment();
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

       View view = (View) inflater.inflate(R.layout.fragment_coordinator,
                container, false);


        mapView = (MapView) view.findViewById(R.id.mapview);
        mapView.onCreate(null);
        mapView2 = (MapView) view.findViewById(R.id.mapview2);
        mapView2.onCreate(savedInstanceState);

//        chat = (ImageView) view.findViewById(R.id.image_chat);

        setUpMap();
        setUpMap2();

        bottomSheetLayout = view.findViewById(R.id.bottom_sheet_layout);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        FloatingActionButton rentBikeFab = (FloatingActionButton) view.findViewById(R.id.rent_bike_fab);
        bottomSheet = new CabBottomSheet(getActivity(), bottomSheetLayout, actionBar, rentBikeFab);



        setUpCallbacks();

        return view ;
    }


    private void setUpMap(){

        // Gets to GoogleMap from the MapView and does initialization stuff
        map = mapView.getMap();
        map.getUiSettings().setMyLocationButtonEnabled(false);
//        map.setMyLocationEnabled(true);

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        try {
            MapsInitializer.initialize(this.getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.setOnMarkerClickListener(this);

        // Updates the location and zoom of the MapView
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(50.934830, 6.946425), 15);
        map.animateCamera(cameraUpdate);

        final LatLng TutorialsPoint = new LatLng(50.934830, 6.946425);
        final LatLng TutorialsPoint2 = new LatLng(50.934135, 6.942425);
        final LatLng TutorialsPoint3 = new LatLng(50.935230, 6.943426);
        final ArrayList<String> freeBikes = new ArrayList<>();
        freeBikes.add("CallaBike No.5033");
        freeBikes.add("CallaBike No.5034");
        freeBikes.add("CallaBike No.5035");

        final ArrayList<String> freeFromage = new ArrayList<>();
        freeFromage.add("Fromage No.5033");
        freeFromage.add("Fromage No.5034");
        freeFromage.add("Fromage No.5035");
        freeFromage.add("Fromage No.5036");
        freeFromage.add("Fromage No.5037");
        freeFromage.add("Fromage No.5038");
        freeFromage.add("Fromage No.5039");
        freeFromage.add("Fromage No.50310");
        freeFromage.add("Fromage No.50311");
        freeFromage.add("Fromage No.50312");
        freeFromage.add("Fromage No.50313");
        freeFromage.add("Fromage No.50314");
        freeFromage.add("Fromage No.50315");
        freeFromage.add("Fromage No.50316");
        freeFromage.add("Fromage No.50317");
        freeFromage.add("Fromage No.50318");
        freeFromage.add("Fromage No.50319");
        freeFromage.add("Fromage No.50320");
        freeFromage.add("Fromage No.50321");
        freeFromage.add("Fromage No.50322");
        freeFromage.add("Fromage No.50323");
        freeFromage.add("Fromage No.50324");
        freeFromage.add("Fromage No.50325");
        freeFromage.add("Fromage No.50326");
        freeFromage.add("Fromage No.50327");
        freeFromage.add("Fromage No.50328");
        freeFromage.add("Fromage No.50329");

        final ArrayList<String> freePate = new ArrayList<>();
        freePate.add("Pate No.5033");
        freePate.add("Pate No.5034");
        freePate.add("Pate No.5035");


        final DummyDataObject data1 = new DummyDataObject("Station Bikes", "3 bikes available", "1 min", freeBikes);
        final DummyDataObject data2 = new DummyDataObject("Station Fromage", "27 fromages available", "2 min", freeFromage);
        final DummyDataObject data3 = new DummyDataObject("Station Pate", "3 pates available", "3 min", freePate);
        markers = new ArrayList<>();
        addMarker(TutorialsPoint, data1);
        addMarker(TutorialsPoint2, data2);
        addMarker(TutorialsPoint3, data3);
    }

    private void addMarker(LatLng pos, DummyDataObject data) {
        Marker marker = map.addMarker(new MarkerOptions().position(pos));
        markers.add(marker);
        markerDataMap.put(marker, data);
    }


    private void setUpMap2(){

        // Gets to GoogleMap from the MapView and does initialization stuff
        map2 = mapView2.getMap();
        map2.getUiSettings().setMyLocationButtonEnabled(false);
//        map.setMyLocationEnabled(true);

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        try {
            MapsInitializer.initialize(this.getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        map2.setOnMarkerClickListener(this);

        // Updates the location and zoom of the MapView
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(50.934830, 6.946425), 15);
        map2.animateCamera(cameraUpdate);

        final LatLng TutorialsPoint = new LatLng(50.934830, 6.946425);
        marker2 = map2.addMarker(new MarkerOptions().position(TutorialsPoint));
    }


    private PagerAdapter buildAdapter() {
        return(new SampleAdapter(getActivity(), getChildFragmentManager()));
    }

    @Override
    public void onResume() {
        mapView.onResume();
        mapView2.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        mapView2.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
        mapView2.onLowMemory();
    }

    private void setUpCallbacks() {
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                bottomSheet.sheetBehavior.setState(BottomSheetBehaviorGoogleMapsLike.STATE_HIDDEN);
            }
        });
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

        if (bottomSheet.sheetBehavior.getState() == BottomSheetBehaviorGoogleMapsLike.STATE_HIDDEN)
            bottomSheet.sheetBehavior.setState(BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED);
        DummyDataObject data = markerDataMap.get(marker);
        bottomSheet.setHeaderTitle(data.title);
        bottomSheet.setFreeBikesTV(data.availableBikes);
        bottomSheet.setStationDistanceTV(data.distanceTime);
        bottomSheet.setFreeBikesList(data.freeBikes);

        return true;
    }

    private void pagerToRecycler(){
        pager.animate().alpha(0.0f);
        pager.setVisibility(View.GONE);
        recyclerPager.setVisibility(View.VISIBLE);
        recyclerPager.animate().alpha(0.0f);
        recyclerPager.animate().setDuration(500).alpha(1.0f);
    }

    private void recyclerToPager(){
        recyclerPager.animate().setDuration(500).alpha(0.0f);
        recyclerPager.setVisibility(View.GONE);
        pager.setVisibility(View.VISIBLE);
        pager.animate().alpha(0.0f);
        pager.animate().setDuration(500).alpha(1.0f);
    }

}
