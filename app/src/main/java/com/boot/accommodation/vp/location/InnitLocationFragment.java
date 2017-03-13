package com.boot.accommodation.vp.location;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.Marker;
import com.boot.accommodation.R;
import com.boot.accommodation.constant.Constants;
import com.boot.accommodation.dto.view.PlaceItemDTO;
import com.boot.accommodation.map.CustomFragmentMapView;
import com.boot.accommodation.map.ImageMarkerMapView;

/**
 * ChooseLocationFragment
 *
 * @author thtuan
 * @since 2:57 PM 08-08-2016
 */
public class InnitLocationFragment extends CustomFragmentMapView {

    private PlaceItemDTO placeItemDTO;//Place item dto

    public static InnitLocationFragment newInstance() {
        return new InnitLocationFragment();
    }

    public static InnitLocationFragment newInstance(Bundle data) {
        InnitLocationFragment t = new InnitLocationFragment();
        t.setArguments(data);
        return t;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            placeItemDTO = args.getParcelable(Constants.IntentExtra.PLACE_ITEM);
        }
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_custom_map;
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
        ImageMarkerMapView imageMarkerMapView = new ImageMarkerMapView(mActivity, placeItemDTO);
        Marker marker = addMarker(imageMarkerMapView, placeItemDTO.getLat(), placeItemDTO.getLng());
        moveTo(placeItemDTO.getLat(), placeItemDTO.getLng());
    }

}
