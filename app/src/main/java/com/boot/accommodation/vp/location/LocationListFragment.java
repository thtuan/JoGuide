package com.boot.accommodation.vp.location;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseFragment;
import com.boot.accommodation.common.info.ServerPath;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.AppVersionDTO;
import com.boot.accommodation.dto.view.LocationDTO;
import com.boot.accommodation.dto.view.LocationFilterItemDTO;
import com.boot.accommodation.dto.view.PlaceItemDTO;
import com.boot.accommodation.dto.view.TimeToGoFilterDTO;
import com.boot.accommodation.dto.view.SortTypeDTO;
import com.boot.accommodation.util.ImageUtil;
import com.boot.accommodation.vp.home.HomeActivity;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Ds dia diem
 *
 * @author tuanlt
 * @since 11:18 AM 5/24/2016
 */
public class LocationListFragment extends BaseFragment implements LocationListViewMgr {

    public static final int ACTION_CHECK_IN_PLACE = 101;
    public static final int ACTION_FARVORITE_PLACE = 102;
    public static final int ACTION_LIKE_PLACE = 103;
    public static final int ACTION_SHARE_PLACE = 104;
    public static final int ACTION_VIEW_DETAIL = 105;
    @Bind(R.id.lv_mode_place)
    RecyclerView lvModePlace;
    LocationListPresenterMgr placeListPresenterMgr;
    private LocationAdapter mPlaceAdapter ; // adapter
    private ArrayList<PlaceItemDTO> lstPlaces = new ArrayList<>();
    public static final int TYPE_PLACE_NORMAL = 0; //Type place normal
    public static final int TYPE_PLACE_FAVOURITE = 1; //Type place favourite
    public static final int TYPE_PLACE_TIME_TO_GO = 2; //Type place time to go
    public static final int TYPE_PLACE_CREATED = 3; //Type place time to go
    private int typePlace = TYPE_PLACE_NORMAL;
    private LocationFilterItemDTO locationFilterItemDTO; //Location filter
    private Double lat = Constants.LAT_LNG_NULL; //Lat
    private Double lng = Constants.LAT_LNG_NULL; //Lng
    private String userId; //UserId
    private boolean getPositionPlace = false; // Variable check position from place
    private TimeToGoFilterDTO timeToGoFilterDTO = new TimeToGoFilterDTO(); //Time to go filter

