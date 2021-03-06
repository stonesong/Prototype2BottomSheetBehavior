package com.example.pierrechanson.prototypebottomsheet;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
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

/**
 * Created by pierrechanson on 14/07/16.
 */
public class CoordinatorFragment extends Fragment implements GoogleMap.OnMarkerClickListener {

    MapView mapView;
    MapView mapView2;
    GoogleMap map;
    GoogleMap map2;

    private LinearLayout header;

    private BottomSheetBehaviorGoogleMapsLike sheetBehavior;
    private View bottomSheet;
    private ArrayList<Marker> markers;
    private Marker marker2;

    private ViewPager pager;
    private ViewPager recyclerPager;
    private ImageView chat;
    private PagerAdapter pagerAdapter;


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
        mapView.onCreate(savedInstanceState);

        mapView2 = (MapView) view.findViewById(R.id.mapview2);
        mapView2.onCreate(savedInstanceState);

//        chat = (ImageView) view.findViewById(R.id.image_chat);

        setUpMap();
        setUpMap2();

        header = (LinearLayout) view.findViewById(R.id.header);

        bottomSheet = view.findViewById(R.id.bottom_sheet_layout);
        setUpBottomSheet();

        pager=(ViewPager)view.findViewById(R.id.bs_pager);
        pager.setAdapter(buildAdapter());

        recyclerPager = (ViewPager) view.findViewById(R.id.recyclerView_pager);
        pagerAdapter = new StationPagerAdapter(getActivity().getSupportFragmentManager());
        recyclerPager.setAdapter(pagerAdapter);

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
        markers = new ArrayList<>();
        markers.add(map.addMarker(new MarkerOptions().position(TutorialsPoint)));
        markers.add(map.addMarker(new MarkerOptions().position(TutorialsPoint2)));
        markers.add(map.addMarker(new MarkerOptions().position(TutorialsPoint3)));
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

    private void setUpBottomSheet(){

        sheetBehavior = BottomSheetBehaviorGoogleMapsLike.from(bottomSheet);

        sheetBehavior.addBottomSheetCallback(new BottomSheetBehaviorGoogleMapsLike.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                Log.d("bottom sheet", "onStateChanged: " + newState);
                // React to state change
                if (newState == BottomSheetBehaviorGoogleMapsLike.STATE_HIDDEN) {
                    Log.d("bottom sheet", "HIDE");
                    recyclerToPager();
                } else if (newState == BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED) {
                    Log.d("bottom sheet", "COLLAPSED");
                    recyclerToPager();
                } else if (newState == BottomSheetBehaviorGoogleMapsLike.STATE_SETTLING) {
                    Log.d("bottom sheet", "SETTLING");

                } else if (newState == BottomSheetBehaviorGoogleMapsLike.STATE_ANCHOR_POINT) {
                    Log.d("bottom sheet", "ANCHORPOINT");
                    pagerToRecycler();

                } else if (newState == BottomSheetBehaviorGoogleMapsLike.STATE_EXPANDED) {
                    Log.d("bottom sheet", "EXTANDED");
                    pagerToRecycler();

                }

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Log.d("bottom sheet", "slideOffset: " + slideOffset);
            }
        });

        sheetBehavior.setState(BottomSheetBehaviorGoogleMapsLike.STATE_HIDDEN);

    }

    private void setUpCallbacks(){

        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheetBehavior.setState(BottomSheetBehaviorGoogleMapsLike.STATE_ANCHOR_POINT);
            }
        });

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                sheetBehavior.setState(BottomSheetBehaviorGoogleMapsLike.STATE_HIDDEN);
            }
        });

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

    @Override
    public boolean onMarkerClick(final Marker marker) {

        sheetBehavior.setState(BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED);
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
