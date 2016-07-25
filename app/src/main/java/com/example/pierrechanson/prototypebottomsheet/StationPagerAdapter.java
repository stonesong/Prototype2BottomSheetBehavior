package com.example.pierrechanson.prototypebottomsheet;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by pierrechanson on 22/07/16.
 */
public class StationPagerAdapter extends FragmentStatePagerAdapter {

    public StationPagerAdapter(FragmentManager fm) {
    super(fm);
}

    @Override
    public CharSequence getPageTitle(int position) {
        return position == 0 ? "Vehicles" : "Details";
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            StationDetailFragment stationFragment;
            stationFragment = new StationDetailFragment();
            return stationFragment;
        } else {
            BookingDetailFragment bookingDetailFragment;
            bookingDetailFragment = new BookingDetailFragment();
            return bookingDetailFragment;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

}
