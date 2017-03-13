package com.boot.accommodation.vp.category;

import android.os.Bundle;
import android.widget.TextView;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.vp.trip.TripFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * New location create
 *
 * @author tuanlt
 * @since 10:39 AM 9/22/2016
 */
public class NewTourActivity extends BaseActivity {

    @Bind(R.id.tvTitle)
    TextView tvTitle;

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
        tvTitle.setText(StringUtil.getString(R.string.text_new_tour));
        switchFragment(TripFragment.newInstance(new Bundle()), R.id.frDetail, true);
    }

    @OnClick(R.id.ivBack)
    public void onClick() {
        onBackPressed();
    }
}
