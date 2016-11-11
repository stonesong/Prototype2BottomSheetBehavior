package com.example.pierrechanson.prototypebottomsheet;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private boolean isHeaderRed = false;

    private static final int HEADER_TRANSITION_DURATION = 200;


    public CabBottomSheet(Activity activity, View bottomSheet, ActionBar actionBar) {
        this.context = activity;
        this.bottomSheet = bottomSheet;
        this.actionBar = actionBar;
        header = (LinearLayout) bottomSheet.findViewById(R.id.header);

        stationFullDistance = (TextView) bottomSheet.findViewById(R.id.station_full_distance);
        stationFullName = (TextView) bottomSheet.findViewById(R.id.stationFullStationName);
        stationFullFreeBikes = (TextView) bottomSheet.findViewById(R.id.station_full_free_bikes);

        rentLayout = (LinearLayout) bottomSheet.findViewById(R.id.rent_layout);
        damageLayout = (LinearLayout) bottomSheet.findViewById(R.id.damage_layout);
        routeLayout = (LinearLayout) bottomSheet.findViewById(R.id.route_layout);

        setUpBottomSheet();
        setUpCallbacks();
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

                } else if (newState == BottomSheetBehaviorGoogleMapsLike.STATE_EXPANDED) {
                    Log.d("bottom sheet", "EXTANDED");
                    setHeaderRed();
                    hideActionBar();

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

        setToastClickListener(rentLayout, "Show rent another");
        setToastClickListener(damageLayout, "Show Report Damage");
        setToastClickListener(routeLayout, "Start Route");
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
            isHeaderRed = true;
        }
    }

    private void setHeaderWhite() {
        if (isHeaderRed) {
            headerColorTransition(HEADER_TRANSITION_DURATION, R.drawable.header_transition_white, R.color.color_black);
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
}
