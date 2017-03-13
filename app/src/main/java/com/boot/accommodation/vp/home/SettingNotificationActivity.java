package com.boot.accommodation.vp.home;

import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.SettingViewDTO;
import com.boot.accommodation.util.PreferenceUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * setting activity
 *
 * @author Vuong-bv
 * @since: 9/8/2016
 */
public class SettingNotificationActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.scNotifyreview)
    SwitchCompat scNotifyreview;
    @Bind(R.id.scNotifyTour)
    SwitchCompat scNotifyTour;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_notification);
        ButterKnife.bind(this);
        initView();
        scNotifyreview.setOnCheckedChangeListener(this);
        scNotifyTour.setOnCheckedChangeListener(this);
    }

    /**
     * check setting of user and show that
     */
    private void initView() {
        if (SettingViewDTO.TURN_ON_NOTIFY_TOUR == JoCoApplication.getInstance().getSettingNotifyTour() ) {
            scNotifyTour.setChecked(true);
        } else {
            scNotifyTour.setChecked(false);
        }
        if (SettingViewDTO.TURN_ON_NOTIFY_REVIEW == JoCoApplication.getInstance().getSettingNotifyReview() ) {
            scNotifyreview.setChecked(true);
        } else {
            scNotifyreview.setChecked(false);
        }
    }


    @OnClick({R.id.ivBack})
    public void onClick(View v) {
        int action = 0;
        switch (v.getId()) {
            case R.id.ivBack:
                super.onBackPressed();
                break;
        }
    }


    /**
     * check turn on or off notify
     *
     * @param buttonView
     * @param isChecked
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.scNotifyTour:
                if (!isChecked) {
                    JoCoApplication.getInstance().setSettingNotifyTour(SettingViewDTO.TURN_OFF_NOTIFY_TOUR);
                    JoCoApplication.getInstance().unSubscribleTopics(PreferenceUtils.getString(Constants.Preference.PREFERENCE_GCM_TOKEN,""), JoCoApplication.getInstance().getProfile().getTourPlanIds());
                } else {
                    JoCoApplication.getInstance().setSettingNotifyTour(SettingViewDTO.TURN_ON_NOTIFY_TOUR);
                    JoCoApplication.getInstance().subscribleTopics(PreferenceUtils.getString(Constants.Preference.PREFERENCE_GCM_TOKEN,""), JoCoApplication.getInstance().getProfile().getTourPlanIds());
                }
                sendBroadcast(Constants.ActionEvent.ACTION_TURN_ON_TOUR_NOTIFY, new Bundle());
                break;
            case R.id.scNotifyreview:
                if (!isChecked) {
                    JoCoApplication.getInstance().setSettingNotifyReview(SettingViewDTO.TURN_OFF_NOTIFY_REVIEW);
                    sendBroadcast(Constants.ActionEvent.ACTION_TURN_ON_REVIEW_NOTIFY, new Bundle());
                } else {
                    JoCoApplication.getInstance().setSettingNotifyReview(SettingViewDTO.TURN_ON_NOTIFY_REVIEW);
                    sendBroadcast(Constants.ActionEvent.ACTION_TURN_ON_REVIEW_NOTIFY, new Bundle());
                }
                break;
        }
    }

}
