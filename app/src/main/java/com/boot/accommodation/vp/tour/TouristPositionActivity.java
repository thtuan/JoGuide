package com.boot.accommodation.vp.tour;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.TouristDTO;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Activity vi tri cua tourist
 *
 * @author tuanlt
 * @since 10:43 AM 6/11/2016
 */
public class TouristPositionActivity extends BaseActivity {

    ArrayList<TouristDTO> lstTouristInfo = new ArrayList<>(); // ds nhan vien
    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        enableSlidingMenu(false);
        setContentView(R.layout.activity_tourist_position);
        ButterKnife.bind(this);
        if(getIntent() != null) {
            lstTouristInfo = getIntent().getParcelableArrayListExtra(Constants.IntentExtra.INTENT_DATA);
        }
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.IntentExtra.INTENT_DATA,lstTouristInfo);
        switchFragment(TouristPositionFragment.newInstance(bundle), R.id.frDetail, false);

    }

    @Override
    public View onCreateView( String name, Context context, AttributeSet attrs ) {
        return super.onCreateView(name, context, attrs);
    }
}
