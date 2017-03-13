package com.boot.accommodation.vp.category;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.constant.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PickLocationActivity extends BaseActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.iv_post)
    ImageView ivPost;
    @Bind(R.id.ll_header)
    RelativeLayout llHeader;
    @Bind(R.id.frGoogleMap)
    FrameLayout frGoogleMap;
    public static final int ACTION_DONE = 2;
    public static final String KEY_LAT = "lat";
    public static final String KEY_LONG = "lng";
    double lat;
    double lng;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_location);
        enableSlidingMenu(false);
        ButterKnife.bind(this);
        if(getIntent() != null) {
            lat = getIntent().getDoubleExtra(KEY_LAT, Constants.LAT_LNG_NULL);
            lng = getIntent().getDoubleExtra(KEY_LONG, Constants.LAT_LNG_NULL);
        }
        switchFragment(ChooseLocationFragment.newInstance(getIntent().getExtras()), R.id.frGoogleMap, false);
    }

    @OnClick({R.id.iv_back, R.id.iv_post})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_post:
                Bundle bundle = new Bundle();
                bundle.putDouble(KEY_LAT, lat);
                bundle.putDouble(KEY_LONG, lng);
                sendBroadcast(ACTION_DONE , bundle);
                finish();
                break;
        }
    }

}
