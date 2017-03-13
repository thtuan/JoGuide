package com.boot.accommodation.vp.tour;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.constant.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Photo of tour/ tour itinerary
 *
 * @author tuanlt
 * @since 5:22 CH 17/08/2016
 */
public class TourPictureActivity extends BaseActivity {

    @Bind(R.id.frDetail)
    FrameLayout frDetail;
    @Bind(R.id.tvTitlePicture)
    TextView tvTitlePicture;

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        enableSlidingMenu(false);
        setContentView(R.layout.activity_tour_picture);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * Init view
     */
    private void initView() {
        if (getIntent() != null) {
            String title = getIntent().getStringExtra(Constants.IntentExtra.TITLE);
            tvTitlePicture.setText(title);
        }
        switchFragment(TourPictureFragment.newInstance(getIntent().getExtras()), R.id.frDetail, false);
    }

    @OnClick({R.id.ivBack})
    public void onClick( View view ) {
        switch (view.getId()) {
            case R.id.ivBack:
                super.onBackPressed();
                break;
        }
    }

}
