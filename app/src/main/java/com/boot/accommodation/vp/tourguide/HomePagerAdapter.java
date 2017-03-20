package com.boot.accommodation.vp.tourguide;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.boot.accommodation.vp.tour.DiscussFragment;

/**
 * Created by thtuan on 3/14/17.
 */

public class HomePagerAdapter extends FragmentStatePagerAdapter {
    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = TourguideFragment.newInstance();
                break;
            case 1:
                fragment = DiscussFragment.newInstance(new Bundle());
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title ="";
        switch (position){
            case 0:
                title = "Tour guide";
                break;
            case 1:
                title = "Discussion";
                break;
        }
        return title;
    }
}
