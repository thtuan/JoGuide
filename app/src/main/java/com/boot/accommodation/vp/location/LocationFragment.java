package com.boot.accommodation.vp.location;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;

import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseFragment;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.PlaceItemDTO;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.util.Utils;
import com.boot.accommodation.vp.position.PositionManager;

import java.util.ArrayList;

import butterknife.Bind;

public class LocationFragment extends BaseFragment {

//    @Bind(R.id.ftSwitchListMap)
//    FloatingActionButton ftSwitchListMap;
    @Bind(R.id.rlFragmentMap)
    RelativeLayout rlFragmentMap;
    private boolean mIsListMode; // kiem tra o che do dang list hay ko
    private ArrayList<PlaceItemDTO> lstPlaces = new ArrayList<>(); //ds dia diem
    public static final int ACTION_SWITCH_MAP_PLACE = 1000;
//    boolean isFromMyFavourite = false; // phai den tu man hinh favourite hay ko
//    private String userId;
//    private LocationFilterItemDTO locationFilterItemDTO; //Location filter
//    private boolean isFromTimeToGo = false; //Time to go
//    Double lat = Constants.LAT_LNG_NULL, lng = Constants.LAT_LNG_NULL; //Lat, lng

    public static LocationFragment newInstance(Bundle bundle) {
        LocationFragment t = new LocationFragment();
        t.setArguments(bundle);
        return t;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_map;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Bundle args = getArguments();
//        if (args != null) {
////            touristId = args.getString(Constants.IntentExtra.TOURIST_ID);
////            isFromMyFavourite = args.getBoolean(Constants.IntentExtra.FROM_MY_FAVOURITE, false);
////            isFromTimeToGo = args.getBoolean(Constants.IntentExtra.FROM_TIME_TO_GO, false);
////            userId = args.getString(Constants.IntentExtra.USER_ID, JoCoApplication.getInstance()
////                .getProfile().getUserData().getId());
////            locationFilterItemDTO = (LocationFilterItemDTO) args.getSerializable(Constants.IntentExtra.LOCATION_FILTER);
////            if(args.containsKey(Constants.IntentExtra.LAT) && args.containsKey(Constants.IntentExtra.LNG)) {
////                lat = args.getDouble(Constants.IntentExtra.LAT);
////                lng = args.getDouble(Constants.IntentExtra.LNG);
////            }
//        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        Bundle bundle = putDataPlaces();
        Bundle bundle = putDataPlaces();
        mActivity.switchFragment(LocationListFragment.newInstance(bundle), R.id.frPlaces, false);
        mIsListMode = true;
        enableRefresh(false);
        validateMyLocation();
    }

    /**
     * Hien thi o che do ban do hay dang list
     *
     * @return
     */
    public boolean switchModeView() {
        mIsListMode = !mIsListMode;
        Bundle bundle = putDataPlaces();
        if (!mIsListMode) {
            LocationListFragment placeListFragment = (LocationListFragment) mActivity.getSupportFragmentManager().findFragmentByTag
                    (Utils.getTag(LocationListFragment.class));
            if (placeListFragment != null) {
                lstPlaces = placeListFragment.getDataPlaces();
                bundle.putParcelableArrayList(Constants.IntentExtra.INTENT_DATA, lstPlaces);
            }
            mActivity.switchFragment(LocationMapFragment.newInstance(bundle), R.id.frPlaces, true);
//            ftSwitchListMap.setImageResource(R.drawable.ic_list_white);
        } else {
            mActivity.switchFragment(LocationListFragment.newInstance(bundle), R.id.frPlaces, true);
//            ftSwitchListMap.setImageResource(R.drawable.ic_map_white);
        }
//        frPlaces.setVisibility(mIsListMode ? View.GONE : View.VISIBLE);
        return mIsListMode;
    }

    @NonNull
    private Bundle putDataPlaces() {
        Bundle bundle = getArguments();
        bundle.putSerializable(Constants.IntentExtra.INTENT_DATA, lstPlaces);
//        bundle.putBoolean(Constants.IntentExtra.FROM_MY_FAVOURITE, isFromMyFavourite);
//        bundle.putBoolean(Constants.IntentExtra.FROM_TIME_TO_GO, isFromTimeToGo);
//        bundle.putString(Constants.IntentExtra.USER_ID, userId);
//        bundle.putString(Constants.IntentExtra.SEARCH_TEXT, "");
//        bundle.putSerializable(Constants.IntentExtra.LOCATION_FILTER, locationFilterItemDTO);
//        if(lat != Constants.LAT_LNG_NULL && lng != Constants.LAT_LNG_NULL) {
//            bundle.putDouble(Constants.IntentExtra.LAT, lat);
//            bundle.putDouble(Constants.IntentExtra.LNG, lng);
//        }
        return bundle;
    }

//    @OnClick({R.id.ftSwitchListMap})
//    public void onClick(View view) {
//        int id = view.getId();
//        switch (id) {
//            case R.id.ftSwitchListMap:
//                switchModeView();
//                break;
//        }
//    }

    @Override
    protected void receiveBroadcast(int action, Bundle extras) {
        switch (action) {
            case ACTION_SWITCH_MAP_PLACE:
                switchModeView();
                break;
        }
    }

    /**
     * Validate my position
     */
    protected void validateMyLocation() {
        if (!PositionManager.getInstance().isEnableGPS()) {
            PositionManager.getInstance().showAlertEnableGPS(rlFragmentMap, false, StringUtil.getString(R.string
                .notify_setting_gps));
        } else if (JoCoApplication.getInstance().getProfile().getMyGPSInfo().getLatitude() == Constants.LAT_LNG_NULL
            || JoCoApplication.getInstance().getProfile().getMyGPSInfo().getLongtitude() == Constants.LAT_LNG_NULL) {
            PositionManager.getInstance().showAlertEnableGPS(rlFragmentMap, true, StringUtil.getString(R.string
                .text_not_found_position));
        }
    }

}
