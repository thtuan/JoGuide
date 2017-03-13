package com.boot.accommodation.vp.placepicker;

import com.boot.accommodation.dto.response.PlacePickerItemDTO;

import java.util.List;

/**
 * Place picker view mgr
 *
 * @author tuanlt
 * @since 9:49 SA 10/09/2016
 */
public interface PlacePickerViewMgr {
    /**
     * Save place
     * @param lstPlace
     */
    void savePlace( List<PlacePickerItemDTO> lstPlace, long numType );

    /**
     * Get list category
     */
    List<Long> getListCategory(String placeId);

    /**
     * Save place scanned
     * @param placeId
     */
    void savePlaceScanned(String placeId);

    /**
     * Check place scanned
     * @param placeId
     * @return
     */
    boolean checkPlaceScanned(String placeId);


}
