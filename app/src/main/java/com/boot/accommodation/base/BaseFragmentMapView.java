package com.boot.accommodation.base;

import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.boot.accommodation.listener.OnAddressListener;
import com.boot.accommodation.listener.OnEventMapControlListener;
import com.boot.accommodation.map.MarkerView;

import java.util.ArrayList;

/**
 * Mo ta class
 *
 * @author tuanlt
 * @since 2:09 PM 5/23/2016
 */
public abstract class BaseFragmentMapView {

    public BaseActivity parent;
    protected MapEventListener listener;
    protected RelativeLayout rlMainMapView;
    protected boolean isViewMyPositionControl = true;
    protected Object marker = null;

    public enum MarkerPosition{
        TOP_LEFT,
    }

    public interface MapEventListener {
        public boolean onSingleTap( LatLng latlng, Point point);
        public boolean onLongTap(LatLng latlng, Point point);
        public boolean onDoubleTap(LatLng latlng, Point point);
        public void onClickMarkerMyPositionEvent();
        public void onMapReady();
        public void onRefresh();
        public void onMapLoaded();
    }

    public void setMapEventListener(MapEventListener listener){
        this.listener = listener;
    }

    public BaseFragmentMapView(BaseActivity parent){
        this.parent = parent;
    }

    public abstract ViewGroup initMap( Bundle savedInstanceState);
    public abstract void initZoomControl();
    public abstract void setZoomBorder();
    public abstract void initMyPositionControl();
    public abstract void moveToCenterMyPosition();
    public abstract void moveMapTo(double lat, double lng);
    public abstract void fitBounds(ArrayList<LatLng> arrayPosition);
    public abstract void clearAllCurrentMarker();
    public abstract void showMarkerInMap( double lat, double lng, View view, MarkerPosition position);
    public abstract String requestGetCusLocation(final double lat, final double lng);
    public abstract void invalidate();
//    public abstract Object addMarker(double lat, double lng, int drawable, MarkerAnchor markerArchor, String seq, String numVisitFact, int colorSeq, int colorNumVisit);
    public abstract void onMapLoaded();
    public abstract void clearAllMarkerOnMap();
    public abstract void setMarkerOnClickListener( Object Marker, View markerView, OnEventMapControlListener listener, int action, Object userData);
    public abstract Object getMapView();
    public abstract void clearPopup();
    public abstract void getAdressFromLatLng( OnAddressListener listener, LatLng latlng);

    public abstract void onResume();
    public abstract void onPause();
    public abstract void onDestroy();
    public abstract void onLowMemory();
    public static enum MarkerAnchor{
        BOTTOM_CENTER,
        CENTER,
        BOTTOM_LEFT;
    }
    public abstract void drawMarkerMyPosition(double lat, double lng);
    public abstract void drawFlagMarkerPosition(double lat, double lng);
    public abstract Marker addMarkerToMap( MarkerView view, double lat, double lng);
    public abstract void initRefreshControl();
    public abstract void setInfoWindowAdapter(GoogleMap.InfoWindowAdapter adapter);
    public abstract void setOnInfoWindowClickListener(OnEventMapControlListener listener, int action);

    /**
     * Add duong di tren google map
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     */
    public abstract void addDirectionMap(double lat1, double lng1, double lat2, double lng2, int colorStroke);

    /**
     * Render map
     */
    public abstract void renderMap();
    public abstract Marker addMarker(double lat, double lng, int resource);

    /**
     * Padding control
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public abstract void paddingControl(int left, int top, int right, int bottom);
}
