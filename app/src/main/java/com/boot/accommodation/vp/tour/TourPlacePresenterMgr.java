package com.boot.accommodation.vp.tour;

import com.boot.accommodation.dto.view.TourItineraryItemDTO;

/**
 * Mgr presenter
 *
 * @author tuanlt
 * @since 6:03 PM 5/26/2016
 */
public interface TourPlacePresenterMgr {
    void getTourPlaceInfo(String tourId, String placeId);
    void getTimeTour( TourItineraryItemDTO tourItineraryItemDTO);

}
