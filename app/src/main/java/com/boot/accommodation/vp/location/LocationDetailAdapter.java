package com.boot.accommodation.vp.location;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.PlaceItemDTO;
import com.boot.accommodation.listener.FragmentListener;

/**
 * Created by xdung on 3/8/2016.
 */
public class LocationDetailAdapter extends FragmentPagerAdapter {

    FragmentListener mFrgListener;
    private static final int NUMBER_PAGES = 3;

    private static final Fragment[] PAGES = new Fragment[NUMBER_PAGES];
    private static final String[] TITLES = new String[NUMBER_PAGES];

    public LocationDetailAdapter(android.support.v4.app.Fragment fragment) {
        super(fragment.getChildFragmentManager());
    }

    public LocationDetailAdapter(FragmentManager fm, PlaceItemDTO item) {
        super(fm);
        this.mFrgListener = mFrgListener;
        Bundle b = new Bundle();
        b.putParcelable(Constants.IntentExtra.PLACE_ITEM, item);
        PAGES[0] = new LocationDetailInfoFragment();
        PAGES[0].setArguments(b);
        PAGES[1] = new LocationDetailPictureFragment();
        PAGES[1].setArguments(b);
        PAGES[2] = new LocationDetailReviewFragment();
        PAGES[2].setArguments(b);
        TITLES[0] = "OVERVIEW";
        TITLES[1] = "PHOTOS";
        TITLES[2] = "REVIEW";
    }

    @Override
    public Fragment getItem(int position) {
        if (position >= 0 && position < NUMBER_PAGES) {
            return PAGES[position];
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUMBER_PAGES;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    public void setOnFragmentChanging(FragmentListener frgListener) {
        this.mFrgListener = frgListener;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (mFrgListener != null) {
            mFrgListener.fragmentChanging(PAGES[position]);
        }
        super.setPrimaryItem(container, position, object);
    }
}
