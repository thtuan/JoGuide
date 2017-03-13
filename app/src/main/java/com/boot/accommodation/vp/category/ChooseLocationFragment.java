package com.boot.accommodation.vp.category;

import android.graphics.Point;
import android.location.Address;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.GPSInfoDTO;
import com.boot.accommodation.listener.OnAddressListener;
import com.boot.accommodation.map.CustomFragmentMapView;

/**
 * ChooseLocationFragment
 *
 * @author thtuan
 * @since 2:57 PM 08-08-2016
 */
public class ChooseLocationFragment extends CustomFragmentMapView implements OnAddressListener {
    private LatLng latLng;
    private boolean isRenderFlagMarker = false; //Variable check flag render or not

    public static ChooseLocationFragment newInstance(Bundle bundle){
        ChooseLocationFragment f = new ChooseLocationFragment();
        f.setArguments(bundle);
        return f;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_custom_map;
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState ) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            latLng = new LatLng(bundle.getDouble(PickLocationActivity.KEY_LAT, Constants.LAT_LNG_NULL),
                    bundle.getDouble(PickLocationActivity.KEY_LONG, Constants.LAT_LNG_NULL));
        }
        validateMyLocation();

    }

    @Override
    public void onMapReady() {
        super.onMapReady();
        renderLayout();
    }

    /**
     * Render layout
     */
    private void renderLayout() {
        boolean isHaveMyPosition = false;
        GPSInfoDTO myGPSInfo = JoCoApplication.getInstance().getProfile().getMyGPSInfo();
        if (myGPSInfo.getLatitude() != Constants.LAT_LNG_NULL && myGPSInfo.getLongtitude() != Constants.LAT_LNG_NULL) {
            drawMarkerMyPosition(myGPSInfo.getLatitude(), myGPSInfo.getLongtitude());
            isHaveMyPosition = true;
        }
        if (latLng == null || (latLng.latitude == Constants.LAT_LNG_NULL && latLng.longitude == Constants.LAT_LNG_NULL)) {
            if (isHaveMyPosition) {
                drawLocationMap(new LatLng(myGPSInfo.getLatitude(), myGPSInfo.getLongtitude()));
            }
        } else {
            drawLocationMap(new LatLng(latLng.latitude, latLng.longitude));
        }
    }

    @Override
    public boolean onSingleTap(LatLng latlng, Point point) {
        if (mActivity instanceof PickLocationActivity){
            ((PickLocationActivity) mActivity).lat = latlng.latitude;
            ((PickLocationActivity) mActivity).lng = latlng.longitude;
        }
//        getAdressFromLatLng(this, latlng);
//        drawFlagMarkerPosition(latlng.latitude,latlng.longitude);
        drawLocationMap(latlng);
        if (mActivity instanceof CreateLocationActivity){
            ((CreateLocationActivity)mActivity).updateLocation(latlng);
        }
        return super.onSingleTap(latlng, point);
    }

    /**
     * Draw location map
     * @param latLng
     */
    public void drawLocationMap(LatLng latLng){
        getAdressFromLatLng(this, latLng);
        drawFlagMarkerPosition(latLng.latitude,latLng.longitude);
        moveTo(latLng);
        isRenderFlagMarker = true;
    }


    @Override
    public void showProgressLoading(boolean isShow) {
        if (isShow){
            showProgress();
        }else {
            closeProgress();
        }
    }

    @Override
    public void setAddress(Address address, String result) {
        if (mActivity instanceof CreateLocationActivity) {
            ((CreateLocationActivity) mActivity).renderAddress(address, result);
        }
    }

    @Override
    public void onClickMarkerMyPositionEvent() {
        super.onClickMarkerMyPositionEvent();
        if(!isRenderFlagMarker){
            GPSInfoDTO myGPSInfo = JoCoApplication.getInstance().getProfile().getMyGPSInfo();
            if (myGPSInfo.getLatitude() != Constants.LAT_LNG_NULL && myGPSInfo.getLongtitude() != Constants.LAT_LNG_NULL) {
                drawLocationMap(new LatLng(myGPSInfo.getLatitude(), myGPSInfo.getLongtitude()));
            }
        }
    }
}
