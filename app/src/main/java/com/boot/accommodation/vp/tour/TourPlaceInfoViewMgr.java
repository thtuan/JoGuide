package com.boot.accommodation.vp.tour;

import com.boot.accommodation.dto.view.TourPlaceViewDTO;

/**
 * Tour place Mgr
 *
 * @author tuanlt
 * @since 6:02 PM 5/26/2016
 */
public interface TourPlaceInfoViewMgr {

    void setTimeTour( StringBuilder time );
    /**
     * render layout
     * @param tourPlaceViewDTO
     */
    void renderLayout( TourPlaceViewDTO tourPlaceViewDTO);

    /**
     * show error
     * @param errorCode
     * @param error
     */
    void showMessageErr(int errorCode, String error);
}
