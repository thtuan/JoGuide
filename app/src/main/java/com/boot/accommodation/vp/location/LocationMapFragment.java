package com.boot.accommodation.vp.location;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.boot.accommodation.R;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.AppVersionDTO;
import com.boot.accommodation.dto.view.PlaceItemDTO;
import com.boot.accommodation.listener.OnEventMapControlListener;
import com.boot.accommodation.map.CustomFragmentMapView;
import com.boot.accommodation.map.ImageMarkerMapView;

import java.util.ArrayList;

/**
 * Hien thi cac dia diem tren ban do
 *
 * @author tuanlt
 * @since 11:12 AM 5/24/2016
 */
public class LocationMapFragment extends CustomFragmentMapView implements OnEventMapControlListener, LocationListViewMgr {

    public static final int ACTION_CLICK_PLACE = 1000; // action click dia diem
    public static final int ACTION_CLICK_PLACE_DETAIL = 1001; // action click chi tiet dia diem
    private ArrayList<PlaceItemDTO> lstPlaces = new ArrayList<>(); //ds dia diem
    ArrayList<LatLng> lstLatLngFitBounds = new ArrayList<>(); // mang fit bound

    public static LocationMapFragment newInstance(Bundle bundle ) {
        LocationMapFragment f = new LocationMapFragment();
        f.setArguments(bundle);
        return f;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_custom_map;
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
//        mActivity.showProgress();
        if (bundle != null) {
            lstPlaces = bundle.getParcelableArrayList(Constants.IntentExtra.INTENT_DATA);
        }
        mActivity.reStartLocating();
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        return v;
    }

    @Override
    public void onMapReady() {
        super.onMapReady();
        renderLayout();
        mapHelper.paddingControl(0,0,8,158);
    }

    @Override
    public void onMapLoaded() {
        super.onMapLoaded();
        fitBounds(lstLatLngFitBounds);
    }

    /**
     * render map
     */
    private void renderLayout() {
        drawPlacesInMap();
    }

    /**
     * Ve tren ban do
     */
    private void drawPlacesInMap() {
        clearAllMarkerOnMap();
        for (int i = 0, size = lstPlaces.size(); i < size; i++) {
            PlaceItemDTO item = lstPlaces.get(i);
            ImageMarkerMapView imageMarkerMapView = new ImageMarkerMapView(mActivity, lstPlaces.get(i));
            Marker marker = addMarker(imageMarkerMapView, item.getLat(), item.getLng());
            setMarkerOnClickListener(marker, imageMarkerMapView, this, ACTION_CLICK_PLACE, item);
            if (item.getLat() != Constants.LAT_LNG_NULL && item.getLng() != Constants.LAT_LNG_NULL) {
                lstLatLngFitBounds.add(new LatLng(item.getLat(), item.getLng()));
            }
        }
        setOnInfoWindowClickListener(this, ACTION_CLICK_PLACE_DETAIL);
        setInfoWindowAdapter(new LocationDetailInfoWindow(mapHelper, mActivity));
    }

    @Override
    public View onEventMap( int eventType, View control, Object data ) {
        switch (eventType) {
            case ACTION_CLICK_PLACE:
                break;
            case ACTION_CLICK_PLACE_DETAIL:
                gotoPlaceDetail((PlaceItemDTO) data);
                break;
        }
        return null;
    }

    @Override
    public void renderLayout( ArrayList<PlaceItemDTO> lstPlaces ) {

    }

    public void gotoPlaceDetail( PlaceItemDTO placeItemDTO ) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.IntentExtra.PLACE_ITEM, placeItemDTO);
        (mActivity).goNextScreen(LocationDetailActivity.class, bundle);
    }

    @Override
    public void showMessageErr( int errorCode, String error ) {
        stopRefresh();
        closeProgress();
        switch (errorCode) {
            default:
                handleError(errorCode, error);
        }
    }

    @Override
    protected void receiveBroadcast(int action, Bundle extras) {
        switch (action) {
            case Constants.ActionEvent.ACTION_UPDATE_LOCATION: {
                renderLayout();
                break;
            }
        }
    }

    @Override
    public void checkAppVersion(AppVersionDTO appVersionDTO) {

    }

}
