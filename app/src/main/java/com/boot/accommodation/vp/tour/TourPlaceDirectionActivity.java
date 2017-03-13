package com.boot.accommodation.vp.tour;

import android.os.Bundle;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.PlaceItemDTO;

import butterknife.ButterKnife;

/**
 * Activity duong di
 *
 * @author tuanlt
 * @since 3:11 PM 6/10/2016
 */
public class TourPlaceDirectionActivity extends BaseActivity {

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        enableSlidingMenu(false);
        setContentView(R.layout.activity_tour_direction);
        ButterKnife.bind(this);
        PlaceItemDTO placeItemDTO = getIntent().getParcelableExtra(Constants.IntentExtra.PLACE_ITEM);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.IntentExtra.PLACE_ITEM,placeItemDTO);
        switchFragment(TourPlaceDirectionFragment.newInstance(bundle), R.id.frDetail, false);
    }
}
