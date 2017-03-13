package com.boot.accommodation.vp.category;

import android.os.Bundle;
import android.widget.TextView;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.LocationFilterItemDTO;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.vp.location.LocationListFragment;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * New location create
 *
 * @author tuanlt
 * @since 10:39 AM 9/22/2016
 */
public class NewLocationCreateActivity extends BaseActivity {

    @Bind(R.id.tvTitle)
    TextView tvTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        enableSlidingMenu(false);
        initView();
    }

    /**
     * Init view
     */
    private void initView() {
        tvTitle.setText(StringUtil.getString(R.string.text_new_location_create));
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.IntentExtra.NEW_CREATE_LOCATION, true);
        bundle.putSerializable(Constants.IntentExtra.LOCATION_FILTER, new LocationFilterItemDTO());
        switchFragment(LocationListFragment.newInstance(bundle), R.id.frDetail, true);
    }

    @OnClick(R.id.ivBack)
    public void onClick() {
        onBackPressed();
    }
}
