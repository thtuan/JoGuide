package com.boot.accommodation.vp.home;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseFragment;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.TouristDTO;
import com.boot.accommodation.dto.view.UserItemDTO;
import com.boot.accommodation.util.ImageUtil;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.TraceExceptionUtils;
import com.boot.accommodation.vp.accommodation.AccommodationActivity;
import com.boot.accommodation.vp.category.CreateLocationActivity;
import com.boot.accommodation.vp.category.MyFavouriteActivity;
import com.boot.accommodation.vp.category.MyTourTripActivity;
import com.boot.accommodation.vp.category.NewLocationCreateActivity;
import com.boot.accommodation.vp.category.NewTourActivity;
import com.boot.accommodation.vp.category.NotificationActivity;
import com.boot.accommodation.vp.promotion.PromotionDealActivity;
import com.boot.accommodation.vp.tour.FeedbackActivity;
import com.boot.accommodation.vp.tour.ProfileActivity;
import com.boot.accommodation.vp.tour.SearchActivity;
//import com.boot.accommodation.vp.weather.WeatherActivity;

import butterknife.Bind;
import butterknife.OnClick;

public class LeftMenuFragment extends BaseFragment {

    @Bind(R.id.llStatistic)
    LinearLayout llStatistic;
    @Bind(R.id.iv_avatar)
    CircularImageView ivAvatar;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.llInfo)
    LinearLayout llInfo;
    @Bind(R.id.rl_header)
    RelativeLayout rlHeader;
    @Bind(R.id.llTours)
    LinearLayout llTours;
    @Bind(R.id.llFavorite)
    LinearLayout llFavorite;
    @Bind(R.id.llNotification)
    LinearLayout llNotification;
    @Bind(R.id.llFeedback)
    LinearLayout llFeedback;
    @Bind(R.id.rlFragmentMap)
    LinearLayout rlBackground;
    @Bind(R.id.tvCountNotification)
    TextView tvCountNotification;
    @Bind(R.id.prLoading)
    ProgressBar prLoading;
    @Bind(R.id.llFeedbackJoCo)
    LinearLayout llFeedbackJoCo;
    @Bind(R.id.llPromotion)
    LinearLayout llPromotion;

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_left_menu;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        renderLayout();
    }

    /**
     * Khoi tao view
     */
    private void initView() {
        //check admin or user login
        if (JoCoApplication.getInstance().getProfile().getUserData().getUserType() == UserItemDTO.TYPE_ADMIN) {
            llFeedback.setVisibility(View.VISIBLE);
            llStatistic.setVisibility(View.VISIBLE);
        } else {
            llFeedbackJoCo.setVisibility(View.VISIBLE);
        }
        enableRefresh(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        renderLayout();
    }

    /**
     * Render layout
     */
    private void renderLayout() {
        UserItemDTO userItemDTO = JoCoApplication.getInstance().getProfile().getUserData();
        tvName.setText(userItemDTO.getFullName());
        if (userItemDTO.getCountNotification() != 0) {
            tvCountNotification.setText(String.valueOf(userItemDTO.getCountNotification()));
            tvCountNotification.setVisibility(View.VISIBLE);
        }
        initInfoUser(userItemDTO);
    }

    /**
     * Show avatar
     *
     * @param userItemDTO
     */
    private void initInfoUser(UserItemDTO userItemDTO) {
        ImageUtil.loadImage(ivAvatar, ImageUtil.getImageUrlThumb(userItemDTO.getAvatar()), prLoading);
        if (!StringUtil.isNullOrEmpty(userItemDTO.getFullName())) {
            tvName.setText(userItemDTO.getFullName());
        }
    }


    @OnClick({R.id.iv_avatar, R.id.rl_header, R.id.llTours, R.id.llFavorite, R.id.llNotification, R.id
            .llStatistic, R.id.llFeedback, R.id.llCreateLocation, R.id.llRating, R.id.llWeather, R.id.llFeedbackJoCo, R.id
            .llPromotion, R.id.llSearch, R.id.llNewLocation, R.id.llNewTour, R.id.llLogout})
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        mActivity.getSlidingMenu().toggle();
        switch (view.getId()) {
            case R.id.llTours:
                mActivity.goNextScreen(MyTourTripActivity.class, bundle);
                break;
            case R.id.llFavorite:
                if (StringUtil.isNullOrEmpty(JoCoApplication.getInstance().getProfile().getUserData().getId())) {
                    mActivity.showLogin();
                } else {
                    mActivity.goNextScreen(MyFavouriteActivity.class, bundle);
                }
                break;
            case R.id.llNotification:
                tvCountNotification.setVisibility(View.GONE);
                mActivity.goNextScreen(NotificationActivity.class);
                break;
//            case R.id.llSetting:
//                mActivity.goNextScreen(SettingActivity.class);
//                break;
            case R.id.llStatistic:
                mActivity.goNextScreen(StatisticsActivity.class, bundle);
                break;
//            case R.id.llMyProfile:
//                UserItemDTO userItemDTO = JoCoApplication.getInstance().getProfile().getUserData();
//                TouristDTO dto = new TouristDTO();
//                bundle.putParcelable(Constants.IntentExtra.TOURIST_DTO, dto.convertToTouristDTO(userItemDTO));
//                mActivity.goNextScreen(ProfileActivity.class, bundle);
//                break;
            case R.id.llFeedback:
                mActivity.goNextScreen(FeedbackActivity.class, bundle);
                break;
            case R.id.rl_header:
            case R.id.iv_avatar:
                if (StringUtil.isNullOrEmpty(JoCoApplication.getInstance().getProfile().getUserData().getId())) {
                    mActivity.showLogin();
                } else {
                    UserItemDTO userItemDTO1 = JoCoApplication.getInstance().getProfile().getUserData();
                    TouristDTO dto1 = new TouristDTO();
                    bundle.putParcelable(Constants.IntentExtra.TOURIST_DTO, dto1.convertToTouristDTO(userItemDTO1));
                    mActivity.goNextScreen(ProfileActivity.class, bundle);
                }
                break;
            case R.id.llCreateLocation:
                UserItemDTO user = JoCoApplication.getInstance().getProfile().getUserData();
                bundle.putParcelable(Constants.IntentExtra.USER_DATA, user);
                mActivity.goNextScreen(CreateLocationActivity.class, bundle);
                break;
//            case R.id.llTimeToGo:
//                mActivity.goNextScreen(TimeToGoActivity.class, bundle);
//                break;
            case R.id.llRating:
                launchMarket();
                break;
            case R.id.llWeather:
//                mActivity.goNextScreen(WeatherActivity.class, bundle);
                break;
            case R.id.llFeedbackJoCo:
                if(mActivity instanceof AccommodationActivity) {
                    ((AccommodationActivity)mActivity).openPopUpFeedBack();
                }
                break;
            case R.id.llPromotion:
                mActivity.goNextScreen(PromotionDealActivity.class, bundle);
                break;
            case R.id.llSearch:
                mActivity.goNextScreen(SearchActivity.class, bundle);
                break;
            case R.id.llNewLocation:
                mActivity.goNextScreen(NewLocationCreateActivity.class, bundle);
                break;
            case R.id.llNewTour:
                mActivity.goNextScreen(NewTourActivity.class, bundle);
                break;
            case R.id.llLogout:
                logout();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            switch (requestCode) {
                default:
                    super.onActivityResult(requestCode, resultCode, data);
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public void updatePhoto(String url) {
        ImageUtil.loadImage(ivAvatar, url);
    }

    public void setCountNotification(int count) {
        UserItemDTO userItemDTO = JoCoApplication.getInstance().getProfile().getUserData();
        userItemDTO.setCountNotification(count);
        JoCoApplication.getInstance().getProfile().setUserData(userItemDTO);
        if (userItemDTO != null && userItemDTO.getCountNotification() != 0) {
            tvCountNotification.setText(String.valueOf(userItemDTO.getCountNotification()));
            tvCountNotification.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void receiveBroadcast(int action, Bundle extras) {
        switch (action) {
            case Constants.ActionEvent.ACTION_CHANGE_PROFILE:
                initInfoUser(JoCoApplication.getInstance().getProfile().getUserData());
                break;
        }
    }

    /**
     * Launch market to rating
     */
    private void launchMarket() {
        Uri uri = Uri.parse("market://details?id=" + JoCoApplication.getInstance().getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            mActivity.startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Log.e(Constants.LogName.EXCEPTION, TraceExceptionUtils.getReportFromThrowable(e));
        }
    }

    /**
     * Xu li logout
     */
    private void logout() {
        mActivity.showAlert(StringUtil.getString(R.string.text_confirm_logout), null, new DialogInterface
                .OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mActivity.signOutApp();
                //Update info then logout
                renderLayout();
            }
        }, getString(R.string.text_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }, getString(R.string.text_cancel), true);
    }

}
