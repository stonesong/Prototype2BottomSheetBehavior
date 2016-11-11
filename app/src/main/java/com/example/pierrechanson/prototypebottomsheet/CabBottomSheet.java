package com.example.pierrechanson.prototypebottomsheet;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by guillaumemeral on 10/11/16.
 */

public class CabBottomSheet {

    public BottomSheetBehaviorGoogleMapsLike sheetBehavior;
    private View bottomSheet;
    private Activity context;
    private LinearLayout header;
    private TextView stationFullName, stationFullFreeBikes, stationFullDistance;
    private ActionBar actionBar;

    private static final int HEADER_TRANSITION_DURATION = 200;


    public CabBottomSheet(Activity activity, View bottomSheet, ActionBar actionBar) {
        this.context = activity;
        this.bottomSheet = bottomSheet;
        this.actionBar = actionBar;
        header = (LinearLayout) bottomSheet.findViewById(R.id.header);

        stationFullDistance = (TextView) bottomSheet.findViewById(R.id.station_full_distance);
        stationFullName = (TextView) bottomSheet.findViewById(R.id.stationFullStationName);
        stationFullFreeBikes = (TextView) bottomSheet.findViewById(R.id.station_full_free_bikes);

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
//                    headerColorTransition(HEADER_TRANSITION_DURATION, R.drawable.header_transition_white, R.color.color_black);
                    showActionBar();
                } else if (newState == BottomSheetBehaviorGoogleMapsLike.STATE_SETTLING) {
                    Log.d("bottom sheet", "SETTLING");

                } else if (newState == BottomSheetBehaviorGoogleMapsLike.STATE_ANCHOR_POINT) {
                    Log.d("bottom sheet", "ANCHORPOINT");
//                    headerColorTransition(HEADER_TRANSITION_DURATION, R.drawable.header_transition, R.color.back_white);
                    showActionBar();

                } else if (newState == BottomSheetBehaviorGoogleMapsLike.STATE_EXPANDED) {
                    Log.d("bottom sheet", "EXTANDED");
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
//        actionBar.hide();
    }

    private void showActionBar() {
//        actionBar.show();
    }


    private void setUpCallbacks() {
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheetBehavior.setState(BottomSheetBehaviorGoogleMapsLike.STATE_ANCHOR_POINT);
            }
        });
    }

    @TargetApi(16)
    private void setViewBackGround(View view, Drawable drawable) {
        view.setBackground(drawable);
    }

//    public void changeHeaderColorWithTransition(int ms, boolean reverse) {
//        if (reverse) {
//            if (!headerRed) {
//                TransitionDrawable transDraw = (TransitionDrawable) ContextCompat.getDrawable(context, R.drawable.header_transition);
//                setViewBackGround(header, transDraw);
//                transDraw.startTransition(ms);
//                headerRed = true;
//                setHeaderTextColorWhite();
//            }
//        } else {
//            if (headerRed) {
//                TransitionDrawable transDraw = (TransitionDrawable) ContextCompat.getDrawable(context, R.drawable.header_transition_white);
//                setViewBackGround(header, transDraw);
//                transDraw.startTransition(ms);
//                headerRed = false;
//                setHeaderTextColorBlack();
//            }
//        }
//    }

    public void headerColorTransition(int ms, int targetBackgroundColor, int targetTextColor) {
        TransitionDrawable transDraw = (TransitionDrawable) ContextCompat.getDrawable(context, targetBackgroundColor);
        setViewBackGround(header, transDraw);
        transDraw.startTransition(ms);
        setHeaderTextColor(targetTextColor);
    }

    public void setHeaderTextColor(int targetTextColor) {
        stationFullDistance.setTextColor(ContextCompat.getColor(context, targetTextColor));
        stationFullName.setTextColor(ContextCompat.getColor(context, targetTextColor));
        stationFullFreeBikes.setTextColor(ContextCompat.getColor(context, targetTextColor));
    }
}