    public static LocationListFragment newInstance(Bundle bundle) {
        LocationListFragment f = new LocationListFragment();
        f.setArguments(bundle);
        return f;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_place_list;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            if(bundle.containsKey(Constants.IntentExtra.INTENT_DATA)) {
                lstPlaces = (ArrayList<PlaceItemDTO>) bundle.getSerializable(Constants.IntentExtra.INTENT_DATA);
            }
            typePlace = bundle.getBoolean(Constants.IntentExtra.FROM_MY_FAVOURITE) == true ? TYPE_PLACE_FAVOURITE :
                    TYPE_PLACE_NORMAL;
            if (TYPE_PLACE_NORMAL == typePlace) {
                typePlace = bundle.getBoolean(Constants.IntentExtra.FROM_TIME_TO_GO) == true ? TYPE_PLACE_TIME_TO_GO :
                        TYPE_PLACE_NORMAL;
            }
            if (TYPE_PLACE_NORMAL == typePlace) {
                typePlace = bundle.containsKey(Constants.IntentExtra.FROM_PLACE_CREATED) == true ? TYPE_PLACE_CREATED :
                        TYPE_PLACE_NORMAL;
            }
            locationFilterItemDTO = (LocationFilterItemDTO) bundle.getSerializable(Constants.IntentExtra.LOCATION_FILTER);
            userId = bundle.getString(Constants.IntentExtra.USER_ID);
            if(bundle.containsKey(Constants.IntentExtra.LAT) && bundle.containsKey(Constants.IntentExtra.LNG)) {
                lat = bundle.getDouble(Constants.IntentExtra.LAT);
                lng = bundle.getDouble(Constants.IntentExtra.LNG);
                getPositionPlace = true;
            } else {
                // If get new, don't care about lat, lng
                if (!bundle.containsKey(Constants.IntentExtra.NEW_CREATE_LOCATION)) {
                    lat = JoCoApplication.getInstance().getProfile().getMyGPSInfo().getLatitude();
                    lng = JoCoApplication.getInstance().getProfile().getMyGPSInfo().getLongtitude();
                } else {
                    getPositionPlace = true;
                }
            }
        }
        mActivity.reStartLocating();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
        showProgress();
    }

    @Override
    public void renderLayout(ArrayList<PlaceItemDTO> lstPlaces) {
//        this.lstPlaces.clear();
        this.lstPlaces = lstPlaces;
        if (mPlaceAdapter == null) {
            mPlaceAdapter = new LocationAdapter(this.lstPlaces);
            mPlaceAdapter.setListener(this);
            mPlaceAdapter.setEnableLoadMore(true);
            lvModePlace.setAdapter(mPlaceAdapter);
        } else {
            mPlaceAdapter.setData(this.lstPlaces);
        }
        mPlaceAdapter.notifyDataSetChanged();
        stopRefresh();
        closeProgress();
        // Using for case create place
        mActivity.closeProgressDialog();
    }

    @Override
    public void gotoPlaceDetail(PlaceItemDTO placeItemDTO) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.IntentExtra.PLACE_ITEM, placeItemDTO);
        (mActivity).goNextScreen(LocationDetailActivity.class, bundle);
    }

    @Override
    public void showMessageErr(int errorCode, String error) {
        stopRefresh();
        closeProgress();
        mActivity.closeProgressDialog();
        switch (errorCode){
            default:
                handleError(errorCode,error);
        }
    }

    @Override
    public void checkAppVersion(AppVersionDTO appVersionDTO) {
        JoCoApplication.getInstance().getProfile().getUserData().setAppVersion(appVersionDTO);
        mActivity.checkAppVersion();
    }

    /**
     * Init data
     */
    private void initData() {
        placeListPresenterMgr = new LocationListPresenter(this);
        placeListPresenterMgr.getPlaces(lat, lng, typePlace, locationFilterItemDTO, timeToGoFilterDTO, userId);
    }

    /**
     * Khoi tao view
     */
    private void initView() {
        lvModePlace.setLayoutManager(new LinearLayoutManager(mActivity));
        lvModePlace.setHasFixedSize(true);
    }

    @Override
    public void onLoadMore(int position) {
        placeListPresenterMgr.getMorePlaces(lat, lng, mPlaceAdapter, typePlace, locationFilterItemDTO);
    }

    @Override
    protected void receiveBroadcast(int action, Bundle extras) {
        switch (action) {
            case Constants.ActionEvent.NOTIFY_REFRESH: {
                mActivity.reStartLocating();
//                this.lstPlaces.clear();
                if(!getPositionPlace){
                    lat = JoCoApplication.getInstance().getProfile().getMyGPSInfo().getLatitude();
                    lng = JoCoApplication.getInstance().getProfile().getMyGPSInfo().getLongtitude();
                }
                updateLocationFilter();
                placeListPresenterMgr.getPlaces(lat, lng, typePlace, locationFilterItemDTO, timeToGoFilterDTO, userId);
                break;
            }
            case Constants.ActionEvent.ACTION_UPDATE_FAVOURITE:
                PlaceItemDTO placeItemDTO = extras.getParcelable(Constants.IntentExtra.PLACE_ITEM);
                for (PlaceItemDTO item : lstPlaces){
                    if (item.getId() == placeItemDTO.getId()){
                        item.setIsFavourite(placeItemDTO.getIsFavourite());
                        break;
                    }
                }
                mPlaceAdapter.notifyDataSetChanged();
                break;
            case Constants.ActionEvent.ACTION_UPDATE_LOCATION: {
                //Neu o trang hien tai moi goi update, search filter thi ko update lai trang home
                if (JoCoApplication.getInstance().getActivityContext() instanceof HomeActivity) {
                    locationFilterItemDTO = (LocationFilterItemDTO) extras.getSerializable
                            (Constants.IntentExtra.LOCATION_FILTER);
                    getPlacesAgain();
                }
                break;
            }
        }
    }

    @Override
    public void onEventControl(int action, Object item, View View, int position) {
        switch (action) {
            case ACTION_VIEW_DETAIL:
                placeListPresenterMgr.gotoPlaceDetail(position);
                break;
            case ACTION_FARVORITE_PLACE:
                placeListPresenterMgr.requestFavouritePlace((PlaceItemDTO)item);
                break;
            case ACTION_SHARE_PLACE:
                PlaceItemDTO dto = (PlaceItemDTO) item;
                ShareLinkContent content = new ShareLinkContent.Builder().setContentTitle(dto.getName()).
                        setContentUrl(Uri.parse(ServerPath.getLocationPath() + dto.getId())).
                        setImageUrl(Uri.parse(ImageUtil.getImageUrl(dto.getPhoto()))).build();
                ShareDialog shareDialog = new ShareDialog(this);
                shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);
                break;
        }
    }

    /**
     * Get data places
     */
    public ArrayList<PlaceItemDTO> getDataPlaces() {
        return lstPlaces;
    }

    /**
     * Get place again
     */
    private void getPlacesAgain(){
        showRefresh();
        placeListPresenterMgr.getPlaces(lat, lng, typePlace, locationFilterItemDTO, timeToGoFilterDTO, userId);
        lvModePlace.scrollToPosition(0);
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (TYPE_PLACE_TIME_TO_GO == typePlace) {
//            mActivity.checkAppVersion();
//        }
    }

    /**
     * Get time to go
     */
    public void getTimeToGo(TimeToGoFilterDTO timeToGoFilterDTO) {
        this.timeToGoFilterDTO = timeToGoFilterDTO;
        updateLocationFilter();
        getPlacesAgain();
    }

    /**
     * Update location filter
     */
    private void updateLocationFilter() {
        if (SortTypeDTO.DISTANCE.getValue() == timeToGoFilterDTO.getSortType()) {
            if (timeToGoFilterDTO.getMapPoint() == null) {
                timeToGoFilterDTO.setMapPoint(new LocationDTO());
            }
            if (Constants.LAT_LNG_NULL != lat) {
                timeToGoFilterDTO.getMapPoint().setLat(lat);
                timeToGoFilterDTO.getMapPoint().setLng(lng);
            } else {
                timeToGoFilterDTO.getMapPoint().setLat(null);
                timeToGoFilterDTO.getMapPoint().setLng(null);
            }
        }
    }

    /**
     * Get location for rest
     */
    public void getLocationForRest(Double lat, Double lng) {
        this.getPlacesAgain();
    }

}
