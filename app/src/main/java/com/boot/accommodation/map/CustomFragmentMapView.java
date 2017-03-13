package com.boot.accommodation.map;

import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.boot.accommodation.JoCoApplication;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseFragment;
import com.boot.accommodation.base.BaseFragmentMapView;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.GPSInfoDTO;
import com.boot.accommodation.listener.OnAddressListener;
import com.boot.accommodation.listener.OnEventMapControlListener;
import com.boot.accommodation.util.StringUtil;
import com.boot.accommodation.vp.position.PositionManager;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Lop base ban do tren fragment
 *
 * @version: 1.0
 * @since: 1.0
 */
public class CustomFragmentMapView extends BaseFragment implements BaseFragmentMapView.MapEventListener {
    public LinearLayout layoutZoomControl;
    public RelativeLayout rlMainMapView;
    public static final int ZOOM_LEVEL_VTMAP_DEFAULT = 4;
    public static final int ZOOM_LEVEL_GGMAP_DEFAULT = 5;
    public static final int ZOOM_LEVEL_VTMAP_NORMAL = 16;
    public static final int ZOOM_LEVEL_GOOGLE_NORMAL = 17;
    // hien thi button dinh vi vi tri cua toi
    protected boolean isViewMyPositionControl = true;
    public BaseFragmentMapView mapHelper;
    InfoWindowAdapter adapter;
    private boolean isFirst = false; //First load

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        adapter = new InfoWindowAdapter() {

            @Override
            public View getInfoWindow( Marker marker ) {
                return null;
            }

            @Override
            public View getInfoContents( Marker marker ) {
                return null;
            }
        };
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_custom_map;
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        mapHelper = new GoogleFragmentMapView(mActivity, adapter);
        rlMainMapView = (RelativeLayout) mapHelper.initMap(savedInstanceState);
        mapHelper.setMapEventListener(this);
        ButterKnife.bind(this, rlMainMapView);
        isFirst = false;
        return rlMainMapView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(!isFirst){
            isFirst = true;
            mapHelper.renderMap();
        }
    }


    /**
     * Di chuyen ban do toi vi tri dang dung
     */
    protected void moveToCenterMyPosition() {
        GPSInfoDTO gps = JoCoApplication.getInstance().getProfile().getMyGPSInfo();
        if (gps != null && gps.getLatitude() != Constants.LAT_LNG_NULL && gps.getLongtitude() != Constants
            .LAT_LNG_NULL) {
            mapHelper.moveMapTo(gps.getLatitude(), gps.getLongtitude());
        }
    }

    protected void moveTo( LatLng latlng ) {
        if (latlng != null && latlng.latitude != Constants.LAT_LNG_NULL && latlng.longitude != Constants.LAT_LNG_NULL) {
            mapHelper.moveMapTo(latlng.latitude, latlng.longitude);
        }
    }

    /**
     * Xu ly fit overlay
     *
     * @return: void
     * @throws: Ngoai le do ham dua ra (neu co)
     */
    public void fitBounds( ArrayList<LatLng> arrayPosition ) {
        mapHelper.fitBounds(arrayPosition);
    }

    /**
     * Xu ly khi click len button vi tri cua toi
     *
     * @return: void
     * @throws: Ngoai le do ham dua ra (neu co)
     */
    public void onClickMarkerMyPositionEvent() {
        drawMarkerMyPosition();
        moveToCenterMyPosition();
    }

    @Override
    public void onMapReady() {
    }

    @Override
    public void onRefresh() {
        mActivity.sendBroadcast(Constants.ActionEvent.NOTIFY_REFRESH, new Bundle());
    }

    @Override
    public void onMapLoaded() {

    }

    @Override
    public boolean onSingleTap( LatLng latlng, Point point ) {
//		mapHelper.onSingleTap(latlng, point);
        return false;
    }

    @Override
    public boolean onLongTap( LatLng latlng, Point point ) {
        return false;
    }

    @Override
    public boolean onDoubleTap( LatLng latlng, Point point ) {
        return false;
    }

    public void OnMapLoaded() {
        mapHelper.onMapLoaded();
    }

    /**
     * Clear all marker tren map
     * @return: void
     * @throws:
     */
    public void clearAllMarkerOnMap() {
        mapHelper.clearAllMarkerOnMap();
    }

    public void setMarkerOnClickListener( Object Marker, View markerView, OnEventMapControlListener listener, int action, Object userData ) {
        mapHelper.setMarkerOnClickListener(Marker, markerView, listener, action, userData);
    }

    public void setOnInfoWindowClickListener( OnEventMapControlListener listener, int action ) {
        mapHelper.setOnInfoWindowClickListener(listener, action);
    }

    public void setZoomBorder() {
        mapHelper.setZoomBorder();
    }

    /**
     * @author: cuonglt3
     * @since: 11:15:10 AM Aug 8, 2014
     * @return: void
     * @throws:
     */
    public void clearPopup() {
        // TODO Auto-generated method stub
        mapHelper.clearPopup();

    }

    public void getAdressFromLatLng( OnAddressListener listener, LatLng latLng ) {
        mapHelper.getAdressFromLatLng(listener, latLng);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapHelper.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapHelper.onPause();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mapHelper.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapHelper.onLowMemory();
    }

    public void moveTo( double lat, double lng ) {
        moveTo(new LatLng(lat, lng));
    }


    protected void drawMarkerMyPosition() {
        GPSInfoDTO gps = JoCoApplication.getInstance().getProfile().getMyGPSInfo();
        if (gps != null && gps.getLatitude() != Constants.LAT_LNG_NULL && gps.getLongtitude() != Constants.LAT_LNG_NULL) {
            drawMarkerMyPosition(gps.getLatitude(), gps.getLongtitude());
        }
    }

    /**
     * Ve vi tri hien tai
     * @param lat
     * @param lng
     */
    protected void drawMarkerMyPosition( double lat, double lng ) {
        mapHelper.drawMarkerMyPosition(lat, lng);
    }

    /**
     * Ve vi tri hien tai
     * @param lat
     * @param lng
     */
    protected void drawFlagMarkerPosition( double lat, double lng ) {
        mapHelper.drawFlagMarkerPosition(lat, lng);
    }

    /**
     * Add marker to map
     * @param view
     */
    public Marker addMarker( MarkerView view, double lat, double lng ) {
        return mapHelper.addMarkerToMap(view, lat, lng);
    }

    public void setInfoWindowAdapter( InfoWindowAdapter adapter ) {
        if (mapHelper != null) {
            mapHelper.setInfoWindowAdapter(adapter);
        }
    }

    /**
     * Add duong di tu lat1,lng1 toi lat2,lng2
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     */
    public void addDirectionMap( double lat1, double lng1, double lat2, double lng2, int colorStroke ) {
        if (mapHelper != null) {
            mapHelper.addDirectionMap(lat1, lng1, lat2, lng2, colorStroke);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * Validate my position
     */
    protected void validateMyLocation() {
        if (!PositionManager.getInstance().isEnableGPS()) {
            PositionManager.getInstance().showAlertEnableGPS(rlMainMapView, false, StringUtil.getString(R.string
                .notify_setting_gps));
        } else if (JoCoApplication.getInstance().getProfile().getMyGPSInfo().getLatitude() == Constants.LAT_LNG_NULL
            || JoCoApplication.getInstance().getProfile().getMyGPSInfo().getLongtitude() == Constants.LAT_LNG_NULL) {
            PositionManager.getInstance().showAlertEnableGPS(rlMainMapView, true, StringUtil.getString(R.string
                .text_not_found_position));
        }
    }

    /**
     * Add marker
     * @param lat
     * @param lng
     * @param resource
     * @return
     */
    protected Marker addMarker(double lat, double lng, int resource) {
        return mapHelper.addMarker(lat,lng, resource);
    }

}
