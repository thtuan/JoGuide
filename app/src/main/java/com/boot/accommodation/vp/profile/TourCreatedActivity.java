package com.boot.accommodation.vp.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.vp.trip.TripFragment;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Time to go places
 *
 * @author tuanlt
 * @since 10:39 AM 9/22/2016
 */
public class TourCreatedActivity extends BaseActivity {

    @Bind(R.id.tvTitle)
    TextView tvTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_created);
        enableSlidingMenu(false);
        initView();
        initData();
    }

    /**
     * Init view
     */
    private void initView(){
        tvTitle.setText(StringUtil.getString(R.string.text_tour_created));
    }

    /**
     * Init data
     */
    private void initData(){
        Bundle bundle = new Bundle();
        if(getIntent() != null){
            bundle = getIntent().getExtras();
        }
        bundle.putBoolean(Constants.IntentExtra.FROM_COLLECTION, true);
        switchFragment(TripFragment.newInstance(bundle), R.id.frDetail, true);
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
