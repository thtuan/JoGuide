package com.boot.accommodation.vp.tour;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.GPSInfoDTO;
import com.boot.accommodation.dto.view.PlaceItemDTO;
import com.boot.accommodation.listener.OnEventMapControlListener;
import com.boot.accommodation.map.CustomFragmentMapView;
import com.boot.accommodation.map.ImageMarkerMapView;
import com.boot.accommodation.vp.location.LocationDetailActivity;
import com.boot.accommodation.vp.location.LocationDetailInfoWindow;

import java.util.ArrayList;

/**
 * Fragment ban do
 *
 * @author tuanlt
 * @since 2:46 PM 6/10/2016
 */
public class TourPlaceDirectionFragment extends CustomFragmentMapView implements OnEventMapControlListener {

    PlaceItemDTO placeItemDTO; // dto place
    public static final int ACTION_CLICK_PLACE = 1000; // action click dia diem
    public static final int ACTION_CLICK_PLACE_DETAIL = 1001; // click detail cua mot dia diem
    public static TourPlaceDirectionFragment newInstance(Bundle bundle) {
        TourPlaceDirectionFragment f = new TourPlaceDirectionFragment();
        f.setArguments(bundle);
        return f;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_custom_map;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle!= null){
            placeItemDTO = bundle.getParcelable(Constants.IntentExtra.PLACE_ITEM);
        }
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState ) {
        super.onViewCreated(view, savedInstanceState);
        validateMyLocation();
    }

    @Override
    public void onMapReady() {
        super.onMapReady();
        renderLayout();
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

    /**
     * Di toi man hinh chi ti
     * @param placeItemDTO
     */
    public void gotoPlaceDetail(PlaceItemDTO placeItemDTO) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.IntentExtra.PLACE_ITEM, placeItemDTO);
        ((BaseActivity) mActivity).goNextScreen(LocationDetailActivity.class, bundle);
    }

    /**
     * render layout
     */
    private void renderLayout(){
        GPSInfoDTO myGPSInfo = JoCoApplication.getInstance().getProfile().getMyGPSInfo();
        ArrayList<LatLng> lstLatLng = new ArrayList<>();
        lstLatLng.add(new LatLng(placeItemDTO.getLat(),
            placeItemDTO.getLng()));
        ImageMarkerMapView imageMarkerMapView = new ImageMarkerMapView(mActivity, placeItemDTO);
        Marker marker = addMarker(imageMarkerMapView, placeItemDTO.getLat(), placeItemDTO.getLng());
        if (myGPSInfo.getLatitude() != Constants.LAT_LNG_NULL && myGPSInfo.getLongtitude() != Constants.LAT_LNG_NULL) {
            lstLatLng.add(new LatLng(myGPSInfo.getLatitude(),myGPSInfo.getLongtitude()));
            drawMarkerMyPosition(myGPSInfo.getLatitude(), myGPSInfo.getLongtitude());
            moveToCenterMyPosition();
            // noi duong di giua hai diem
            addDirectionMap(myGPSInfo.getLatitude(),myGPSInfo.getLongtitude(), placeItemDTO.getLat(),
                    placeItemDTO.getLng(), Color.RED);
        } else {
            moveTo(placeItemDTO.getLat(), placeItemDTO.getLng());
        }
        fitBounds(lstLatLng);
        setMarkerOnClickListener(marker, imageMarkerMapView, this, ACTION_CLICK_PLACE, placeItemDTO);
        setOnInfoWindowClickListener(this, ACTION_CLICK_PLACE_DETAIL);
        setInfoWindowAdapter(new LocationDetailInfoWindow(mapHelper,mActivity));
    }
}
