package com.boot.accommodation.vp.trip;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseFragment;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.ProfileDTO;
import com.boot.accommodation.dto.view.TouristDTO;
import com.boot.accommodation.dto.view.TripItemDTO;
import com.boot.accommodation.util.PreferenceUtils;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.vp.tour.ProfileActivity;
import com.boot.accommodation.vp.tour.TourActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class TripFragment extends BaseFragment implements TripViewMgr {

    @Bind(R.id.rvTrip)
    RecyclerView rvTrip;
    public static final int ACTION_VIEW_TRIP_INFO = 1001;
    public static final int ACTION_VIEW_OWNER_INFO = 1002;
    public static final int ACTION_FARVORITE_TRIP = 1003;
    public static final int ACTION_LIKE_TRIP = 1004;
    public static final int ACTION_SHARE_TRIP = 1005;
    public static final int ACTION_GET_TOUR_DEFAULT = 1; // lay tour mac dinh cho trang home
    public static final int ACTION_GET_TOUR_FAVOURITE = 2;
    public static final int ACTION_GET_MY_TOUR_TRIP = 3;
    public static final int ACTION_GET_COLLECTION = 4;
    private int typeTour = ACTION_GET_TOUR_DEFAULT; // lay tour mac dinh

    String userId; // user id
    String search; // search text
    TripAdapter tripAdapter;
    TripPresenterMgr tripPresenterMgr;
    private String gcmToken;
    private List<Long> tourPlanIds;

    public static TripFragment newInstance(Bundle bundle) {
        TripFragment t = new TripFragment();
        t.setArguments(bundle);
        return t;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        typeTour = ACTION_GET_TOUR_DEFAULT;
        gcmToken = PreferenceUtils.getString(Constants.Preference.PREFERENCE_GCM_TOKEN, "");
        Bundle args = getArguments();
        if (args != null) {
            boolean isFromMyFavourite = args.getBoolean(Constants.IntentExtra.FROM_MY_FAVOURITE, false);
            boolean isFromMyTourTrip = args.getBoolean(Constants.IntentExtra.FROM_MY_TOUR_TRIP, false);
            boolean isFromCollection = args.getBoolean(Constants.IntentExtra.FROM_COLLECTION, false);
            userId = args.getString(Constants.IntentExtra.USER_ID);
            search = args.getString(Constants.IntentExtra.SEARCH_TEXT);
            if (isFromMyFavourite) {
                typeTour = ACTION_GET_TOUR_FAVOURITE;
            } else if (isFromMyTourTrip) {
                typeTour = ACTION_GET_MY_TOUR_TRIP;
            } else if (isFromCollection) {
                typeTour = ACTION_GET_COLLECTION;
            }
        }
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_trip;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        showProgress();
        initData();
    }

    /**
     * Khoi tao view
     */
    private void initView() {
        rvTrip.setLayoutManager(new LinearLayoutManager(mActivity));
        rvTrip.setHasFixedSize(true);
    }

    /**
     * Khoi tao data
     */
    private void initData() {
        tripPresenterMgr = new TripPresenter(this);
        tripPresenterMgr.getTours(typeTour, userId, search);
    }

    /**
     * control event
     *
     * @param action   type action
     * @param item     item?
     * @param View     view ?
     * @param position position
     */
    @Override
    public void onEventControl(int action, Object item, View View, int position) {
        Bundle bundle = new Bundle();
        switch (action) {
            case ACTION_VIEW_TRIP_INFO:
                if (item instanceof TripItemDTO) {
                    bundle.putParcelable(Constants.IntentExtra.TRIP_ITEM, ((TripItemDTO) item));
                    mActivity.goNextScreen(TourActivity.class, bundle);
                } else {
                    // truong hop co position image
                    mActivity.goNextScreen(TourActivity.class, (Bundle) item);
                }
                break;
            case ACTION_VIEW_OWNER_INFO:
                TouristDTO dto = new TouristDTO();
                bundle.putParcelable(Constants.IntentExtra.TOURIST_DTO, dto.convertToTouristDTO(((TripItemDTO) item).getUserOwner()));
                mActivity.goNextScreen(ProfileActivity.class, bundle);
                break;
            case ACTION_FARVORITE_TRIP:
                TripItemDTO data = (TripItemDTO) item;
                if (((TripItemDTO) item).getTourPlanId() == 0) {
                    tripPresenterMgr.requestFavouriteTour(data, ((TripItemDTO) item).getTourId());
                } else {
                    tripPresenterMgr.requestFavouriteTour(data, ((TripItemDTO) item).getTourPlanId());
                }
                break;
            case ACTION_SHARE_TRIP:
                mActivity.showMessage(StringUtil.getString(R.string.text_share_success) + ((TripItemDTO) item).getName());
                break;
        }
    }

    @Override
    public void onLoadMore(int position) {
        tripPresenterMgr.getMoreTours(tripAdapter);
    }

    @Override
    public void renderLayout(List<TripItemDTO> mListTrips) {
        closeProgress();
        if (tripAdapter == null) {
            tripAdapter = new TripAdapter(mListTrips);
            tripAdapter.setListener(this);
            tripAdapter.setEnableLoadMore(true);
            rvTrip.setAdapter(tripAdapter);
        } else {
            tripAdapter.setData(mListTrips);
        }
        tripAdapter.notifyDataSetChanged();
        stopRefresh();
        startNotifyService(mListTrips);
//        swipeRefreshLayout.setRefreshing(false);
    }

    /**
     * Start notify service
     *
     * @param mListTrips
     */
    private void startNotifyService(List<TripItemDTO> mListTrips) {
        tourPlanIds = new ArrayList<>();
        for (TripItemDTO tripItemDTO : mListTrips) {
            if (tripItemDTO.getIsJoin()) {
                tourPlanIds.add(tripItemDTO.getTourPlanId());
            }
        }
        ProfileDTO profileDTO = JoCoApplication.getInstance().getProfile();
        profileDTO.setTourPlanIds(tourPlanIds);
        JoCoApplication.getInstance().subscribleTopics(PreferenceUtils.getString(Constants.Preference.PREFERENCE_GCM_TOKEN, ""), tourPlanIds);
    }

    @Override
    public void getToursError(int errorCode, String error) {
        closeProgress();
        renderLayout(new ArrayList<TripItemDTO>());
        switch (errorCode) {
            default:
                handleError(errorCode, error);
        }
    }

    @Override
    public void setNotification(int count) {
        if (mActivity.leftMenuFragment != null) {
            mActivity.leftMenuFragment.setCountNotification(count);
        }
    }

    @Override
    protected void receiveBroadcast(int action, Bundle extras) {
        switch (action) {
            case Constants.ActionEvent.NOTIFY_REFRESH:
                tripPresenterMgr.getTours(typeTour, userId, search);
                break;
            case Constants.ActionEvent.ACTION_SIGN_OUT:
                JoCoApplication.getInstance().unSubscribleTopics(PreferenceUtils.getString(Constants.Preference.PREFERENCE_GCM_TOKEN, ""), tourPlanIds);
                break;
        }
    }

}
