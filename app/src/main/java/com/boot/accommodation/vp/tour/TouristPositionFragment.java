package com.boot.accommodation.vp.tour;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.boot.accommodation.R;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.TouristDTO;
import com.boot.accommodation.listener.OnEventMapControlListener;
import com.boot.accommodation.map.CustomFragmentMapView;
import com.boot.accommodation.map.TouristMarkerMapView;

import java.util.ArrayList;
import java.util.List;

/**
 * Class vi tri tourist
 *
 * @author tuanlt
 * @since 2:45 PM 5/27/2016
 */
public class TouristPositionFragment extends CustomFragmentMapView implements OnEventMapControlListener {

    public static final int ACTION_CLICK_TOURIST = 1000; // click tourist
    ArrayList<LatLng> lstLatLng = new ArrayList<>();
    List<TouristDTO> lstTouristInfo = new ArrayList<>(); // ds nhan vien
    public static TouristPositionFragment newInstance( Bundle bundle ) {
        TouristPositionFragment f = new TouristPositionFragment();
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
        if(bundle!= null){
            lstTouristInfo = bundle.getParcelableArrayList(Constants.IntentExtra.INTENT_DATA);
        }
    }

    /**
     * render layout
     */
    private void renderLayout(){
        drawPositionInMap();
    }

    @Override
    public void onMapReady() {
        super.onMapReady();
        renderLayout();

    }

    @Override
    public void onMapLoaded() {
        fitBounds(lstLatLng);
    }

    /**
     * Ve tren ban do
     */
    private void drawPositionInMap(){
        for(int i = 0, size = lstTouristInfo.size(); i < size; i++){
            TouristDTO item = lstTouristInfo.get(i);
            if(item.getLocation() != null) {
                TouristMarkerMapView textMarkerMapView = new TouristMarkerMapView(mActivity, lstTouristInfo.get(i));
                Marker marker = addMarker(textMarkerMapView, item.getLocation().getLat(), item.getLocation().getLng());
                setMarkerOnClickListener(marker, textMarkerMapView, this, ACTION_CLICK_TOURIST, item);
                if(item.getLocation().getLat() != Constants.LAT_LNG_NULL && item.getLocation().getLng() != Constants
                    .LAT_LNG_NULL) {
                    lstLatLng.add(new LatLng(item.getLocation().getLat(), item.getLocation().getLng()));
                }
            }
        }
    }

    @Override
    public View onEventMap( int eventType, View control, Object data ) {
        switch (eventType){
            case ACTION_CLICK_TOURIST:
                break;
        }
        return null;
    }
}
