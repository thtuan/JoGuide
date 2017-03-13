package com.boot.accommodation.vp.location;

import android.graphics.Point;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.PlaceItemDTO;
import com.boot.accommodation.map.CustomFragmentMapView;
import com.boot.accommodation.map.ImageMarkerMapView;

/**
 * Place item
 */
public class LocationDetailReportFragment extends CustomFragmentMapView {

    private PlaceItemDTO placeItemDTO; //Place item
    private double lat, lng; //Lat, lng

    public static LocationDetailReportFragment newInstance(Bundle data) {
        LocationDetailReportFragment fragment = new LocationDetailReportFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            placeItemDTO = args.getParcelable(Constants.IntentExtra.PLACE_ITEM);
            lat = placeItemDTO.getLat();
            lng = placeItemDTO.getLng();
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onMapReady() {
        super.onMapReady();
        renderLayout();
        moveTo(placeItemDTO.getLat(), placeItemDTO.getLng());
    }

    /**
     * Render layout
     */
    private void renderLayout() {
        clearAllMarkerOnMap();
        ImageMarkerMapView imageMarkerMapView = new ImageMarkerMapView(mActivity, placeItemDTO);
        addMarker(imageMarkerMapView, lat, lng);
//        drawFlagMarkerPosition(lat,lng);
    }

    @Override
    public boolean onSingleTap(LatLng latlng, Point point) {
        lat = latlng.latitude;
        lng = latlng.longitude;
        if (mActivity instanceof LocationDetailReportActivity) {
            ((LocationDetailReportActivity) mActivity).updateLocation(latlng.latitude, latlng.longitude);
        }
        renderLayout();
        return super.onSingleTap(latlng, point);
    }

    /**
     * Update location
     * @param placeItemDTO
     */
    public void refreshLocation(PlaceItemDTO placeItemDTO){
        this.placeItemDTO = placeItemDTO;
        renderLayout();
    }

}
