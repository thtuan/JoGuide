package com.boot.accommodation.vp.category;

import android.os.Bundle;

import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.vp.trip.TripFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Lay ds my tour/trip
 *
 * @author tuanlt
 * @since 10:04 AM 6/14/2016
 */
public class MyTourTripActivity extends BaseActivity {

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        enableSlidingMenu(false);
        setContentView(R.layout.activity_my_tour_trip);
        ButterKnife.bind(this);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.IntentExtra.FROM_MY_TOUR_TRIP, true);
        bundle.putString(Constants.IntentExtra.USER_ID, JoCoApplication.getInstance().getProfile().getUserData().getId());
        bundle.putString(Constants.IntentExtra.SEARCH_TEXT,"");
        switchFragment(TripFragment.newInstance(bundle), R.id.frDetail, false);
}

    @OnClick(R.id.ivBack)
    public void onClick() {
        super.onBackPressed();
    }
}
