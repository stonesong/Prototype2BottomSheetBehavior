package com.example.pierrechanson.prototypebottomsheet;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by guillaumemeral on 10/11/16.
 */

public class CabBottomSheet {

    public BottomSheetBehaviorGoogleMapsLike sheetBehavior;
    private View bottomSheet;
    private Activity context;
    private LinearLayout header;
    private TextView stationFullName, stationFullFreeBikes, stationFullDistance;
    private LinearLayout rentLayout, damageLayout, routeLayout;
    private ActionBar actionBar;
    private RecyclerView freeBikesListView;
    private FreeBikesAdapter freeBikesAdapter;
    private LinearLayoutManager linearLayoutManager;
    private FloatingActionButton rentBikeFab;
    private boolean isHeaderRed = false;

    private static final int HEADER_TRANSITION_DURATION = 200;


    public CabBottomSheet(Activity activity, View bottomSheet, ActionBar actionBar, FloatingActionButton rentBikeFab) {
        this.context = activity;
        this.bottomSheet = bottomSheet;
        this.actionBar = actionBar;
        this.rentBikeFab = rentBikeFab;
        header = (LinearLayout) bottomSheet.findViewById(R.id.header);

        stationFullDistance = (TextView) bottomSheet.findViewById(R.id.station_full_distance);
        stationFullName = (TextView) bottomSheet.findViewById(R.id.stationFullStationName);
        stationFullFreeBikes = (TextView) bottomSheet.findViewById(R.id.station_full_free_bikes);


        freeBikesListView = (RecyclerView) bottomSheet.findViewById(R.id.free_bike_list);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        freeBikesListView.setLayoutManager(linearLayoutManager);
        ArrayList<String> bikeList = new ArrayList<>();
        freeBikesAdapter = new FreeBikesAdapter(context, bikeList);
        freeBikesListView.setAdapter(freeBikesAdapter);



        setUpBottomSheet();
        setUpCallbacks();
        setupButtonLayout();
    }

    private void setupButtonLayout() {
        rentLayout = (LinearLayout) bottomSheet.findViewById(R.id.rent_layout);
        damageLayout = (LinearLayout) bottomSheet.findViewById(R.id.damage_layout);
        routeLayout = (LinearLayout) bottomSheet.findViewById(R.id.route_layout);

        setToastClickListener(rentLayout, "Show rent another");
        setToastClickListener(damageLayout, "Show Report Damage");
        setToastClickListener(routeLayout, "Start Route");

        ImageView stationCircle = (ImageView) rentLayout.findViewById(R.id.station_circle);
        ImageView stationWarning = (ImageView) damageLayout.findViewById(R.id.station_warning);
        ImageView stationRoute = (ImageView) routeLayout.findViewById(R.id.station_route);

        setRedColorFilter(stationCircle);
        setRedColorFilter(stationWarning);
        setRedColorFilter(stationRoute);

    }

    private void setRedColorFilter(ImageView v) {
        v.setColorFilter(ContextCompat.getColor(context.getApplicationContext(), R.color.color_red));
    }


    private void setUpBottomSheet() {
        sheetBehavior = BottomSheetBehaviorGoogleMapsLike.from(bottomSheet);


        Resources r = context.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, r.getDisplayMetrics());
        sheetBehavior.setPeekHeight((int) px);

