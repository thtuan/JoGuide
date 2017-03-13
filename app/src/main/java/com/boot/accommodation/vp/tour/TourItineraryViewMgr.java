package com.boot.accommodation.vp.tour;

import com.boot.accommodation.dto.view.TourItineraryItemDTO;

import java.util.ArrayList;

/**
 * Mo ta class
 *
 * @author Vuong-bv
 * @since: 5/19/2016
 */
public interface TourItineraryViewMgr {

    /**
     * render layout
     * @param tourItinerary
     */
    void renderLayout(ArrayList<TourItineraryItemDTO> tourItinerary);
    /**
     * show error
     * @param errorCode
     * @param error
     */
    void showMessageErr(int errorCode, String error);
}
