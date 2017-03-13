package com.boot.accommodation.vp.placepicker;

import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.boot.accommodation.R;
import com.boot.accommodation.dto.response.PlacePickerItemDTO;
import com.boot.accommodation.dto.view.LocationDTO;
import com.boot.accommodation.map.CustomFragmentMapView;

import java.util.ArrayList;

/**
 * Fragment ban do
 *
 * @author tuanlt
 * @since 2:46 PM 6/10/2016
 */
public class PlacePickerFragment extends CustomFragmentMapView {

    public static PlacePickerFragment newInstance( Bundle bundle ) {
        PlacePickerFragment f = new PlacePickerFragment();
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
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState ) {
        super.onViewCreated(view, savedInstanceState);
        ((PlacePickerActivity)mActivity).getAllPlacesInReference();
    }

    @Override
    public void onMapReady() {
        super.onMapReady();
    }

    @Override
    public boolean onSingleTap(LatLng latlng, Point point) {
        if (mActivity instanceof PlacePickerActivity){
            drawFlagMarkerPosition(latlng.latitude,latlng.longitude);
            ((PlacePickerActivity)mActivity).getLocation(latlng);
        }
        return super.onSingleTap(latlng, point);
    }

    /**
     * Render layout
     */
    public void renderLayout(final ArrayList<PlacePickerItemDTO> lstPlaces) {
        AddMarker task = new AddMarker(lstPlaces);
        task.execute();
//        clearAllMarkerOnMap();
//        for (int i = 0, size = lstPlaces.size(); i < size; i++) {
//            LocationDTO locationDTO = lstPlaces.get(i).getGeometry().getLocation();
//            addMarker(locationDTO.getLat(),locationDTO.getLng(), R.drawable.ic_place_position);
//        }
    }

    /**
     * Delete file
     */
    private class AddMarker extends AsyncTask<String, Void, Exception> {
        public ArrayList<PlacePickerItemDTO> lstPlaces;
        public AddMarker(ArrayList<PlacePickerItemDTO> lstPlaces){
            this.lstPlaces = lstPlaces;
        }
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Exception doInBackground(String... params) {
            try {
                // Delete file if date create > numday
            } catch (Exception e) {
                return e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Exception result) {
            for (int i = 0, size = lstPlaces.size(); i < size; i++) {
                LocationDTO locationDTO = lstPlaces.get(i).getGeometry().getLocation();
                addMarker(locationDTO.getLat(), locationDTO.getLng(), R.drawable.ic_location_postion);
            }
        }
    }
}
