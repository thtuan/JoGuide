package com.boot.accommodation.vp.profile;

import com.boot.accommodation.dto.view.PlaceCollectionDTO;
import com.boot.accommodation.dto.view.TourCollectionDTO;

/**
 * Mo ta class
 *
 * @author tuanlt
 * @since 2:59 PM 7/11/2016
 */
public interface ProfileCollectionViewMgr {
    /**
     * Render layout check in
     *
     * @param lstTripItem
     */
    void renderFavouriteTours(TourCollectionDTO lstTripItem );

    /**
     * Render layout favourite
     *
     * @param lstPlaceItem
     */
    void renderFavouritePlaces(PlaceCollectionDTO lstPlaceItem );

    /**
     * show error
     * @param errorCode
     * @param error
     */
    void showMessageErr(int errorCode, String error);

    /**
     * Render layout favourite
     *
     * @param lstTripItem
     */
    void renderToursCreated(TourCollectionDTO lstTripItem);

    /**
     * Render layout favourite
     *
     * @param lstTripItem
     */
    void renderPlacesCreated(PlaceCollectionDTO lstPlaceItem);
}
