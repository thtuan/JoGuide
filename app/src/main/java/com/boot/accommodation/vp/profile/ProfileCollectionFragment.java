package com.boot.accommodation.vp.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseFragment;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.PlaceCollectionDTO;
import com.boot.accommodation.dto.view.PlaceItemDTO;
import com.boot.accommodation.dto.view.TourCollectionDTO;
import com.boot.accommodation.dto.view.TouristDTO;
import com.boot.accommodation.dto.view.TripItemDTO;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.vp.location.LocationDetailActivity;
import com.boot.accommodation.vp.tour.TourActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Profile fragment
 *
 * @author Vuong-bv
 * @since: 6/4/2016
 */
public class ProfileCollectionFragment extends BaseFragment implements ProfileCollectionViewMgr {

    @Bind(R.id.tvNumFavourite)
    TextView tvNumFavourite; // so luong favourite
    @Bind(R.id.rvFavouriteLocation)
    RecyclerView rvFavouriteLocation; // ds favourite
    @Bind(R.id.tvNumChecked)
    TextView tvNumChecked; // so luong checkin
    @Bind(R.id.ivNextTour)
    ImageView ivNextTour; // so luong checkin
    @Bind(R.id.ivNextFavourite)
    ImageView ivNextFavourite; // so luong checkin
    @Bind(R.id.rvFavouriteTours)
    RecyclerView rvFavouriteTours; // ds check in
    public static final int ACTION_VIEW_PLACE_DETAIL = 1000;//  action for even click
    public static final int ACTION_VIEW_TOUR_DETAIL = 1001;//  action for even click
    @Bind(R.id.tvNumTourCreated)
    TextView tvNumTourCreated;
    @Bind(R.id.ivNextTourCreated)
    ImageView ivNextTourCreated;
    @Bind(R.id.rvTourCreated)
    RecyclerView rvTourCreated;
    @Bind(R.id.tvNumPlaceCreated)
    TextView tvNumPlaceCreated;
    @Bind(R.id.ivNextPlaceCreated)
    ImageView ivNextPlaceCreated;
    @Bind(R.id.rvPlaceCreated)
    RecyclerView rvPlaceCreated;
    private ProfileCollectionPlaceAdapter favouriteAdapter; // adapter favourite
    private ProfileCollectionTourAdapter checkedInAdapter; // checkin adapter
    private ProfileCollectionPresenter profileCollectionPresenter; // presenter
    private TouristDTO touristDTO; // info tourist
    private static final int NUM_GET = 5;// num get favourite/tour
    private ProfileCollectionTourAdapter toursCreatedAdapter; // adapter favourite
    private ProfileCollectionPlaceAdapter placesCreatedAdapter; // adapter place

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            touristDTO = args.getParcelable(Constants.IntentExtra.TOURIST_DTO);
        }
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_profile_collection;
    }

    public static ProfileCollectionFragment newInstance(Bundle data) {
        ProfileCollectionFragment t = new ProfileCollectionFragment();
        t.setArguments(data);
        return t;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    /**
     * Khoi tao control
     */
    private void initView() {
        rvFavouriteLocation.setHasFixedSize(true);
        rvFavouriteTours.setHasFixedSize(true);
    }

    /**
     * Khoi tao data
     */
    private void initData() {
        profileCollectionPresenter = new ProfileCollectionPresenter(this);
        profileCollectionPresenter.getProfileCollection(touristDTO.getId());
    }

    @OnClick({R.id.ivNextFavourite, R.id.ivNextTour, R.id.ivNextTourCreated, R.id.ivNextPlaceCreated})
    public void OnClick(View v) {
        int option = v.getId();
        switch (option) {
            case R.id.ivNextFavourite: {
                Bundle data = new Bundle();
                data.putInt(Constants.IntentExtra.TYPE_FAVOURITE, UserFavouriteActivity.LOCATION_FAVOURITE);
                data.putBoolean(Constants.IntentExtra.FROM_MY_FAVOURITE, true);
                data.putString(Constants.IntentExtra.USER_ID, touristDTO.getId() + "");
                mActivity.goNextScreen(UserFavouriteActivity.class, data);
                break;
            }
            case R.id.ivNextTour: {
                Bundle data = new Bundle();
                data.putInt(Constants.IntentExtra.TYPE_FAVOURITE, UserFavouriteActivity.TOUR_FAVOURITE);
                data.putBoolean(Constants.IntentExtra.FROM_MY_FAVOURITE, true);
                data.putString(Constants.IntentExtra.USER_ID, touristDTO.getId() + "");
                mActivity.goNextScreen(UserFavouriteActivity.class, data);
                break;
            }
            case R.id.ivNextTourCreated: {
                Bundle data = new Bundle();
                data.putString(Constants.IntentExtra.USER_ID, touristDTO.getId() + "");
                mActivity.goNextScreen(TourCreatedActivity.class, data);
                break;
            }
            case R.id.ivNextPlaceCreated: {
                Bundle data = new Bundle();
                data.putString(Constants.IntentExtra.USER_ID, touristDTO.getId() + "");
                mActivity.goNextScreen(PlaceCreatedActivity.class, data);
                break;
            }
        }
    }

    @Override
    public void onEventControl(int action, Object item, View View, int position) {
        switch (action) {
            case ACTION_VIEW_PLACE_DETAIL: {
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.IntentExtra.PLACE_ITEM, (PlaceItemDTO) item);
                mActivity.goNextScreen(LocationDetailActivity.class, bundle);
                break;
            }
            case ACTION_VIEW_TOUR_DETAIL:
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.IntentExtra.TRIP_ITEM, ((TripItemDTO) item));
                mActivity.goNextScreen(TourActivity.class, bundle);
                break;
        }
    }

    @Override
    public void renderFavouriteTours(TourCollectionDTO tourCollectionDTO) {
        closeProgress();
        if (checkedInAdapter == null) {
            checkedInAdapter = new ProfileCollectionTourAdapter(tourCollectionDTO.getTours());
            checkedInAdapter.setListener(this);
            rvFavouriteTours.setAdapter(checkedInAdapter);
        } else {
            checkedInAdapter.setData(tourCollectionDTO.getTours());
        }
        checkedInAdapter.notifyDataSetChanged();
        tvNumChecked.setText(tourCollectionDTO.getTotal() + Constants.STR_SPACE + StringUtil.getString(R.string
                .text_favourite_tour));
        if (tourCollectionDTO.getTotal() > NUM_GET) {
            ivNextTour.setVisibility(View.VISIBLE);
        } else {
            ivNextTour.setVisibility(View.GONE);
        }
        stopRefresh();
    }

    @Override
    public void renderFavouritePlaces(PlaceCollectionDTO favouritePlaces) {
        closeProgress();
        if (favouriteAdapter == null) {
            favouriteAdapter = new ProfileCollectionPlaceAdapter(favouritePlaces.getPlaces());
            favouriteAdapter.setListener(this);
            rvFavouriteLocation.setAdapter(favouriteAdapter);
        } else {
            favouriteAdapter.setData(favouritePlaces.getPlaces());
        }
        favouriteAdapter.notifyDataSetChanged();
        tvNumFavourite.setText(favouritePlaces.getTotal() + Constants.STR_SPACE + StringUtil.getString(R.string
                .text_favourite_accommodation));
        if (favouritePlaces.getTotal() > NUM_GET) {
            ivNextFavourite.setVisibility(View.VISIBLE);
        } else {
            ivNextFavourite.setVisibility(View.GONE);
        }
        stopRefresh();
    }

    @Override
    public void showMessageErr(int errorCode, String error) {
        closeProgress();
        switch (errorCode) {
            default:
                handleError(errorCode, error);
        }
    }

    @Override
    public void renderToursCreated(TourCollectionDTO tourCreated) {
        closeProgress();
        if (toursCreatedAdapter == null) {
            toursCreatedAdapter = new ProfileCollectionTourAdapter(tourCreated.getTours());
            toursCreatedAdapter.setListener(this);
            rvTourCreated.setAdapter(toursCreatedAdapter);
        } else {
            toursCreatedAdapter.setData(tourCreated.getTours());
        }
        toursCreatedAdapter.notifyDataSetChanged();
        tvNumTourCreated.setText(tourCreated.getTotal() + Constants.STR_SPACE + StringUtil.getString(R.string
                .text_tour_created));
        if (tourCreated.getTotal() > NUM_GET) {
            ivNextTourCreated.setVisibility(View.VISIBLE);
        } else {
            ivNextTourCreated.setVisibility(View.GONE);
        }
        stopRefresh();
    }

    @Override
    public void renderPlacesCreated(PlaceCollectionDTO placeCollectionDTO) {
        closeProgress();
        if (placesCreatedAdapter == null) {
            placesCreatedAdapter = new ProfileCollectionPlaceAdapter(placeCollectionDTO.getPlaces());
            placesCreatedAdapter.setListener(this);
            rvPlaceCreated.setAdapter(placesCreatedAdapter);
        } else {
            placesCreatedAdapter.setData(placeCollectionDTO.getPlaces());
        }
        placesCreatedAdapter.notifyDataSetChanged();
        tvNumPlaceCreated.setText(placeCollectionDTO.getTotal() + Constants.STR_SPACE + StringUtil.getString(R.string
                .text_accommodation_created));
        if (placeCollectionDTO.getTotal() > NUM_GET) {
            ivNextPlaceCreated.setVisibility(View.VISIBLE);
        } else {
            ivNextPlaceCreated.setVisibility(View.GONE);
        }
        stopRefresh();
    }

    @Override
    protected void receiveBroadcast(int action, Bundle extras) {
        switch (action) {
            case Constants.ActionEvent.NOTIFY_REFRESH:
                profileCollectionPresenter.getProfileCollection(touristDTO.getId());
                break;
            default:
                super.receiveBroadcast(action, extras);
        }
    }

}
