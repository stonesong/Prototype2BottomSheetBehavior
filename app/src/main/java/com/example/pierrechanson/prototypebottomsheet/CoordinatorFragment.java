package com.example.pierrechanson.prototypebottomsheet;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
        mapView.onCreate(null);
        mapView2 = (MapView) view.findViewById(R.id.mapview2);
        mapView2.onCreate(savedInstanceState);

//        chat = (ImageView) view.findViewById(R.id.image_chat);

        setUpMap();
        setUpMap2();

        bottomSheetLayout = view.findViewById(R.id.bottom_sheet_layout);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        bottomSheet = new CabBottomSheet(getActivity(), bottomSheetLayout, actionBar);

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

        bottomSheet.sheetBehavior.setState(BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED);
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
