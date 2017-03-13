package com.boot.accommodation.vp.placepicker;

import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;
import com.boot.accommodation.R;
import com.boot.accommodation.base.BaseActivity;
import com.boot.accommodation.dto.response.PlacePickerItemDTO;
import com.boot.accommodation.util.PreferenceUtils;
import com.boot.accommodation.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * Activity duong di
 *
 * @author tuanlt
 * @since 3:11 PM 6/10/2016
 */
public class PlacePickerActivity extends BaseActivity implements PlacePickerViewMgr {

    private PlacePickerPresenter placePickerPresenter; // Place picker presenter
    private HashMap<String, List<Long>> hmPlacesWithCategory; // Place key with category
    private HashSet<String> hPlacesScanned; // Place scanned
    private HashMap<String, PlacePickerItemDTO> hmPlaces;
    public static final String REFERENCE_PLACE_WITH_CATEGORY = "JoCoSharePreferencesPlaceCategory";
    public static final String REFERENCE_PLACE_SCANNED = "JoCoSharePreferencesPlaceScanned";
    public static final String REFERENCE_PLACE = "JoCoSharePreferencesPlace";
    PlacePickerFragment placePickerFragment; //Place picker fragment

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        enableSlidingMenu(false);
        setContentView(R.layout.activity_tour_direction);
        ButterKnife.bind(this);
//        switchFragment(PlacePickerFragment.newInstance(new Bundle()), R.id.frDetail, false);
//        initData();
    }

    /**
     * Init data
     */
    private void initData() {
        placePickerPresenter = new PlacePickerPresenter(this);
        hmPlacesWithCategory = (HashMap<String, List<Long>>) PreferenceUtils.readObject(REFERENCE_PLACE_WITH_CATEGORY);
        hmPlaces = (HashMap<String, PlacePickerItemDTO>) PreferenceUtils.readObject(REFERENCE_PLACE);
        hPlacesScanned = (HashSet<String>) PreferenceUtils.readObject(REFERENCE_PLACE_SCANNED);
    }

    /**
     * Save place
     *
     * @param lstPlace
     */
    public void savePlace( List<PlacePickerItemDTO> lstPlace, long numType ) {
//        hmPlacesWithCategory = (HashMap<String, List<Long>>) PreferenceUtils.readObject(REFERENCE_PLACE_WITH_CATEGORY);
//        hmPlaces = (HashMap<String, PlacePickerItemDTO>) PreferenceUtils.readObject(REFERENCE_PLACE);
        if(hmPlacesWithCategory == null){
            hmPlacesWithCategory = new HashMap<>();
        }
        if(hmPlaces == null){
            hmPlaces = new HashMap<>();
        }
        for (PlacePickerItemDTO item : lstPlace) {
            List<Long> lstType = null;
            if (hmPlacesWithCategory.containsKey(item.getPlace_id())) {
                lstType = hmPlacesWithCategory.get(item.getPlace_id());
                boolean isExisted = false;
                for (Long typeItem : lstType) {
                    if (numType == typeItem.intValue()) {
                        isExisted = true;
                        break;
                    }
                }
                if (!isExisted) {
                    lstType.add(numType);
                }
            }
            if(lstType == null){
                lstType = new ArrayList<>();
                lstType.add(numType);
            }
            //Add places
            hmPlaces.put(item.getPlace_id(), item);
            hmPlacesWithCategory.put(item.getPlace_id(), lstType);
        }
//        PreferenceUtils.saveObject(hmPlacesWithCategory, REFERENCE_PLACE_WITH_CATEGORY);
//        PreferenceUtils.saveObject(hmPlaces, REFERENCE_PLACE);
//        getAllPlacesInReference();
    }

    /**
     * Get all place in reference
     */
    public void getAllPlacesInReference(){
        if(hmPlaces != null) {
            ArrayList<PlacePickerItemDTO> arList = new ArrayList<PlacePickerItemDTO>();
            for (Map.Entry<String, PlacePickerItemDTO> map : hmPlaces.entrySet()) {
                arList.add(map.getValue());
            }
            if (placePickerFragment == null) {
                placePickerFragment = (PlacePickerFragment) getSupportFragmentManager().findFragmentByTag
                        (Utils.getTag(PlacePickerFragment.class));
            }
            placePickerFragment.renderLayout(arList);
        }
    }

    @Override
    public List<Long> getListCategory(String placeId) {
        return hmPlacesWithCategory.get(placeId);
    }

    /**
     *
     * @param placeId
     * @return
     */
    public boolean checkPlaceScanned(String placeId){
//        hPlacesScanned = (HashSet<String>) PreferenceUtils.readObject(REFERENCE_PLACE_SCANNED);
        return hPlacesScanned!= null && hPlacesScanned.contains(placeId);
    }

    /**
     * Save place scanned
     * @param placeId
     * @return
     */
    public void savePlaceScanned(String placeId){
//        hPlacesScanned = (HashSet<String>) PreferenceUtils.readObject(REFERENCE_PLACE_SCANNED);
        if(hPlacesScanned == null){
            hPlacesScanned = new HashSet<>();
        }
        hPlacesScanned.add(placeId);
//        PreferenceUtils.saveObject(hPlacesScanned, REFERENCE_PLACE_SCANNED);
    }

    /**
     * Get location
     * @param latLng
     */
    public void getLocation(LatLng latLng){
        placePickerPresenter.getListPlacesAll(latLng.latitude, latLng.longitude);
    }

    @Override
    protected void onDestroy() {
        PreferenceUtils.saveObject(hPlacesScanned, REFERENCE_PLACE_SCANNED);
        PreferenceUtils.saveObject(hmPlacesWithCategory, REFERENCE_PLACE_WITH_CATEGORY);
        PreferenceUtils.saveObject(hmPlaces, REFERENCE_PLACE);
        super.onDestroy();
    }

}