        sheetBehavior.addBottomSheetCallback(new BottomSheetBehaviorGoogleMapsLike.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                Log.d("bottom sheet", "onStateChanged: " + newState);
                // React to state change
                if (newState == BottomSheetBehaviorGoogleMapsLike.STATE_HIDDEN) {
                    Log.d("bottom sheet", "HIDE");
                    showActionBar();
                } else if (newState == BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED) {
                    Log.d("bottom sheet", "COLLAPSED");
                    setHeaderWhite();
                    showActionBar();
                } else if (newState == BottomSheetBehaviorGoogleMapsLike.STATE_SETTLING) {
                    Log.d("bottom sheet", "SETTLING");

                } else if (newState == BottomSheetBehaviorGoogleMapsLike.STATE_ANCHOR_POINT) {
                    Log.d("bottom sheet", "ANCHORPOINT");
                    setHeaderRed();
                    showActionBar();
                    bottomSheet.requestLayout();

                } else if (newState == BottomSheetBehaviorGoogleMapsLike.STATE_EXPANDED) {
                    Log.d("bottom sheet", "EXTANDED");
                    setHeaderRed();
                    hideActionBar();
                    //We need to make this call to make sure UI is updated after changing the bottomsheet recyclerview data
                    bottomSheet.requestLayout();

                }

                if (sheetBehavior.getState() == BottomSheetBehaviorGoogleMapsLike.STATE_DRAGGING) {
                    if (newState == BottomSheetBehaviorGoogleMapsLike.STATE_ANCHOR_POINT) {
                        headerColorTransition(HEADER_TRANSITION_DURATION, R.drawable.header_transition, R.color.back_white);
                    } else if (newState == BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED) {
                        headerColorTransition(HEADER_TRANSITION_DURATION, R.drawable.header_transition_white, R.color.color_black);
                    }
                }

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Log.d("bottom sheet", "slideOffset: " + slideOffset);
            }
        });

        sheetBehavior.setState(BottomSheetBehaviorGoogleMapsLike.STATE_HIDDEN);

    }

    private void hideActionBar() {
        actionBar.hide();
    }

    private void showActionBar() {
        actionBar.show();
    }


    private void setUpCallbacks() {
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheetBehavior.setState(BottomSheetBehaviorGoogleMapsLike.STATE_ANCHOR_POINT);
            }
        });

        rentBikeFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Rent " + freeBikesAdapter.getFirstBike() + " ?", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setToastClickListener(View view, final String text) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @TargetApi(16)
    private void setViewBackGround(View view, Drawable drawable) {
        view.setBackground(drawable);
    }


    public void headerColorTransition(int ms, int targetBackgroundColor, int targetTextColor) {
        TransitionDrawable transDraw = (TransitionDrawable) ContextCompat.getDrawable(context, targetBackgroundColor);
        setViewBackGround(header, transDraw);
        transDraw.startTransition(ms);
        setHeaderTextColor(targetTextColor);
    }

    private void setHeaderRed() {
        if (!isHeaderRed) {
            headerColorTransition(HEADER_TRANSITION_DURATION, R.drawable.header_transition, R.color.back_white);
            rentBikeFab.getDrawable().setColorFilter(ContextCompat.getColor(context, R.color.color_red), PorterDuff.Mode.MULTIPLY);
            rentBikeFab.setBackgroundTintList(context.getResources().getColorStateList(R.color.back_white));
            isHeaderRed = true;
        }
    }

    private void setHeaderWhite() {
        if (isHeaderRed) {
            headerColorTransition(HEADER_TRANSITION_DURATION, R.drawable.header_transition_white, R.color.color_black);
            rentBikeFab.getDrawable().setColorFilter(ContextCompat.getColor(context, R.color.back_white), PorterDuff.Mode.MULTIPLY);
            rentBikeFab.setBackgroundTintList(context.getResources().getColorStateList(R.color.color_red));
            isHeaderRed = false;
        }
    }

    public void setHeaderTextColor(int targetTextColor) {
        stationFullDistance.setTextColor(ContextCompat.getColor(context, targetTextColor));
        stationFullName.setTextColor(ContextCompat.getColor(context, targetTextColor));
        stationFullFreeBikes.setTextColor(ContextCompat.getColor(context, targetTextColor));
    }

    public void setHeaderTitle(CharSequence text) {
        stationFullName.setText(text);
    }

    public void setFreeBikesTV(CharSequence text) {
        stationFullFreeBikes.setText(text);
    }

    public void setStationDistanceTV(CharSequence text) {
        stationFullDistance.setText(text);
    }

    public void setFreeBikesList(ArrayList<String> bikeList) {
        freeBikesAdapter.setData(bikeList);
    }

    protected ArrayAdapter freeBikesAdapter(final ArrayList<String> bikeList) {
        if (bikeList != null) {
            return new ArrayAdapter<String>(context, R.layout.simple_free_bikes_list, bikeList) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    return createFreeBikeItemView(convertView, bikeList.get(position));
                }

                @Override
                public int getCount() {
                    return bikeList.size();
                }
                @Override
                public long getItemId(int position) {

                    return position;
                }

            };
        } else {
            return null;
        }
    }

    public View createFreeBikeItemView(View convertView, String text) {
        View itemView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.simple_free_bikes_list, null);
        } else {
            itemView = convertView;
        }

        TextView tv = (TextView) itemView.findViewById(R.id.simple_free_bikes_list_bike_nr);
        tv.setText(text);
        return itemView;
    }
}
