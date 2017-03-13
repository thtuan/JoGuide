package com.boot.accommodation.vp.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.vp.location.LocationListFragment;
import com.boot.accommodation.vp.trip.TripFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Favourite user
 *
 * @author tuanlt
 * @since 5:10 PM 12/12/16
 */
public class UserFavouriteActivity extends BaseActivity {

    @Bind(R.id.tvTitle)
    TextView tvTitle;
    public static final int TOUR_FAVOURITE = 1;
    public static final int LOCATION_FAVOURITE = 2;
    private int typeFavourite = LOCATION_FAVOURITE;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        enableSlidingMenu(false);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * Init view
     */
    private void initView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            typeFavourite = bundle.getInt(Constants.IntentExtra.TYPE_FAVOURITE);
        }
        if (LOCATION_FAVOURITE == typeFavourite) {
            tvTitle.setText(StringUtil.getString(R.string.text_favourite_accommodation));
            switchFragment(LocationListFragment.newInstance(bundle), R.id.frDetail, true);
        } else {
            tvTitle.setText(StringUtil.getString(R.string.text_favourite_tour));
            switchFragment(TripFragment.newInstance(bundle), R.id.frDetail, true);
        }
    }

    @OnClick({R.id.ivBack})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
        }
    }
}
