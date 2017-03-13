package com.boot.accommodation.vp.location;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseFragment;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.LocationFilterItemDTO;
import com.boot.accommodation.dto.view.PlaceItemDTO;
import com.boot.accommodation.listener.OnEventControl;
import com.boot.accommodation.vp.category.CreateLocationActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Mo ta class
 *
 * @author Vuong-bv
 * @since: 6/10/2016
 */
public class LocationSearchFragment extends BaseFragment implements LocationSearchViewMgr {

    public static final int ACTION_VIEW_PLACE_DETAIL = 1000;//  action for even click

    LocationFavouriteResultAdapter favouriteAdapter;
    LocationNearbyResultAdapter nearByAdapter;
    LocationSearchPresenterMgr placeSearchPresenterMgr;
    @Bind(R.id.rvFavouriteLocation)
    RecyclerView rvFavouriteLocation;
    @Bind(R.id.rvNearbyLocation)
    RecyclerView rvNearbyLocation;
    @Bind(R.id.llCreateLocation)
    LinearLayout llCreateLocation;
    private String keyWord;
    private PlaceItemDTO placeItemDTO;
    private LocationFilterItemDTO locationFilterItemDTO = new LocationFilterItemDTO(); //Location filter
    private List<PlaceItemDTO> nearBy; //List nearby

    public static LocationSearchFragment newInstance(Bundle data) {
        LocationSearchFragment f = new LocationSearchFragment();
        f.setArguments(data);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            keyWord = args.getString(Constants.IntentExtra.KEYWORD);
            locationFilterItemDTO = (LocationFilterItemDTO)args.getSerializable(Constants.IntentExtra.LOCATION_FILTER);
        }
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_search_location;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }


    /**
     * Khoi tao view
     */
    private void initView() {
        rvFavouriteLocation.setHasFixedSize(true);
        rvNearbyLocation.setLayoutManager(new LinearLayoutManager(mActivity));
        rvNearbyLocation.setHasFixedSize(true);
    }

    /**
     * Khoi tao data
     */
    private void initData() {
        placeSearchPresenterMgr = new LocationSearchPresenter(this);
//        if(!StringUtil.isNullOrEmpty(keyWord)){
            showProgress();
            getLocationSearch(keyWord, false);
//        }
    }

    @Override
    public void renderLayout(List<PlaceItemDTO> favourite, List<PlaceItemDTO> nearBy) {
        closeProgress();
        this.nearBy = nearBy;
        //set adapter for favourite result
        if (favouriteAdapter == null) {
            favouriteAdapter = new LocationFavouriteResultAdapter(favourite);
            rvFavouriteLocation.setAdapter(favouriteAdapter);
            favouriteAdapter.setEnableLoadMore(true);
            favouriteAdapter.setHorizotalList(true);
            favouriteAdapter.setListener(new OnEventControl() {
                @Override
                public void onEventControl( int action, Object item, View View, int position ) {
                    gotoPlace((PlaceItemDTO) item);
                }

                @Override
                public void onLoadMore( int position ) {
                    placeSearchPresenterMgr.getMoreFavouriteLocation(favouriteAdapter,keyWord, JoCoApplication
                        .getInstance().getProfile().getMyGPSInfo().getLatitude(), JoCoApplication
                        .getInstance().getProfile().getMyGPSInfo().getLongtitude(), locationFilterItemDTO);
                }
            });
        } else {
            favouriteAdapter.setData(favourite);
        }

        favouriteAdapter.notifyDataSetChanged();
        //set adapter for nearby result
        if (nearByAdapter == null) {
            nearByAdapter = new LocationNearbyResultAdapter(nearBy);
            rvNearbyLocation.setAdapter(nearByAdapter);
            nearByAdapter.setEnableLoadMore(true);
            nearByAdapter.setListener(new OnEventControl() {
                @Override
                public void onEventControl( int action, Object item, View View, int position ) {
                    gotoPlace((PlaceItemDTO) item);
                }

                @Override
                public void onLoadMore( int position ) {
                    placeSearchPresenterMgr.getMoreNearByLocation(nearByAdapter,keyWord, JoCoApplication
                        .getInstance().getProfile().getMyGPSInfo().getLatitude(), JoCoApplication
                        .getInstance().getProfile().getMyGPSInfo().getLongtitude(), locationFilterItemDTO);
                }
            });
        } else {
            nearByAdapter.setData(nearBy);
        }
        nearByAdapter.notifyDataSetChanged();
        stopRefresh();
//        llCreateLocation.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessageErr(int errorCode, String error) {
        stopRefresh();
        closeProgress();
        switch (errorCode){
            default:
                handleError(errorCode,error);
        }
    }

    @OnClick({R.id.llCreateLocation})
    public void onClick(View v) {
        int action = 0;
        switch (v.getId()) {
            case R.id.llCreateLocation:
                mActivity.goNextScreen(CreateLocationActivity.class, new Bundle());
                break;
        }
    }


    /**
     * Go to place
     * @param item
     */
    private void gotoPlace(PlaceItemDTO item){
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.IntentExtra.PLACE_ITEM, item);
        mActivity.goNextScreen(LocationDetailActivity.class, bundle);
    }

    @Override
    public void onEventControl(int action, Object item, View View, int position) {
        switch (action) {
            case ACTION_VIEW_PLACE_DETAIL:
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.IntentExtra.PLACE_ITEM, (PlaceItemDTO)item);
                mActivity.goNextScreen(LocationDetailActivity.class, bundle);
                break;
        }
    }

    /**
     * Get location search
     * @param keyWord
     */
    public void getLocationSearch(String keyWord, boolean isShowRefresh) {
        if (isShowRefresh) {
            showRefresh();
        }
        this.keyWord = keyWord;
        Double lat = JoCoApplication.getInstance().getProfile().getMyGPSInfo().getLatitude();
        Double lng = JoCoApplication.getInstance().getProfile().getMyGPSInfo().getLongtitude();
        placeSearchPresenterMgr.getSearchLocation(keyWord, lat, lng, locationFilterItemDTO);
    }

    @Override
    protected void receiveBroadcast(int action, Bundle extras) {
        switch (action){
            case Constants.ActionEvent.ACTION_UPDATE_FAVOURITE:
                placeItemDTO = extras.getParcelable(Constants.IntentExtra.PLACE_ITEM);
                for (PlaceItemDTO item : nearBy){
                    if (item.getId() == placeItemDTO.getId()){
                        item.setIsFavourite(placeItemDTO.getIsFavourite());
                        break;
                    }
                }
                nearByAdapter.notifyDataSetChanged();
                break;
            case Constants.ActionEvent.NOTIFY_REFRESH:
                getLocationSearch(keyWord, true);
                break;
//            case Constants.ActionEvent.ACTION_UPDATE_LOCATION: {
////                locationFilterItemDTO = (LocationFilterItemDTO) extras.getSerializable
////                        (Constants.IntentExtra.LOCATION_FILTER);
//                Double lat = JoCoApplication.getInstance().getProfile().getMyGPSInfo().getLatitude();
//                Double lng = JoCoApplication.getInstance().getProfile().getMyGPSInfo().getLongtitude();
//                showRefresh();
//                placeSearchPresenterMgr.getSearchLocation(keyWord, lat, lng, locationFilterItemDTO);
//                break;
//            }

        }

    }
}
