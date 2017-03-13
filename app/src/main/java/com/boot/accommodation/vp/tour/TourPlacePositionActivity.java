package com.boot.accommodation.vp.tour;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.TourItineraryItemDTO;
import com.boot.accommodation.vp.location.TourLocationPositionFragment;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Vi tri cac diem trong tour
 *
 * @author tuanlt
 * @since 5:26 PM 6/10/2016
 */
public class TourPlacePositionActivity extends BaseActivity {

    private ArrayList<TourItineraryItemDTO> tourItinerary = new ArrayList<>(); //ds dia diem
    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        enableSlidingMenu(false);
        setContentView(R.layout.activity_tour_place_position);
        ButterKnife.bind(this);
        if(getIntent() != null) {
            tourItinerary = getIntent().getParcelableArrayListExtra(Constants.IntentExtra.INTENT_DATA);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.IntentExtra.INTENT_DATA,tourItinerary);
        switchFragment(TourLocationPositionFragment.newInstance(bundle), R.id.frDetail, false);
    }

    @Override
    public View onCreateView( String name, Context context, AttributeSet attrs ) {

        return super.onCreateView(name, context, attrs);
    }
}
